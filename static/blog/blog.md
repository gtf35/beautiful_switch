# 做一个漂亮的开关并发布 Jcenter 仓库

今天夜白给我一个设计图，问我能不能实现：

![设计图](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/yuanpic.png)

诶嘿，咱们完全不慌嘀，走起来

![完全不慌](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/wqbh.jpg)

新建一个「Empty Activity」 的项目，包名：「top.gtf35.customui」，API 18

![新建项目](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/xjxm.png)

然后再新建一个库来承载自定义控件：「File」-「New」-「New Module」

![新建库](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/xjlib.png)

选「Android Library」

![选 Android Library](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/androidlib.png)

编辑一个 library 的信息，这里就都叫「BeautifulSwitch」了，美丽的控件

![1566186764469](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/configlib.png)

不管好看不好看，就叫漂亮的控件，就是这么自信

![就是自信](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/zmzx.jpg)

于是现在就应该能看到两个模块了，默认的「app」和新建的「BeautifulSwitch」

![看到两个模块](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kdlgmk.png)

这时，我们的库还没有依赖到 app 中，我们需要添加下依赖

```java
dependencies {
    ......
    implementation project(':BeautifulSwitch')//依赖本地 BeautifulSwitch 库
	......
}
```

![添加本地依赖](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tjbdyl.png)

自定义控件的家就建好了。啥玩意，这年头控件都有房了？

![没车没房](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/mcmf.gif)

唉，在 BeautifulSwitch 项目下新建一个 BeautifulSwitch 类继承自 View 开始画控件

![新建类](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/xjl.jpg)

会发现有报错，按 alt + enter 修复

![有报错](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/ybc.png)

按住 ctrl 多选，选前 3 个重写

![选前3个](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/xqsg.png)

完成之后就是这个样子：

![完成图](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/wczhjszgyz.png)

还记得自定义控件的精髓么，onDraw 呀

![相视一笑](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/xsyx.jpg)

按 ctrl + o 打开重写界面，直接键盘输入 onDraw 即可自动选中，回车确定

![重写界面](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/cxjm.png)

下面我们来分析下这个设计图

![](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/fenjie.jpg)

可以看到，一共分为5部分，左边半圆(半径30)，右边半圆，上直线(长度130)，下直线，中间圆心(直径20)，其中线条宽度10

![墨磨叽](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/mj.jpg)

定义一个绘制相关信息类，「Infos」：

![基础绘制信息类](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jchzxxl.png)

要在这里一次性新建好所有的绘制所用的对象，因为每次都在 onDraw 里新建对象，比较耗费性能

![绘制信息类](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/hzxxl.png)

因为没有画圆的 API ，就只能画椭圆了。因为两个椭圆的位置不变，所以椭圆的外切矩形也需要提前定义好

![左圆](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/zy.jpg)

蓝色实线是 view 的坐标系，粉色田字框线为外接矩形，绿色实线为外接矩形四条边的坐标

于是便可以写出如下代码：

![绘制两个半圆的外切矩形](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/hzlgby.png)

因为上述的尺寸都是比例，所以在真正绘制时的尺寸需要实时计算缩放比例：

![真正绘制的尺寸计算](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/ssjscc.png)

算出来当前的高和宽，先假设可以按宽计算缩放比，即用实际的宽除上当前的宽得到缩放比例。

用这个比例缩放下高，要是超过画布的高度证明需要按高重新计算缩放比，这时候就得到了绘制需要的尺寸。但是控件在水平上填充，在垂直上居中才会好看。所以还需要直线长度填充画布宽度，需要引入并计算垂直偏移量

![真闹心](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/znx.jpg)

然后重写 onMeasure 调用我们的计算方法：

主要就是处理 wrap_content 和 match_parent

![重写测量](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/cxcl.png)

定义下需要的颜色等全局变量

![全局变量](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/qjbl.png)

来大体想一下需要暴露的 xml 属性，先在 res/values 下新建 attrs.xml

![新建attrs](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/xjattrs.png)

需要可以在布局中定义默认颜色，默认是被禁用，默认的开启状态，颜色又分为：

- 开启的时候外框线颜色
- 关闭的时候外框线颜色
- 开启的时候内部的小圆颜色
- 关闭的时候内部小圆的颜色

![attrs](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/attrs.png)

在构造方法里引入

![引入属性](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/yrsx.png)

定义颜色的 setter 和 getter 方法

![颜色的setter和getter](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/yssg.png)

定义启用/禁用/开启/关闭的暴露 api

![暴露的api](C:\Users\gtf35\Desktop\自定义控件\blapi.png)

因为在被系统回收后(例如屏幕旋转)会重建，全局变量会丢失，所以要重写 onSaveInstanceState  和 onRestoreInstanceState 来保存和恢复信息。

定义需要保存的变量的key：

![定义key](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/dykey.png)

重写 onSaveInstanceState 在销毁的时候保存信息

![保存信息](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bcxx.png)

 重写 onRestoreInstanceState 来恢复信息

![恢复信息](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/hfxx.png)

定义点击监听和状态改变监听接口

![定义接口](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/dyjk.png)

定义接口暴露的 setter 方法

![接口暴露](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jiekoubaolu.png)

都准备好了，在 onDraw 里开画

![画图](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kaihua.png)

细节在截图中，不知道数学计算如何得出来的自己画个草图就知道了

![你放屁](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/nfp.jpg)

是时候让他动起来了，动画分成两部分：中间小圆的移动和颜色的渐变。

小圆的移动使用 ObjectAnimator 来直接调用内部 progress 的 getter 和 setter 方法：

```java
ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", mProgress, 100);animator.start();
```

颜色的变换使用 ValueAnimator ，内部手动调用改变颜色：

![1566279226077](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/ysdh.png)

总体来看，开关的开启开启动画过程如下：

![开启动画](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kqdh.png)

相应的关闭动画也差不多：

![关闭动画](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/gbdh.png)

写一个切换打开/关闭的方法，暴露出来，方便别人和自己调用

![1566282293329](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kgqh.png)

为了让他可以响应点击事件，我们需要重写 onTouchEvent ，在手指抬起时调用切换状态的方法

![重写 onThouch](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/ontouch.png)

我们在 app 模块的 MainActivity 里加入这个控件，运行测试一下

![测试结果](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/csjg.png)

没毛病，so easy

![666](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/666.jpg)

下面我们上传 GitHub，造福社会

完成版本可参考：我的[GitHub](https://github.com/gtf35/beautiful_switch/blob/master/BeautifulSwitch/src/main/java/top/gtf35/withyebai/BeautifulSwitch.java)

先引入 git 版本控制：

![引入版本控制](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jrbbkz.png)

然后右上角的勾勾提交一个 commit：

![提交commit](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/commit.png)

选除了 .idea 之外的所有需要的文件

![提交commit](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tijiaocommit.png)

有警告，提示有 warnings 没有处理，我们点中间的 commit 继续提交

![有警告](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/yjg.png)

底部显示提交成功，我们就已经准备好了本地的版本控制

![提交成功](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tjcg.png)

现在我们提交到 GitHub

![提交 Github](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tjgitub.png)

然后他就会检查你在 GitHub 上的登录信息，如果没有登陆过会要求你输入账号密码

![登录 GitHub](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/dlgtihub.png)

然后输入仓库名字和描述，点击 share ，他就会把项目分享到 GitHub

![填写信息](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/sptgotub.png)

当右下角出现这个提示时，就上传完成了

![上传成功](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/sharesuccess.png)

现在我们来考虑上传到 jcenter ，让别人可以一句 implementation 就依赖你的控件

![谁爱用呀](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/wu.jpg)

说的好像有人用一样，🤦‍♀️，我们先注册一个 jcenter 的账号

打开 bintray 的[免费账号注册](https://bintray.com/signup)界面，注意一定是免费账号，否则是 30 天企业版试用，是没有下面的一些选项的

![注册账号](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/zczh.png)

这里直接用 GitHub 登录就可以了，再填入邮箱等基本信息，就可以登录了

![主界面](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jcenterzjm.png)

选择主界面上的 Add New Repository ，添加一个新的仓库

![添加新仓库](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tjxck.png)

在 name 里给仓库起个好名字， 我就叫 maven ，然后 Type 类型选 Maven ，然后 Create 创建即可。

提示：名字最好就叫 maven ，这是一会要用的插件的默认名字，如果不叫这个，一会在讲插件那里，记得对号入座修改 :)

在主界面上就能看到我们新建的仓库了

![看到新建的仓库](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kdxjdck.png)

点击名字进入仓库

![进入仓库](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jrck.png)

在仓库中，选 add new package 创建一个新的包，我们用这个包来放我们的控件

![设置项目](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/szxm.png)

- Name(名字)就写我们这个项目的名字了：BeautifulSwitch
- Description(描述)就写我们这个项目的描述：an beautiful switch on Android
- Licenses(许可证)就写我们这个项目的许可证，我主要就是为了分享，我就用 MIT 许可证了
- Version control (版本控制)这里写我们刚才在 Github 的开源地址，这个必须是一个网址，否则日后在提交的时候会提示 Version control 404

然后 Create Package 创建包，我们这个容器就创建好了。其中 Name 要记好

这时候我们在 jcenter 这边算是准备差不多了，就差查看 api key 了

![1566297167171](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/djtx.png)

把鼠标放在网页右上角的头像上，会弹出一个菜单，选 Edit Profile

![点击api](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/djapi.png)

点击左边的 API Key 选项

![点击复制按钮](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/djfz.png)

点击复制按钮复制 api key，现在回到我们的代码准备上传到 jcenter

我们打开根目录下的 local.properties 文件

![填入配置](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jryhpz.png)

追加：bintrayKey 和 bintrayUser，分别为 API Key 和 jcenter 用户名

这个文件本身是存放的本地的目录，属于不应该手动编辑的。但是由于他只在本机有效的特性，不会被 Android Studio 给长传到仓库(想选都找不到这个文件)。放在这里可以防止不小心上传自己的 Key

![有内鬼](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/yng.jpg)

我们打开项目根目录下的 build.gradle

![添加插件](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/gmldgradle.png)

我们添加一个插件：com.novoda:bintray-release

```gradle
dependencies {
        ...
        classpath 'com.novoda:bintray-release:0.9.1'
        ...
    }
```

同步 gradle 之后，我们打开 BeautifulSwitch project 的 build.gradle

![添加项目上传插件](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tjsccj.png)

在末尾加入：

```
apply plugin: 'com.novoda.bintray-release'

Properties properties = new Properties()
properties.load(project.rootProject.file('local.properties').newDataInputStream())
def bintrayUserVal = properties.getProperty('bintrayUser')
def bintrayKeyVal = properties.getProperty('bintrayKey')

publish {
    userOrg = 'gtfdeyouxiang'  //jcenter的用户名
    groupId = 'top.gtf35.lib.withyebai'    //组织id
    artifactId = 'BeautifulSwitch'    //libName
    publishVersion = '1.1'        //libVersion
    desc = 'an beautiful switch on Android'//lib desc
    website = 'https://github.com/gtf35/beautiful_switch' //lib的地址
    bintrayUser = bintrayUserVal //上面task中读取到的jcenterName
    bintrayKey = bintrayKeyVal //上面task中读取到的apiKey
    dryRun = false
}
```

其中 userOrg 为 jcenter 的用户名。groupId ，artifactId，publishVersion共同组成了日后用户引入的 

```
implementation "groupId:artifactId:publishVersion"
```

artifactId 应该和新建 package 时的 Name 一致，desc为项目描述，website为项目网址。

如果你的仓库不叫 maven，还需要配置 repoName = '仓库名字'

配置完成后打开右侧的 gradle 标签

![选择上传](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/xzsc.png)

选择 bintrayUpload 选项上传到 jcenter 仓库

![就算成功](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/jscg.png)

出现 BUILD SUCCESSFUL 就算是成功了，我们回到刚才的网页上的 maven 仓库

![看看结果](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kkjg.png)

就可以看到我们的仓库已经有版本号了，点进去，选 Files

![看看文件](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/kkwj.png)

能看到已经有文件了，我们再回到 maven 仓库的主页，准备添加到 jcenter

![1566298704563](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/fxjcenter.png)

点击右下角的 Add to JCenter，添加到 JCenter

![提交审核](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/tjsh.png)你可以在最大的框框里写一些描述，但是没有特殊情况，直接点 send ，发送审核即可

大概20分钟之后，就会收到邮件

![1566299097123](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/sltjcg.png)

邮件里说已经被收录，到这里就全部完成了。

别人就可以通过

```
dependencies {
   ....
   implementation 'top.gtf35.lib.withyebai:BeautifulSwitch:1.1'
   ....
}
```

来使用你的控件了。现在给你的控件写一个小 demo 和 readme 列出介绍和用法就好了

![bye](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/bye.jpg)

最后贴一个完成的动图

![完成图](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/switch.gif)

这个上述的所有代码可在我的[GitHub](https://github.com/gtf35/beautiful_switch/)上获取，最后感谢我的搭档夜白，大家有缘江湖再见

![江湖再见](https://github.com/gtf35/beautiful_switch/blob/master/static/blog/bqb/jhzj.jpg)

