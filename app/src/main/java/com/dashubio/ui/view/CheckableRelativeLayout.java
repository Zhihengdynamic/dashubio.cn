package com.dashubio.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dashubio.R;

public class CheckableRelativeLayout extends RelativeLayout implements Checkable {

    private Context mContext;
    private boolean mChecked;
    private TextView mTvValue = null;
    private TextView mTvUnit = null;

    public CheckableRelativeLayout(Context context) {
        this(context, null, 0);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.checkable_relative_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvValue = (TextView) findViewById(R.id.value_tv);
    }


    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        if (checked) {
            mTvValue.setTextColor(mContext.getResources().getColor(android.R.color.white));
            mTvUnit.setTextColor(mContext.getResources().getColor(android.R.color.white));
            setBackgroundDrawable(getResources().getDrawable(R.drawable.gridview_select_background));
        } else {
            mTvValue.setTextColor(mContext.getResources().getColor(R.color.text_c808080));
            mTvUnit.setTextColor(mContext.getResources().getColor(R.color.text_c808080));
            setBackgroundDrawable(getResources().getDrawable(R.drawable.gridview_normal_background));
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public void setTextContent(String text) {
        if (mTvValue != null) {
            mTvValue.setText(text);
        }
    }

}
