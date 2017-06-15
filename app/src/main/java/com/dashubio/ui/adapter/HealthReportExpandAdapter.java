
package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.HealthReportItem;
import com.dashubio.model.HealthReportMonthItem;
import com.dashubio.model.HealthReportProjectItem;
import com.dashubio.ui.fragment.second.SecondHealthReportFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 健康报告ExpandAdapter
 */
public class HealthReportExpandAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private SecondHealthReportFragment mSecondHealthReportFragment;

    private List<HealthReportItem> mHealthReportList = new ArrayList<HealthReportItem>();

    public HealthReportExpandAdapter(Context ctx, List<HealthReportItem> healthReportList, SecondHealthReportFragment secondHealthReportFragment) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHealthReportList = healthReportList;
        mSecondHealthReportFragment = secondHealthReportFragment;
    }

    /**
     * 刷新页面
     */
    public synchronized void refresh(List<HealthReportItem> dataList) {
        this.mHealthReportList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mHealthReportList == null ? 0 : mHealthReportList.size();
    }

    @Override
    public HealthReportItem getGroup(int groupPosition) {
        return mHealthReportList.get(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        List<HealthReportMonthItem> reportMonthList = getGroup(groupPosition).getReportMonthList();
//        return reportMonthList == null ? 0 : reportMonthList.size();
        return 1;
    }

    @Override
    public List<HealthReportMonthItem> getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getReportMonthList();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupViewHolder();

            convertView = mInflater.inflate(R.layout.expandablelistview_group_item_witharrow,
                    null);
            groupHolder.sectionName = (TextView) convertView
                    .findViewById(R.id.title_name);
            groupHolder.showChild = (ImageView) convertView.findViewById(R.id.show_child);

            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }

        HealthReportItem healthReportItem = getGroup(groupPosition);
        groupHolder.sectionName.setText(healthReportItem.getYear() + mContext.getString(R.string.year));
        if (isExpanded) {
            groupHolder.showChild.setImageResource(R.drawable.navigation_collapse);
        } else {
            groupHolder.showChild.setImageResource(R.drawable.navigation_expand);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView != null) {
            convertView = null;
        }
        final ChildViewHolder childHolder = new ChildViewHolder();
        convertView = mInflater.inflate(R.layout.health_report_child_item, null);
        childHolder.month_tagflowlayout = (TagFlowLayout) convertView.findViewById(R.id.month_tagflowlayout);
        convertView.setTag(childHolder);

        final HealthReportItem healthReportItem = getGroup(groupPosition);
        final List<HealthReportMonthItem> reportMonthItemList = getChild(groupPosition, childPosition);

        TagAdapter<HealthReportMonthItem> healthReportMonthTagAdapter = new TagAdapter<HealthReportMonthItem>(reportMonthItemList) {
            @Override
            public View getView(FlowLayout parent, int position, HealthReportMonthItem healthReportMonthItem) {
                TextView propertySingleNameTv =
                        (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_choose_detection_item, parent, false);
                propertySingleNameTv.setText(healthReportMonthItem.getMonth() + mContext.getString(R.string.month));
                return propertySingleNameTv;
            }
        };
        childHolder.month_tagflowlayout.setAdapter(healthReportMonthTagAdapter);
        childHolder.month_tagflowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                final HealthReportMonthItem reportMonthItem = reportMonthItemList.get(position);
                HealthReportProjectItem selectedReportProjectItem = null;
                TagFlowLayout projectTagFlowLayout = (TagFlowLayout) mInflater.inflate(R.layout.health_report_project_tagflowlayout, null);
                TagAdapter<HealthReportProjectItem> healthReportProjectTagAdapter = new TagAdapter<HealthReportProjectItem>(reportMonthItem.getProjectList()) {
                    @Override
                    public View getView(FlowLayout parent, int position, HealthReportProjectItem healthReportProjectItem) {
                        TextView propertySingleNameTv =
                                (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_choose_detection_item, parent, false);
                        propertySingleNameTv.setText(healthReportProjectItem.getName());
                        return propertySingleNameTv;
                    }
                };
                projectTagFlowLayout.setAdapter(healthReportProjectTagAdapter);

                String title = healthReportItem.getYear() + mContext.getString(R.string.year) + reportMonthItem.getMonth() + mContext.getString(R.string.month);
                final MaterialDialog alert = new MaterialDialog(mContext)
                        .setTitle(title).setContentView(projectTagFlowLayout);
                alert.setCanceledOnTouchOutside(true);
//                alert.setPositiveButton(R.string.ok, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //mUCenterActionsCreator.toGetHealthReportData(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId(), "5", "2016", "12");
//                        String deviceUserId = mSecondHealthReportFragment.mDeviceUserId;
//                        String sessionKey = mSecondHealthReportFragment.mSessionKey;
//                        String sessionValue = mSecondHealthReportFragment.mSessionValue;
//                        String secondUserId = mSecondHealthReportFragment.mSecondUser.getId();
//                        mSecondHealthReportFragment.mUCenterActionsCreator.toGetHealthReportData(deviceUserId, sessionKey, sessionValue,
//                                secondUserId, );
//                        alert.dismiss();
//                    }
//                })
//                alert.setNegativeButton(R.string.cancel, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alert.dismiss();
//                    }
//                });
                projectTagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        if (selectPosSet == null || selectPosSet.size() == 0) {
                            //
                        } else {
                            Iterator<Integer> it = selectPosSet.iterator();
                            while (it.hasNext()) {
                                int pos = it.next();
                                HealthReportProjectItem reportProjectItem = reportMonthItem.getProjectList().get(pos);
//
//                                String secondUserId = mSecondHealthReportFragment.mSecondUser.getId();
//                                //post访问需要提交的参数
//                                String postData = "mid=" + secondUserId;
//                                postData += "&pro=" + reportProjectItem.getId();
//                                postData += "&year=" + healthReportItem.getYear();
//                                postData += "&mouth=" + reportMonthItem.getMonth();
//                                Logger.i("postData = " + postData);
//                                //zzz&QueryTypeLst=1&CertificateTxt=dsds";
//                                //由于webView.postUrl(url, postData)中 postData类型为byte[] ，
//                                //通过EncodingUtils.getBytes(data, charset)方法进行转换
//                                //mSecondHealthReportFragment.mWebView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
                                mSecondHealthReportFragment.postURL(healthReportItem, reportMonthItem, reportProjectItem);
//                                mSecondHealthReportFragment.mUCenterActionsCreator.toGetHealthReportData(url,
//                                        secondUserId, String.valueOf(reportProjectItem.getId()), healthReportItem.getYear(), reportMonthItem.getMonth());
                                alert.dismiss();
                            }
                        }
                    }
                });
                alert.show();
                return false;
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        /* 很重要：实现ChildView点击事件，必须返回true */
        return true;
    }

    public final class GroupViewHolder {
        private TextView sectionName;
        private ImageView showChild;
    }

    public final class ChildViewHolder {
        //private ToggleButton queToggle;
        private TagFlowLayout month_tagflowlayout;
    }

}
