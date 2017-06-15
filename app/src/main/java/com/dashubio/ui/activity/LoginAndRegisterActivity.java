package com.dashubio.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dashubio.R;
import com.dashubio.actions.DeviceActionsCreator;
import com.dashubio.actions.TodoActions;
import com.dashubio.app.ErrorCode;
import com.dashubio.app.UserManager;
import com.dashubio.app.event.AddressSelectedEvent;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.model.ucenter.User;
import com.dashubio.stores.DeviceStore;
import com.dashubio.utils.AddressInitTask;
import com.dashubio.utils.DBManager;
import com.dashubio.utils.MemberInfo;
import com.dashubio.utils.SecondUserEntityUtils;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.WifiUtils;
import com.dashubio.utils.versionUpdate.UpdateInfo;
import com.dashubio.utils.versionUpdate.UpdateInfoService;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.Province;
import okhttp3.Call;

// 登录和注册界面
public class LoginAndRegisterActivity extends BaseActivity implements OnClickListener {
    private DBManager dbManager;


    // 更新版本要用到的一些信息
    private UpdateInfo info;
    private ProgressDialog pBar;

    TextView tv_login, tv_register;
    PopupWindow login_window = null, register_window = null, submit_window = null, forget_window = null;
    TimeCount time;
    //DBOpenHelper databaseHelper;

    private String mAccount;//登录名
    private String mPassword;//登录密码
    private boolean mRememberPassword = false;//是否记住密码

    private int mUserId = -1;//注册成功后返回用户ID

    private TextView region_tv;

    private Province selectedProvince;//选择的省
    public City selectedCity;//选择的市
    // public AddressPicker.County selectedCounty;//选择的县

    private Dispatcher mDispatcher;
    private DeviceActionsCreator mDeviceActionsCreator;
    private DeviceStore mDeviceStore;

    public final static int MSG_SUCCESS = 0;
    public final static int MSG_FAILURE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);
        dbManager = new DBManager(this);

            Toast.makeText(getApplicationContext(), "正在检查版本更新..", Toast.LENGTH_SHORT).show();
            // 自动检查有没有新版本 如果有新版本就提示更新
            new Thread() {
                public void run() {
                    try {
                        UpdateInfoService updateInfoService = new UpdateInfoService(
                                getApplicationContext());
                        info = updateInfoService.getUpDateInfo();
                        handler1.sendEmptyMessage(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
            initDependencies();
            initView();
            time = new TimeCount(60000, 1000);
    }




    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            // 如果有更新就提示
            if (isNeedUpdate()) {
                showUpdateDialog();
            }
        };
    };


    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("有新版本" + info.getVersion());
        builder.setMessage(info.getDescription());
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    downFile(info.getUrl());
                } else {
                    Toast.makeText(getApplicationContext(), "SD卡不可用，请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.create().show();
    }

    private boolean isNeedUpdate() {

        String v = info.getVersion(); // 最新版本的版本号
        Toast.makeText(getApplicationContext(), v, Toast.LENGTH_SHORT).show();
        if (v.equals(getVersion())) {
            return false;
        } else {
            return true;
        }
    }

    // 获取当前版本的版本号
    private String getVersion() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);

            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";

        }
    }

    void downFile(final String url) {
        pBar = new ProgressDialog(LoginAndRegisterActivity.this);    //进度条，在下载的时候实时更新进度，提高用户友好度
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setTitle("正在更新");
        pBar.setMessage("请稍候...");
        pBar.setProgress(0);
        pBar.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url(url).build().execute(new FileCallBack(Environment.getExternalStorageDirectory()+"/dashu" , "dashu.apk") {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        down();
                    }


                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);

                        int mtotal = (int) total;
                        pBar.setMax(mtotal);
                        pBar.setProgress((int)(mtotal* progress));
                    }
                });
            }
        }).start();
    }

    void down() {
        handler1.post(new Runnable() {
            public void run() {
                pBar.cancel();
                update();
            }
        });
    }

    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory()+"/dashu", "dashu.apk")),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDispatcher.register(this);
        mDispatcher.register(mDeviceStore);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDispatcher.unregister(this);
        mDispatcher.unregister(mDeviceStore);
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mDeviceActionsCreator = DeviceActionsCreator.getInstance(DeviceActionsCreator.class);
        mDeviceStore = DeviceStore.getInstance(DeviceStore.class);
    }


    private void initView() {
        // TODO Auto-generated method stub
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_login: // 登录
                showLoginWindow();
                break;

            case R.id.tv_register: // 注册
                showRegisterWindow(v);
                break;

            default:
                break;
        }
    }

    // 注册窗口
    Button btn_code;

    //设备注册
    private void showRegisterWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_register, null);
        register_window = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        register_window.setBackgroundDrawable(cd);

        register_window.setOutsideTouchable(true);
        register_window.setFocusable(true);
        register_window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        register_window.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);

        ImageView cancle = (ImageView) contentView.findViewById(R.id.cancle);
        final EditText et_imei = (EditText) contentView.findViewById(R.id.et_imei);
        final EditText et_phone = (EditText) contentView.findViewById(R.id.et_phone);
        final EditText et_password = (EditText) contentView.findViewById(R.id.et_password);
        final EditText et_password_again = (EditText) contentView.findViewById(R.id.et_password_again);
        final EditText et_code = (EditText) contentView.findViewById(R.id.et_code);
        btn_code = (Button) contentView.findViewById(R.id.btn_code);
        Button btn_reg = (Button) contentView.findViewById(R.id.btn_reg);

        cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                register_window.dismiss();
                time.cancel();
            }
        });
        btn_code.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String telephone = et_phone.getText().toString();
                if (TextUtils.isEmpty(telephone)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.input_reg_telphone);
                    return;
                }
                mDeviceActionsCreator.toGetVerificationCode(telephone);

                if (time != null) {
                    time.cancel();
                }
                time.start();
            }
        });
        btn_reg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //设备串号
                String serialNumber = et_imei.getText().toString();
                if (TextUtils.isEmpty(serialNumber)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_enter_the_serial_number_of_equipment);
                    return;
                }

                //手机号
                String telephone = et_phone.getText().toString();
                if (TextUtils.isEmpty(telephone)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.input_reg_telphone);
                    return;
                }

                //密码
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_input_password);
                    return;
                }

                //再次输入密码
                String confirmPassword = et_password_again.getText().toString();
                if (!password.equals(confirmPassword)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.confirm_password_error);
                    return;
                }

                //验证码
                String verificationCode = et_code.getText().toString();
                if (TextUtils.isEmpty(verificationCode)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_input_verification_code);
                    return;
                }

                mDeviceActionsCreator.toDeviceRegister(serialNumber, telephone, password, verificationCode);
            }
        });
    }

    // 登录窗口
    private void showLoginWindow() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_login, null);
        login_window = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        login_window.setBackgroundDrawable(cd);

        login_window.setOutsideTouchable(true);
        login_window.setFocusable(true);
        //设置弹出窗体需要软键盘，
        login_window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        login_window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        login_window.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        final EditText et_account = (EditText) contentView.findViewById(R.id.et_account);
        final EditText et_password = (EditText) contentView.findViewById(R.id.et_password);

        Button btn_login = (Button) contentView.findViewById(R.id.btn_login);
        Button btn_reg = (Button) contentView.findViewById(R.id.btn_reg);

        ImageView cancle = (ImageView) contentView.findViewById(R.id.cancle);
        CheckBox cb_remember = (CheckBox) contentView.findViewById(R.id.cb_remember);

            cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mRememberPassword = true;
                    } else {
                        mRememberPassword = false;
                    }
                }
            });


            if (UserManager.getInstance().isLogin()) {
                mAccount = UserManager.getInstance().getUsername();
                mPassword = UserManager.getInstance().getPassword();
                et_account.setText(mAccount);
                et_password.setText(mPassword);
                mRememberPassword = true;
                cb_remember.setChecked(true);
            }


            TextView tv_forget = (TextView) contentView.findViewById(R.id.tv_forget);
        //判断有无网络
        if (WifiUtils.isNetworkConnected(this)){
            btn_login.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    //用户名
                    mAccount = et_account.getText().toString();
                    if (TextUtils.isEmpty(mAccount)) {
                        ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_input_username);
                        return;
                    }

                    //密码
                    mPassword = et_password.getText().toString();
                    if (TextUtils.isEmpty(mPassword)) {
                        ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_input_password);
                        return;
                    }

                    mDeviceActionsCreator.toDeviceLogin(mAccount, mPassword);

                }
            });
            btn_reg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    login_window.dismiss();
                    showRegisterWindow(v);
                }
            });
            cancle.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    login_window.dismiss();
                }
            });
            tv_forget.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    login_window.dismiss();
                    showForgetPswWindow(v);
                }
            });
        }else{

            btn_login.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    //用户名
                    mAccount = et_account.getText().toString();
                    if (TextUtils.isEmpty(mAccount)) {
                        ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_input_username);
                        return;
                    }

                    //密码
                    mPassword = et_password.getText().toString();
                    if (TextUtils.isEmpty(mPassword)) {
                        ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_input_password);
                        return;
                    }

                    ArrayList<MemberInfo> infoList = new ArrayList<>();
                    infoList = dbManager.searchData(mAccount,mPassword);

                    String result = "";
                    String user_id = "";
                    for (MemberInfo info : infoList) {
                        result = result + String.valueOf(info._id) + "|" + info.name
                                + "|" + info.pwds ;
                        result = result + "\n" + "------------------------------------------" + "\n";
                        user_id = String.valueOf(info._id);
                    }

                    SecondUserEntityUtils.userId = user_id;


                    //这里判断数据库里有没有对应的账户密码

                    if (result.indexOf(mAccount)!=-1&&result.indexOf(mPassword)!=-1){
                        Log.e("DBM", "验证登录用户已存在" );
                        HomeActivity.startWithTypeAndSecondUser(true, null, LoginAndRegisterActivity.this);
                    }else {
                        Log.e("DBM", "验证登录用户账户或者密码不正确" );
                        Toast.makeText(LoginAndRegisterActivity.this, "你还未在线登陆过", Toast.LENGTH_SHORT).show();
                    }



                }
            });
        }

    }

    // 忘记密码窗口
    private void showForgetPswWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_forget_password, null);
        forget_window = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        forget_window.setBackgroundDrawable(cd);

        forget_window.setOutsideTouchable(true);
        forget_window.setFocusable(true);
        forget_window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        forget_window.showAtLocation((View) view.getParent(), Gravity.CENTER, 0, 0);

        ImageView cancle = (ImageView) contentView.findViewById(R.id.cancle);
        final LinearLayout ll_one = (LinearLayout) contentView.findViewById(R.id.ll_one);
        final LinearLayout ll_two = (LinearLayout) contentView.findViewById(R.id.ll_two);
        final EditText et_phone = (EditText) contentView.findViewById(R.id.et_phone);
        final EditText et_code = (EditText) contentView.findViewById(R.id.et_code);
        btn_code = (Button) contentView.findViewById(R.id.btn_code);
        EditText et_password = (EditText) contentView.findViewById(R.id.et_password);
        EditText et_password_again = (EditText) contentView.findViewById(R.id.et_password_again);
        EditText et_imei = (EditText) contentView.findViewById(R.id.et_imei);
        final Button btn_next = (Button) contentView.findViewById(R.id.btn_next);
        final Button btn_ok = (Button) contentView.findViewById(R.id.btn_ok);

        cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                forget_window.dismiss();
            }
        });
        btn_next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!TextUtils.isEmpty(et_phone.getText().toString())
                        && !TextUtils.isEmpty(et_code.getText().toString())) {
                    ll_one.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);
                    ll_two.setVisibility(View.VISIBLE);
                    btn_ok.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_code.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "验证码已发送，请注意查收！",
                        Toast.LENGTH_LONG).show();
                if (time != null) {
                    time.cancel();
                }
                time.start();
            }
        });
        btn_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "密码设置成功！",
                        Toast.LENGTH_LONG).show();
                forget_window.dismiss();
            }
        });
    }

    // 提交注册信息窗口
    private void showInfoWindow() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_register_info, null);
        submit_window = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        submit_window.setBackgroundDrawable(cd);

        submit_window.setOutsideTouchable(true);
        submit_window.setFocusable(true);
        submit_window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        submit_window.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        ImageView cancle = (ImageView) contentView.findViewById(R.id.cancle);
        Button btn_submit = (Button) contentView.findViewById(R.id.btn_submit);
        final EditText et_company = (EditText) contentView.findViewById(R.id.et_company);
        final EditText et_contact = (EditText) contentView.findViewById(R.id.et_contact);
        final EditText et_contel = (EditText) contentView.findViewById(R.id.et_contel);
        final EditText et_address = (EditText) contentView.findViewById(R.id.et_address);
        region_tv = (TextView) contentView.findViewById(R.id.region_tv);
        region_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedProvinceName = selectedProvince == null ? "" : selectedProvince.getAreaName();
                selectedProvinceName = selectedProvinceName == null ? "" : selectedProvinceName;
                String selectedCityName = selectedCity == null ? "" : selectedCity.getAreaName();
                selectedCityName = selectedCityName == null ? "" : selectedCityName;
                new AddressInitTask(LoginAndRegisterActivity.this, true).execute(selectedProvinceName, selectedCityName);
            }
        });
        cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                submit_window.dismiss();
            }
        });
        btn_submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //单位名称
                String companyName = et_company.getText().toString();
                companyName = companyName == null ? "" : companyName;

                //联系人
                String contact = et_contact.getText().toString();
                if (TextUtils.isEmpty(contact)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_enter_contact);
                    return;
                }

                //联系电话
                String contactPhone = et_contel.getText().toString();
                if (TextUtils.isEmpty(contactPhone)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_enter_contact_phone);
                    return;
                }

                //省份和城市
                if (selectedProvince == null) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_select_area);
                    return;
                }
                String province = selectedProvince.getAreaName();
                String city = selectedCity.getAreaName();

                //详细地址
                String detailAddress = et_address.getText().toString();
                if (TextUtils.isEmpty(detailAddress)) {
                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.please_enter_detail_address);
                    return;
                }

                Map<String, String> options = new HashMap<>();
                String userId = mUserId > 0 ? String.valueOf(mUserId) : "";
                options.put("euid", userId);
                options.put("company", companyName);
                options.put("name", contact);
                options.put("tel", contactPhone);
                options.put("province", province);
                options.put("city", city);
                options.put("address", detailAddress);

                mDeviceActionsCreator.toDeviceAddUserDetail(options);
            }
        });
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_code.setBackgroundColor(Color.parseColor("#B6B6D8"));
            btn_code.setClickable(false);
            btn_code.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            btn_code.setText("重新获取验证码");
            btn_code.setClickable(true);
            btn_code.setBackgroundColor(getResources()
                    .getColor(R.color.blue_dep));

        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (login_window != null) {
            login_window.dismiss();
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceStoreChange(DeviceStore.DeviceStoreChangeEvent event) {
        String currentStatus = event.getCurrentStatus();//获取当前状态

        if (TextUtils.isEmpty(currentStatus)) {
            return;
        }

        //开始请求
        if (TodoActions.DEVICE_LOADING_START.equals(currentStatus)) {
            showLoadingDialog();
            return;
        }

        //下面为接收到服务器返回响应

        //服务器连接异常
        if (TodoActions.DEVICE_SERVER_CONNECTION_ERROR.equals(currentStatus)) {
            hideLoadingDialog();
            HttpResult errorResp = event.getHttpResp();
            ToastUtils.show(this, errorResp.getMsg());
            return;
        }

        //处理请求完成
        hideLoadingDialog();
        HttpResult resp = event.getHttpResp();
        if (resp == null) {
            return;
        }
        if (ErrorCode.SUCCESS.equals(resp.getStatus())) {
            switch (currentStatus) {
                case TodoActions.DEVICE_LOGIN_FETCH_DATA_COMPLETE: //登录
                    User user = (User) resp.getData();
                    user.setUsername(mAccount);
                    user.setPassword(mPassword);
                    UserManager.getInstance().saveUser(user);
                    HomeActivity.startWithTypeAndSecondUser(true, null, LoginAndRegisterActivity.this);

                    //如果数据库中有该用户，那么不用在将该用户添加到数据库中
                    ArrayList<MemberInfo> infoList = new ArrayList<>();
                    infoList = dbManager.searchData(mAccount,mPassword);

                    String result = "";
                    for (MemberInfo info : infoList) {
                        result = result + String.valueOf(info._id) + "|" + info.name
                                + "|" + info.pwds ;
                        result = result + "\n" + "------------------------------------------" + "\n";
                    }

                    if (result.indexOf(mAccount)!=-1&&result.indexOf(mPassword)!=-1) {
                    }else {
                        dbManager.add(mAccount, mPassword);
                        Toast.makeText(this, "成功存入本地数据库", Toast.LENGTH_SHORT).show();

                    }

                    ToastUtils.show(LoginAndRegisterActivity.this, R.string.successful_operation);
                    login_window.dismiss();
                    break;

                case TodoActions.GET_VERIFICATION_CODE_COMPLETE: //发送短信验证码接口
                    break;




                case TodoActions.DEVICE_REGISTER_FETCH_DATA_COMPLETE: //设备注册
                    ToastUtils.show(this, R.string.successful_operation);
                    mUserId = resp.getErrorCode();
                    register_window.dismiss();
                    showInfoWindow();
                    break;

                case TodoActions.DEVICE_ADD_USER_DETAIL_COMPLETE: //设备持有者详细信息
                    ToastUtils.show(this, R.string.register_success_please_wait_for_background_audit);
                    submit_window.dismiss();
                    showLoginWindow();
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = handleErrorResp(resp);
        }
    }


    /**
     * 地址选择
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddressSelectedEvent(AddressSelectedEvent event) {
        selectedProvince = event.getProvince();
        selectedCity = event.getCity();
        //selectedCounty = event.getCounty();
        if (region_tv != null) {
            region_tv.setText(selectedProvince.getAreaName() + selectedCity.getAreaName());
        }
    }

}
