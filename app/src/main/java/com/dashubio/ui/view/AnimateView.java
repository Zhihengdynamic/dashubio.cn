package com.dashubio.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import org.afree.data.xy.XYSeries;
import org.afree.data.xy.XYSeriesCollection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/17.
 */

public class AnimateView extends View {
    ArrayList<Float> arrast = new ArrayList();
    Path path;

    float startX = 10;
    float startY = 10;
    float stopX = 10;
    float stopY = 10;
    Paint paint;

    public AnimateView(Context context) {
        super(context);
        paint = new Paint();
        path = new Path();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    public  void addDATA(float data){
        arrast.add(data);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(320, 320);

        path.moveTo(0,20);
        /*int tmp = 0;
        for (int i = 0; i < 100; i++) {
            path.lineTo(tmp+10, 320 / 2+100);
            path.lineTo(tmp+20, 320 / 2-150);
            path.lineTo(tmp+22, 320 / 2-145);
            path.lineTo(tmp+23, 320 / 2-160);
            path.lineTo(tmp+25, 320 / 2+30);
            path.lineTo(tmp+30, 320 / 2);
            path.lineTo(tmp+40, 320 / 2);
            tmp = tmp+40;
        }*/


        int tmp = 0;
        for (int i = 0; i < arrast.size(); i++) {
            path.lineTo(tmp+40, arrast.get(i));
            tmp = tmp+40;
        }

        canvas.drawPath(path,paint);
        invalidate();//通过调用这个方法让系统自动刷新视图
        scrollBy(2,0);
    }
    private static final String DEFAULT_USER_NAME = "ECG Chart";
    private XYSeriesCollection mSeries;
    public XYSeriesCollection getDataset() {
        if (null == mSeries) {
            XYSeries series1 = new XYSeries(DEFAULT_USER_NAME);

            mSeries = new XYSeriesCollection(series1);
        }
        return mSeries;
    }
}
