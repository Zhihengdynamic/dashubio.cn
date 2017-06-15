package com.dashubio.ui.fragment.second;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HistoricalData;
import com.dashubio.model.HistoricalDataCategory;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.OneUser.SecondHistoryActivity;
import com.dashubio.ui.activity.OneUser.SecondMeasureActivity;
import com.dashubio.ui.adapter.HistoryDataExpandAdapter;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

// 第二登陆人首页--历史数据
public class SecondHistoryFragment extends BaseFragment implements OnClickListener, TagFlowLayout.OnSelectListener {

    View mRootView = null;

    @BindView(R.id.category_container)
    ScrollView mCategoryContainer;//类别目录

    @BindView(R.id.detect_instrument_1_title)
    LinearLayout mDetectInstrument1Title;
    @BindView(R.id.detect_instrument_1_tv)
    TextView mDetectInstrument1Tv;
    @BindView(R.id.detect_instrument_1_tagflowlayout)
    TagFlowLayout mDetectInstrument1Tagflowlayout;
    TagAdapter<HistoricalDataCategory.CategoryItem> mDetectInstrument1TagAdapter;

    @BindView(R.id.detect_instrument_2_title)
    LinearLayout mDetectInstrument2Title;
    @BindView(R.id.detect_instrument_2_tv)
    TextView mDetectInstrument2Tv;
    @BindView(R.id.detect_instrument_2_tagflowlayout)
    TagFlowLayout mDetectInstrument2Tagflowlayout;
    TagAdapter<HistoricalDataCategory.CategoryItem> mDetectInstrument2TagAdapter;

    @BindView(R.id.detect_instrument_3_title)
    LinearLayout mDetectInstrument3Title;
    @BindView(R.id.detect_instrument_3_tv)
    TextView mDetectInstrument3Tv;
    @BindView(R.id.detect_instrument_3_tagflowlayout)
    TagFlowLayout mDetectInstrument3Tagflowlayout;
    TagAdapter<HistoricalDataCategory.CategoryItem> mDetectInstrument3TagAdapter;

    List<HistoricalDataCategory> mCategoryList;

    @BindView(R.id.data_show_container)
    View mDataShowContainer;//数据展示


    @BindView(R.id.historical_data_lv)
    PullToRefreshExpandableListView mPullToRefreshExpandableListView;
    HistoryDataExpandAdapter mHistoryDataExpandAdapter;
    private List<HistoricalData> mHistoricalDataList = new ArrayList<HistoricalData>();

    private SecondUser mSecondUser;

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBaseActivity instanceof SecondHistoryActivity) {
            mSecondUser = ((SecondMeasureActivity) mBaseActivity).mSecondUser;
        } else {
            mSecondUser = ((HomeActivity) mBaseActivity).mSecondUser;
        }
        initDependencies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_second_history, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initUI();
        if (mUCenterActionsCreator != null) {
            mUCenterActionsCreator.toGetHistoricalDataCategoryList(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId());
        }
        return mRootView;
    }



    private void initUI() {

        mDetectInstrument1Tagflowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet == null || selectPosSet.size() == 0) {

                } else {
                    Iterator<Integer> it = selectPosSet.iterator();
                    while (it.hasNext()) {
                        int pos = it.next();
                        HistoricalDataCategory historicalDataCategory = mCategoryList.get(0);
                        List<HistoricalDataCategory.CategoryItem> subItems = historicalDataCategory.getSubItems();
                        HistoricalDataCategory.CategoryItem title = historicalDataCategory.getTitle();
                        mUCenterActionsCreator.toGetHistoricalDataList(mDeviceUserId, mSessionKey, mSessionValue,
                                mSecondUser.getId(), title.getId(), subItems.get(pos).getId());
                    }
                }
            }
        });

        mDetectInstrument2Tagflowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet == null || selectPosSet.size() == 0) {
                    // mHealthDetectingItemIndex = NO_SELECTED_ITEM;
                } else {
                    Iterator<Integer> it = selectPosSet.iterator();
                    while (it.hasNext()) {
                        int pos = it.next();
                        HistoricalDataCategory historicalDataCategory = mCategoryList.get(1);
                        List<HistoricalDataCategory.CategoryItem> subItems = historicalDataCategory.getSubItems();
                        HistoricalDataCategory.CategoryItem title = historicalDataCategory.getTitle();
                        mUCenterActionsCreator.toGetHistoricalDataList(mDeviceUserId, mSessionKey, mSessionValue,
                                mSecondUser.getId(), title.getId(), subItems.get(pos).getId());
                    }
                }
            }
        });

        mDetectInstrument3Tagflowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet == null || selectPosSet.size() == 0) {
                    // mHealthDetectingItemIndex = NO_SELECTED_ITEM;
                } else {
                    Iterator<Integer> it = selectPosSet.iterator();
                    while (it.hasNext()) {
                        int pos = it.next();
                        HistoricalDataCategory historicalDataCategory = mCategoryList.get(2);
                        List<HistoricalDataCategory.CategoryItem> subItems = historicalDataCategory.getSubItems();
                        HistoricalDataCategory.CategoryItem title = historicalDataCategory.getTitle();
                        mUCenterActionsCreator.toGetHistoricalDataList(mDeviceUserId, mSessionKey, mSessionValue,
                                mSecondUser.getId(), title.getId(), subItems.get(pos).getId());
                    }
                }
            }
        });

        mDetectInstrument2Tv.setOnClickListener(this);

        //mHistoricalDataAdapter = new HistoricalDataAdapter(null);
        mHistoryDataExpandAdapter = new HistoryDataExpandAdapter(mBaseActivity, mHistoricalDataList);
        mPullToRefreshExpandableListView.getRefreshableView().setAdapter(mHistoryDataExpandAdapter);
    }

    @Override
    public void onSelected(Set<Integer> selectPosSet) {
        if (selectPosSet == null || selectPosSet.size() == 0) {
        } else {
            Iterator<Integer> it = selectPosSet.iterator();
            while (it.hasNext()) {
                int pos = it.next();
                HistoricalDataCategory historicalDataCategory = mCategoryList.get(0);
                List<HistoricalDataCategory.CategoryItem> subItems = historicalDataCategory.getSubItems();
                HistoricalDataCategory.CategoryItem title = historicalDataCategory.getTitle();
                mUCenterActionsCreator.toGetHistoricalDataList(mDeviceUserId, mSessionKey, mSessionValue,
                        mSecondUser.getId(), title.getId(), subItems.get(pos).getId());
            }
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detect_instrument_2_tv:
                mUCenterActionsCreator.toGetHistoricalDataList(mDeviceUserId, mSessionKey, mSessionValue,
                        mSecondUser.getId(), "3", "1");
                break;

            default:
                break;
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
                case TodoActions.UCENTER_GET_HISTORICAL_DATA_CATEGORY_LIST_COMPLETE: //历史数据日期
                    mCategoryList = (List<HistoricalDataCategory>) resp.getData();
                    if (mCategoryList == null || mCategoryList.size() <= 0) {
                        return;
                    }


                    if (mCategoryList.size() >= 1) {
                        HistoricalDataCategory historicalDataCategory = mCategoryList.get(0);
                        mDetectInstrument1Title.setVisibility(View.VISIBLE);
                        mDetectInstrument1Tv.setText(historicalDataCategory.getTitle().getName());
                        mDetectInstrument1TagAdapter = new TagAdapter<HistoricalDataCategory.CategoryItem>(mCategoryList.get(0).getSubItems()) {
                            @Override
                            public View getView(FlowLayout parent, int position, HistoricalDataCategory.CategoryItem categoryItem) {
                                TextView propertySingleNameTv =
                                        (TextView) LayoutInflater.from(mBaseActivity).inflate(R.layout.item_choose_detection_item, parent, false);
                                propertySingleNameTv.setText(categoryItem.getName());
                                return propertySingleNameTv;
                            }
                        };
                        mDetectInstrument1Tagflowlayout.setAdapter(mDetectInstrument1TagAdapter);
                    }

                    if (mCategoryList.size() >= 2) {
                        HistoricalDataCategory historicalDataCategory = mCategoryList.get(1);
                        mDetectInstrument2Title.setVisibility(View.VISIBLE);
                        mDetectInstrument2Tv.setText(historicalDataCategory.getTitle().getName());
                        mDetectInstrument2TagAdapter = new TagAdapter<HistoricalDataCategory.CategoryItem>(mCategoryList.get(1).getSubItems()) {
                            @Override
                            public View getView(FlowLayout parent, int position, HistoricalDataCategory.CategoryItem categoryItem) {
                                TextView propertySingleNameTv =
                                        (TextView) LayoutInflater.from(mBaseActivity).inflate(R.layout.item_choose_detection_item, parent, false);
                                propertySingleNameTv.setText(categoryItem.getName());
                                return propertySingleNameTv;
                            }
                        };
                        mDetectInstrument2Tagflowlayout.setAdapter(mDetectInstrument2TagAdapter);
                    }

                    if (mCategoryList.size() >= 3) {
                        HistoricalDataCategory historicalDataCategory = mCategoryList.get(2);
                        mDetectInstrument3Title.setVisibility(View.VISIBLE);
                        mDetectInstrument3Tv.setText(historicalDataCategory.getTitle().getName());
                        mDetectInstrument3TagAdapter = new TagAdapter<HistoricalDataCategory.CategoryItem>(mCategoryList.get(2).getSubItems()) {
                            @Override
                            public View getView(FlowLayout parent, int position, HistoricalDataCategory.CategoryItem categoryItem) {
                                TextView propertySingleNameTv =
                                        (TextView) LayoutInflater.from(mBaseActivity).inflate(R.layout.item_choose_detection_item, parent, false);
                                propertySingleNameTv.setText(categoryItem.getName());
                                return propertySingleNameTv;
                            }
                        };
                        mDetectInstrument3Tagflowlayout.setAdapter(mDetectInstrument3TagAdapter);
                    }
                    break;

                case TodoActions.UCENTER_GET_HISTORICAL_DATA_LIST_COMPLETE: //历史数据
                    List<HistoricalData> historicalDataList = (List<HistoricalData>) resp.getData();
                    mHistoryDataExpandAdapter.addData(historicalDataList, true);
                    mCategoryContainer.setVisibility(View.GONE);
                    mDataShowContainer.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
        //mPullRefreshListView.onRefreshComplete();
    }

}
