package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.HealthWarningCategory;

import java.util.List;

/**
 * 健康预警标题
 */
public class HealthWarningSpAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<HealthWarningCategory> mDataList;

    public HealthWarningSpAdapter(Context context, List<HealthWarningCategory> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public HealthWarningCategory getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_health_warning_sp, null);
            viewHolder.warningSpTv = (TextView) convertView.findViewById(R.id.tv_health_warning_category);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HealthWarningCategory healthWarningCategory = getItem(position);
        viewHolder.warningSpTv.setText(healthWarningCategory.getName());
        return convertView;
    }

    class ViewHolder {
        TextView warningSpTv;
    }

}
