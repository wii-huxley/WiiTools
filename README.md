# WiiTools

一个 Android 的工具库

## 用法

* Android Studio

    ```groovy
    compile 'com.huxley:wiitools:2.8.0
    ```

* Eclipse
	
	下载最新 aar:[wiitools-2.8.0.aar](https://dl.bintray.com/huangweiyi/maven/com/huxley/wiitools/2.8.0/wiitools-2.8.0.aar)
	
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

#### ReflectUtil的使用

* ReflectUtil.on 包裹一个类或者对象，表示在这个类或对象上进行反射，类的值可以使Class,也可以是完整的类名（包含包名信息）
* ReflectUtil.create 用来调用之前的类的构造方法，有两种重载，一种有参数，一种无参数
* ReflectUtil.call 方法调用，传入方法名和参数，如有返回值还需要调用get
* ReflectUtil.get 获取（field和method返回）值相关，会进行类型转换，常与call和field组合使用
* ReflectUtil.field 获取属性值相关，需要调用get获取该值
* ReflectUtil.set 设置属性相关。

#### DialogFragmentHelper 的使用

* showTimeDialog：选择时间的弹出窗
* showPasswordInsertDialog：输入密码的弹出窗
* showListDialog：显示列表的弹出窗
* showIntervalInsertDialog：两个输入框的弹出窗
* showInsertDialog：一个输入框的弹出窗
* showDateDialog：选择日期的弹出窗
* showConfirmDialog：确认和取消的弹出窗
* showTips：简单提示弹出窗
* showProgress：加载中的弹出窗
* showCustomDialog：自定义提示弹出窗
