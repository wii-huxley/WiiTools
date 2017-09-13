package com.huxley.wiitools.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * Created by huxley on 2017/8/17.
 */

public class GsonUtils {

    private static Gson mGson;

    public static Gson get() {
        if (mGson == null) {
            synchronized (GsonUtils.class) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
        return mGson;
    }

    public static String toJson(Object obj) {
        return get().toJson(obj);
    }

    public static <D> D fromJson(String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        return get().fromJson(jsonString, new TypeToken<D>() {
        }.getType());
    }

    public static <D> D fromJson(JsonElement jsonString, Type typeToken) {
        if (jsonString == null || StringUtils.isEmpty(jsonString.toString())) {
            return null;
        }
        return get().fromJson(jsonString, typeToken);
    }

    public static <D> D fromJson(String jsonString, Type typeToken) {
        if (jsonString == null || StringUtils.isEmpty(jsonString)) {
            return null;
        }
        return get().fromJson(jsonString, typeToken);
    }

    public static <D> D fromJson(String jsonString, Class<D> dClass) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        return get().fromJson(jsonString, dClass);
    }

    public static Gson getNiceGson(final String... names) {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        for (String name : names) {
                            if (name.equals(f.getName())) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    public static Gson getBadGson(final String... names) {
        return new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        for (String name : names) {
                            if (name.equals(f.getName())) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }
}
