package com.dashubio.ui.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.DiseaseHistoryItem;
import com.dashubio.utils.Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * 既往史（手术、外伤、输血）Adapter
 */
public class PastHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<DiseaseHistoryItem> mDataList;
    private LinearLayout mOperationContainer;
    private int mCurrentPositon = -1;
    private Calendar mCalendar;

    public List<DiseaseHistoryItem> getDataList() {
        return mDataList;
    }

    public PastHistoryAdapter(Context context, List<DiseaseHistoryItem> dataList, LinearLayout operationContainer) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
        this.mOperationContainer = operationContainer;
        this.mCalendar = Calendar.getInstance();
    }

    /**
     * 刷新页面
     */
    public synchronized void refresh(List<DiseaseHistoryItem> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
        Utils.setLayoutHeight(mOperationContainer, mDataList.size() * mContext.getResources().getDimensionPixelSize(R.dimen.disease_history_item_height));
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_past_history, null);
            holder.deleteItem = convertView.findViewById(R.id.iv_delete_item);
//            holder.itemNum = (TextView) convertView.findViewById(R.id.item_num);
            holder.itemName = (EditText) convertView.findViewById(R.id.item_name_tv);
            holder.itemName.addTextChangedListener(new ItemNameTextWatcher(holder));
            holder.itemTime = (TextView) convertView.findViewById(R.id.item_time_tv);
            holder.addItem = (ImageView) convertView.findViewById(R.id.iv_add_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DiseaseHistoryItem diseaseHistoryItem = (DiseaseHistoryItem) getItem(position);

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCount() <= 1) {
                    return;
                }
                mDataList.remove(position);
                Utils.setLayoutHeight(mOperationContainer, mDataList.size() * mContext.getResources().getDimensionPixelSize(R.dimen.disease_history_item_height));
                notifyDataSetChanged();
            }
        });

//        holder.itemNum.setText(position + "");
        holder.itemName.setTag(position);
        holder.itemName.setText(diseaseHistoryItem.getName());
        holder.itemTime.setText(diseaseHistoryItem.getDateTime());
        final TextView timeTv = holder.itemTime;
        holder.itemTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPositon = position;
                DatePickerDialog dpd = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = (monthOfYear + 1) + "";
                        String day = dayOfMonth + "";
                        if (monthOfYear < 9) {
                            month = "0" + month;
                        }
                        if (dayOfMonth < 10) {
                            day = "0" + day;
                        }

                        String dataStr = year + "-" + month + "-" + day;
                        timeTv.setText(dataStr);
                        diseaseHistoryItem.setDateTime(dataStr);
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCount() >= 5) {
                    return;
                }
                mDataList.add(new DiseaseHistoryItem());
                Utils.setLayoutHeight(mOperationContainer, mDataList.size() * mContext.getResources().getDimensionPixelSize(R.dimen.disease_history_item_height));
                notifyDataSetChanged();
            }
        });

        if (position == 0) {
            holder.addItem.setVisibility(View.VISIBLE);
        } else {
            holder.addItem.setVisibility(View.GONE);
        }

        return convertView;
    }


    public static class ViewHolder implements Serializable {
        public View deleteItem;//删除子项
        //        public TextView itemNum;//子项编号
        public EditText itemName;//子项名称
        public TextView itemTime;//子项时间
        public ImageView addItem;//添加子项
    }

    class ItemNameTextWatcher implements TextWatcher {

        /**
         * 这里其实是缓存了一屏数目的viewholder， 也就是说一屏能显示10条数据，那么内存中就会有10个viewholder
         * 在这的作用是通过edittext的tag拿到对应的position，用于储存edittext的值
         */
        private ViewHolder mHolder;

        public ItemNameTextWatcher(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int position = (Integer) mHolder.itemName.getTag();
            DiseaseHistoryItem diseaseHistoryItem = (DiseaseHistoryItem) getItem(position);
            String content = s.toString();
            if (!TextUtils.isEmpty(content)) {
                content.trim();
            }
            diseaseHistoryItem.setName(content);
        }
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month = (monthOfYear + 1) + "";
            String day = dayOfMonth + "";
            if (monthOfYear < 9) {
                month = "0" + month;
            }
            if (dayOfMonth < 10) {
                day = "0" + day;
            }

        }
    };

}




