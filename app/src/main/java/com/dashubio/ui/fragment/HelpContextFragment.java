package com.dashubio.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.ui.activity.AllUser.HelpActivity;
import com.dashubio.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpContextFragment extends BaseFragment {
    @BindView(R.id.tv_helptitle)
    TextView tvHelptitle;
    @BindView(R.id.tv_helpcontext)
    TextView tvHelpcontext;
    private View view;

    private String title, tvcontext;

    public HelpContextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help_context, container, false);
        ButterKnife.bind(this, view);
        Intent intent = ((HelpActivity)getActivity()).getIntent();
        title = intent.getStringExtra("data1");
        tvcontext = intent.getStringExtra("data2");
        tvHelptitle.setText(title);
        tvHelpcontext.setText("\u3000\u3000"+tvcontext);//首行缩进
        return view;
    }

}
