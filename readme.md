# BeautifulSwitch

一个在 Android 平台上美观的开关控件

[![](https://img.shields.io/github/stars/gtf35/beautiful_switch?style=for-the-badge)]()  [![](https://img.shields.io/github/forks/gtf35/beautiful_switch?style=for-the-badge)]()  [![](https://img.shields.io/github/release/gtf35/beautiful_switch?style=for-the-badge)](https://github.com/gtf35/beautiful_switch/releases) 

## 0 效果图
![效果图](https://github.com/gtf35/beautiful_switch/blob/master/static/switch.gif)

## 1 设计图
![设计图](https://github.com/gtf35/beautiful_switch/blob/master/static/yuanpic.png)

## 2 特性
   - 美观，ui设计师 夜白 设计
   - 支持颜色自定义
   - 支持禁用/启用
   - 支持代码打开/关闭开关
   - 支持点击事件监听
   - 完全支持 wrap_content/match_parent/固定大小等尺寸
   - 支持 AndroidStudio 预览
   - 支持 API Level 18 (Android 4.3)
   - 小巧，仅仅一个类

## 3 使用
### 	3x1 依赖：在 app 级别的 build.gradle 添加

```Gradle
dependencies {
   ....
   implementation 'top.gtf35.lib.withyebai:BeautifulSwitch:1.0'
   ....
}
```

### 	3x2 引入：在 layout 布局文件中添加
```xml
<top.gtf35.withyebai.BeautifulSwitch
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
### 	3x3 监听：在调用实例的 setOnSwitchClickListener 监听开关的点击事件

```java
beautifulSwitch.setOnSwitchClickListener(new BeautifulSwitch.OnClickListener() {
    @Override
    public void onClick(View v, boolean isOpen) {
      	 //回调运行在主线程，可直接操作 UI
      	 statuesTv.setText("状态:" + (isOpen? "开": "关"));
   	}
});
```


## 4 混淆
没有使用任何反射等奇技淫巧，无需关注

## 5 高级使用

### 		5x0 demo 预览

![demo预览](https://github.com/gtf35/beautiful_switch/blob/master/static/demopic.png)

   	可以参考 demo ，在 demo 里有所有方法的演示
   	看 demo 运行理解，更高效

### 	5x1 默认开启的开关
   在xml使用 app:is_open="true" 属性可设置默认的状态
   true 为默认开。 false 为默认关
   需引入 app 的 xml 命名空间

```xml
<top.gtf35.withyebai.BeautifulSwitch
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:is_open="true"/>
```

###		5x2 自定颜色的开关

   在xml使用 app:outline_open_color="@android:color/holo_blue_bright" 属性可设置开关在开启时默认的外框线颜色
   在xml使用 app:outline_close_color="@android:color/holo_blue_dark" 属性可设置开关在关闭时默认的外框线颜色
   在xml使用 app:inline_open_color="@android:color/holo_green_light" 属性可设置开关在开启时默认的内部小圆颜色
   在xml使用 app:inline_close_color="@android:color/holo_green_dark" 属性可设置开关在关闭时默认的内部小圆颜色
   需引入 app 的 xml 命名空间
        
```xml
<top.gtf35.withyebai.BeautifulSwitch
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:outline_open_color="@android:color/holo_blue_bright"
        app:outline_close_color="@android:color/holo_blue_dark"
        app:inline_open_color="@android:color/holo_green_light"
        app:inline_close_color="@android:color/holo_green_dark" />
```

###		5x3 默认禁用的开关
   在xml使用 app:enable="false" 属性可设置默认的启用状态
   true 为默认启用。 false 为默认禁用
   在禁用状态下无法点击也不响应点击事件 (BeautifulSwitch.OnClickListener)
   需引入 app 的 xml 命名空间
```xml
<top.gtf35.withyebai.BeautifulSwitch
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:enable="false" />
```

###		5x4 使用代码动态控制开关
   #### 5x4x0 动态禁用或启用开关：
```java
//调用 BeautifulSwitch 实例的 setEnabled 方法可以设置启用状态
//true 为启用。 false 为禁用
// 设置之后不可被点击也不会回调点击事件 (BeautifulSwitch.OnClickListener)
beautifulSwitch.setEnabled(false);//禁用开关
beautifulSwitch.setEnabled(true);//启用开关
```

   #### 5x4x1 动态打开开关演示
```java
//调用 BeautifulSwitch 实例的 open()/close() 方法可以动态开启/关闭开关，
//这个接口会有动画，如果不需要动画请参考下述的 setOpen(boolean isOpen) 接口
//此接口会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
//如果当前已经是开启/关闭状态会有颜色切换，为提醒作用，
// 如果不喜欢可以在调用前调用 isOpen() 获取当前开启状态,详见下文获取状态
beautifulSwitch.open();//动态开启开关
beautifulSwitch.close();//动态关闭开关
```

#### 5x4x2 代码动态改变开关配色

```java
//调用 BeautifulSwitch 实例的 setOutlineOpenColor(int color) 方法可以可动态设置开关在开启时的外框线颜色
beautifulSwitch.setOutlineOpenColor(Color.parseColor("#ff00ddff"));
//调用 BeautifulSwitch 实例的 setOutlineCloseColor(int color) 方法可以可动态设置开关在关闭时的外框线颜色
beautifulSwitch.setOutlineCloseColor(Color.parseColor("#ff0099cc"));
//调用 BeautifulSwitch 实例的 setInlineOpenColor(int color) 方法可以可动态设置开关在开启时的内部小圆颜色
beautifulSwitch.setInlineOpenColor(Color.parseColor("#ff99cc00"));
//调用 BeautifulSwitch 实例的 setInlineCloseColor(int color) 方法可以可动态设置开关在关闭时的内部小圆颜色
beautifulSwitch.setInlineCloseColor(Color.parseColor("#ff669900"));
//以上操作即时生效
```

#### 5x4x3 动态切换开关状态

```java
//调用 BeautifulSwitch 实例的 change() 方法可以动态切换开关状态，
//比如开关状态是"开",调用此方法就会将开关关闭，
//同理开关状态是"关",调用此方法就会将开关开启，
//此接口会返回一个布尔值，代表调用之后目前开关是开还是关
//开启为“true”；关闭为“fasle”
//这个接口会有动画，如果不需要动画请参考下述的 setOpen(boolean isOpen) 接口
boolean isOpen = beautifulSwitch.change();
Toast.makeText(TestActivity.this, "现在是" + (isOpen? "开": "关") + "的", Toast.LENGTH_SHORT).show();
//注：同时也会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
```

#### 5x4x4 使用状态监听器监听开关的切换

```java
beautifulSwitch.setOnStatuesChangeListener(new BeautifulSwitch.OnSwitchChangeListener() {
    /*回调当前 View 的实例和切换后状态(也就是当前的开关状态)*/
    @Override
    public void onSwitchChange(View v, boolean isOpen) {
        //回调运行在主线程，可直接操作 UI
        TextView tv = findViewById(R.id.tv_statue_listener);
        tv.setText("状态监听:现在是" + (isOpen? "开": "关") + "的");
    }
});
```

#### 5x4x5 直接开启/关闭(无动画)

```java
//调用 BeautifulSwitch 实例的 setOpen(boolean isOpen) 方法可以直接指定开关的打开或者关闭，无动画，
//true 为开启。 false 为关闭
//此接口会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
beautifulSwitch.setOpen(true);//直接开启
beautifulSwitch.setOpen(false);//直接关闭
```

### 5x5 获取开关状态

```java
//调用 BeautifulSwitch 实例的 isOpen() 方法可以动态获取当前的开关状态，
//此接口会返回一个布尔值，代表目前开关是开还是关
//开启为“true”；关闭为“fasle”
boolean isOpen = beautifulSwitch.isOpen();
Toast.makeText(TestActivity.this, "现在状态是" + (isOpen? "开": "关") + "的", Toast.LENGTH_SHORT).show();
```



## 6 关于

- 程序：gtf35 gtfdeyouxiang@gmail.com
- 美术：夜白 3412436097@qq.com
- 开源协议：MIT
- 文档撰写日期：2019/08/13