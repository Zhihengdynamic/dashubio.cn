package com.dashubio.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dashubio.R;


/**
 * Created by Administrator on 2015/11/9 0009.
 */
public class LoadingDialog extends Dialog {

    private Context mContext;
    // 加载布局
    private LayoutInflater mInflater;
    // 进度图片
    private ImageView iv_progress;
    // 进度提示语
    private TextView tx_progress_hint;

    private Animation loadAnim;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, boolean cancelable,
                         OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public LoadingDialog(Context context, int theme, boolean isCancelable) {
        super(context, theme);
        mContext = context;
        WindowManager.LayoutParams p = getWindow().getAttributes();
        getWindow().setAttributes(p);

        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        View view = (View) mInflater.inflate(R.layout.dialog_progress_layout,
                null);
        iv_progress = (ImageView) view.findViewById(R.id.iv_progress);
        tx_progress_hint = (TextView) view.findViewById(R.id.tx_progress_hint);

        // 加载动画
        loadAnim = AnimationUtils.loadAnimation(context,
                R.anim.load_progressdialog);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setCancelable(isCancelable);
    }

    public void setProgressDialogHint(String progressDialogHint) {
        tx_progress_hint.setText(progressDialogHint);
        iv_progress.startAnimation(loadAnim);
    }

    public void setProgressDialogHint(int strResId) {
        tx_progress_hint.setText(mContext.getString(strResId));
        iv_progress.startAnimation(loadAnim);
    }


}
