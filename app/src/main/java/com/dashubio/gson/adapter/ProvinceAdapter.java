package com.dashubio.gson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.utils.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/5.
 */

public class ProvinceAdapter extends BaseAdapter {
    public static List list = new ArrayList();
    Context context;
    protected LayoutInflater inflater;

    public ProvinceAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDATA(String w86) {
        list.add(w86);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.popitemlayout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String w86 = (String) list.get(i);
        viewHolder.tvPopname.setText(w86);

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_popname)
        TextView tvPopname;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
