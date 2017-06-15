
package com.dashubio.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.contec.jar.BC401.BC401_Struct;
import com.dashubio.R;
import com.dashubio.app.AppApplication;
import com.dashubio.app.event.UrineMessageEvent;
import com.dashubio.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 尿液分析仪数据
 *
 * @author ZXB
 */
public class UrineAnalyzerDataDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String KEY_CONTENT = "TestFragment:Content";

    public static UrineAnalyzerDataDialogFragment newInstance(ArrayList<BC401_Struct> dataList) {
        final UrineAnalyzerDataDialogFragment fragment = new UrineAnalyzerDataDialogFragment();

        final Bundle args = new Bundle();
        args.putSerializable(KEY_CONTENT, dataList);
        fragment.setArguments(args);

        return fragment;
    }

    public UrineAnalyzerDataDialogFragment() {
    }

    private ListView mListView;

    private ArrayList<BC401_Struct> mDataList;

    private Button mSaveBtn;//保存
    private Button mCancelBtn;//取消

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataList = getArguments() != null ? (ArrayList<BC401_Struct>) getArguments().getSerializable(KEY_CONTENT) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_urine_analyzer_data, container, false);
        mListView = (ListView) v.findViewById(R.id.listView);
        ListViewAdapter listViewAdapter = new ListViewAdapter(AppApplication.getContext());
        mListView.setAdapter(listViewAdapter);
        mSaveBtn = (Button) v.findViewById(R.id.saveBtn);
        mSaveBtn.setOnClickListener(this);
        mCancelBtn = (Button) v.findViewById(R.id.cancelBtn);
        mCancelBtn.setOnClickListener(this);
        return v;
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using
        // onCreateView() is
        // to modify any dialog characteristics. For example, the dialog
        // includes a
        // title by default, but your custom layout might not need it. So here
        // you can
        // remove the dialog title, but you must call the superclass to get the
        // Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
//                ToastUtils.show(getActivity(), R.string.save);
                EventBus.getDefault().post(new UrineMessageEvent(""));
                dismiss();
                break;

            case R.id.cancelBtn:
                ToastUtils.show(getActivity(), R.string.cancel);
                dismiss();
                break;

            default:
                break;
        }
    }


    public class ListViewAdapter extends BaseAdapter {
        private Context mContext;

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from
                        (this.mContext).inflate(R.layout.dialog_urine_analyzer_list_item, null, false);
                holder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
                holder.gridView = (GridView) convertView.findViewById(R.id.listview_item_gridview);
                holder.divider = convertView.findViewById(R.id.divider);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BC401_Struct data= mDataList.get(position);
            String timeStr = data.Year + "年" + data.Month + "月" + data.Date + "日" + " " + data.Hour + ":" + data.Min + ":" + data.Sec;
            holder.timeTv.setText(timeStr);
            GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, data);
            holder.gridView.setAdapter(gridViewAdapter);

            if(position == getCount() - 1){
                holder.divider.setVisibility(View.GONE);
            }

            return convertView;

        }

        private class ViewHolder {
            TextView timeTv;
            GridView gridView;
            View divider;
        }
    }


    public class GridViewAdapter extends BaseAdapter {
        private Context mContext;
        private BC401_Struct mBC401Struct;

        public GridViewAdapter(Context mContext, BC401_Struct bc401Struct) {
            super();
            this.mContext = mContext;
            this.mBC401Struct = bc401Struct;
        }

        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public Object getItem(int position) {
            return position;
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
                convertView = LayoutInflater.from
                        (this.mContext).inflate(R.layout.dialog_urine_analyzer_grid_item, null, false);
                holder.dataTitleTv = (TextView) convertView.findViewById(R.id.data_title_tv);
                holder.dataValueTv = (TextView) convertView.findViewById(R.id.data_value_tv);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            switch (position){
                case 0:
                    holder.dataTitleTv.setText("尿胆原（URO）");
                    String uroStr = "";
                    String uroUnit = "umol/l";
                    switch (mBC401Struct.URO){
                        case 0:
                            uroStr = "3.3";
                            break;

                        case 1:
                            uroStr = "33";
                            break;

                        case 2:
                            uroStr = "66";
                            break;

                        case 3:
                            uroStr = "131";
                            break;
                    }
                    uroStr += uroUnit;
                    holder.dataValueTv.setText(uroStr);
                    break;

                case 1:
                    holder.dataTitleTv.setText("潜血（BLD）");
                    String bldStr = "";
                    switch (mBC401Struct.BLD){
                        case 0:
                            bldStr = "-";
                            break;

                        case 1:
                            bldStr = "10/ul";
                            break;

                        case 2:
                            bldStr = "25/ul";
                            break;

                        case 3:
                            bldStr = "50/ul";
                            break;

                        case 4:
                            bldStr = "250/ul";
                            break;
                    }
                    holder.dataValueTv.setText(bldStr);
                    break;

                case 2:
                    holder.dataTitleTv.setText("胆红素（BIL）");
                    String bilStr = "";
                    switch (mBC401Struct.BIL){
                        case 0:
                            bilStr = "0umol/l";
                            break;

                        case 1:
                            bilStr = "17umol/l";
                            break;

                        case 2:
                            bilStr = "50umol/l";
                            break;

                        case 3:
                            bilStr = "100umol/l";
                            break;
                    }
                    holder.dataValueTv.setText(bilStr);
                    break;

                case 3:
                    holder.dataTitleTv.setText("酮体（KET）");
                    String ketStr = "";
                    switch (mBC401Struct.KET){
                        case 0:
                            ketStr = "0mmol/l";
                            break;

                        case 1:
                            ketStr = "1.5mmol/l";
                            break;

                        case 2:
                            ketStr = "4.0mmol/l";
                            break;

                        case 3:
                            ketStr = "8.0mmol/l";
                            break;
                    }
                    holder.dataValueTv.setText(ketStr);
                    break;

                case 4:
                    holder.dataTitleTv.setText("葡萄糖（GLU）");
                    String gluStr = "";
                    switch (mBC401Struct.GLU){
                        case 0:
                            gluStr = "0mmol/l";
                            break;

                        case 1:
                            gluStr = "2.8mmol/l";
                            break;

                        case 2:
                            gluStr = "5.5mmol/l";
                            break;

                        case 3:
                            gluStr = "14mmol/l";

                        case 4:
                            gluStr = "28mmol/l";

                        case 5:
                            gluStr = "55mmol/l";
                            break;
                    }
                    holder.dataValueTv.setText(gluStr);
                    break;

                case 5:
                    holder.dataTitleTv.setText("蛋白质（PRO）");
                    String proStr = "";
                    switch (mBC401Struct.PRO){
                        case 0:
                            proStr = "0g/l";
                            break;

                        case 1:
                            proStr = "0.15g/l";
                            break;

                        case 2:
                            proStr = "0.3g/l";
                            break;

                        case 3:
                            proStr = "1g/l";
                            break;

                        case 4:
                            proStr = "3g/l";
                            break;
                    }
                    holder.dataValueTv.setText(proStr);
                    break;

                case 6:
                    holder.dataTitleTv.setText("PH值");
                    String phStr = "";
                    switch (mBC401Struct.PH){
                        case 0:
                            phStr = "5";
                            break;

                        case 1:
                            phStr = "6";
                            break;

                        case 2:
                            phStr = "7";
                            break;

                        case 3:
                            phStr = "8";
                            break;

                        case 4:
                            phStr = "9";
                            break;
                    }
                    holder.dataValueTv.setText(phStr);
                    break;

                case 7:
                    holder.dataTitleTv.setText("亚硝酸盐（NIT）");
                    String nitStr = "";
                    switch (mBC401Struct.NIT){
                        case 0:
                            nitStr = "-";
                            break;

                        case 1:
                            nitStr = "18umol/l";
                            break;
                    }
                    holder.dataValueTv.setText(nitStr);
                    break;

                case 8:
                    holder.dataTitleTv.setText("白细胞（LEU）");
                    //传统单位
                    String leuStr = "";
                    switch (mBC401Struct.LEU){
                        case 0:
                            leuStr = "-";
                            break;

                        case 1:
                            leuStr = "15/ul";
                            break;

                        case 2:
                            leuStr = "70/ul";
                            break;

                        case 3:
                            leuStr = "125/ul";
                            break;

                        case 4:
                            leuStr = "500/ul";
                            break;
                    }
                    holder.dataValueTv.setText(leuStr);
                    break;

                case 9:
                    holder.dataTitleTv.setText("比重（SG）");
                    String sgStr = "";
                    switch (mBC401Struct.SG){
                        case 0:
                            sgStr = "<=1.005";
                            break;

                        case 1:
                            sgStr = "1.010";
                            break;

                        case 2:
                            sgStr = "1.015";
                            break;

                        case 3:
                            sgStr = "1.020";
                            break;

                        case 4:
                            sgStr = "1.025";
                            break;

                        case 5:
                            sgStr = ">=1.030";
                            break;
                    }
                    holder.dataValueTv.setText(sgStr);
                    break;

                case 10:
                    holder.dataTitleTv.setText("维生素C（VC）");
                    String vcStr = "";
                    switch (mBC401Struct.VC){
                        case 0:
                            vcStr = "0mmol/l";
                            break;

                        case 1:
                            vcStr = "0.6mmol/l";
                            break;

                        case 2:
                            vcStr = "1.4mmol/l";
                            break;

                        case 3:
                            vcStr = "2.8mmol/l";
                            break;

                        case 4:
                            vcStr = "5.6mmol/l";
                            break;
                    }
                    holder.dataValueTv.setText(vcStr);
                    break;
            }

            return convertView;

        }


        private class ViewHolder {
            TextView dataTitleTv;
            TextView dataValueTv;
        }

    }


}
