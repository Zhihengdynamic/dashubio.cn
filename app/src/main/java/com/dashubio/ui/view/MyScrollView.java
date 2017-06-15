package com.dashubio.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/20.
 */

public class MyScrollView extends HorizontalScrollView {


    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);




//            scroll();



    }

    public boolean isStoping = true;



    private void scroll() {
        if (isStoping){
            scrollBy(10,0);
        }else {
            scrollBy(0,0);
        }
    }

    @Override
    public void fling(int velocityX) {
        super.fling(300);
    }
}
