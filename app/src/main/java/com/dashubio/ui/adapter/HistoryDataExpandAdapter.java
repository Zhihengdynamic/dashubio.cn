
package com.dashubio.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dashubio.R;
import com.dashubio.model.HistoricalData;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataExpandAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<HistoricalData> mHistoricalDataList = new ArrayList<HistoricalData>();

    public List<HistoricalData> getHistoricalDataList() {
        return mHistoricalDataList;
    }

    public void setHistoricalDataList(List<HistoricalData> historicalDataList) {
        this.mHistoricalDataList = historicalDataList;
    }


    public HistoryDataExpandAdapter(Context ctx, List<HistoricalData> historicalDataList) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHistoricalDataList = historicalDataList;
    }

    public void addData(List<HistoricalData> dataList, boolean update) {
        if (mHistoricalDataList == null) {
            mHistoricalDataList = new ArrayList<>();
        }

        if (update) {
            mHistoricalDataList.clear();
        }

        if (dataList != null && dataList.size() > 0) {
            mHistoricalDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mHistoricalDataList == null ? 0 : mHistoricalDataList.size();
    }

    @Override
    public HistoricalData getGroup(int groupPosition) {
        return mHistoricalDataList.get(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // return mComQesSelectionNodeList.get(groupPosition).childslist.size();
        return 1;
    }

    @Override
    public HistoricalData getChild(int groupPosition, int childPosition) {
        return mHistoricalDataList.get(groupPosition);
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.expandablelistview_group_item_witharrow, null);
            groupHolder.sectionName = (TextView) convertView.findViewById(R.id.title_name);
            groupHolder.showChild = (ImageView) convertView.findViewById(R.id.show_child);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }

        HistoricalData historicalData = getGroup(groupPosition);

        String dateStr = "";
        String addTime = historicalData.getAddtime();
//        if (!TextUtils.isEmpty(addTime)) {
//            dateStr = addTime.substring(0, 10);
//        }
        groupHolder.sectionName.setText(addTime);
        if (isExpanded) {
            groupHolder.showChild.setImageResource(R.drawable.navigation_collapse);
        } else {
            groupHolder.showChild.setImageResource(R.drawable.navigation_expand);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView != null) {
            convertView = null;
        }
        final ChildViewHolder childHolder = new ChildViewHolder();
        convertView = mInflater.inflate(R.layout.common_question_child_item, null);
        childHolder.resultTv = (TextView) convertView.findViewById(R.id.result_tv);
        childHolder.warningTv = (TextView) convertView.findViewById(R.id.warning_tv);
        convertView.setTag(childHolder);

        HistoricalData historicalData = getChild(groupPosition, childPosition);
        String statusStr = "";
        String valueStr = historicalData.getValue();
        if (!TextUtils.isEmpty(valueStr)) {
            statusStr = valueStr.replaceAll(";", "\n");
        }
        statusStr += " " + historicalData.getUnit();
        childHolder.resultTv.setText(statusStr);

        childHolder.warningTv.setText(historicalData.getWarning());
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
        private TextView resultTv;
        private TextView warningTv;
    }

    /**
     * 改变提问的显示状态
     *
     * @param queToggle
     * @param groupPosition
     * @param childPosition
     * @param isChecked
     */
    private void changeQueToggle(ToggleButton queToggle, TextView anwText, int groupPosition,
                                 int childPosition,
                                 boolean isChecked) {
        // if (isChecked) {
        // queToggle.setTextColor(mContext.getResources().getColor(R.color.group_select));
        // } else {
        // queToggle.setTextColor(mContext.getResources().getColor(R.color.black));
        // }
        int childrenCount = getChildrenCount(groupPosition);
        if (childrenCount == 1) {
            if (isChecked) {
                // queToggle
                // .setBackgroundResource(R.drawable.expandablelistview_child_question_singleline_select);
                queToggle
                        .setTextColor(mContext.getResources().getColor(R.color.group_select));
            } else {
                // queToggle
                // .setBackgroundResource(R.drawable.expandablelistview_child_question_singleline_normal);
                queToggle
                        .setTextColor(mContext.getResources().getColor(R.color.black));
            }
            anwText
                    .setBackgroundResource(R.drawable.expandablelistview_child_reply_bottom);
        } else if (childrenCount == 2) {
            if (childPosition == 0) {
                if (isChecked) {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_top_select);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.group_select));
                } else {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_top_normal);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.black));
                }
                anwText
                        .setBackgroundResource(R.drawable.expandablelistview_child_reply_middle);
            } else {
                if (isChecked) {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_bottom_select);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.group_select));
                } else {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_middle_normal);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.black));
                }
                anwText
                        .setBackgroundResource(R.drawable.expandablelistview_child_reply_bottom);
            }
        } else if (childrenCount >= 3) {
            if (isChecked) {
                if (childPosition == 0) {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_top_select);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.group_select));
                    anwText
                            .setBackgroundResource(R.drawable.expandablelistview_child_reply_middle);

                } else if (childPosition == (childrenCount - 1)) {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_middle_select);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.group_select));
                    anwText
                            .setBackgroundResource(R.drawable.expandablelistview_child_reply_bottom);
                } else {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_middle_select);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.group_select));
                    anwText
                            .setBackgroundResource(R.drawable.expandablelistview_child_reply_middle);
                }
            } else {
                if (childPosition == 0) {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_top_normal);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.black));
                    anwText
                            .setBackgroundResource(R.drawable.expandablelistview_child_reply_middle);

                } else if (childPosition == (childrenCount - 1)) {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_bottom_normal);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.black));
                    anwText
                            .setBackgroundResource(R.drawable.expandablelistview_child_reply_bottom);
                } else {
                    // queToggle
                    // .setBackgroundResource(R.drawable.expandablelistview_child_question_middle_normal);
                    queToggle
                            .setTextColor(mContext.getResources().getColor(R.color.black));
                    anwText
                            .setBackgroundResource(R.drawable.expandablelistview_child_reply_middle);
                }
            }

        }
    }

}
