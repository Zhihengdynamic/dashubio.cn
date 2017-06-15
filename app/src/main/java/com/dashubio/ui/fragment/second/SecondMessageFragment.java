package com.dashubio.ui.fragment.second;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.entity.ChatEntity;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.Utils;
import com.dashubio.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;


// 第二登陆人首页--用户留言
public class SecondMessageFragment extends BaseFragment implements OnClickListener{

	View view = null;
	ListView lv_msg;
	MsgAdapter adapter;
	Button btn_reply, btn_send;
	RelativeLayout rl_edit;
	EditText et_content;
	List<ChatEntity> chatList = null;  
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_second_message, container,false);
		initView();
		initChatContent();
		return view;
	}

	private void initChatContent() {
		// TODO Auto-generated method stub
		chatList = new ArrayList<ChatEntity>();  
        ChatEntity chatEntity = null;  
        for (int i = 0; i < 2; i++) {  
            chatEntity = new ChatEntity();  
            if (i % 2 == 0) {  
                chatEntity.setComeMsg(true);  
                chatEntity.setContent("医生您好，请问什么是高血压？什么会引起高血压？年轻人也会得吗？");  
                chatEntity.setChatTime(Utils.getTodayDate());  
            }else {  
                chatEntity.setComeMsg(false);  
                chatEntity.setContent("高血压病是指病因尚未明确····");  
                chatEntity.setChatTime(Utils.getTodayDate());  
            }  
            chatList.add(chatEntity);  
        }
        adapter = new MsgAdapter(chatList);  
        lv_msg.setAdapter(adapter);  
	}

	private void initView() {
		// TODO Auto-generated method stub
		lv_msg = (ListView) view.findViewById(R.id.lv_msg);
		btn_reply = (Button) view.findViewById(R.id.btn_reply);
		btn_send = (Button) view.findViewById(R.id.btn_send);
		rl_edit = (RelativeLayout) view.findViewById(R.id.rl_edit);
		et_content = (EditText) view.findViewById(R.id.et_content);
		et_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		et_content.setSingleLine(false);
		et_content.setHorizontallyScrolling(false);
		btn_reply.setOnClickListener(this);
		btn_send.setOnClickListener(this);
	}
	
	class MsgAdapter extends BaseAdapter {
		
		private LayoutInflater mInflater = null;
		private ViewHolder holder;
		private int COME_MSG = 0;  
        private int TO_MSG = 1;  
        private List<ChatEntity> chatList = null;
		public MsgAdapter (List<ChatEntity> chatList) {
			this.mInflater = LayoutInflater.from(getActivity().getApplicationContext());
			this.chatList = chatList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chatList.size();  
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return chatList.get(position); 
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override  
        public int getItemViewType(int position) {  
            // 区别两种view的类型，标注两个不同的变量来分别表示各自的类型  
            ChatEntity entity = chatList.get(position);  
            if (entity.isComeMsg())  
            {  
                return COME_MSG;  
            }else{  
                return TO_MSG;  
            }  
        } 
		
		 @Override  
	     public int getViewTypeCount() {  
	         // 这个方法默认返回1，如果希望listview的item都是一样的就返回1，我们这里有两种风格，返回2  
	         return 2;  
	     } 

		@Override
		public View getView(int position, View arg1, ViewGroup parent) {
			// TODO Auto-generated method stub
			holder = new ViewHolder();
			if (arg1 == null) {
				if (chatList.get(position).isComeMsg()) {  
					arg1 = mInflater.inflate(R.layout.list_item_chat_come, null);  
                }else {  
                	arg1 = mInflater.inflate(R.layout.list_item_chat_to, null);  
                }
				holder.tv_item_chat_time = (TextView) arg1
						.findViewById(R.id.tv_item_chat_time);
				holder.img_item_chat = (ImageView) arg1
						.findViewById(R.id.img_item_chat);
				holder.tv_item_chat_content = (TextView) arg1
						.findViewById(R.id.tv_item_chat_content);
				
				arg1.setTag(holder);
			} else {
				holder = (ViewHolder) arg1.getTag();
			}	
			holder.tv_item_chat_time.setText(chatList.get(position).getChatTime());
			holder.tv_item_chat_content.setText(chatList.get(position).getContent());  
			if (chatList.get(position).isComeMsg()) { 
				holder.img_item_chat.setImageResource(R.drawable.img_touxiang);  
			} else {
				holder.img_item_chat.setImageResource(R.drawable.img_touxiang2);  
			}
			
			return arg1;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_reply:
			btn_reply.setVisibility(View.GONE);
			rl_edit.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_send:
			send();
			rl_edit.setVisibility(View.GONE);
			btn_reply.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
	
	private void send(){  
        ChatEntity chatEntity = new ChatEntity();  
        chatEntity.setChatTime(Utils.getTodayDate());  
        chatEntity.setContent(et_content.getText().toString());  
        chatEntity.setComeMsg(false);  
        chatList.add(chatEntity);  
        adapter.notifyDataSetChanged();  
        lv_msg.setSelection(chatList.size() - 1);  
        et_content.setText("");  
    }  
}
