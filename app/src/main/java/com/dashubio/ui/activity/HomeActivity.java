package com.dashubio.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.app.UserManager;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.gson.GsonJiexi;
import com.dashubio.gson.UserGet;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.model.ucenter.User;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.view.TitleTextView;
import com.dashubio.utils.ADTitle;
import com.dashubio.utils.DBManager;
import com.dashubio.utils.GsonTitle;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.dashubio.utils.WifiUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 程序的主界面。
 * 
 * @author guolin
 */
public class HomeActivity extends BaseActivity {
	@BindView(R.id.titv_title)
	TitleTextView titv_title;

	public String mDeviceUserId = "";
	public String mSessionKey = "";
	public String mSessionValue = "";

	private String url = "";
	public int mCurPageIndex = Utils.FIRST_PAGE_INDEX;//当前需要请求显示的页数
	private UCenterStore mUCenterStore;

	public static final String ARG_TYPE = "arg_type";
	private UCenterActionsCreator mUCenterActionsCreator;
	private String userUrl;


	public static void startWithTypeAndSecondUser(boolean allUserType, SecondUser secondUser, Activity startingActivity) {
		Intent intent = new Intent(startingActivity, HomeActivity.class);
		intent.putExtra(ARG_TYPE, allUserType);
		intent.putExtra(Utils.ARG_SECONDUSER, secondUser);
		startingActivity.startActivity(intent);
	}




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		Log.d("ZZZ", ">>>>>>>>>>>>>>>>");


		mDeviceUserId = UserManager.getInstance().getUid();
		mSessionKey = Utils.T_SESSION + mDeviceUserId;
		mSessionValue = UserManager.getInstance().getSession();
//		url = "http://dashubio.returnlive.com/Mobile/Index/getad/" + mSessionKey + "/" + mSessionValue + "/uid/" + mDeviceUserId;
		url = "http://dashubio.cn/Mobile/Index/getad/" + mSessionKey + "/" + mSessionValue + "/uid/" + mDeviceUserId;


		userUrl = "http://dashubio.cn/Mobile/Ulogin/userlist/uid/"+mDeviceUserId+ "/" +mSessionKey+ "/" +mSessionValue+"/p/-1";
//		userUrl = "http://dashubio.returnlive.com/Mobile/Ulogin/userlist/uid/"+mDeviceUserId+ "/" +mSessionKey+ "/" +mSessionValue+"/p/-1";
		Log.d("ZZZ", "userUrl=="+userUrl);

		new Thread(new Runnable() {
			@Override
			public void run() {
				OkHttpUtils.get().url(userUrl).build().execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						Log.d("ZZZ", "失败=="+e);

					}

					@Override
					public void onResponse(String response, int id) {
//						Log.d("ZZZ", "response=="+response);
						List<UserGet.UserBean> userList = GsonJiexi.gsonUserData(response);
						DBManager dbManager = new DBManager(HomeActivity.this);
						ArrayList<UserGet.UserBean> userList2 = new ArrayList<>();
						userList2 = dbManager.searchUserData2();
						String result = "";
						for (UserGet.UserBean info : userList2) {
							result = result + String.valueOf(info.card_id);
							result = result + "\n" + "------------------------------------------" + "\n";

						}

//						Log.d("ZZZ", "result: =="+result);

						for (int i = userList.size()-1; i >-1; i--) {
							UserGet.UserBean users = userList.get(i);
							String userName = String.valueOf(users.name);
							String userid = String.valueOf(users.id);
							String card_id = String.valueOf(users.card_id);
							String phone = "";
							if (result.indexOf(card_id)!=-1){
//										Log.d("ZZZ", "数据库有数据");
								//数据库有数据
							}else {
								dbManager.addUserName(userName,userid,card_id,phone);

							}
						}

					}
				});
			}
		}).start();



		new Thread(new Runnable() {
			@Override
			public void run() {
				OkHttpUtils.get().url(url).build().execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {

					}

					@Override
					public void onResponse(String response, int id) {
						ADTitle.DataBean title = GsonTitle.gsonData(response);
						titv_title.setText(title.getCon()+" ");


					}
				});
			}
		}).start();


		Intent intent = getIntent();
		if (intent != null) {
			//boolean isAllUser = intent.getBooleanExtra(HomeActivity.ARG_TYPE, MenuFragment.DEFAULT_ALL_USER_TYPE);
			mSecondUser = (SecondUser) intent.getSerializableExtra(Utils.ARG_SECONDUSER);
		}
	}

}
