package com.example.xw.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by xw on 2016/9/26.
 */
public class MyRadar extends View {
    private int count=6;  //六边形，数据个数6
    private float angle= (float) (Math.PI/3);  //60度
    private double[] data={50,50,50,50,50,50,50}; //默认数据
    private float maxValue=100;     //默认最大值
    private String[] titles={"a","b","c","d","e","f"};  //默认标题

    private Paint radarPaint;                //蜘蛛网画笔
    private  Paint valuePaint;               //内容区画笔
    private Paint textPaint;                 //文字画笔

    private float radius;                   //网格最大半径
    private int centerX;                  //中心X
    private int centerY;                  //中心Y


    public MyRadar(Context context) {
        this(context,null);
    }
    public MyRadar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRadar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(sp2px(300),sp2px(300));
        }
        else if (widthMeasureSpec==MeasureSpec.AT_MOST){
            setMeasuredDimension(sp2px(250),heightSpecSize);
        }else if (heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,sp2px(250));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w)/2*0.65f;
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void init() {
        radarPaint=new Paint();
        radarPaint.setAntiAlias(true);
        radarPaint.setColor(Color.GRAY);
        radarPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(sp2px(15));
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawHexagon(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for(int i=0;i<count;i++){
            double percent = data[i]/maxValue;
            float x = (float) (centerX+radius*Math.cos(angle*i)*percent);
            float y = (float) (centerY+radius*Math.sin(angle*i)*percent);
            if(i==0){
                path.moveTo(x, centerY);
            }else{
                path.lineTo(x,y);
            }
            //绘制小圆点
            canvas.drawCircle(x,y,5,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(127);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }


    private void drawHexagon(Canvas canvas) {
        Path path=new Path();
        float r=radius/(count-1);
        for (int i = 0; i <count ; i++) {

            float R=r*i;
            path.reset();
            for (int j=0;j<count;j++){
                if(j==0){
                    path.moveTo(centerX+R,centerY);
                }
                else{
                    float x= (float) (centerX+R*Math.cos(angle*j));
                    float y= (float) (centerY+R*Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }

            path.close();
            canvas.drawPath(path,radarPaint);

        }
        for (int i = 0; i <count ; i++) {
            path.reset();
            path.moveTo(centerX,centerY);
            float x= (float) (centerX+radius*Math.cos(angle*i));
            float y= (float) (centerY+radius*Math.sin(angle*i));
            path.lineTo(x,y);
            canvas.drawPath(path,radarPaint);
        }

    }

    private void drawText(Canvas canvas) {

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i <count ; i++) {
            float x= (float) (centerX+(radius+fontHeight/2)*Math.cos(angle*i));
            float y= (float) (centerY+(radius+fontHeight/2)*Math.sin(angle*i));

            if(angle*i>=0&&angle*i<=Math.PI/2){
                canvas.drawText(titles[i], x,y+fontHeight/2,textPaint);
            } else if(angle*i>Math.PI/2&&angle*i<=Math.PI){
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i], x-dis,y+fontHeight/2,textPaint);
            }
            else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }else if(angle*i>=3*Math.PI/2&&angle*i<=Math.PI*2){
                canvas.drawText(titles[i], x,y,textPaint);
            }


        }
    }


    //设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    //设置数值
    public void setData(double[] data) {
        this.data = data;
    }


    public float getMaxValue() {
        return maxValue;
    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }
    //设置标题颜色
    public void setTextPaintColor(int color){
        textPaint.setColor(color);
    }

    //设置覆盖局域颜色
    public void setValuePaintColor(int color){
        valuePaint.setColor(color);

    }
    //设置雷达图颜色
    public void setMainPaintColor(int color){
        radarPaint.setColor(color);
    }


    private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
    //sp转px单位
    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
}
}
