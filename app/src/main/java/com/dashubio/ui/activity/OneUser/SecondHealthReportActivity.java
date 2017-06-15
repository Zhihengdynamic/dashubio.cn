package com.dashubio.ui.activity.OneUser;

import android.os.Bundle;

import com.dashubio.R;
import com.dashubio.ui.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * 声音界面的Activity，加入了sound_activity的布局。
 * 
 * @author guolin
 */
public class SecondHealthReportActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_health);
		ButterKnife.bind(this);
		if(mHeaderTitle != null){
			mHeaderTitle.setText(R.string.health_report);
		}
	}

}