package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.HealthFilesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择的疾病Grid
 */
public class SelectedDiseaseGridAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater mInflater;
    private List<HealthFilesItem> mDataList = new ArrayList<HealthFilesItem>();
    private ActionListener mActionListener;

    /* 构造函数 */
    public SelectedDiseaseGridAdapter(Context context, List<HealthFilesItem> dataList, ActionListener actionListener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
        this.mActionListener = actionListener;
    }

    public void addData(List<HealthFilesItem> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
//        if (mDataList == null) {
//            mDataList = new ArrayList<>();
//        }
//
////        mDataList.clear();
//        if (dataList != null && dataList.size() > 0) {
//            mDataList.addAll(dataList);
//        }
//        Logger.i("mDataList.size() = " + mDataList.size());
//        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public HealthFilesItem getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_selected_disease_grid, null);
            holder.diseaseNameTv = (TextView) convertView.findViewById(R.id.disease_name_tv);
            holder.icoDelete = (ImageView) convertView.findViewById(R.id.ico_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HealthFilesItem diseaseItem = getItem(position);
        holder.diseaseNameTv.setText(diseaseItem.getName());
        holder.icoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionListener != null) {
                    mActionListener.onContactItemDelete(position);
                }
            }
        });

        return convertView;
    }


    public class ViewHolder {
        TextView diseaseNameTv;//  疾病
        ImageView icoDelete; // 删除
    }

    /**
     * 按钮点击
     */
    public interface ActionListener {
        //删除
        void onContactItemDelete(int position);
    }

}
