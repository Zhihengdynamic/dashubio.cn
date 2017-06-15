package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.EarlyWarning;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class EarlyWarningListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<EarlyWarning> mDataList;

    public EarlyWarningListAdapter(Context context, List<EarlyWarning> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
    }

    public void addData(List<EarlyWarning> dataList, boolean update) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (update) {
            mDataList.clear();
        }

        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public EarlyWarning getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_early_warning, null);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.normalRangeTv = (TextView) convertView.findViewById(R.id.normal_range_tv);
            holder.illustrationTv = (TextView) convertView.findViewById(R.id.illustration_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EarlyWarning earlyWarning = getItem(position);

        holder.nameTv.setText(earlyWarning.getName());
        holder.normalRangeTv.setText(earlyWarning.getMin_v() + "-" + earlyWarning.getMax_v() + " " + earlyWarning.getUnit());
        holder.illustrationTv.setText(earlyWarning.getContent());

        return convertView;
    }


    static class ViewHolder {
        TextView nameTv;
        TextView normalRangeTv;
        TextView illustrationTv;
    }

}
