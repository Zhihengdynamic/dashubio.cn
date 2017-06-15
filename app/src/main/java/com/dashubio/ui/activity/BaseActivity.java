package com.dashubio.ui.activity;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.app.ErrorCode;
import com.dashubio.commons.ActivityCollector;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.ui.activity.AllUser.RegisterActivity;
import com.dashubio.ui.view.LoadingDialog;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

// activity基类
public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.header_left_container)
    protected View mHeaderLeft;

    @Nullable
    @BindView(R.id.header_title)
    protected TextView mHeaderTitle;

    @Nullable
    @BindView(R.id.header_right_container)
    protected View mHeaderRight;

    @Nullable
    @BindView(R.id.header_right_text)
    protected TextView mHeaderRightText;

    public LoadingDialog mLoadingDialog;

    //public String mUserId = "";

    public SecondUser mSecondUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        } else {
            if (this instanceof RegisterActivity) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            }
        }
        ActivityCollector.addActivity(this);

        mLoadingDialog = new LoadingDialog(this, R.style.dialog, false);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.setProgressDialogHint(R.string.wait_a_moment);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub  
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void closeAll() {
        ActivityCollector.finishAll();
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

    @Optional
    @OnClick(R.id.header_left_container)
    void goBack() {
        finish();
    }

    /**
     * 处理错误响应
     *
     * @param resp
     * @return
     */
    public boolean handleErrorResp(HttpResult resp) {
        if (resp == null) {
            return false;
        }

        switch (resp.getErrorCode()) {
            case ErrorCode.CONNECTION_TIMEOUT_OR_ILLEGAL_REQUEST://连接超时，或非法请求
                ToastUtils.show(this, R.string.connection_timeout_or_illegal_request);
                return true;

            case ErrorCode.PARAMETER_ERROE://参数错误
                ToastUtils.show(this, R.string.parameter_error);
                return true;

            case ErrorCode.PARAMETER_COMMIT_FAILED://参数提交失败
                ToastUtils.show(this, R.string.parameter_commit_failed);
                return true;

            case ErrorCode.DATA_EMPTY://数据为空
                ToastUtils.show(this, R.string.data_empty);
                return true;

            case ErrorCode.USERID_CANNOT_EMPTY://用户id不可为空
                ToastUtils.show(this, R.string.userid_cannot_empty);
                return true;

            case ErrorCode.COMPANY_NAME_CANNOT_EMPYT://公司名称不可为空
                ToastUtils.show(this, R.string.company_name_cannot_empty);
                return true;

            case ErrorCode.USERNAME_CANNOT_EMPYT://用户姓名不可为空
                ToastUtils.show(this, R.string.username_cannot_empty);
                return true;

            case ErrorCode.CONTACT_PHONE_CANNOT_EMPYT://联系电话不可为空
                ToastUtils.show(this, R.string.contact_phone_cannot_empty);
                return true;

            case ErrorCode.PROVINCE_CANNOT_EMPYT://省份不可为空
                ToastUtils.show(this, R.string.province_cannot_empty);
                return true;

            case ErrorCode.CITY_CANNOT_EMPYT://城市不可为空
                ToastUtils.show(this, R.string.city_cannot_empty);
                return true;

            case ErrorCode.DETAIL_ADDRESS_CANNOT_EMPYT://详细地址不可为空
                ToastUtils.show(this, R.string.detail_address_cannot_empty);
                return true;

            case ErrorCode.FORMAT_ERROR://格式错误
                ToastUtils.show(this, R.string.format_error);
                return true;

            case ErrorCode.DEVICE_HAS_BEEN_REGISTERED://设备已注册
                ToastUtils.show(this, R.string.device_has_been_registered);
                return true;

            case ErrorCode.DEVICE_NAME_NOT_VALID://设备名不合法
                ToastUtils.show(this, R.string.device_name_not_valid);
                return true;

            case ErrorCode.PHONE_HAS_BEEN_OCCUPIED://手机号已被占用
                ToastUtils.show(this, R.string.phone_has_been_occupied);
                return true;

            case ErrorCode.PASSWORD_LENGTH_NOT_VALID://密码长度不合法
                ToastUtils.show(this, R.string.password_length_not_valid);
                return true;

            case ErrorCode.PHONE_NOT_LEGAL://手机号不合法
                ToastUtils.show(this, R.string.phone_not_legal);
                return true;

            case ErrorCode.SMS_VERIFICATION_CODE_ERROR://短信验证码错误
                ToastUtils.show(this, R.string.sms_verification_code_error);
                return true;

            case ErrorCode.USER_NOT_EXIST://用户不存在
                ToastUtils.show(this, R.string.user_not_exist);
                return true;

            case ErrorCode.PASSWORD_ERROR://密码错误
                ToastUtils.show(this, R.string.password_error);
                return true;

            case ErrorCode.USER_ID_CARD_HAS_BEEN_REGISTERED://用户身份证已注册
                ToastUtils.show(this, R.string.user_id_card_has_been_registered);
                return true;

            case ErrorCode.USER_ID_CARD_CANNOT_EMPTY://用户身份证id不可为空
                ToastUtils.show(this, R.string.user_id_card_cannot_empty);
                return true;

            case ErrorCode.USER_NAME_CANNOT_EMPTY://用户姓名不可为空
                ToastUtils.show(this, R.string.username_cannot_empty);
                return true;

            case ErrorCode.USER_GENDER_CANNOT_EMPTY://用户性别不可为空
                ToastUtils.show(this, R.string.user_gender_cannot_empty);
                return true;

            case ErrorCode.PHONE_CANNOT_EMPTY://电话不可为空
                ToastUtils.show(this, R.string.phone_cannot_empty);
                return true;

            default:
                ToastUtils.show(this, R.string.network_exception_please_try_again_later);
                return true;
        }
    }



}
