package com.dashubio.ui.fragment.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.dashubio.R;
import com.dashubio.app.UserManager;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.ui.activity.AllUser.HelpActivity;
import com.dashubio.ui.activity.AllUser.LoginActivity;
import com.dashubio.ui.activity.AllUser.ManagerActivity;
import com.dashubio.ui.activity.AllUser.RegisterActivity;
import com.dashubio.ui.activity.AllUser.WarningActivity;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.IndexActivity;
import com.dashubio.ui.activity.OneUser.SecondHealthFilesActivity;
import com.dashubio.ui.activity.OneUser.SecondHealthReportActivity;
import com.dashubio.ui.activity.OneUser.SecondHealthWarningActivity;
import com.dashubio.ui.activity.OneUser.SecondHistoryActivity;
import com.dashubio.ui.activity.OneUser.SecondMeasureActivity;
import com.dashubio.ui.adapter.MenuAdapter;
import com.dashubio.ui.fragment.HelpFragment2;
import com.dashubio.ui.fragment.IndexFragment;
import com.dashubio.ui.fragment.LoginFragment;
import com.dashubio.ui.fragment.ManagerFragment;
import com.dashubio.ui.fragment.RegisterFragment;
import com.dashubio.ui.fragment.WarningFragment;
import com.dashubio.ui.fragment.second.SecondHealthFilesFragment;
import com.dashubio.ui.fragment.second.SecondHealthReportFragment;
import com.dashubio.ui.fragment.second.SecondHealthWarningFragment;
import com.dashubio.ui.fragment.second.SecondHistoryFragment;
import com.dashubio.ui.fragment.second.SecondMeasureFragment;
import com.dashubio.ui.view.RoundImageView;
import com.dashubio.utils.Utils;
import com.dashubio.utils.log.Logger;
import java.util.Arrays;


/**
 * 菜单的Fragment，用于显示菜单界面内容，以及处理菜单界面的点击事件。
 *
 * @author guolin
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener, OnItemClickListener {





    private RoundImageView mHospitalTv;
    private TextView mCompanyNameTv;
    private TextView mQuitTv;

    /**
     * 菜单界面中只包含了一个ListView。
     */
    private ListView mMenuListView;

    /**
     * ListView的适配器。
     */
//	private ArrayAdapter<String> adapter;
    private MenuAdapter mMenuAdapter;

    /**
     * 用于填充ListView的数据，这里就简单只用了两条数据。
     */
    private String[] mAllUserMenuItems = {"首页", "用户注册", "用户登录", "用户管理", "预警设置", "使用帮助"};//
    private String[] mOneUserMenuItems = {"首页", "开始测量", "历史数据", "健康报告", "健康档案", "健康指导"};
    private String[] mMenuItems;

    /**
     * 所有用户还是单个用户
     */
    public final static boolean DEFAULT_ALL_USER_TYPE = true;//默认的用户类型
    public boolean isAllUser = DEFAULT_ALL_USER_TYPE;

    public SecondUser mSecondUser;

    /**
     * 是否是双页模式。如果一个Activity中包含了两个Fragment，就是双页模式。
     */
    private boolean isTwoPane;


    /**
     * 当Activity和Fragment建立关联时，初始化适配器中的数据。
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            isAllUser = intent.getBooleanExtra(HomeActivity.ARG_TYPE, DEFAULT_ALL_USER_TYPE);
            // mUser = (User) intent.getSerializableExtra(Utils.ARG_USER);
        }
        Logger.i("MenuFragment------>isAllUser = " + isAllUser);
        if (isAllUser) {
            mMenuItems = mAllUserMenuItems;
        } else {
            mMenuItems = mOneUserMenuItems;
        }
        //adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, menuItems);
        mMenuAdapter = new MenuAdapter(getActivity(), Arrays.asList(mMenuItems));
    }

    /**
     * 加载menu_fragment布局文件，为ListView绑定了适配器，并设置了监听事件。
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, container, false);


        mHospitalTv = (RoundImageView) view.findViewById(R.id.hospital_iv);
        mCompanyNameTv = (TextView) view.findViewById(R.id.tv_company_name);
        mQuitTv = (TextView) view.findViewById(R.id.quit_tv);
        if (isAllUser) {
            mHospitalTv.setImageResource(R.drawable.hospital);
            mQuitTv.setText(R.string.quit);
        } else {
            mHospitalTv.setImageResource(R.drawable.default_avatar);
            mQuitTv.setText(R.string.back);
        }
        mQuitTv.setOnClickListener(this);
        mMenuListView = (ListView) view.findViewById(R.id.menu_list);
        mMenuListView.setAdapter(mMenuAdapter);
        mMenuListView.setOnItemClickListener(this);

        BaseFragment fragment = new IndexFragment();
        getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
        mMenuAdapter.changeSelected(0);

        return view;
    }

    /**
     * 当Activity创建完毕后，尝试获取一下布局文件中是否有details_layout这个元素，如果有说明当前
     * 是双页模式，如果没有说明当前是单页模式。
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().findViewById(R.id.details_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAllUser) {
            mCompanyNameTv.setText(UserManager.getInstance().getCompany());
        } else {
            SecondUser secondUser = ((HomeActivity) getActivity()).mSecondUser;
            mCompanyNameTv.setText(secondUser.getFullname());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_tv:
                mBaseActivity.finish();
                break;
        }
    }

    /**
     * 处理ListView的点击事件，会根据当前是否是双页模式进行判断。如果是双页模式，则会动态添加Fragment。
     * 如果不是双页模式，则会打开新的Activity。
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            if (isTwoPane) {
                BaseFragment fragment = null;
                if (isAllUser) {
                    //第一个界面------所有用户
                    switch (position) {
                        case 0://首页
                            fragment = new IndexFragment();
                            break;

                        case 1://用户注册
                            fragment = new RegisterFragment();
                            break;

                        case 2://用户登录
                            fragment = new LoginFragment();
                            break;

                        case 3://用户管理
                            fragment = new ManagerFragment();
                            break;

                        case 4://预警设置
                            fragment = new WarningFragment();
                            break;

                        case 5://使用帮助
//                            fragment = new HelpFragment();
                            fragment = new HelpFragment2();
                            break;
                    }
                } else {
                    //第二个界面------单个用户
                    switch (position) {
                        case 0://首页
                            fragment = new IndexFragment();
                            break;

                        case 1:// 开始测量
                            fragment = new SecondMeasureFragment();
                            break;

                        case 2:// 历史数据
                            fragment = new SecondHistoryFragment();
                            break;

                        case 3:// 健康报告
                            fragment = new SecondHealthReportFragment();
                            break;

                        case 4:// 健康档案
                            fragment = new SecondHealthFilesFragment();
                            break;

                        case 5:// 健康预警
                            fragment = new SecondHealthWarningFragment();
                            break;
                    }
                }
                getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
            } else {
                Intent intent = null;
                if (isAllUser) {
                    //第一个界面------所有用户
                    switch (position) {
                        case 0://首页
                            intent = new Intent(getActivity(), IndexActivity.class);
                            break;

                        case 1://用户注册
                            intent = new Intent(getActivity(), RegisterActivity.class);
                            break;

                        case 2://用户登录
                            intent = new Intent(getActivity(), LoginActivity.class);
                            break;

                        case 3://用户管理
                            intent = new Intent(getActivity(), ManagerActivity.class);
                            break;

                        case 4://预警设置
                            intent = new Intent(getActivity(), WarningActivity.class);
                            break;

                        case 5://使用帮助
                            intent = new Intent(getActivity(), HelpActivity.class);
                            break;
                    }
                } else {
                    //第二个界面------单个用户
                    switch (position) {
                        case 0://首页
                            intent = new Intent(getActivity(), IndexActivity.class);
                            break;

                        case 1:// 开始测量
                            intent = new Intent(getActivity(), SecondMeasureActivity.class);
                            intent.putExtra(Utils.ARG_SECONDUSER, mSecondUser);
                            break;

                        case 2:// 历史数据
                            intent = new Intent(getActivity(), SecondHistoryActivity.class);
                            intent.putExtra(Utils.ARG_SECONDUSER, mSecondUser);
                            break;

                        case 3:// 健康报告
                            intent = new Intent(getActivity(), SecondHealthReportActivity.class);
                            break;

                        case 4:// 健康档案
                            intent = new Intent(getActivity(), SecondHealthFilesActivity.class);
                            break;

                        case 5:// 健康预警
                            intent = new Intent(getActivity(), SecondHealthWarningActivity.class);
                            break;
                    }
                }
                startActivity(intent);
            }

            if (isTwoPane) {
                mMenuAdapter.changeSelected(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
