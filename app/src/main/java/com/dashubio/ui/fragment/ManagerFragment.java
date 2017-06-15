package com.dashubio.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.model.ucenter.User;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.DBManager;
import com.dashubio.utils.MemberInfo;
import com.dashubio.utils.SecondUser2;
import com.dashubio.utils.SecondUserEntityUtils;
import com.dashubio.utils.Tiwen;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.dashubio.utils.ViewHolder;
import com.dashubio.utils.WifiUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

// 首页--用户管理界面
public class ManagerFragment extends BaseFragment {

    private View mRootView;//整个界面的跟布局
    private static final String TAG = "ZZZ";
    TextView mUserListTv;

    EditText mInputQueryEt;
    View mGoSearch;
    String result;

    PullToRefreshListView mPullRefreshListView;
    UserAdapter adapter;

    public int mCurPageIndex = Utils.FIRST_PAGE_INDEX;//当前需要请求显示的页数

    private String mQuery = "";//搜做关键字

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
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_manager, container, false);
            initView();
            mCurPageIndex = Utils.FIRST_PAGE_INDEX;
            mUCenterActionsCreator.toGetUserList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex, "");


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
        mUserListTv = (TextView) mRootView.findViewById(R.id.user_list_tv);

        mInputQueryEt = (EditText) mRootView.findViewById(R.id.input_query_et);
        mGoSearch = mRootView.findViewById(R.id.go_search);

        mGoSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mInputQueryEt.getText().toString();
                mCurPageIndex = Utils.FIRST_PAGE_INDEX;
                mQuery = query == null ? "" : query;
                mUCenterActionsCreator.toGetUserList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex, mQuery);
                Utils.hideSoftKeyboard(mBaseActivity, mInputQueryEt);
            }
        });

        mPullRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.pull_refresh_list);
        adapter = new UserAdapter(null);
        mPullRefreshListView.setAdapter(adapter);

        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCurPageIndex = Utils.FIRST_PAGE_INDEX;
                mUCenterActionsCreator.toGetUserList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex, mQuery);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mUCenterActionsCreator.toGetUserList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex, mQuery);
            }

        });
    }



    class UserAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;
        private ViewHolder holder;

        private List<SecondUser> mDataList;

        public UserAdapter(List<SecondUser> dataList) {
            this.mInflater = LayoutInflater.from(getActivity().getApplicationContext());
            mDataList = dataList;
        }

        public void addData(List<SecondUser> dataList, boolean update) {
            if (mDataList == null) {
                mDataList = new ArrayList<>();
            }

            if (update) {
                mDataList.clear();
            }

            if (dataList != null && dataList.size() > 0) {
                mDataList.addAll(dataList);
            }


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
        public View getView(int position, View convertView, ViewGroup parent) {
            holder = new ViewHolder();
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_manager, null);
                holder.img_item_user = (ImageView) convertView.findViewById(R.id.img_item_user);
                holder.tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
                holder.tv_item_sex = (TextView) convertView.findViewById(R.id.tv_item_sex);
                holder.tv_item_times = (TextView) convertView.findViewById(R.id.tv_item_times);
                holder.btn_item_login = (Button) convertView.findViewById(R.id.btn_item_login);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            SecondUser user = mDataList.get(position);
            //有网条件下加载用户列表
                holder.tv_item_name.setText(user.getFullname());

            //先从数据库查找，如果有加载过，就不用存储到数据库，

            if (WifiUtils.isNetworkConnected(getActivity())){//当有网络的时候将数据存储进数据库，此处可能导致重复加载
                DBManager dbManager = new DBManager(getActivity());
                ArrayList<SecondUser> userList = new ArrayList<>();
                userList = dbManager.searchUserData();
                String result = "";
                for (SecondUser info : userList) {
                    result = result + String.valueOf(info.fullname);
                    result = result + "\n" + "------------------------------------------" + "\n";

                }


                for(SecondUser users : mDataList){
                    String userName = String.valueOf(users.fullname);
                    String userid = String.valueOf(users.getId());
                    String card_id = String.valueOf(users.getCardId());
                    String phone = String.valueOf(users.getPhone());
                    if (result.indexOf(userName)!=-1){
                        //数据库有数据
                    }else {
                        dbManager.addUserName(userName,userid,card_id,phone);
                    }

                }

            }

            int sex = user.getSex();

            switch (sex) {
                case User.NO_SEX:
                    holder.tv_item_sex.setText(R.string.no);
                    break;

                case User.MALE:
                    holder.tv_item_sex.setText(R.string.male);
                    break;

                case User.FEMALE:
                    holder.tv_item_sex.setText(R.string.female);
                    break;
            }

            ItemListener itemListener = new ItemListener(position);
            holder.btn_item_login.setOnClickListener(itemListener);
            return convertView;
        }

        class ItemListener implements OnClickListener {
            private int m_position;
            ItemListener(int pos) {
                m_position = pos;
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_item_login: // 登录按钮
                        DBManager dbManager = new DBManager(getActivity());
                        ArrayList<SecondUser> userlist = new ArrayList<>();
                        userlist = dbManager.searchUserId((m_position+1)+"");
                        String mid = "";
                        for (SecondUser user : userlist){
                            mid = String.valueOf(user.getId());
                        }


                        SecondUserEntityUtils.mid = mid;
                        SecondUserEntityUtils.secondId = (m_position+1)+"";
                        Log.d("MyBroadcastReceiver", "点击第"+SecondUserEntityUtils.secondId+"_id");
                        HomeActivity.startWithTypeAndSecondUser(false, mDataList.get(m_position), mBaseActivity);
                        break;
                    default:
                        break;
                }

            }
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
            //只能在这个地方读取数据库信息
            //如果没有网络则从数据库中拿数据，测试数据
            DBManager dbManager = new DBManager(getActivity());
            ArrayList<SecondUser> infoList = new ArrayList<>();
            ArrayList<SecondUser> userList = new ArrayList<>();
            infoList = dbManager.searchUserData();
           /* for (int i = infoList.size()-1; i >-1 ; i--) {
                SecondUser secondUser = infoList.get(i);
                userList.add(secondUser);
            }*/
            adapter.addData(infoList, true);









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
                case TodoActions.UCENTER_GET_SECOND_USER_LIST_COMPLETE: //用户列表
                    List<SecondUser> dataList = (List<SecondUser>) resp.getData();

                    boolean update = true;
                    if (mCurPageIndex > Utils.FIRST_PAGE_INDEX) {
                        update = false;
                    }
                    adapter.addData(dataList, update);
                    if (dataList == null || dataList.size() < Utils.PAGE_SIZE) {
                        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        if (mCurPageIndex == Utils.FIRST_PAGE_INDEX) {
                            mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        mCurPageIndex++;
                    }

                    if (TextUtils.isEmpty(mQuery)) {
                        mUserListTv.setText(R.string.user_list);
                    } else {
                        mUserListTv.setText(getString(R.string.search) + "：" + mQuery);
                    }
                    break;
            }
        } else {
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        mPullRefreshListView.onRefreshComplete();
    }




}
