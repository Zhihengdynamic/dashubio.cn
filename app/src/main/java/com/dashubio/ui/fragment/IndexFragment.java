package com.dashubio.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.GetMeasuredData;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.OneUser.SecondMeasureActivity;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.ViewHolder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kanak.emptylayout.EmptyLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// 首页--检查单数据
public class IndexFragment extends BaseFragment {

    private View mRootView;//整个界面的跟布局
    TextView tv_time;
    PullToRefreshListView mPullRefreshListView;
    private RecordAdapter adapter;
    private int currentPosition = -1;

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    public final static int MSG_SUCCESS = 0;
    public final static int MSG_FAILURE = 1;

    public Handler mHandler = new Handler() {
        // 重写handleMessage()方法，此方法在UI线程运行
        @Override
        public void handleMessage(Message msg) {
            hideLoadingDialog();
            switch (msg.what) {
                //
                case MSG_SUCCESS:
                    //ToastUtils.show(LoginAndRegisterActivity.this, R.string.successful_operation);
                    break;

                // 否则提示失败
                case MSG_FAILURE:
//					HttpResult resp = new HttpResult();
//					resp.setErrorCode(msg.arg1);
//					handleErrorResp(resp);
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_index, container, false);
            initView();
            getMeasuredData();

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
        tv_time = (TextView) mRootView.findViewById(R.id.tv_time);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
       // calendar.roll(java.util.Calendar.DAY_OF_YEAR, -1);
        tv_time.setText(df.format(calendar.getTime()) + " " + getString(R.string.check_list));

        mPullRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.lv_record);

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getMeasuredData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //mUCenterActionsCreator.toGetUserList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex, mQuery);
            }

        });

        adapter = new RecordAdapter(null);
        mPullRefreshListView.setAdapter(adapter);

        // initialize the empty view
        mEmptyLayout = new EmptyLayout(mBaseActivity, mPullRefreshListView.getRefreshableView());
    }

    /**
     * 获取检测数据
     */
    private void getMeasuredData() {
        SecondUser secondUser;
        if (mBaseActivity instanceof SecondMeasureActivity) {
            secondUser = ((SecondMeasureActivity) mBaseActivity).mSecondUser;
        } else {
            secondUser = ((HomeActivity) mBaseActivity).mSecondUser;
        }
        String userId = "";
        if (secondUser != null) {
            userId = secondUser.getId();
        }
        mUCenterActionsCreator.toGetYesterdayMeasuredData(mDeviceUserId, mSessionKey, mSessionValue, userId);
    }


    class RecordAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;
        private ViewHolder holder;
        List<GetMeasuredData> mDataList;

        public RecordAdapter(List<GetMeasuredData> dataList) {
            this.mInflater = LayoutInflater.from(getActivity().getApplicationContext());
            this.mDataList = dataList;
        }

        public void addData(List<GetMeasuredData> dataList, boolean update) {
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }

            if (update) {
                mDataList.clear();
            }

            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertVeiw, ViewGroup parent) {
            holder = new ViewHolder();
            if (convertVeiw == null) {
                convertVeiw = mInflater.inflate(R.layout.list_item_index, null);
                holder.tv_item_index_name = (TextView) convertVeiw.findViewById(R.id.tv_item_index_name);
                holder.tv_item_index_age = (TextView) convertVeiw.findViewById(R.id.tv_item_index_age);
                holder.tv_item_index_subject = (TextView) convertVeiw.findViewById(R.id.tv_item_index_subject);
                holder.tv_item_index_time = (TextView) convertVeiw.findViewById(R.id.tv_item_index_time);
                holder.view_tv = (TextView) convertVeiw.findViewById(R.id.view_tv);
                //holder.view_btn = (Button) convertVeiw.findViewById(R.id.view_btn);
                holder.result_status_container = convertVeiw.findViewById(R.id.result_status_container);
                holder.result_status_tv = (TextView) convertVeiw.findViewById(R.id.result_status_tv);
                holder.warning_tv = (TextView) convertVeiw.findViewById(R.id.warning_tv);
                convertVeiw.setTag(holder);
            } else {
                holder = (ViewHolder) convertVeiw.getTag();
            }

            final GetMeasuredData getMeasuredData = mDataList.get(position);
            holder.tv_item_index_name.setText(getMeasuredData.getName());
            holder.tv_item_index_age.setText(getMeasuredData.getAge());
            holder.tv_item_index_subject.setText(getMeasuredData.getProject());
            //holder.tv_item_index_time.setText(Utils.stampToDate(getMeasuredData.getAddtime()));
            String dateStr = "";
            String addTime = getMeasuredData.getAddtime();
            if (!TextUtils.isEmpty(addTime)) {
                dateStr = addTime.substring(0, 10);
            }
            holder.tv_item_index_time.setText(dateStr);

            String statusStr = "";
            String valueStr = getMeasuredData.getVal();
            if (!TextUtils.isEmpty(valueStr)) {
                statusStr = valueStr.replaceAll(";", "\n");
            }
            statusStr += " " + getMeasuredData.getUnit();
            holder.result_status_tv.setText(statusStr);

            String warning = getMeasuredData.getWarning();
            if (TextUtils.isEmpty(warning)) {
                holder.warning_tv.setText("");
            } else {
                holder.warning_tv.setText(warning);
            }

            final boolean isShowDetail = getMeasuredData.isShowDetail();

            holder.view_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMeasuredData.setShowDetail(!isShowDetail);
                    notifyDataSetChanged();
                }
            });

            if (isShowDetail) {
                holder.result_status_container.setVisibility(View.VISIBLE);
            } else {
                holder.result_status_container.setVisibility(View.GONE);
            }

            return convertVeiw;
        }
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
                case TodoActions.UCENTER_GET_YESTERDAY_MEASURED_DATA_COMPLETE: //前一天检测数据接口
                    //ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    List<GetMeasuredData> dataList = (List<GetMeasuredData>) resp.getData();
                    adapter.addData(dataList, true);

                    if(dataList == null || dataList.size() <= 0){
                        onShowEmpty();
                    }
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        mPullRefreshListView.onRefreshComplete();
    }

}
