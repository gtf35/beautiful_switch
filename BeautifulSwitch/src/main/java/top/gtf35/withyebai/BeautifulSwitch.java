package top.gtf35.withyebai;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import top.gtf35.withyebai.beautifulswitch.R;

/**
 * 一个漂亮的开关
 * 设计：夜白
 * 程序：gtf35
 * gtfdeyouxiang@gamil.com
 * 2019/08/12
 * */

public class BeautifulSwitch extends View {

    private int mProgress = 0;//动画进度
    private boolean mEnable = true;//是否启用
    private boolean mIsOpen;//当前状态：打开/关闭
    private int mStrokeWidthOpenColor = Color.parseColor("#B5CCFF");//外框打开颜色
    private int mStrokeWidthCloseColor = Color.parseColor("#eaeff6");//外框关闭颜色
    private int mStrokeWidthColor = isOpen()? mStrokeWidthOpenColor: mStrokeWidthCloseColor;//默认外框颜色
    private int mSmallCircleOpenColor = Color.parseColor("#4680FF");//内部指示器小圆打开时的颜色
    private int mSmallCircleCloseColor = Color.parseColor("#cbd7e9");//内部指示器小圆关闭时的颜色
    private int mSmallCircleColor = isOpen()? mSmallCircleOpenColor: mSmallCircleCloseColor;//内部指示器小圆默认的颜色
    private OnClickListener mOnClickListener;//点击监听器
    private OnSwitchChangeListener mOnSwitchChangeListener;//开关状态改变监听器
    private Infos mInfos = new Infos();

    private final static String mEnableKey = "mEnable";
    private final static String mIsOpenKey = "mIsOpen";
    private final static String mProgressKey = "mProgress";
    private final static String mStrokeWidthOpenColorKey = "mStrokeWidthOpenColor";
    private final static String mStrokeWidthCloseColorKey = "mStrokeWidthCloseColor";
    private final static String mStrokeWidthColorKey = "mStrokeWidthColor";
    private final static String mSmallCircleOpenColorKey = "mSmallCircleOpenColor";
    private final static String mSmallCircleCloseColorKey = "mSmallCircleCloseColor";
    private final static String mSmallCircleColorKey = "mSmallCircleColor";

    /*点击回调接口*/
    public interface OnClickListener{
        void onClick(View v, boolean isOpen);
    }

    /*开关状态改变回调接口*/
    public interface OnSwitchChangeListener{
        void onSwitchChange(View v, boolean isOpen);
    }

    /*设置点击监听*/
    public void setOnSwitchClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    /*设置状态改变监听*/
    public void setOnStatuesChangeListener(OnSwitchChangeListener l) {
        mOnSwitchChangeListener = l;
    }

    public BeautifulSwitch(Context context) {
        super(context);
    }

    public BeautifulSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BeautifulSwitch);
        mEnable = typedArray.getBoolean(R.styleable.BeautifulSwitch_enable, true);//启用
        mIsOpen = typedArray.getBoolean(R.styleable.BeautifulSwitch_is_open, false);//默认是否是开启的
        //开启的时候外框线颜色
        mStrokeWidthOpenColor = typedArray.getColor(R.styleable.BeautifulSwitch_outline_open_color, mSmallCircleOpenColor);
        //关闭的时候外框线颜色
        mStrokeWidthCloseColor = typedArray.getColor(R.styleable.BeautifulSwitch_outline_close_color, mStrokeWidthCloseColor);
        //开启的时候内部的小圆颜色
        mSmallCircleOpenColor = typedArray.getColor(R.styleable.BeautifulSwitch_inline_open_color, mSmallCircleOpenColor);
        //关闭的时候内部小圆的颜色
        mSmallCircleCloseColor = typedArray.getColor(R.styleable.BeautifulSwitch_inline_close_color, mSmallCircleCloseColor);
        //回收资源
        typedArray.recycle();
        //刷新其他相关默认值
        mProgress = mIsOpen? 100: 0;
        mSmallCircleColor = mIsOpen? mSmallCircleOpenColor: mSmallCircleCloseColor;
        mStrokeWidthColor = mIsOpen? mStrokeWidthOpenColor: mStrokeWidthCloseColor;
    }

    public BeautifulSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*在系统回收时保存状态*/
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putBoolean(mEnableKey, mEnable);
        bundle.putBoolean(mIsOpenKey, mIsOpen);
        bundle.putInt(mProgressKey, mProgress);
        bundle.putInt(mStrokeWidthOpenColorKey, mStrokeWidthOpenColor);
        bundle.putInt(mStrokeWidthCloseColorKey, mStrokeWidthCloseColor);
        bundle.putInt(mStrokeWidthColorKey, mStrokeWidthColor);
        bundle.putInt(mSmallCircleOpenColorKey, mSmallCircleOpenColor);
        bundle.putInt(mSmallCircleCloseColorKey, mSmallCircleCloseColor);
        bundle.putInt(mSmallCircleColorKey, mSmallCircleColor);
        return bundle;
    }

    /*在系统重建的时候恢复状态*/
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mEnable = bundle.getBoolean(mEnableKey, true);
            mIsOpen = bundle.getBoolean(mIsOpenKey, false);
            mProgress = bundle.getInt(mProgressKey, 0);
            mStrokeWidthOpenColor = bundle.getInt(mStrokeWidthOpenColorKey);
            mStrokeWidthCloseColor = bundle.getInt(mStrokeWidthCloseColorKey);
            mStrokeWidthColor = bundle.getInt(mStrokeWidthColorKey);
            mSmallCircleCloseColor = bundle.getInt(mSmallCircleCloseColorKey);
            mSmallCircleColor = bundle.getInt(mSmallCircleColorKey);
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    /*开启的时候外框线颜色*/
    public void setOutlineOpenColor(int color){
        mStrokeWidthOpenColor = color;
        mStrokeWidthColor = mIsOpen? mStrokeWidthOpenColor: mStrokeWidthCloseColor;
        invalidate();//触发重绘
    }

    /*关闭的时候外框线颜色*/
    public void  setOutlineCloseColor(int color){
        mStrokeWidthCloseColor = color;
        mStrokeWidthColor = mIsOpen? mStrokeWidthOpenColor: mStrokeWidthCloseColor;
        invalidate();//触发重绘
    }

    /*开启的时候内部的小圆颜色*/
    public void setInlineOpenColor(int color){
        mSmallCircleOpenColor = color;
        mSmallCircleColor = mIsOpen? mSmallCircleOpenColor: mSmallCircleCloseColor;
        invalidate();//触发重绘
    }

    /*关闭的时候内部小圆的颜色*/
    public void setInlineCloseColor(int color){
        mSmallCircleCloseColor = color;
        mSmallCircleColor = mIsOpen? mSmallCircleOpenColor: mSmallCircleCloseColor;
        invalidate();//触发重绘
    }

    /*获取开启的时候外框线颜色*/
    public int getOutlineOpenColor(){
        return mStrokeWidthOpenColor;

    }

    /*获取关闭的时候外框线颜色*/
    public int getOutlineCloseColor(){
        return mStrokeWidthCloseColor;
    }

    /*获取开启的时候内部的小圆颜色*/
    public int getInlineOpenColor(){
        return mSmallCircleOpenColor;
    }

    /*获取关闭的时候内部小圆的颜色*/
    public int getInlineCloseColor(){
        return mSmallCircleCloseColor;
    }

    /*获取当前动画进度*/
    public int getProgress() {
        return mProgress;
    }

    /*设置当前动画进度*/
    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();//触发重绘
    }

    /*获取启用状态*/
    public boolean isEnable() {
        return mEnable;
    }

    /*设置启用状态*/
    @Override
    public void setEnabled(boolean isEnable) {
        this.mEnable = isEnable;
    }

    /*获取外框当前的颜色*/
    public int getStrokeWidthColor() {
        return mStrokeWidthColor;
    }

    /*设置外框当前的颜色*/
    public void setStrokeWidthColor(int strokeWidthColor) {
        this.mStrokeWidthColor = strokeWidthColor;
    }

    /*获取内部小圆的颜色*/
    public int getSmallCircleColor() {
        return mSmallCircleColor;
    }

    /*设置内部小圆的颜色*/
    public void setSmallCircleColor(int smallCircleColor) {
        this.mSmallCircleColor = smallCircleColor;
    }

    /*获取当前打开状态*/
    public boolean isOpen() {
        return mIsOpen;
    }

    /*直接打开无动画*/
    public void setOpen(boolean isOpen) {
        mProgress = isOpen? 100: 0;
        mIsOpen = isOpen;
        if (mOnSwitchChangeListener != null)mOnSwitchChangeListener.onSwitchChange(this, isOpen);//回调状态改变事件
        mSmallCircleColor = isOpen? mSmallCircleOpenColor: mSmallCircleCloseColor;
        mStrokeWidthColor = isOpen? mStrokeWidthOpenColor: mStrokeWidthCloseColor;
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    /*开启，带动画*/
    public void open(){
        mIsOpen = true;
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", mProgress, 100);
        animator.start();
        ValueAnimator circleColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), mStrokeWidthCloseColor, mStrokeWidthOpenColor);
        circleColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setStrokeWidthColor((int)animation.getAnimatedValue());
            }
        });
        circleColorAnimator.start();
        ValueAnimator smallCircleColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), mSmallCircleCloseColor, mSmallCircleOpenColor);
        smallCircleColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setSmallCircleColor((int)animation.getAnimatedValue());
            }
        });
        smallCircleColorAnimator.start();
        if (mOnSwitchChangeListener != null)mOnSwitchChangeListener.onSwitchChange(this, true);//回调状态改变事件
    }

    /*关闭，带动画*/
    public void close(){
        mIsOpen = false;
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", mProgress, 0);
        animator.start();
        ValueAnimator circleColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(),  mStrokeWidthOpenColor, mStrokeWidthCloseColor);
        circleColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setStrokeWidthColor((int)animation.getAnimatedValue());
            }
        });
        circleColorAnimator.start();
        ValueAnimator smallCircleColorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), mSmallCircleOpenColor, mSmallCircleCloseColor);
        smallCircleColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setSmallCircleColor((int)animation.getAnimatedValue());
            }
        });
        smallCircleColorAnimator.start();
        if (mOnSwitchChangeListener != null)mOnSwitchChangeListener.onSwitchChange(this, false);//回调状态改变事件
    }


    /*绘制相关信息类*/
    private static class Infos{

        int strokeWidth = 15;//描边宽度
        int halfStrokeWidth = strokeWidth/2;//溢出宽度，需要做描边保护
        int circleR = 45;//圆弧半径
        int lineX = 180;//直线长度
        float moveY;//Y轴偏移，为了垂直居中
        int smallCircleR = 15;//内部指示器小圆的半径
        int finalHeight, finalWidth;//最后整体的宽高
        int defaultWidth = strokeWidth + 2 * circleR + lineX;//控件默认宽度
        int defaultHeight = strokeWidth + 2 * circleR;//控件默认高度

        /*计算绘制尺寸/初始化绘制对象*/
        void init(int width, int height){
            calculationData(width, height);
            initDrawObjects();
        }

        /*计算绘制尺寸*/
        void calculationData(int sWidth, int sHeight){
            finalWidth = strokeWidth + 2 * circleR + lineX;//默认控件宽度，因为会被调用多次，所以需要每次都计算一次
            finalHeight = strokeWidth + 2 * circleR;//默认控件高度
            /*缩放，此处必须支持小数，因为缩放的很可能比你预设的小，也就是缩放比小于1，缩小*/
            double fixSize = (double) sWidth / (double)finalWidth; //按宽计算缩放比
            if (sHeight < finalHeight * fixSize ) fixSize = (double)sHeight / (double)finalHeight;//按高计算缩放比
            circleR = (int)(circleR * fixSize);
            strokeWidth = (int)(strokeWidth * fixSize);
            smallCircleR = (int)(smallCircleR * fixSize);
            halfStrokeWidth = strokeWidth/2;
            finalHeight = (int)(finalHeight * fixSize);
            finalWidth = (int)(finalWidth * fixSize);
            lineX = sWidth - strokeWidth - 2 * circleR;
            moveY = (sHeight - finalHeight)/2f;
            moveY = moveY < 0? 0: moveY;
        }

        //外框的两个半圆
        RectF strokeOutLeftRect;
        RectF strokeOutRightRect;
        Paint paint;//画笔
        Path path;//路径

        /*初始化绘制对象*/
        void initDrawObjects(){
            //外框的两个半圆
            strokeOutLeftRect =  new RectF(halfStrokeWidth, halfStrokeWidth + moveY,
                    halfStrokeWidth + 2 * circleR, halfStrokeWidth + 2 * circleR + moveY);
            strokeOutRightRect = new RectF(halfStrokeWidth + lineX, halfStrokeWidth + moveY,
                    halfStrokeWidth + 2 * circleR + lineX, halfStrokeWidth + 2 * circleR + moveY);
            paint = new Paint();//画笔
            path = new Path();//路径
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mInfos.paint.setAntiAlias(true);//抗锯齿

        /*准备画边框，设置画笔*/
        mInfos.paint.setColor(mStrokeWidthColor);//画笔颜色
        mInfos.paint.setStyle(Paint.Style.STROKE);//描边模式
        mInfos.paint.setStrokeWidth(mInfos.strokeWidth);//描边宽度
        /*画外边框路径*/
        mInfos.path.moveTo(mInfos.halfStrokeWidth + mInfos.circleR + mInfos.lineX, mInfos.halfStrokeWidth + mInfos.moveY);
        mInfos.path.lineTo(mInfos.halfStrokeWidth + mInfos.circleR, mInfos.halfStrokeWidth + mInfos.moveY);
        mInfos.path.moveTo(mInfos.halfStrokeWidth + mInfos.circleR, mInfos.halfStrokeWidth + 2 * mInfos.circleR + mInfos.moveY);
        mInfos.path.lineTo(mInfos.halfStrokeWidth + mInfos.circleR + mInfos.lineX, mInfos.halfStrokeWidth + 2 * mInfos.circleR + mInfos.moveY);
        canvas.drawPath(mInfos.path, mInfos.paint);
        canvas.drawArc(mInfos.strokeOutRightRect, -90,  180, false, mInfos.paint);
        canvas.drawArc(mInfos.strokeOutLeftRect, -90, -180, false, mInfos.paint);

        /*准备画内部的小圆，设置画笔*/
        mInfos.paint.setColor(mSmallCircleColor);//画笔的颜色
        mInfos.paint.setStyle(Paint.Style.FILL);//填充不描边模式
        float smallCircleX = mInfos.halfStrokeWidth + (mProgress / 100f) * mInfos.lineX + mInfos.circleR;
        /*画内部指示器小圆*/
        canvas.drawCircle((int)smallCircleX, mInfos.halfStrokeWidth + mInfos.circleR + mInfos.moveY, mInfos.smallCircleR, mInfos.paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setDefaultSize(widthMeasureSpec, heightMeasureSpec);
    }

    /*开/关之间切换*/
    public boolean change(){
        if (!mEnable) return isOpen();//禁用的情况下不响应
        if (isOpen()){
            close();
        } else {
            open();
        }
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this, isOpen());//回调点击事件
        }
        return isOpen();
    }

    /*处理点击事件*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y = event.getY();
        //Log.d("onTouchEvent" ,"X=" + x + "  y=" + y);
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                change();
        }
        return true;//拦截事件
    }

    /*
    * 让 wrap_context 生效
    * https://blog.csdn.net/carson_ho/article/details/62037760
    * */
    private void setDefaultSize(int widthMeasureSpec, int heightMeasureSpec){
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看

        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            /*高宽全是当布局参数设置为wrap_content时*/
            mInfos.initDrawObjects();//不用计算大小，但是需要初始化绘制对象
            setMeasuredDimension(mInfos.defaultWidth, mInfos.defaultHeight);//全应用默认大小
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            /*宽是wrap_content，高是match_parent或都指定了大小*/
            mInfos.init(mInfos.defaultWidth, heightSize);//高用测量值，宽用默认值
            setMeasuredDimension(mInfos.finalWidth, heightSize);//应用大小
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            /*高是wrap_content，宽是match_parent或都指定了大小*/
            mInfos.init(widthSize, mInfos.defaultHeight);//宽用测量值，高用默认值
            setMeasuredDimension(widthSize, mInfos.finalHeight);//应用大小
        } else {
            /*高宽全是match_parent或都指定了大小*/
            mInfos.init(widthSize, heightSize);//高宽全用测量值计算
            setMeasuredDimension(widthSize, heightSize);//应用的应该是实际的大小，因为这种情况是人家指定画布大小了，所以大小必须等于画布，然后再用画布大小去计算自己
        }
    }

}