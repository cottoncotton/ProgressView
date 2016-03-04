package com.example.wxj.progressview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/4.
 */
public class ProgressView extends View {

    private float maxCount;
    private float currentCount;
    private int color;
    private Paint mPaint;//画笔
    private int mWidth,mHeight;

    public ProgressView(Context context) {
        super(context);
    }
    public ProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /***
     * 设置最大的进度值
     * @param maxCount
     */
    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }
    public void setColor(int color){
        this.color=color;
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();//新建画笔
        //画框框
        mPaint.setColor(Color.rgb(169,169,169));//设置颜色
        mPaint.setAntiAlias(true);//抗锯齿
        int round = mHeight/2;
        RectF rectBg = new RectF(0, 0, mWidth, mHeight);//左上角的坐标是（0,0）右下角的坐标是（mWidth, mHeight）
        canvas.drawRoundRect(rectBg, round, round, mPaint);//在画布上绘制这个矩形
        //画背景
        mPaint.setColor(Color.rgb(211,211,211));
        RectF rectBlackBg = new RectF(2, 2, mWidth-2, mHeight-2);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section = currentCount/maxCount;
        RectF rectProgressBg = new RectF(3, 3, (mWidth-3)*section, mHeight-3);
        mPaint.setColor(color);
        canvas.drawRoundRect(rectProgressBg,round,round,mPaint);
    }
    /**
     * 将dip转换成px
     * @param dip
     * @return
     */
    private int dipToPx(int dip) {
        //得到屏幕的密度
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);
        //父视图不对子视图施加任何限制，子视图可以得到任意想要的大小或者子视图的大小最多是widthSpecSize大小
        if(widthSpecMode==MeasureSpec.EXACTLY||widthSpecMode==MeasureSpec.AT_MOST){
            //则设置为widthSpecSize大小
            mWidth=widthSpecSize;
        }else{
            //否则设为0
            mWidth=0;
        }
        //子视图的大小最多是heightSpecSize大小或父视图希望子视图的大小是heightSpecSize中指定的大小
        if(heightSpecMode==MeasureSpec.AT_MOST||heightSpecMode==MeasureSpec.UNSPECIFIED){
            heightSpecMode=dipToPx(15);//15dip的像素
        }else{
            //否则设置为heightSpecSize
            mHeight=heightSpecSize;
        }
        setMeasuredDimension(mWidth,mHeight);
    }
}
