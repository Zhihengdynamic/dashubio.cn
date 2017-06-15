package com.dashubio.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dashubio.R;
import com.dashubio.commons.Session;
import com.dashubio.ui.fragment.HelpFragment2;
import com.dashubio.ui.fragment.IndexFragment;
import com.dashubio.ui.fragment.LoginFragment;
import com.dashubio.ui.fragment.ManagerFragment;
import com.dashubio.ui.fragment.MessageFragment;
import com.dashubio.ui.fragment.RegisterFragment;
import com.dashubio.ui.fragment.WarningFragment;
import com.dashubio.ui.view.RoundImageView;

// 主界面
public class MainActivity extends BaseActivity implements OnClickListener {

    RoundImageView img_user;
    //	AutoScrollTextView tv_scroll;
    TextView tv_company_name, tv_quit, tv_user_register, tv_user_login,
            tv_user_manage, tv_warning_setting, tv_help, tv_user_msg, tv_scroll;
    FrameLayout content;
    int tag = 0;
    private final String ACTION_NAME = "com.action.change";  // 通知activity切换fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setFragment(tag);
        setBtnBackground(tag);
        //注册广播
        registerBoradcastReceiver();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (Session.isBack) {
            tag = 0;
            setFragment(tag);
            setBtnBackground(tag);
            Session.isBack = false;
        }
    }

    private void setFragment(int which) {
        // TODO Auto-generated method stub
        FragmentManager fragment = getFragmentManager();
        FragmentTransaction transacction = fragment.beginTransaction();
        switch (which) {
            case 0: // 首页
                IndexFragment frament1 = new IndexFragment();
                transacction.replace(R.id.content, frament1);
                break;
            case 1: // 用户注册
                RegisterFragment frament2 = new RegisterFragment();
                transacction.replace(R.id.content, frament2);
                break;
            case 2: // 用户登录
                LoginFragment frament3 = new LoginFragment();
                transacction.replace(R.id.content, frament3);
                break;
            case 3: // 用户管理
                ManagerFragment frament4 = new ManagerFragment();
                transacction.replace(R.id.content, frament4);
                break;
            case 4: // 预警设置
                WarningFragment frament5 = new WarningFragment();
                transacction.replace(R.id.content, frament5);
                break;
            case 5: // 使用帮助
//                HelpFragment frament6 = new HelpFragment();
                HelpFragment2 frament6 = new HelpFragment2();
                transacction.replace(R.id.content, frament6);
                break;
            case 6: // 用户留言
                MessageFragment frament7 = new MessageFragment();
                transacction.replace(R.id.content, frament7);
                break;

            default:
                break;
        }
        transacction.commitAllowingStateLoss();
    }

    private void initView() {
        // TODO Auto-generated method stub
        img_user = (RoundImageView) findViewById(R.id.img_user);
//    	tv_scroll = (AutoScrollTextView) findViewById(R.id.tv_scroll);
        tv_scroll = (TextView) findViewById(R.id.tv_scroll);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_quit = (TextView) findViewById(R.id.tv_quit);
        tv_user_register = (TextView) findViewById(R.id.tv_user_register);
        tv_user_login = (TextView) findViewById(R.id.tv_user_login);
        tv_user_manage = (TextView) findViewById(R.id.tv_user_manage);
        tv_warning_setting = (TextView) findViewById(R.id.tv_warning_setting);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_user_msg = (TextView) findViewById(R.id.tv_user_msg);
        tv_quit.setOnClickListener(this);
        tv_user_register.setOnClickListener(this);
        tv_user_login.setOnClickListener(this);
        tv_user_manage.setOnClickListener(this);
        tv_warning_setting.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_user_msg.setOnClickListener(this);

//    	tv_scroll.init(getWindowManager());
//    	tv_scroll.startScroll();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tv_quit: // 退出
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_user_register: // 用户注册
                tag = 1;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_user_login: // 用户登录
                tag = 2;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_user_manage: // 用户管理
                tag = 3;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_warning_setting: // 预警设置
                tag = 4;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_help: // 使用帮助
                tag = 5;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_user_msg: // 用户留言
                tag = 6;
                setBtnBackground(tag);
                setFragment(tag);
                break;

            default:
                break;
        }
    }

    public void setBtnBackground(int which) {
        switch (which) {
            case 0:
                tv_user_register.setBackgroundResource(
                        R.color.menu_color);
                tv_user_login.setBackgroundResource(
                        R.color.menu_color);
                tv_user_manage.setBackgroundResource(
                        R.color.menu_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.menu_color);
                tv_help.setBackgroundResource(
                        R.color.menu_color);
                tv_user_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 1:
                tv_user_register.setBackgroundResource(
                        R.color.btn_color);
                tv_user_login.setBackgroundResource(
                        R.color.menu_color);
                tv_user_manage.setBackgroundResource(
                        R.color.menu_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.menu_color);
                tv_help.setBackgroundResource(
                        R.color.menu_color);
                tv_user_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 2:
                tv_user_register.setBackgroundResource(
                        R.color.menu_color);
                tv_user_login.setBackgroundResource(
                        R.color.btn_color);
                tv_user_manage.setBackgroundResource(
                        R.color.menu_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.menu_color);
                tv_help.setBackgroundResource(
                        R.color.menu_color);
                tv_user_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 3:
                tv_user_register.setBackgroundResource(
                        R.color.menu_color);
                tv_user_login.setBackgroundResource(
                        R.color.menu_color);
                tv_user_manage.setBackgroundResource(
                        R.color.btn_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.menu_color);
                tv_help.setBackgroundResource(
                        R.color.menu_color);
                tv_user_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 4:
                tv_user_register.setBackgroundResource(
                        R.color.menu_color);
                tv_user_login.setBackgroundResource(
                        R.color.menu_color);
                tv_user_manage.setBackgroundResource(
                        R.color.menu_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.btn_color);
                tv_help.setBackgroundResource(
                        R.color.menu_color);
                tv_user_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 5:
                tv_user_register.setBackgroundResource(
                        R.color.menu_color);
                tv_user_login.setBackgroundResource(
                        R.color.menu_color);
                tv_user_manage.setBackgroundResource(
                        R.color.menu_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.menu_color);
                tv_help.setBackgroundResource(
                        R.color.btn_color);
                tv_user_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 6:
                tv_user_register.setBackgroundResource(
                        R.color.menu_color);
                tv_user_login.setBackgroundResource(
                        R.color.menu_color);
                tv_user_manage.setBackgroundResource(
                        R.color.menu_color);
                tv_warning_setting.setBackgroundResource(
                        R.color.menu_color);
                tv_help.setBackgroundResource(
                        R.color.menu_color);
                tv_user_msg.setBackgroundResource(
                        R.color.btn_color);
                break;
            default:
                break;
        }
    }


    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                tag = 3;
                setFragment(tag);
            }
        }
    };


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
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


}
