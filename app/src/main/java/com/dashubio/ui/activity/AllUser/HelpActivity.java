package com.dashubio.ui.activity.AllUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.dashubio.R;
import com.dashubio.ui.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * 声音界面的Activity，加入了sound_activity的布局。
 * 
 * @author guolin
 */
public class HelpActivity extends BaseActivity {
	private String title, tvcontext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		ButterKnife.bind(this);


		if(mHeaderTitle != null){
			mHeaderTitle.setText(R.string.use_help);
		}
	}

}
