package com.dashubio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.model.HelpContent;
import com.dashubio.model.HelpItem2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzb on 2017/2/9.
 */

public class HelpAdapter extends BaseAdapter {
    Context context;
    protected LayoutInflater inflater;
    public List<HelpContent.DataBean> list = new ArrayList();

    public HelpAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDATA(HelpContent.DataBean helpContent) {
        list.add(helpContent);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HelpContent.DataBean  getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_help, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        HelpContent.DataBean helpContent = list.get(i);
        viewHolder.tvItemHelpContent.setText(helpContent.getTitle());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_help_content)
        TextView tvItemHelpContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
