package top.gtf35.customui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import top.gtf35.withyebai.BeautifulSwitch;


public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        normallyUsedSwitch();//正常使用的开关演示
        defaultOnSwitch();//默认开启的开关演示
        customColorSwitch();//自定义颜色的开关演示
        defaultDisabledSwitch();//默认禁用的开关颜色
        codeControlledSwitch();//代码控制的开关演示
    }

    /**
     * 正常使用的开关
     */
    private void normallyUsedSwitch(){
        BeautifulSwitch beautifulSwitch = findViewById(R.id.beautiful_switch_one);
        final TextView statuesTv = findViewById(R.id.tv_one_statues);
        //设置开关点击监听器
        beautifulSwitch.setOnSwitchClickListener(new BeautifulSwitch.OnClickListener() {
            /*回调当前 View 的实例和点击后状态(也就是当前的开关状态)*/
            @Override
            public void onClick(View v, boolean isOpen) {
                //回调运行在主线程，可直接操作 UI
                statuesTv.setText("状态:" + (isOpen? "开": "关"));
            }
        });
        //注：同时也会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下述的“状态监听”
    }

    /*
    * 默认开启的开关
    * */
    private void defaultOnSwitch(){
        //在xml使用 app:is_open="true" 属性可设置默认的状态
        //true 为默认开。 false 为默认关
        //需引入 app 的 xml 命名空间
        //其余操作同正常使用的开关 normallyUsedSwitch ,再次不再赘述
        BeautifulSwitch beautifulSwitch = findViewById(R.id.beautiful_switch_two);
        final TextView statuesTv = findViewById(R.id.tv_two_statues);
        beautifulSwitch.setOnSwitchClickListener(new BeautifulSwitch.OnClickListener() {
            @Override
            public void onClick(View v, boolean isOpen) {
                statuesTv.setText("状态:" + (isOpen? "开": "关"));
            }
        });
    }

    /*
    * 自定义颜色的开关
    * */
    private void customColorSwitch(){
        //在xml使用 app:outline_open_color="@android:color/holo_blue_bright" 属性可设置开关在开启时默认的外框线颜色
        //在xml使用 app:outline_close_color="@android:color/holo_blue_dark" 属性可设置开关在关闭时默认的外框线颜色
        //在xml使用 app:inline_open_color="@android:color/holo_green_light" 属性可设置开关在开启时默认的内部小圆颜色
        //在xml使用 app:inline_close_color="@android:color/holo_green_dark" 属性可设置开关在关闭时默认的内部小圆颜色
        //需引入 app 的 xml 命名空间
        //其余操作同正常使用的开关 normallyUsedSwitch ,再次不再赘述
        BeautifulSwitch beautifulSwitch = findViewById(R.id.beautiful_switch_three);
        final TextView statuesTv = findViewById(R.id.tv_three_statues);
        beautifulSwitch.setOnSwitchClickListener(new BeautifulSwitch.OnClickListener() {
            @Override
            public void onClick(View v, boolean isOpen) {
                statuesTv.setText("状态:" + (isOpen? "开": "关"));
            }
        });
    }

    /*
    * 默认禁用的开关
    * */
    private void defaultDisabledSwitch(){
        //在xml使用 app:enable="false" 属性可设置默认的启用状态
        //true 为默认启用。 false 为默认禁用
        //在禁用状态下无法点击也不响应点击事件 (BeautifulSwitch.OnClickListener)
        //需引入 app 的 xml 命名空间
        //其余操作同正常使用的开关 normallyUsedSwitch ,再次不再赘述
        BeautifulSwitch beautifulSwitch = findViewById(R.id.beautiful_switch_four);
        final TextView statuesTv = findViewById(R.id.tv_four_statues);
        beautifulSwitch.setOnSwitchClickListener(new BeautifulSwitch.OnClickListener() {
            @Override
            public void onClick(View v, boolean isOpen) {
                statuesTv.setText("状态:" + (isOpen? "开": "关"));
            }
        });
    }

    /*
    * 代码控制的开关
    * */
    private void codeControlledSwitch(){
        final BeautifulSwitch beautifulSwitch = findViewById(R.id.beautiful_switch_five);
        final TextView statuesTv = findViewById(R.id.tv_five_statues);
        beautifulSwitch.setOnSwitchClickListener(new BeautifulSwitch.OnClickListener() {
            @Override
            public void onClick(View v, boolean isOpen) {
                statuesTv.setText("状态:" + (isOpen? "开": "关"));
            }
        });

        //使用代码动态禁用开关演示
        findViewById(R.id.btn_disable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 setEnabled 方法可以设置启用状态
                //true 为启用。 false 为禁用
                //此处调用 setEnabled(false) 为禁用开关，
                // 设置之后不可被点击也不会回调点击事件 (BeautifulSwitch.OnClickListener)
                beautifulSwitch.setEnabled(false);
            }
        });

        //使用代码动态启用开关演示
        findViewById(R.id.btn_enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //和上述的启用的代码语句相同，
                //这里是启用，根据上文启用是 true
                //所以传入 true
                beautifulSwitch.setEnabled(true);
            }
        });

        //使用代码动态打开开关演示
        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 open() 方法可以动态开启开关，
                //这个接口会有动画，如果不需要动画请参考下述的 setOpen(boolean isOpen) 接口
                //此接口会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
                //如果当前已经是开启状态会有颜色切换，为提醒作用，
                // 如果不喜欢可以在调用前调用 isOpen() 获取当前开启状态,详见下文获取状态
                beautifulSwitch.open();
            }
        });

        //使用代码动态关闭开关演示
        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 close() 方法可以动态开启开关，
                //这个接口会有动画，如果不需要动画请参考下述的 setOpen(boolean isOpen) 接口
                //此接口会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
                //如果当前已经是开启状态会有颜色切换，为提醒作用，
                // 如果不喜欢可以在调用前调用 isOpen() 获取当前开启状态,详见下文获取状态
                beautifulSwitch.close();
            }
        });

        //使用代码动态改变开关配色演示
        findViewById(R.id.btn_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 setOutlineOpenColor(int color) 方法可以可动态设置开关在开启时的外框线颜色
                beautifulSwitch.setOutlineOpenColor(Color.parseColor("#ff00ddff"));
                //调用 BeautifulSwitch 实例的 setOutlineCloseColor(int color) 方法可以可动态设置开关在关闭时的外框线颜色
                beautifulSwitch.setOutlineCloseColor(Color.parseColor("#ff0099cc"));
                //调用 BeautifulSwitch 实例的 setInlineOpenColor(int color) 方法可以可动态设置开关在开启时的内部小圆颜色
                beautifulSwitch.setInlineOpenColor(Color.parseColor("#ff99cc00"));
                //调用 BeautifulSwitch 实例的 setInlineCloseColor(int color) 方法可以可动态设置开关在关闭时的内部小圆颜色
                beautifulSwitch.setInlineCloseColor(Color.parseColor("#ff669900"));
                //以上操作即时生效
            }
        });

        //使用代码动态切换开关状态演示
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 change() 方法可以动态切换开关状态，
                //比如开关状态是"开",调用此方法就会将开关关闭，
                //同理开关状态是"关",调用此方法就会将开关开启，
                //此接口会返回一个布尔值，代表调用之后目前开关是开还是关
                //开启为“true”；关闭为“fasle”
                //这个接口会有动画，如果不需要动画请参考下述的 setOpen(boolean isOpen) 接口
                boolean isOpen = beautifulSwitch.change();
                Toast.makeText(TestActivity.this, "现在是" + (isOpen? "开": "关") + "的", Toast.LENGTH_SHORT).show();
                //注：同时也会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
            }
        });

        //使用状态监听器监听开关的切换
        beautifulSwitch.setOnStatuesChangeListener(new BeautifulSwitch.OnSwitchChangeListener() {
            /*回调当前 View 的实例和切换后状态(也就是当前的开关状态)*/
            @Override
            public void onSwitchChange(View v, boolean isOpen) {
                //回调运行在主线程，可直接操作 UI
                TextView tv = findViewById(R.id.tv_statue_listener);
                tv.setText("状态监听:现在是" + (isOpen? "开": "关") + "的");
            }
        });

        //直接开启，无动画
        findViewById(R.id.btn_set_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 setOpen(boolean isOpen) 方法可以直接指定开关的打开或者关闭，无动画，
                //true 为开启。 false 为关闭
                //此接口会回调 BeautifulSwitch.OnSwitchChangeListener 详情见下属的“状态监听”
                beautifulSwitch.setOpen(true);
            }
        });

        //直接关闭，无动画
        findViewById(R.id.btn_set_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //和上述的开启的代码语句相同，
                //这里是关闭，根据上文关闭是 false
                //所以传入 false
                beautifulSwitch.setOpen(false);
            }
        });

        //获取开关状态
        findViewById(R.id.btn_get_isopen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用 BeautifulSwitch 实例的 isOpen() 方法可以动态获取当前的开关状态，
                //此接口会返回一个布尔值，代表目前开关是开还是关
                //开启为“true”；关闭为“fasle”
                boolean isOpen = beautifulSwitch.isOpen();
                Toast.makeText(TestActivity.this, "现在状态是" + (isOpen? "开": "关") + "的", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
