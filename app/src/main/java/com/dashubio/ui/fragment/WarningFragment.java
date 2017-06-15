package com.dashubio.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.EarlyWarning;
import com.dashubio.model.HttpResult;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.adapter.EarlyWarningListAdapter;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

// 首页--预警设置界面
public class WarningFragment extends BaseFragment {

    private View mRootView = null;

    private PullToRefreshListView mPullRefreshListView;
    private EarlyWarningListAdapter mEarlyWarningListAdapter;
    private List<EarlyWarning> mDataList;

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_warning, container, false);
        mPullRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.pull_refresh_list);

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mUCenterActionsCreator.toGetEarlyWarningList(mDeviceUserId, mSessionKey, mSessionValue);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

        });

        mUCenterActionsCreator.toGetEarlyWarningList(mDeviceUserId, mSessionKey, mSessionValue);
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
                case TodoActions.UCENTER_GET_EARLY_WARNING_LIST_COMPLETE: //健康预警列表
                    //ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    Log.d("TAC","健康预警列表");
                    mDataList = (List<EarlyWarning>) resp.getData();
                    mEarlyWarningListAdapter = new EarlyWarningListAdapter(mBaseActivity, mDataList);
                    mPullRefreshListView.setAdapter(mEarlyWarningListAdapter);
                    break;

                case TodoActions.UCENTER_SET_EARLY_WARNING_COMPLETE: //健康预警设置
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        mPullRefreshListView.onRefreshComplete();
    }

}
