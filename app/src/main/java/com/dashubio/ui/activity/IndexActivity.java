package com.dashubio.ui.activity;

import android.os.Bundle;

import com.dashubio.R;

import butterknife.ButterKnife;

/**
 * 首页
 * 
 * @author Xinbin Zhang
 */
public class IndexActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		ButterKnife.bind(this);
		if(mHeaderTitle != null){
			mHeaderTitle.setText(R.string.home_page);
		}
	}

}
