
package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.HelpItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用帮助ExpandAdapter
 */
public class HelpExpandAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;

    private List<HelpItem> mHelpItemtList = new ArrayList<HelpItem>();

    public HelpExpandAdapter(Context ctx, List<HelpItem> helpItemtList) {
        mContext = ctx;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHelpItemtList = helpItemtList;
    }

    /**
     * 刷新页面
     */
    public synchronized void refresh(List<HelpItem> dataList) {
        this.mHelpItemtList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mHelpItemtList == null ? 0 : mHelpItemtList.size();
    }

    @Override
    public HelpItem getGroup(int groupPosition) {
        return mHelpItemtList.get(groupPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public HelpItem getChild(int groupPosition, int childPosition) {
        return mHelpItemtList.get(groupPosition);
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

        HelpItem helpItem = getGroup(groupPosition);
        groupHolder.sectionName.setText(helpItem.getTitle());
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
        convertView = mInflater.inflate(R.layout.item_help_child, null);
        childHolder.helpContentTv = (TextView) convertView.findViewById(R.id.help_content_tv);
        convertView.setTag(childHolder);

        final HelpItem helpItem = getChild(groupPosition, childPosition);
        childHolder.helpContentTv.setText(helpItem.getContent());
        childHolder.helpContentTv
                .setBackgroundResource(R.drawable.expandablelistview_child_reply_bottom);


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
        private TextView helpContentTv;
    }

}
