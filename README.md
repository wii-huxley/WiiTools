# WiiTools

一个 Android 的工具库

## 用法

* Android Studio

    ```groovy
    compile 'com.huxley:wiitools:2.0.3
    ```

* Eclipse
	
	下载最新 aar:[wiitools-2.0.3.aar](https://dl.bintray.com/huangweiyi/maven/com/huxley/wiitools/2.0.3/wiitools-2.0.3.aar)
	
## 配置

在 application 中进行 WiiTools 的配置

```java
WiiTools.init(this);
```

#### WiiToast 的使用

* 显示普通信息的 Toast

    ```java
    WiiToast.show("普通信息");
    ```
    
* 显示提示信息的 Toast

    ```java
    WiiToast.info("提示信息");
    ```
        
* 显示成功信息的 Toast

    ```java
    WiiToast.success("成功信息");
    ```
            
* 显示警告信息的 Toast

    ```java
    WiiToast.warn("警告信息");
    ```
            
* 显示错误信息的 Toast

    ```java
    WiiToast.error("错误信息");
    ```
    
#### WiiCrash 的使用

* 使用新浪邮箱

    ```java
    WiiTools.init(this)
            .initSinaEmailCrash("emailName", "emailPassword");
    ```

#### HandlerBus 的使用

* 注册一个消息处理者

    ```java
    HandlerBus.getInstance().register(MainActivity.class, new IHandler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            mBtnTestHandlerBus.setText(MessageFormat.format("{0}{1}", msg.obj, msg.what));
                            break;
                        case 2:
                            mBtnTestHandlerBus.setText(MessageFormat.format("{0}{1}", msg.obj, msg.what));
                            break;
                    }
                }
            });
    ```
    
* 发送一条消息

    ```java
    HandlerBus.getInstance().post(MainActivity.class, 1, "HandlerBus 成功了");
    ```
    
#### WiiLog 的使用

```java
WiiTools.init(this)
    .initLog(true, "WiiLog");
WiiLog.i("");
```