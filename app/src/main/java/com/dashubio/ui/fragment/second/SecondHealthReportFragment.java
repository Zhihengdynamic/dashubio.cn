package com.dashubio.ui.fragment.second;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.app.UserManager;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HealthReportItem;
import com.dashubio.model.HealthReportMonthItem;
import com.dashubio.model.HealthReportProjectItem;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.OneUser.SecondHealthReportActivity;
import com.dashubio.ui.adapter.HealthComprehensiveExpandAdapter;
import com.dashubio.ui.adapter.HealthReportExpandAdapter;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.ui.view.ProgressWebView;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.dashubio.utils.log.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import org.apache.http.util.EncodingUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 第二登陆人首页--健康报告
public class SecondHealthReportFragment extends BaseFragment {

    View mRootView = null;


    @BindView(R.id.pull_refresh_expandable_list)
    public PullToRefreshExpandableListView mPullRefreshExpandableListView;
    @BindView(R.id.btn_details)
    Button btnDetails;
    @BindView(R.id.btn_comprehensive)
    Button btnComprehensive;
    @BindView(R.id.layout_btn)
    LinearLayout layoutBtn;
    private ExpandableListView mExpandableListView;
    private HealthReportExpandAdapter mHealthReportExpandAdapter;
    private HealthComprehensiveExpandAdapter mHealthReportComprehensiveAdapter;

    @BindView(R.id.web_container)
    View mWebContainer;

    @BindView(R.id.report_title_tv)
    TextView mReportTitleTv;

    @BindView(R.id.webview)
    public ProgressWebView mWebView;

    private String mHtml5Url = "http://dashubio.returnlive.com/Manage/Equid/reportzer";
    private String url = "http://dashubio.returnlive.com/Manage/Equid/compiledzer";
    public String mDeviceUserId = "";
    public String mSessionKey = "";
    public String mSessionValue = "";
    public String mid = "";


    private Handler mHandler = new Handler();

    public SecondUser mSecondUser;

    public Dispatcher mDispatcher;
    public UCenterActionsCreator mUCenterActionsCreator;
    public UCenterStore mUCenterStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBaseActivity instanceof SecondHealthReportActivity) {
            mSecondUser = ((SecondHealthReportActivity) mBaseActivity).mSecondUser;
        } else {
            mSecondUser = ((HomeActivity) mBaseActivity).mSecondUser;
        }
        initDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_second_health, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        mDeviceUserId = UserManager.getInstance().getUid();
        mSessionKey = Utils.T_SESSION + mDeviceUserId;
        mSessionValue = UserManager.getInstance().getSession();
        mid = mSecondUser.getId();


        //默认显示详情
        initView();
        //mUCenterActionsCreator.toGetHealthReportData(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId(), "5", "2016", "12");
        if (mUCenterActionsCreator != null && mSecondUser != null) {
            mUCenterActionsCreator.toGetHealthReportList(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
        }

        if (mUCenterActionsCreator != null && mSecondUser != null) {
            mUCenterActionsCreator.toGetHealthReportList2(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
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
        mPullRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshBase refreshView) {

                if (mUCenterActionsCreator != null && mSecondUser != null) {
                    mUCenterActionsCreator.toGetHealthReportList(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
                }
            }
        });


        mExpandableListView = mPullRefreshExpandableListView.getRefreshableView();
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setDividerHeight(0);

        mHealthReportExpandAdapter = new HealthReportExpandAdapter(mBaseActivity, null, this);
        mExpandableListView.setAdapter(mHealthReportExpandAdapter);

        WebSettings webSettings = mWebView.getSettings();
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);

       /* WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Logger.e("title", title);
                setTitleName(title);
            }
        };
        mWebView.setWebChromeClient(wvcc);*/
        // mWebView.clearCache(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.i("url = " + url);
                //view.loadUrl(url);
                // return true;
                // 解决重定向
                return false;
            }
        });

        // loadURL();
    }

//    public void loadURL() {
//        if (mHtml5Url != null)
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mWebView.loadUrl(mHtml5Url);
//                    Logger.e("loadUrl " + mHtml5Url);
//                }
//            }, 0);
//    }

    public void postURL(final HealthReportItem reportItem, final HealthReportMonthItem monthItem, final HealthReportProjectItem projectItem) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String secondUserId = mSecondUser.getId();
                //post访问需要提交的参数
                String postData = "mid=" + secondUserId;
                postData += "&pro=" + projectItem.getId();
                postData += "&year=" + reportItem.getYear();
                postData += "&month=" + monthItem.getMonth();

                mWebView.postUrl(mHtml5Url, EncodingUtils.getBytes(postData, "BASE64"));

                String title = reportItem.getYear() + mBaseActivity.getString(R.string.year);
                title += monthItem.getMonth() + mBaseActivity.getString(R.string.month);
                title += " " + projectItem.getName();
                mReportTitleTv.setText(title);
                mWebContainer.setVisibility(View.VISIBLE);
                mPullRefreshExpandableListView.setVisibility(View.GONE);
                layoutBtn.setVisibility(View.GONE);

            }
        }, 0);

    }


    public void postComprehensiveURL(final HealthReportItem reportItem, final HealthReportMonthItem monthItem) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String secondUserId = mSecondUser.getId();
                String postData = "mid=" + secondUserId;
                postData += "&year=" + reportItem.getYear();
                postData += "&month=" + monthItem.getMonth();

                mWebView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));

                String title = reportItem.getYear() + mBaseActivity.getString(R.string.year);
                title += monthItem.getMonth() + mBaseActivity.getString(R.string.month);
                mReportTitleTv.setText(title);
                mWebContainer.setVisibility(View.VISIBLE);
                mPullRefreshExpandableListView.setVisibility(View.GONE);

                layoutBtn.setVisibility(View.GONE);
            }
        }, 0);
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

      /*  if (TodoActions.UCENTER_LOADING_START2.equals(currentStatus)) {
            showLoadingDialog();
            return;
        }*/


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
                case TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE: //健康报告列表接口
                    List<HealthReportItem> healthReportList = (List<HealthReportItem>) resp.getData();
                    mHealthReportExpandAdapter.refresh(healthReportList);
                    /*if (healthReportList != null) {
                        for (int i = 0; i < healthReportList.size(); i++) {
                            mExpandableListView.expandGroup(i);

                        }
                    }*/

                    mWebContainer.setVisibility(View.GONE);
                    mPullRefreshExpandableListView.setVisibility(View.VISIBLE);
                    break;


                case TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE2: //健康报告列表接口2
                    healthReportList = (List<HealthReportItem>) resp.getData();
                    mHealthReportComprehensiveAdapter.refresh(healthReportList);
                    /*if (healthReportList != null) {
                        for (int i = 0; i < healthReportList.size(); i++) {
                            mExpandableListView.expandGroup(i);
                        }
                    }*/
                    mWebContainer.setVisibility(View.GONE);
                    mPullRefreshExpandableListView.setVisibility(View.VISIBLE);
                    break;

//                case TodoActions.UCENTER_GET_HEALTH_REPORT_DATA_COMPLETE: //健康报告接口
//                    String reportData = (String) resp.getData();
//                    Logger.i("reportData = " + reportData);
//                    mPullRefreshExpandableListView.setVisibility(View.GONE);
//                    mWebView.loadDataWithBaseURL(null, reportData, "text/html", "UTF-8", null);
//                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        mPullRefreshExpandableListView.onRefreshComplete();
    }

    @OnClick({R.id.btn_details, R.id.btn_comprehensive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_details:
                btnDetails.setBackground(getActivity().getResources().getDrawable(R.drawable.shape1));
                btnComprehensive.setBackground(getActivity().getResources().getDrawable(R.drawable.shape2));
                initView();
                //mUCenterActionsCreator.toGetHealthReportData(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId(), "5", "2016", "12");
                if (mUCenterActionsCreator != null && mSecondUser != null) {
                    mUCenterActionsCreator.toGetHealthReportList(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
                }

                break;
            case R.id.btn_comprehensive:
                btnDetails.setBackground(getActivity().getResources().getDrawable(R.drawable.shape2));
                btnComprehensive.setBackground(getActivity().getResources().getDrawable(R.drawable.shape1));
                comprehensive();
                if (mUCenterActionsCreator != null && mSecondUser != null) {
                    mUCenterActionsCreator.toGetHealthReportList2(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
                }

                break;
        }
    }

    private void comprehensive() {
        mPullRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {


            @Override
            public void onRefresh(PullToRefreshBase refreshView) {
                if (mUCenterActionsCreator != null && mSecondUser != null) {
                    mUCenterActionsCreator.toGetHealthReportList2(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
                }
            }
        });

        mExpandableListView = mPullRefreshExpandableListView.getRefreshableView();
        mExpandableListView.setGroupIndicator(null);

        mExpandableListView.setDividerHeight(0);

        mHealthReportComprehensiveAdapter = new HealthComprehensiveExpandAdapter(mBaseActivity, null, this);
        mExpandableListView.setAdapter(mHealthReportComprehensiveAdapter);

        WebSettings webSettings = mWebView.getSettings();
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("TAC","url"+url);

                return false;
            }
        });


    }
}
