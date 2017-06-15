package com.dashubio.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.dashubio.ui.fragment.second.SecondMeasureFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/13.
 */

public class PathView extends CardiographView {

    public static ArrayList<Float> arrast = new ArrayList();


    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static void addDATA(float data){
        arrast.add(data);

    }

    @Override
    protected void onDraw(Canvas canvas) {


        canvas.translate(0,mHeight/2);

        //用path模拟一个心电图样式
        mPath.moveTo(0,0);
        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);

        float tmp = 0;

        float endY = 0;
        Log.d("tag2","endY = "+endY);
        for (int i = 0; i < arrast.size(); i++) {
                endY = arrast.get(i)/50;//按屏幕比例缩小Y轴比例

            if (tmp>8000){
                tmp=8000;//指定长度8000，避免过多占用内存
            }else {
                tmp = (float) (tmp+0.5);
                mPath.lineTo(tmp, endY);
            }
        }

        canvas.drawPath(mPath,mPaint);

//当长度大于800的时候，心电图开始移动
        if (tmp>800){
            scroll();
        }




        invalidate();
//        postInvalidateDelayed(300);
    }



    public boolean isStoping = true;

    private void scroll(){
        if (isStoping){
            scrollBy(30,0);
        }else {
            scrollBy(0,0);
        }
    }
}
