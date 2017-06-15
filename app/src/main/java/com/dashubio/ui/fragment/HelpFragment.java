package com.dashubio.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HelpItem;
import com.dashubio.model.HttpResult;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.adapter.HelpExpandAdapter;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.kanak.emptylayout.EmptyLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// 首页--使用帮助
public class HelpFragment extends BaseFragment {

    View mRootView = null;

    @BindView(R.id.pull_refresh_expandable_list)
    public PullToRefreshExpandableListView mPullRefreshExpandableListView;

    private ExpandableListView mExpandableListView;
    private HelpExpandAdapter mHelpExpandAdapter;

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    public int mCurPageIndex = Utils.FIRST_PAGE_INDEX;//当前需要请求显示的页数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_help, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        mCurPageIndex = Utils.FIRST_PAGE_INDEX;


        mUCenterActionsCreator.toGetHelpList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex);
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
        mPullRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshBase refreshView) {
                mUCenterActionsCreator.toGetHelpList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex);
            }
        });

        mExpandableListView = mPullRefreshExpandableListView.getRefreshableView();
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setDividerHeight(0);

        mHelpExpandAdapter = new HelpExpandAdapter(mBaseActivity, null);
        mExpandableListView.setAdapter(mHelpExpandAdapter);

        // initialize the empty view
        mEmptyLayout = new EmptyLayout(mBaseActivity, mExpandableListView);
    }



//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        mUCenterActionsCreator.toGetHelpDetail(mDeviceUserId, mSessionKey, mSessionValue, "2");
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUCenterStoreChange(UCenterStore.UCenterStoreChangeEvent event) {
        String currentStatus = event.getCurrentStatus();//获取当前状态

        if (TextUtils.isEmpty(currentStatus)) {
            return;
        }

        //开始请求
        if (TodoActions.UCENTER_LOADING_START.equals(currentStatus)) {
            onShowLoading();
            showLoadingDialog();
            return;
        }

        //下面为接收到服务器返回响应

        //服务器连接异常
        if (TodoActions.UCENTER_SERVER_CONNECTION_ERROR.equals(currentStatus)) {
            hideLoadingDialog();
            HttpResult errorResp = event.getHttpResp();
            ToastUtils.show(mBaseActivity, errorResp.getMsg());
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
                case TodoActions.UCENTER_GET_HELP_LIST_COMPLETE: //帮助列表
                    //ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    List<HelpItem> helpItemtList = (List<HelpItem>) resp.getData();
                    mHelpExpandAdapter.refresh(helpItemtList);
                    if(helpItemtList == null || helpItemtList.size() == 0){
                        onShowEmpty();
                    }
                    break;

                case TodoActions.UCENTER_GET_HELP_DETAIL_COMPLETE: //帮助详情
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        mPullRefreshExpandableListView.onRefreshComplete();
    }

}
