package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dashubio.R;

import java.util.List;

/**
 * Created by tangjie on 2015/9/19.
 */
public class DetectAdapter extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    private List<String> mDataList;

    public DetectAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDataList = dataList;
    }


    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public String getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_detect, null);
            viewHolder.valueTv = (TextView) convertView.findViewById(R.id.value_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.valueTv.setText(getItem(position));
        return convertView;
    }

    class ViewHolder {
        TextView valueTv;
    }
}
