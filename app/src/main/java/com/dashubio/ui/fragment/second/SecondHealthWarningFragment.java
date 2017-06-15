package com.dashubio.ui.fragment.second;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HealthWarningCategory;
import com.dashubio.model.HistoricalData;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.OneUser.SecondHealthWarningActivity;
import com.dashubio.ui.activity.OneUser.SecondMeasureActivity;
import com.dashubio.ui.adapter.HealthWarningAdapter;
import com.dashubio.ui.adapter.HealthWarningSpAdapter;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// 第二登陆人首页--健康指导
public class SecondHealthWarningFragment extends BaseFragment {

    View mRootView = null;

    @BindView(R.id.health_warning_spinner)
    Spinner mHealthWarningSpinner;

    @BindView(R.id.pullToRefreshListView)
    PullToRefreshListView mPullRefreshListView;
    @BindView(R.id.tv_nonedata)
    TextView tvNonedata;
    private HealthWarningAdapter mHealthWarningAdapter;

    private List<HealthWarningCategory> mHealthWarningCategoryList;

    private SecondUser mSecondUser;

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBaseActivity instanceof SecondHealthWarningActivity) {
            mSecondUser = ((SecondMeasureActivity) mBaseActivity).mSecondUser;
        } else {
            mSecondUser = ((HomeActivity) mBaseActivity).mSecondUser;
        }
        initDependencies();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_second_health_warning, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        if (mUCenterActionsCreator != null) {
            mUCenterActionsCreator.toGetHealthWarningCategoryList(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDispatcher.register(this);
        mDispatcher.register(mUCenterStore);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDispatcher.unregister(this);
        mDispatcher.unregister(mUCenterStore);
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mUCenterActionsCreator = UCenterActionsCreator.getInstance(UCenterActionsCreator.class);
        mUCenterStore = UCenterStore.getInstance(UCenterStore.class);
    }

    private void initView() {
        mHealthWarningSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mHealthWarningCategoryList != null && mHealthWarningCategoryList.size() > 0) {
                    mUCenterActionsCreator.toGetHealthWarningDataList(mDeviceUserId, mSessionKey, mSessionValue,
                            mSecondUser.getId(), mHealthWarningCategoryList.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //
            }

        });

        mHealthWarningAdapter = new HealthWarningAdapter(mBaseActivity, null);
        mPullRefreshListView.setAdapter(mHealthWarningAdapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUCenterStoreChange(UCenterStore.UCenterStoreChangeEvent event) {
        String currentStatus = event.getCurrentStatus();//获取当前状态

        if (TextUtils.isEmpty(currentStatus)) {
            return;
        }

        //开始请求
        if (TodoActions.UCENTER_LOADING_START.equals(currentStatus)) {
            showLoadingDialog();
            return;
        }

        //下面为接收到服务器返回响应

        //服务器连接异常
        if (TodoActions.UCENTER_SERVER_CONNECTION_ERROR.equals(currentStatus)) {
            hideLoadingDialog();
            HttpResult errorResp = event.getHttpResp();
//            ToastUtils.show(mBaseActivity, errorResp.getMsg());
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
                case TodoActions.UCENTER_GET_HEALTH_WARNING_CATEGORY_LIST_COMPLETE: //预警标题接口
                    mHealthWarningCategoryList = (List<HealthWarningCategory>) resp.getData();
                    HealthWarningSpAdapter healthWarningSpAdapter = new HealthWarningSpAdapter(mBaseActivity, mHealthWarningCategoryList);
                    mHealthWarningSpinner.setAdapter(healthWarningSpAdapter);

                    if (mHealthWarningCategoryList != null && mHealthWarningCategoryList.size() > 0) {
                        tvNonedata.setVisibility(View.GONE );
                        mUCenterActionsCreator.toGetHealthWarningDataList(mDeviceUserId, mSessionKey, mSessionValue,
                                mSecondUser.getId(), mHealthWarningCategoryList.get(0).getId());
                    }else {
                        tvNonedata.setVisibility(View.VISIBLE);
                    }
                    break;

                case TodoActions.UCENTER_GET_HEALTH_WARNING_DATA_LIST_COMPLETE: //预警项目列表接口
                    List<HistoricalData> historicalDataList = (List<HistoricalData>) resp.getData();
                    mHealthWarningAdapter.addData(historicalDataList, true);
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        //mPullRefreshListView.onRefreshComplete();
    }


}
