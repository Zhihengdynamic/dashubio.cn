package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.HistoricalData;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康预警Adapter
 */
public class HealthWarningAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<HistoricalData> mDataList;

    public HealthWarningAdapter(Context context, List<HistoricalData> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDataList = dataList;
    }

    public void addData(List<HistoricalData> dataList, boolean update) {
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
    public HistoricalData getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_health_warning, null);
            viewHolder.ageTv = (TextView) convertView.findViewById(R.id.age_tv);
            viewHolder.measuredValueTv = (TextView) convertView.findViewById(R.id.measured_value_tv);
            viewHolder.warningTv = (TextView) convertView.findViewById(R.id.warning_tv);
            viewHolder.viewTv = (TextView) convertView.findViewById(R.id.view_tv);
            viewHolder.healthInstructionContainer = convertView.findViewById(R.id.health_instruction_container);
            viewHolder.healthInstructionTv = (TextView) convertView.findViewById(R.id.health_instruction_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final HistoricalData historicalData = getItem(position);
        viewHolder.ageTv.setText(historicalData.getAge());
        viewHolder.measuredValueTv.setText(historicalData.getValue() + historicalData.getUnit());
        viewHolder.warningTv.setText(historicalData.getWarning());

        viewHolder.healthInstructionTv.setText("健康指导");

        final boolean isShowHealthInstruction = historicalData.isShowHealthInstruction();

        viewHolder.viewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historicalData.setShowHealthInstruction(!isShowHealthInstruction);
                notifyDataSetChanged();
            }
        });

        if (isShowHealthInstruction) {
            viewHolder.healthInstructionContainer.setVisibility(View.VISIBLE);
        } else {
            viewHolder.healthInstructionContainer.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView ageTv;
        TextView measuredValueTv;
        TextView warningTv;
        TextView viewTv;
        View healthInstructionContainer;
        TextView healthInstructionTv;
    }

}
