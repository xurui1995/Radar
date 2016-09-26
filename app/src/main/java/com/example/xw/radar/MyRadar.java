package com.example.xw.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xw on 2016/9/26.
 */
public class MyRadar extends View {
    private int count=6;  //六边形，数据个数6
    private float angle= (float) (Math.PI/3);
    private double[] data={100,60,60,60,100,50,10,20}; //
    private double maxValue=100;
    private String[] titles={"发球","经验","防守","技巧","速度","力量"};

    private Paint radarPaint;
    private  Paint valuePaint;
    private Paint textPaint;

    private float radius;                   //网格最大半径
    private int centerX;                  //中心X
    private int centerY;                  //中心Y


    public MyRadar(Context context) {
        this(context,null);

    }

    public MyRadar(Context context, AttributeSet attrs) {
        this(context, null,0);
    }

    public MyRadar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w)/2*0.65f;
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
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
        textPaint.setTextSize(15);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawHexagon(canvas);
        drawText(canvas);
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
            }else if(angle*i>=3*Math.PI/2&&angle*i<=Math.PI*2){
                canvas.drawText(titles[i], x,y,textPaint);
            }else if(angle*i>Math.PI/2&&angle*i<=Math.PI){

                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y+fontHeight/2,textPaint);
            }else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }
           /* if (angle*i>=0&&angle*i<=Math.PI/2){
                canvas.drawText(titles[i],x,y,textPaint);
            }
            else if (angle*i>Math.PI/2&&angle*i<=Math.PI){
                float length=textPaint.measureText(titles[i]);
                canvas.drawText(titles[i],x-length,y,textPaint);
            }
            else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){
                float length=textPaint.measureText(titles[i]);
                canvas.drawText(titles[i],x-length,y+fontHeight,textPaint);
            }
            else{
                canvas.drawText(titles[i],x,y+fontHeight,textPaint);
            }*/
        }
    }
}
