package com.dashubio.ui.fragment.base;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.dashubio.R;
import com.dashubio.app.UserManager;
import com.dashubio.ui.activity.BaseActivity;
import com.dashubio.ui.view.LoadingDialog;
import com.dashubio.utils.Utils;
import com.kanak.emptylayout.EmptyLayout;

import butterknife.Unbinder;

/**
 * Created by Xinbin Zhang on 2015/7/23.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    public static final String TYPE_KEY = "TYPE_KEY";
    public String mType = "";// 类型

    public LoadingDialog mLoadingDialog;
    protected EmptyLayout mEmptyLayout; // this is required to show different layouts (loading or empty or error)

    public String mDeviceUserId = "";
    public String mSessionKey = "";
    public String mSessionValue = "";

    protected Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = (BaseActivity) getActivity();
        mLoadingDialog = new LoadingDialog(mBaseActivity, R.style.dialog, false);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.setProgressDialogHint(R.string.wait_a_moment);

        mDeviceUserId = UserManager.getInstance().getUid();


        mSessionKey = Utils.T_SESSION + mDeviceUserId;
        mSessionValue = UserManager.getInstance().getSession();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    public void showLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    // Triggered when "Empty" button is clicked
    public void onShowEmpty() {
        if (mEmptyLayout != null) {
            mEmptyLayout.showEmpty();
        }
    }

    // Triggered when "Loading" button is clicked
    public void onShowLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.showLoading();
        }
    }

    // Triggered when "Error" button is clicked
    public void onShowError() {
        if (mEmptyLayout != null) {
            mEmptyLayout.showError();
        }
    }


}
