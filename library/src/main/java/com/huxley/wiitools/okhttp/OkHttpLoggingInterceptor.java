package com.huxley.wiitools.okhttp;


import com.huxley.wiitools.utils.DataUtils;
import com.huxley.wiitools.utils.log.WiiLog;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by huxley on 2017/4/27.
 */
public class OkHttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private String tag;

    public OkHttpLoggingInterceptor(String tag) {
        this.tag = tag;
    }

    public OkHttpLoggingInterceptor() {
        this("log_okhttp");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        Headers requestHeaders = request.headers();
        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        WiiLog.d( MessageFormat.format("--> START {0} {1}", request.method(), protocol));
        if (requestHeaders != null) {
            for (int i = 0, count = requestHeaders.size(); i < count; i++) {
                String name = requestHeaders.name(i);
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    WiiLog.i(name + ": " + requestHeaders.value(i));
                }
            }
        }
        if (requestBody != null) {
            WiiLog.i(MessageFormat.format("Content-Type: {0}", requestBody.contentType()));
            if (bodyEncoded(requestHeaders)) {
                WiiLog.d(MessageFormat.format("--> END {0} (encoded body omitted)", request.method()));
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                MediaType contentType = requestBody.contentType();
                Charset charset = contentType == null ? UTF8 : contentType.charset(UTF8);
                if (isPlaintext(buffer)) {
                    WiiLog.i(MessageFormat.format("{0}&{1}", DataUtils.urlDecode(request.url().toString()), DataUtils.urlDecode(buffer.readString(charset))));
                    WiiLog.d(MessageFormat.format("--> END {0} ({1}-byte body)", request.method(), requestBody.contentLength()));
                } else {
                    WiiLog.d(MessageFormat.format("--> END {0} (binary {1}-byte body omitted)", request.method(), requestBody.contentLength()));
                }
            }
        } else {
            WiiLog.i(DataUtils.urlDecode(request.url().toString()));
            WiiLog.d("--> END " + request.method());
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            WiiLog.e("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        WiiLog.d(MessageFormat.format("<-- {0} {1} ({2}ms, {3} body)", response.code(), response.message(), tookMs, bodySize));
        WiiLog.i(response.headers().toString());
        if (!HttpHeaders.hasBody(response)) {
            WiiLog.d("<-- END HTTP");
        } else if (bodyEncoded(response.headers())) {
            WiiLog.d("<-- END HTTP (encoded body omitted)");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    WiiLog.i("Couldn't decode the response body; charset is likely malformed.");
                    WiiLog.d("<-- END HTTP");
                    return response;
                }
            }
            if (!isPlaintext(buffer)) {
                WiiLog.d("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                return response;
            }
            if (contentLength != 0) {
                WiiLog.i(buffer.clone().readString(charset));
            }
            WiiLog.d("<-- END HTTP (" + buffer.size() + "-byte body)");
        }

        return response;
    }


    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    /**
     * 是否是纯文本
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}
