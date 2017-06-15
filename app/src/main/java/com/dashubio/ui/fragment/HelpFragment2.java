package com.dashubio.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.gson.GsonJiexi;
import com.dashubio.model.EarlyWarning;
import com.dashubio.model.HelpContent;
import com.dashubio.model.HelpItem;
import com.dashubio.model.HelpItem2;
import com.dashubio.model.HttpResult;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.AllUser.HelpActivity;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.adapter.EarlyWarningListAdapter;
import com.dashubio.ui.adapter.HelpAdapter;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.ui.fragment.base.MenuFragment;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment2 extends BaseFragment {
    @BindView(R.id.lv_help)
    ListView lvHelp;
    private View view;
    private Unbinder unbinder;
    private String url ;
    private HelpAdapter adapter;
    private String helpTitle,helpContext;
    HelpContent.DataBean helpContent;
    List<HelpContent.DataBean> list;



    public HelpFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        url= "http://dashubio.returnlive.com/Mobile/Index/help/"+mSessionKey+"/"+mSessionValue+"/uid/"+mDeviceUserId;
        url= "http://dashubio.cn/Mobile/Index/help/"+mSessionKey+"/"+mSessionValue+"/uid/"+mDeviceUserId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.help_fragment2, container, false);
        unbinder=ButterKnife.bind(this, view);
        adapter = new HelpAdapter(getActivity());
        lvHelp.setAdapter(adapter);

        init();
        lvHelp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                helpContent = (HelpContent.DataBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("data1", helpContent.getTitle());
                intent.putExtra("data2", helpContent.getContent());
                getActivity().startActivity(intent);


            }
        });
        return view;
    }

    private void init() {

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

//                Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                list=GsonJiexi.gsonData(response);
                for (int i = 0; i<list.size(); i++) {
                     helpContent = list.get(i);
                    adapter.addDATA(helpContent);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }



}
