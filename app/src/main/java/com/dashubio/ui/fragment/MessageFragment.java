package com.dashubio.ui.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;
import com.dashubio.model.LeavingMessage;
import com.dashubio.model.entity.ChatEntity;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.dashubio.utils.ViewHolder;
import com.dashubio.utils.log.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 首页--用户留言
public class MessageFragment extends BaseFragment implements OnClickListener {

    View view = null;
    private PullToRefreshListView mPullRefreshMsgListView;
    private LeavingMessageAdapter mLeavingMessageAdapter;
    private LeavingMessage mCheckedLeavingMessage;//点击的留言

    ListView lv_content;
    MsgAdapter adapter;
    Button btn_reply, btn_send;
    RelativeLayout rl_btn, rl_edit;
    EditText et_content;
    List<ChatEntity> chatList = null;

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
        view = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        mCurPageIndex = Utils.FIRST_PAGE_INDEX;
        mUCenterActionsCreator.toGetMessageList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex);
        return view;
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

    private void initChatContent() {
        chatList = new ArrayList<ChatEntity>();
        ChatEntity chatEntity = null;
        for (int i = 0; i < 2; i++) {
            chatEntity = new ChatEntity();
            if (i % 2 == 0) {
                chatEntity.setComeMsg(true);
                chatEntity.setContent("医生您好，请问什么是高血压？什么会引起高血压？年轻人也会得吗？");
                chatEntity.setChatTime(Utils.getTodayDate());
            } else {
                chatEntity.setComeMsg(false);
                chatEntity.setContent("高血压病是指病因尚未明确····");
                chatEntity.setChatTime(Utils.getTodayDate());
            }
            chatList.add(chatEntity);
        }
        adapter = new MsgAdapter(chatList);
        mPullRefreshMsgListView.setAdapter(adapter);
    }

    private void initView() {
        rl_btn = (RelativeLayout) view.findViewById(R.id.rl_btn);
        rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
        mPullRefreshMsgListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_msg_list);
        lv_content = (ListView) view.findViewById(R.id.lv_content);
        btn_reply = (Button) view.findViewById(R.id.btn_reply);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        et_content = (EditText) view.findViewById(R.id.et_content);
        et_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_content.setSingleLine(false);
        et_content.setHorizontallyScrolling(false);
        btn_reply.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        mLeavingMessageAdapter = new LeavingMessageAdapter(null);
        mPullRefreshMsgListView.setAdapter(mLeavingMessageAdapter);
        mPullRefreshMsgListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mPullRefreshMsgListView.setVisibility(View.GONE);
                rl_btn.setVisibility(View.VISIBLE);
                lv_content.setVisibility(View.VISIBLE);
                initChatContent();
                adapter = new MsgAdapter(chatList);
                lv_content.setAdapter(adapter);

                mCheckedLeavingMessage = mLeavingMessageAdapter.getLeavingMsgList().get(position - 1);

                List<LeavingMessage> leavingMsgList = mLeavingMessageAdapter.getLeavingMsgList();
                mUCenterActionsCreator.toGetMessageDetail(mDeviceUserId, mSessionKey, mSessionValue, leavingMsgList.get(position - 1).getId());
            }
        });

        // mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshMsgListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCurPageIndex = Utils.FIRST_PAGE_INDEX;
                mUCenterActionsCreator.toGetMessageList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Logger.i("onPullUp------>mCurPageIndex = " + mCurPageIndex);
                mUCenterActionsCreator.toGetMessageList(mDeviceUserId, mSessionKey, mSessionValue, mCurPageIndex);
            }

        });

    }

    /**
     * 留言Adapter
     */
    class LeavingMessageAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;

        List<LeavingMessage> mLeavingMsgList;

        public List<LeavingMessage> getLeavingMsgList() {
            return mLeavingMsgList;
        }

        public LeavingMessageAdapter(List<LeavingMessage> dataList) {
            this.mInflater = LayoutInflater.from(getActivity().getApplicationContext());
            mLeavingMsgList = dataList;
        }

        public void addData(List<LeavingMessage> dataList, boolean update) {
            if (mLeavingMsgList == null) {
                mLeavingMsgList = new ArrayList<>();
            }

            if (update) {
                mLeavingMsgList.clear();
            }

            if (dataList != null && dataList.size() > 0) {
                mLeavingMsgList.addAll(dataList);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mLeavingMsgList == null ? 0 : mLeavingMsgList.size();
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
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_leaving_message, null);
                holder.msgNameTv = (TextView) convertView.findViewById(R.id.msg_name_tv);
                holder.msgTimeTv = (TextView) convertView.findViewById(R.id.msg_time_tv);
                holder.msgContentTv = (TextView) convertView.findViewById(R.id.msg_content_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            LeavingMessage leavingMessage = mLeavingMsgList.get(position);
            holder.msgNameTv.setText(leavingMessage.getName());
            long time = Utils.string2Long(leavingMessage.getTime());
            if (time > 0) {
                String timeStr = Utils.getFormatedDateTime(Utils.TIME_PATTERN, time);
                holder.msgTimeTv.setText(timeStr);
            }
            holder.msgContentTv.setText(leavingMessage.getContent());
            return convertView;
        }


        public class ViewHolder {
            public TextView msgNameTv;
            public TextView msgTimeTv;
            public TextView msgContentTv;
        }

    }

    class MsgAdapter extends BaseAdapter {

        private LayoutInflater mInflater = null;
        private ViewHolder holder;
        private int COME_MSG = 0;
        private int TO_MSG = 1;
        private List<ChatEntity> chatList = null;

        public MsgAdapter(List<ChatEntity> chatList) {
            this.mInflater = LayoutInflater.from(getActivity().getApplicationContext());
            this.chatList = chatList;
        }

        @Override
        public int getCount() {
            return chatList.size();
        }

        @Override
        public Object getItem(int position) {
            return chatList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            // 区别两种view的类型，标注两个不同的变量来分别表示各自的类型  
            ChatEntity entity = chatList.get(position);
            if (entity.isComeMsg()) {
                return COME_MSG;
            } else {
                return TO_MSG;
            }
        }

        @Override
        public int getViewTypeCount() {
            // 这个方法默认返回1，如果希望listview的item都是一样的就返回1，我们这里有两种风格，返回2
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            holder = new ViewHolder();
            if (convertView == null) {
                if (chatList.get(position).isComeMsg()) {
                    convertView = mInflater.inflate(R.layout.list_item_chat_come, null);
                } else {
                    convertView = mInflater.inflate(R.layout.list_item_chat_to, null);
                }
                holder.tv_item_chat_time = (TextView) convertView.findViewById(R.id.tv_item_chat_time);
                holder.img_item_chat = (ImageView) convertView.findViewById(R.id.img_item_chat);
                holder.tv_item_chat_content = (TextView) convertView.findViewById(R.id.tv_item_chat_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_item_chat_time.setText(chatList.get(position).getChatTime());
            holder.tv_item_chat_content.setText(chatList.get(position).getContent());
            if (chatList.get(position).isComeMsg()) {
                holder.img_item_chat.setImageResource(R.drawable.img_touxiang);
            } else {
                holder.img_item_chat.setImageResource(R.drawable.img_touxiang2);
            }

            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reply:
                btn_reply.setVisibility(View.GONE);
                rl_edit.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_send:
                String submitContent = et_content.getText().toString();
                if (TextUtils.isEmpty(submitContent)) {
                    ToastUtils.show(mBaseActivity, R.string.please_enter_leaving_message_content);
                    return;
                }

                Map<String, String> options = new HashMap<>();
                options.put("m_id", "");
                options.put("msg_id", mCheckedLeavingMessage.getId());
                options.put("content", submitContent);

                mUCenterActionsCreator.toSubmitLeavingMessage(mDeviceUserId, mSessionKey, mSessionValue, options);
//                if (!TextUtils.isEmpty(.trim())){
//                send();
//                rl_edit.setVisibility(View.GONE);
//                btn_reply.setVisibility(View.VISIBLE);
//            }
                break;

            default:
                break;
        }
    }

//    private void send() {
//        ChatEntity chatEntity = new ChatEntity();
//        chatEntity.setChatTime(Utils.getTodayDate());
//        chatEntity.setContent(et_content.getText().toString());
//        chatEntity.setComeMsg(false);
//        chatList.add(chatEntity);
//        adapter.notifyDataSetChanged();
//        //mPullRefreshMsgListView.setSelection(chatList.size() - 1);
//        et_content.setText("");
//    }

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
                case TodoActions.UCENTER_GET_MESSAGE_LIST_COMPLETE: //留言列表
                    //ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    List<LeavingMessage> dataList = (List<LeavingMessage>) resp.getData();
                    boolean update = true;
                    if (mCurPageIndex > Utils.FIRST_PAGE_INDEX) {
                        update = false;
                    }
                    mLeavingMessageAdapter.addData(dataList, update);
                    if (dataList == null || dataList.size() < Utils.PAGE_SIZE) {
                        mPullRefreshMsgListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        if (mCurPageIndex == Utils.FIRST_PAGE_INDEX) {
                            mPullRefreshMsgListView.setMode(PullToRefreshBase.Mode.BOTH);
                        }
                        mCurPageIndex++;
                    }
                    break;

                case TodoActions.UCENTER_SUBMIT_LEAVING_MESSAGE_COMPLETE: //提交留言
                    et_content.setText("");
                    rl_edit.setVisibility(View.GONE);
                    btn_reply.setVisibility(View.VISIBLE);
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        mPullRefreshMsgListView.onRefreshComplete();
    }

}
