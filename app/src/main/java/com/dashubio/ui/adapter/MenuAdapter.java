/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 *
 */
public class MenuAdapter extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    private List<String> mDataList = null;
    private int mSelectPositon = -1;   //选中项

    public MenuAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mDataList = dataList;
    }


    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public String getItem(int position) {
        return mDataList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_fragment, null);
            holder.menuName = (TextView) convertView.findViewById(R.id.menu_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.menuName.setText(getItem(position));

        if(mSelectPositon == position){
            holder.menuName.setSelected(true);  //选中项背景
        }else{
            holder.menuName.setSelected(false);  //其他项背景
        }

        return convertView;
    }


    public static class ViewHolder {
        TextView menuName;
    }

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelectPositon){
            mSelectPositon = positon;
            notifyDataSetChanged();
        }
    }

}