package com.dashubio.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dashubio.R;
import com.dashubio.commons.Session;
import com.dashubio.model.ucenter.User;
import com.dashubio.ui.fragment.second.SecondHealthFilesFragment;
import com.dashubio.ui.fragment.second.SecondHealthReportFragment;
import com.dashubio.ui.fragment.second.SecondHealthWarningFragment;
import com.dashubio.ui.fragment.second.SecondHistoryFragment;
import com.dashubio.ui.fragment.second.SecondIndexFragment;
import com.dashubio.ui.fragment.second.SecondMeasureFragment;
import com.dashubio.ui.fragment.second.SecondMessageFragment;
import com.dashubio.ui.view.RoundImageView;


// 第二登陆人主界面
public class SecondMainActivity extends BaseActivity implements OnClickListener {


    private static final String ARG_USER = "arg_user";


    RoundImageView img_user;
    //	AutoScrollTextView tv_scroll;
    TextView tv_company_name, tv_quit, tv_start_measure, tv_history_data,
            tv_health_report, tv_health_files, tv_health_warning, tv_msg, tv_scroll;
    FrameLayout content;
    int tag = 0;

    public User mUser;

    public static void startWithUser(User user, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, SecondMainActivity.class);
        intent.putExtra(ARG_USER, user);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        Intent intent = getIntent();
        if (intent != null) {
            mUser = (User) intent.getSerializableExtra(ARG_USER);
        }

        initView();
        setBtnBackground(tag);
        setFragment(tag);
    }

    private void setFragment(int which) {
        FragmentManager fragment = getFragmentManager();
        FragmentTransaction transacction = fragment.beginTransaction();
        switch (which) {
            case 0: // 首页
                SecondIndexFragment frament1 = new SecondIndexFragment();
                transacction.replace(R.id.content, frament1);
                break;
            case 1: // 开始测量
                SecondMeasureFragment frament2 = new SecondMeasureFragment();
                transacction.replace(R.id.content, frament2);
                break;
            case 2: // 历史数据
                SecondHistoryFragment frament3 = new SecondHistoryFragment();
                transacction.replace(R.id.content, frament3);
                break;
            case 3: // 健康报告
                SecondHealthReportFragment frament4 = new SecondHealthReportFragment();
                transacction.replace(R.id.content, frament4);
                break;
            case 4: // 健康档案
                SecondHealthFilesFragment frament5 = new SecondHealthFilesFragment();
                transacction.replace(R.id.content, frament5);
                break;
            case 5: // 健康预警
                SecondHealthWarningFragment frament6 = new SecondHealthWarningFragment();
                transacction.replace(R.id.content, frament6);
                break;
            case 6: // 用户留言
                SecondMessageFragment frament7 = new SecondMessageFragment();
                transacction.replace(R.id.content, frament7);
                break;

            default:
                break;
        }
        transacction.commitAllowingStateLoss();
    }

    private void setBtnBackground(int which) {
        switch (which) {
            case 0:
                tv_start_measure.setBackgroundResource(
                        R.color.menu_color);
                tv_history_data.setBackgroundResource(
                        R.color.menu_color);
                tv_health_report.setBackgroundResource(
                        R.color.menu_color);
                tv_health_files.setBackgroundResource(
                        R.color.menu_color);
                tv_health_warning.setBackgroundResource(
                        R.color.menu_color);
                tv_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 1:
                tv_start_measure.setBackgroundResource(
                        R.color.btn_color);
                tv_history_data.setBackgroundResource(
                        R.color.menu_color);
                tv_health_report.setBackgroundResource(
                        R.color.menu_color);
                tv_health_files.setBackgroundResource(
                        R.color.menu_color);
                tv_health_warning.setBackgroundResource(
                        R.color.menu_color);
                tv_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 2:
                tv_start_measure.setBackgroundResource(
                        R.color.menu_color);
                tv_history_data.setBackgroundResource(
                        R.color.btn_color);
                tv_health_report.setBackgroundResource(
                        R.color.menu_color);
                tv_health_files.setBackgroundResource(
                        R.color.menu_color);
                tv_health_warning.setBackgroundResource(
                        R.color.menu_color);
                tv_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 3:
                tv_start_measure.setBackgroundResource(
                        R.color.menu_color);
                tv_history_data.setBackgroundResource(
                        R.color.menu_color);
                tv_health_report.setBackgroundResource(
                        R.color.btn_color);
                tv_health_files.setBackgroundResource(
                        R.color.menu_color);
                tv_health_warning.setBackgroundResource(
                        R.color.menu_color);
                tv_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 4:
                tv_start_measure.setBackgroundResource(
                        R.color.menu_color);
                tv_history_data.setBackgroundResource(
                        R.color.menu_color);
                tv_health_report.setBackgroundResource(
                        R.color.menu_color);
                tv_health_files.setBackgroundResource(
                        R.color.btn_color);
                tv_health_warning.setBackgroundResource(
                        R.color.menu_color);
                tv_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 5:
                tv_start_measure.setBackgroundResource(
                        R.color.menu_color);
                tv_history_data.setBackgroundResource(
                        R.color.menu_color);
                tv_health_report.setBackgroundResource(
                        R.color.menu_color);
                tv_health_files.setBackgroundResource(
                        R.color.menu_color);
                tv_health_warning.setBackgroundResource(
                        R.color.btn_color);
                tv_msg.setBackgroundResource(
                        R.color.menu_color);
                break;
            case 6:
                tv_start_measure.setBackgroundResource(
                        R.color.menu_color);
                tv_history_data.setBackgroundResource(
                        R.color.menu_color);
                tv_health_report.setBackgroundResource(
                        R.color.menu_color);
                tv_health_files.setBackgroundResource(
                        R.color.menu_color);
                tv_health_warning.setBackgroundResource(
                        R.color.menu_color);
                tv_msg.setBackgroundResource(
                        R.color.btn_color);
                break;
            default:
                break;
        }
    }

    private void initView() {
        img_user = (RoundImageView) findViewById(R.id.img_user);
//    	tv_scroll = (AutoScrollTextView) findViewById(R.id.tv_scroll);
        tv_scroll = (TextView) findViewById(R.id.tv_scroll);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_quit = (TextView) findViewById(R.id.tv_quit);
        tv_start_measure = (TextView) findViewById(R.id.tv_start_measure);
        tv_history_data = (TextView) findViewById(R.id.tv_history_data);
        tv_health_report = (TextView) findViewById(R.id.tv_health_report);
        tv_health_files = (TextView) findViewById(R.id.tv_health_files);
        tv_health_warning = (TextView) findViewById(R.id.tv_health_warning);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_quit.setOnClickListener(this);
        tv_start_measure.setOnClickListener(this);
        tv_history_data.setOnClickListener(this);
        tv_health_report.setOnClickListener(this);
        tv_health_files.setOnClickListener(this);
        tv_health_warning.setOnClickListener(this);
        tv_msg.setOnClickListener(this);

//    	tv_scroll.init(getWindowManager());
//    	tv_scroll.startScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_quit: // 退出
//			Intent intent = new Intent();
//			intent.setClass(SecondMainActivity.this, MainActivity.class);
//			startActivity(intent);
                Session.isBack = true;
                finish();
                break;
            case R.id.tv_start_measure: // 开始测量
                tag = 1;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_history_data: // 历史数据
                tag = 2;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_health_report: // 健康报告
                tag = 3;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_health_files: // 健康档案
                tag = 4;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_health_warning: // 健康预警
                tag = 5;
                setBtnBackground(tag);
                setFragment(tag);
                break;
            case R.id.tv_msg: // 用户留言
                tag = 6;
                setBtnBackground(tag);
                setFragment(tag);
                break;

            default:
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次返回上级界面",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Session.isBack = true;
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
