package com.dashubio.ui.activity.OneUser;

import android.content.Intent;
import android.os.Bundle;

import com.dashubio.R;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.ui.activity.BaseActivity;
import com.dashubio.utils.Utils;

import butterknife.ButterKnife;

/**
 * 声音界面的Activity，加入了sound_activity的布局。
 * 
 * @author guolin
 */
public class SecondMeasureActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_measure);
		Intent intent = getIntent();
		if (intent != null) {
			mSecondUser = (SecondUser) intent.getSerializableExtra(Utils.ARG_SECONDUSER);
		}
		ButterKnife.bind(this);
		if(mHeaderTitle != null){
			mHeaderTitle.setText(R.string.start_measuring);
		}
	}

}
