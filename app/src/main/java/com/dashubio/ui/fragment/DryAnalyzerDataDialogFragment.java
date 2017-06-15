
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
import android.widget.TextView;

import com.dashubio.R;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.AppApplication;
import com.dashubio.app.event.MessageEvent;
import com.dashubio.model.DryDetectItem;
import com.dashubio.model.DryDetectResult;
import com.dashubio.ui.view.NoScrollGridView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 干式生化仪数据
 *
 * @author ZXB
 */
public class DryAnalyzerDataDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final String KEY_KEY_UCENTER_ACTIONS_CREATOR = "UCenterActionsCreator:Key";

    public static DryAnalyzerDataDialogFragment newInstance(DryDetectResult resultData, UCenterActionsCreator ucenterActionsCreator) {
        final DryAnalyzerDataDialogFragment fragment = new DryAnalyzerDataDialogFragment();

        final Bundle args = new Bundle();
        args.putSerializable(KEY_CONTENT, resultData);
        args.putSerializable(KEY_KEY_UCENTER_ACTIONS_CREATOR, ucenterActionsCreator);
        fragment.setArguments(args);

        return fragment;
    }

    public DryAnalyzerDataDialogFragment() {
    }

    private TextView mDateTimeTv;
    private TextView mDeviceNumberTv;

    private NoScrollGridView mGridView;

    private DryDetectResult mResultData;
    private List<DryDetectItem> mItemList;

    private Button mSaveBtn;//保存
    private Button mCancelBtn;//取消

    private UCenterActionsCreator mUCenterActionsCreator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultData = getArguments() != null ? (DryDetectResult) getArguments().getSerializable(KEY_CONTENT) : null;
        mUCenterActionsCreator = getArguments() != null ? (UCenterActionsCreator) getArguments().getSerializable(KEY_KEY_UCENTER_ACTIONS_CREATOR) : null;
        if (mResultData != null) {
            mItemList = mResultData.getItemList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dialog_dry_analyzer_data, container, false);
        mDateTimeTv = (TextView) v.findViewById(R.id.dateTimeTv);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());
        String dataTimeStr = formatter.format(curDate);
        mDateTimeTv.setText(dataTimeStr);
        mDeviceNumberTv = (TextView) v.findViewById(R.id.deviceNumberTv);
        if(mResultData != null){
            mDeviceNumberTv.setText(getString(R.string.machine_number) + mResultData.getDeviceNumber());
        }
        mGridView = (NoScrollGridView) v.findViewById(R.id.item_gridview);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(AppApplication.getContext(), mItemList);
        mGridView.setAdapter(gridViewAdapter);
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
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                EventBus.getDefault().post(new MessageEvent(""));
                dismiss();
                break;

            case R.id.cancelBtn:
                dismiss();
                break;

            default:
                break;
        }
    }


    public class GridViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<DryDetectItem> mItemList;

        public GridViewAdapter(Context mContext, List<DryDetectItem> itemList) {
            super();
            this.mContext = mContext;
            this.mItemList = itemList;
        }

        @Override
        public int getCount() {
            return mItemList == null ? 0 : mItemList.size();
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
                        (this.mContext).inflate(R.layout.dialog_dry_analyzer_grid_item, null, false);
                holder.dataValueTv = (TextView) convertView.findViewById(R.id.data_value_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            DryDetectItem item = mItemList.get(position);
            String showStr = item.getName() + "\n";
            String showValue = getString(R.string.measured_value) + " " + item.getValue() + " " + item.getUnit();
            if (DryDetectItem.REAL_VALUE != item.getShowMin()) {
                if (item.getValue() < item.getShowMin()) {
                    showValue = getString(R.string.measured_value) + " <" + " " + item.getShowMin() + " " + item.getUnit();
                } else if (item.getValue() > item.getShowMax()) {
                    showValue = getString(R.string.measured_value) + " >" + " " + item.getShowMax() + " " + item.getUnit();
                }
            }
            showStr += showValue;
            showStr += "\n";
            showStr += "（ " + item.getReferenceRange() + " ）";

            holder.dataValueTv.setText(showStr);
            return convertView;
        }


        private class ViewHolder {
            TextView dataValueTv;
        }

    }


}
