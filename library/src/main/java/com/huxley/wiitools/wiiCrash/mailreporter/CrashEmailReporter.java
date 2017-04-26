/*
 * @(#)CrashEmailReport.java		       Project: CrashHandler
 * Date: 2014-5-27
 *
 * Copyright (c) 2014 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huxley.wiitools.wiiCrash.mailreporter;

import com.huxley.wiitools.wiiCrash.AbstractCrashHandler;

import java.io.File;


/**
 * 已经实现的日志报告类，这里通过邮件方式发送日志报告
 */
public class CrashEmailReporter extends AbstractCrashHandler {
    private String mReceiveEmail;
    private String mSendEmail;
    private String mSendPassword;
    private String mHost;
    private String mPort;

    /**
     * 设置接收者
     */
    public CrashEmailReporter setReceiver(String receiveEmail) {
        mReceiveEmail = receiveEmail;
        return this;
    }

    /**
     * 设置发送者邮箱
     */
    public CrashEmailReporter setSender(String email) {
        mSendEmail = email;
        return this;
    }

    /**
     * 设置发送者密码
     */
    public CrashEmailReporter setSendPassword(String password) {
        mSendPassword = password;
        return this;
    }

    /**
     * 设置 SMTP 主机
     */
    public CrashEmailReporter setSMTPHost(String host) {
        mHost = host;
        return this;
    }

    /**
     * 设置端口
     */
    public CrashEmailReporter setPort(String port) {
        mPort = port;
        return this;
    }

    @Override
    protected void sendReport(String title, String body, File file) {
        LogMail sender = new LogMail().setUser(mSendEmail).setPass(mSendPassword)
                .setFrom(mSendEmail).setTo(mReceiveEmail).setHost(mHost).setPort(mPort)
                .setSubject(title).setBody(body);
        sender.init();
        try {
            sender.addAttachment(file.getPath(), file.getName());
            sender.send();
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
