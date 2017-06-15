package com.dashubio.ui.fragment.second;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.contec.jar.BC401.BC401_Data;
import com.contec.jar.BC401.BC401_Struct;
import com.contec.jar.BC401.DeviceCommand;
import com.contec.jar.BC401.DevicePackManager;
import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.AppApplication;
import com.dashubio.app.ErrorCode;
import com.dashubio.app.event.MessageEvent;
import com.dashubio.app.event.UrineMessageEvent;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.DetectItem;
import com.dashubio.model.DryDetectResult;
import com.dashubio.model.HttpResult;
import com.dashubio.model.health.BloodPressureMeasuredData;
import com.dashubio.model.health.EcgMeasuredData;
import com.dashubio.model.health.OxygenMeasuredData;
import com.dashubio.model.health.TemperatureMeasuredData;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.OneUser.SecondMeasureActivity;
import com.dashubio.ui.fragment.DryAnalyzerDataDialogFragment;
import com.dashubio.ui.fragment.UrineAnalyzerDataDialogFragment;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.ui.view.CardiographView;
import com.dashubio.ui.view.MyScrollView;
import com.dashubio.ui.view.PathView;
import com.dashubio.ui.view.XYPlotView;
import com.dashubio.utils.DBManager;
import com.dashubio.utils.SecondUserEntityUtils;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.Utils;
import com.dashubio.utils.WifiUtils;
import com.dashubio.utils.log.Logger;
import com.example.android.bluetoothlegatt.BleGattAttributes;
import com.example.android.bluetoothlegatt.BluetoothLeService;
import com.google.gson.Gson;
import com.linktop.whealthService.HealthApi;
import com.linktop.whealthService.OnBLEService;
import com.linktop.whealthService.task.BT_task;
import com.linktop.whealthService.task.Bp_task;
import com.linktop.whealthService.task.Bs_task;
import com.linktop.whealthService.task.Ox_task;
import com.linktop.whealthService.task.battery_task;
import com.todddavies.components.progressbar.ProgressWheel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.afree.data.xy.XYSeries;
import org.afree.data.xy.XYSeriesCollection;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import app.akexorcist.bluetotohspp.library.BluetoothConnection;
import app.akexorcist.bluetotohspp.library.BluetoothDeviceListActivity;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;


// 第二登陆人首页--开始测量
public class SecondMeasureFragment extends BaseFragment implements OnClickListener,
        HealthApi.EcgCallBack, HealthApi.HealthApiCallBack, HealthApi.BleCallBack {

    @BindView(R.id.device_status_tv)
    TextView mDeviceStatusTv;//设备连接状态

    @BindView(R.id.link_health_instrument_tv)
    TextView mLinkHealthInstrumentTv;//设备健康检测仪

    @BindView(R.id.measure_health_instrument_tv)
    TextView mMeasureHealthInstrumentTv;//健康检测仪开始测量

    @BindView(R.id.health_detecting_instrument_tagflowlayout)
    TagFlowLayout mHealthDetectingTagflowlayout;//健康检测仪检测项
    private final int NO_SELECTED_ITEM = -1;//没有选择
    private int mHealthDetectingItemIndex = NO_SELECTED_ITEM; //健康检测仪检测项
    private boolean mStartDetected = false;//是否开始检测

    @BindView(R.id.link_dry_biochemical_analyzer_tv)
    TextView mLinkDryBiochemicalAnalyzerTv;//设备干式生化仪

    @BindView(R.id.measure_dry_biochemical_analyzer_tv)
    TextView mMeasureDryBiochemicalAnalyzerTv;//干式生化仪开始测量

    @BindView(R.id.link_urine_analyzer_tv)
    TextView mLinkUrineAnalyzerTv;//设备尿液分析仪

    @BindView(R.id.measure_urine_analyzer_tv)
    TextView mMeasureUrineAnalyzerTv;//尿液分析仪开始测量

    private static View mEcgViewContainer;
    //private EcgView mEcgView;//心电图
//    private XYPlotView mEcgXYPlotView;//心电图
    private PathView mEcgXYPlotView;//心电图
    private XYSeries mEcgDefaultSeries;
    public static boolean isStopRunning = false;
    private double mEcgLastX = 0.0;
    private TextView mPRMax;//PR最大值
    private TextView mPRMin;//PR最小值
    private TextView mHeartRate;//心率
    private TextView mHeartRateVar;//心率变异性
    private TextView mMood;//心情
    private TextView mBreathingRate;//呼吸率
    private TextView mStopMeasuring;

    private DBManager dbManager;

    private static final boolean mSecure = BluetoothConnection.INSECURE;//是否安全连接蓝牙

    private HealthApi mHealthApi;
    private boolean bleOn = false;

    boolean ecg_modual_exist = false;

    private Timer batteryQueryTimer;

    String ecg = "";

    private boolean bs_modual_exist;

    private BluetoothDevice mHealthBluetoothDevice;//健康检测仪
    private BluetoothDevice mDryBluetoothDevice;//干式生化仪
    private BluetoothDevice mUrineBluetoothDevice;//尿液分析仪

    /**
     * Name of the connected device
     */
    private String mConnectedDeviceName = null;

    private String mDryReceivedData = "";//干式生化仪接收的数据
    private BC401_Data mUrineData;//尿液分析仪接收的数据

    private static final int REQUEST_OPEN_BT = 0;

    public final static int DETECT_TYPE_NO_DEVICE = -1;//没有连接设备
    public final static int DETECT_TYPE_HEALTH_DETECT = 0;//健康检测仪
    public final static int DETECT_TYPE_DRY_BIOCHEMICAL_ANALYZER = 1;//干式生化仪
    public final static int DETECT_TYPE_URINE_ANALYZER = 3;//尿液分析仪

    private int mDetectType = DETECT_TYPE_NO_DEVICE;

    private BluetoothSPP mUrineBluetoothSPP;//尿液分析仪操作
    private BluetoothLeService mDryBluetoothLeService;//干式生化仪操作

    private SecondUser mSecondUser;
    private Gson mGson;

    private EcgMeasuredData mEcgMeasuredData = new EcgMeasuredData();//心电数据
    private AlertDialog dia;
    private TextView pr_max_value,heart_rate_colon_value,pr_min_value,heart_rate_variability_value,startCeLiang,mood_value;

    //体温
    private Handler mbtHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideMeasuredProgressDialog();
            mStartDetected = false;
            mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
            switch (msg.what) {
                case BT_task.BT_RESULT:
                    final double result = msg.getData().getDouble("BodyTempResult");
                    String bodyTemperature = "体温：" + String.format("%.1f", result);
                    Logger.i(bodyTemperature);
                    //ToastUtils.show(mBaseActivity, bodyTemperature);
                    final MaterialDialog materialDialog = new MaterialDialog(mBaseActivity);
                    materialDialog.setTitle(R.string.body_temperature)
                            .setMessage(bodyTemperature)
                            .setPositiveButton(R.string.save, new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (WifiUtils.isNetworkConnected(getActivity())) {

                                        TemperatureMeasuredData measuredData = new TemperatureMeasuredData();
                                        //体温
                                        DetectItem temperatureItem = new DetectItem();
                                        temperatureItem.setValue((float) result);
                                        temperatureItem.setUnit("℃");
                                        measuredData.setTemperature(temperatureItem);

                                        mUCenterActionsCreator.toAddHealthDetectionData(mSecondUser.getId(), mDeviceUserId, mSessionKey, mSessionValue, measuredData);

                                        materialDialog.dismiss();
                                    }else {
                                        //将得到的数据存在数据库
                                        dbManager.addtiwen(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid, ((float) result) + "");
                                        materialDialog.dismiss();
                                        Toast.makeText(getActivity(), "成功存入本地数据库", Toast.LENGTH_SHORT).show();


                                    }


                                }
                            })
                            .setNegativeButton(R.string.cancel,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            materialDialog.dismiss();
                                        }
                                    });
                    materialDialog.show();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    //血氧
    @SuppressLint("HandlerLeak")
    private Handler moxHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Ox_task.OXYGEN_TEST_FINISH: //血氧测量结果
                    hideMeasuredProgressDialog();
                    mHealthApi.stopOX();
                    mStartDetected = false;
                    mMeasureHealthInstrumentTv.setText(R.string.start_measuring);

                    final double[] data = msg.getData().getDoubleArray("oxyResult");
                    if (data != null) {
//                        Logger.i("血氧:" + (int) (data[1] + 0.5));
//                        ToastUtils.show(mBaseActivity, "血氧:" + (int) (data[1] + 0.5));

//                        Logger.i("心率:" + (int) data[0]);
//                        ToastUtils.show(mBaseActivity, "心率:" + (int) data[0]);

                        String oxygen = "血氧:" + (int) (data[1] + 0.5) + "\n"
                                + "心率:" + (int) data[0];

                        final MaterialDialog materialDialog = new MaterialDialog(mBaseActivity);
                        materialDialog.setTitle(R.string.oxygen)
                                .setMessage(oxygen)
                                .setPositiveButton(R.string.save, new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (WifiUtils.isNetworkConnected(getActivity())) {
                                            OxygenMeasuredData measuredData = new OxygenMeasuredData();
                                            //血氧
                                            DetectItem oxygenItem = new DetectItem();
                                            oxygenItem.setValue((float) (data[1] + 0.5));
                                            oxygenItem.setUnit("");
                                            measuredData.setOxygen(oxygenItem);

                                            //心率
                                            DetectItem heartRateItem = new DetectItem();
                                            heartRateItem.setValue((float) data[0]);
                                            heartRateItem.setUnit("");
                                            measuredData.setHeartRate(heartRateItem);

                                            mUCenterActionsCreator.toAddHealthDetectionData(mSecondUser.getId(), mDeviceUserId, mSessionKey, mSessionValue, measuredData);

                                            materialDialog.dismiss();
                                        }else {
                                            //数据库存储
                                            dbManager.addxueyang(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid, ((float) (data[1] + 0.5)) + "", ((float) data[0]) + "");
                                            Toast.makeText(getActivity(), "成功存入本地数据库", Toast.LENGTH_SHORT).show();
                                            materialDialog.dismiss();
                                        }
                                    }
                                })
                                .setNegativeButton(R.string.cancel,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                materialDialog.dismiss();
                                            }
                                        });
                        materialDialog.show();
                    }
                    break;

                case Ox_task.AXIS_DATA://血氧数据
                    final int red_value = msg.getData().getInt("oxDataRed");
//                    ToastUtils.show(mBaseActivity, String.valueOf(red_value));
                    break;

                case Ox_task.OX_CMD_TIMEOUT://血氧测试超时
                    hideMeasuredProgressDialog();
                    mHealthApi.stopOX();
                    mStartDetected = false;
                    mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
                    int ox_cmd_type = msg.getData().getInt("ox_cmd_type");
                    ToastUtils.show(mBaseActivity, R.string.blood_oxygen_test_timeout);
                    break;
                default:
                    break;
            }
        }
    };

    //血压
    @SuppressLint("HandlerLeak")
    private Handler mbpHandler = new Handler() {
        public void handleMessage(Message msg) {
            hideMeasuredProgressDialog();
            mHealthApi.stopBP();
            mStartDetected = false;
            mMeasureHealthInstrumentTv.setText(R.string.start_measuring);

            switch (msg.what) {
                case Bp_task.BP_RESULT:
                    final int systolic = msg.arg1;
                    final int diastolic = msg.arg2;
                    Logger.i("高压:" + systolic);
                    Logger.i("低压:" + diastolic);
                    String bloodPressure = "高压:" + systolic + "\n"
                            + "低压:" + diastolic + "\n";

                    final MaterialDialog materialDialog = new MaterialDialog(mBaseActivity);
                    materialDialog.setTitle(R.string.blood_pressure)
                            .setMessage(bloodPressure)
                            .setPositiveButton(R.string.save, new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (WifiUtils.isNetworkConnected(getActivity())){
                                        BloodPressureMeasuredData measuredData = new BloodPressureMeasuredData();
                                        //高压
                                        DetectItem highPressureItem = new DetectItem();
                                        highPressureItem.setValue((float) systolic);
                                        highPressureItem.setUnit("mmHg");
                                        measuredData.setHighPressure(highPressureItem);



                                        //低压
                                        DetectItem lowPressureItem = new DetectItem();
                                        lowPressureItem.setValue((float) diastolic);
                                        lowPressureItem.setUnit("mmHg");
                                        measuredData.setLowPressure(lowPressureItem);

                                        mUCenterActionsCreator.toAddHealthDetectionData(mSecondUser.getId(), mDeviceUserId, mSessionKey, mSessionValue, measuredData);
                                        Log.d("MyBroadcastReceiver", "mSecondUser.getId(): "+mSecondUser.getId());
                                        Log.d("MyBroadcastReceiver", "mDeviceUserId: "+mDeviceUserId);
                                        materialDialog.dismiss();
                                    }else {//无网络存储在本地数据库
                                        dbManager.addxueya(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid,((float) systolic)+"",((float) diastolic)+"");
                                        materialDialog.dismiss();
                                        Toast.makeText(getActivity(), "成功存入本地数据库", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            })
                            .setNegativeButton(R.string.cancel,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            materialDialog.dismiss();
                                        }
                                    });
                    materialDialog.show();

                    // bp_button_state = START;
                    break;
                case Bp_task.LOUQI:
                    Toast.makeText(getActivity(),
                            "泄露", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    //血糖
    @SuppressLint("HandlerLeak")
    private Handler mbsHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bs_task.BS_PAPER_STATUS:
                    int init_paper = msg.getData().getByte("initpaperStatus");
                    if (init_paper == Bs_task.PAPER_OUT) {
                        // bs_button_state = START;
                        Toast.makeText(getActivity(), "开始测量血糖", Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "纸未放入", Toast.LENGTH_LONG).show();
                    } else if (init_paper == Bs_task.PAPER_IN)
                        // contentFragment.bsFragment.dialog();
                        Toast.makeText(getActivity(), "纸已放入", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_PAPER_IS_READY:
                    Toast.makeText(getActivity(), "等待血液进入···", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_PAPER_CHECK_TIMEOUT:
                    // bs_button_state = START;
                    Toast.makeText(getActivity(), "纸张放入超时", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_BLOOD_CHECK_TIMEOUT:
                    // bs_button_state = START;
                    Toast.makeText(getActivity(), "血液进入超时···", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_TESTING_PAPER_OUT:
                    // bs_button_state = START;
                    Toast.makeText(getActivity(), "纸未放入", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_RESULT:
                    //bs_button_state = START;
                    double data = msg.getData().getDouble("bsresult");
                    Logger.i("血糖值：" + data);
                    ToastUtils.show(mBaseActivity, "血糖值：" + data);
                    break;
                case Bs_task.BS_PAPER_IS_USED:
                    //bs_button_state = START;
                    Toast.makeText(getActivity(), "正在打印", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_GET_VER_FAIL:
                    // bs_button_state = START;
                    Toast.makeText(getActivity(), "获取版本号失败", Toast.LENGTH_LONG).show();
                    break;
                case Bs_task.BS_BLOOD_IN_DETECTED:
                    Toast.makeText(getActivity(), "血液分析仪未连接", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };


    //心电测量回调
    @Override
    public void onReceive(int[] data) {
        switch (data[0]) {
            case HealthApi.ECG_DATA://心电数据
                ecg = ecg + data[1] + "\n";

//                mEcgXYPlotView.addDATA(data[1]);
                if (mStartDetected) {
                    //mEcgView.addEcgData0(data[1]);


                    mEcgXYPlotView.addDATA(data[1]);
                    mEcgDefaultSeries.add(mEcgLastX, data[1]);
                    mEcgLastX += 0.35;
                }



                break;
            case HealthApi.ECG_HEARTRATE://心率数据
                final int avg_hr = (Integer) data[1];
                Logger.i("心率数据: " + avg_hr);
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mHeartRate.setText(avg_hr + "");
                        heart_rate_colon_value.setText(avg_hr + "");
                        mEcgMeasuredData.getHeartRate().setValue(avg_hr);
                    }
                });
                break;
            case HealthApi.ECG_RRMAX://RRMAX 数据
                final int rrmaxint = data[1];
                Logger.i("RRMAX 数据: " + rrmaxint);
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mPRMax.setText(rrmaxint + "");
                        pr_max_value.setText(rrmaxint + "");
                        mEcgMeasuredData.getPrmax().setValue(rrmaxint);
                        //mEcgMeasuredData.getPrmax().setUnit("-");
                    }
                });
                break;
            case HealthApi.ECG_RRMIN://RRMIX 数据
                final int rrminint = data[1];
                Logger.i("RRMIX 数据: " + rrminint);
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mPRMin.setText(rrminint + "");
                        pr_min_value.setText(rrminint + "");
                        mEcgMeasuredData.getPrmin().setValue(rrminint);
                        // mEcgMeasuredData.getPrmin().setUnit("-");
                    }
                });
                break;
            case HealthApi.ECG_HRV://心率变异性数据
                final int hRV = data[1];

                boolean isfinish = false;
                Logger.i("心率变异性数据: " + hRV);
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        heart_rate_variability_value.setText(hRV + "");
//                        mHeartRateVar.setText(hRV + "");
                        mEcgMeasuredData.getHeartRateVariability().setValue(hRV);
                        //mEcgMeasuredData.getHeartRateVariability().setUnit("-");






                    }
                });
                break;
            case HealthApi.ECG_MOOD://心情数据
                final int mood = data[1];
                Logger.i("心情数据: " + mood);
                Log.d("ZZB","心情数据: " + mood);
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mood_value.setText(mood + "");
//                        mMood.setText(mood + "");
                        mEcgMeasuredData.getMood().setValue(mood);
                        // mEcgMeasuredData.getMood().setUnit("-");
                    }
                });
                break;
            case HealthApi.ECG_BR://呼吸率
                // tv.append("Robust HR: " + data + "\n");
                Logger.i("呼吸率: " + data + "\n" + "");
                final int br = data[1];
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mBreathingRate.setText(br + "");
//                        mEcgMeasuredData.getRespirationRate().setValue(br);
                        // mEcgMeasuredData.getRespirationRate().setUnit("-");
                    }
                });
                break;
            case HealthApi.ECG_END://心电测量结束
                Logger.i("-----心电测量结束-----");
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mStartDetected = false;
                        mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
//                        mStopMeasuring.setText(R.string.save);
                        startCeLiang.setText("保存");
                        mEcgXYPlotView.isStoping = false;//停止心电图
                    }
                });
                break;

            default:
                break;
        }
    }

    @Override
    public void healthCallBack(int type, Object data) {
        switch (type) {
            case HealthApi.BIND_FINISHED:
                break;

            case HealthApi.BATTERY:
                setBatteryUi((byte[]) data);
                break;

            case HealthApi.UPDATA_CON_INTERVAL_FAIL_ACTION:
                Toast.makeText(getActivity(), "更新连接间隔错误", Toast.LENGTH_SHORT).show();
                break;

            case HealthApi.QUERY_ECG_MODULE_EXIST:
                byte modual = (Byte) data;
                if (modual == 0)
                    ecg_modual_exist = false;
                else if (modual == 1) {
                    ecg_modual_exist = true;
                }
                break;

            case HealthApi.QUERY_BS_MODULE_EXIST:
                byte modual1 = (Byte) data;
                if (modual1 == 0)
                    bs_modual_exist = false;
                else if (modual1 == 1) {
                    bs_modual_exist = true;
                }
                break;

            case HealthApi.INTENT_DEVICE_VERSION_SOFE:
                String sofe = (String) data;
                break;

            case HealthApi.INTENT_DEVICE_VERSION_HARD:
                String hard = (String) data;
                break;
        }
    }

    @Override
    public void bleCallBack(int type, Object data) {
        if (type == HealthApi.BLE_STATUS) {
            setBleUiState((Integer) data);
        }
    }

    private void modual_init() {
        mHealthApi.ecgQuery();
        mHealthApi.bsQuery();
        mHealthApi.batteryQuery();

        if (batteryQueryTimer == null)
            batteryQueryTimer = new Timer();
        batteryQueryTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHealthApi.batteryQuery();
            }
        }, 300000, 300000);
    }

    private void setBleUiState(int mState) {
        switch (mState) {
            case OnBLEService.BLE_CONNECTED:
                bleOn = true;
                setStatus(getString(R.string.title_connected_to, mHealthBluetoothDevice.getName()));
                Toast.makeText(getActivity(), "蓝牙已连接", Toast.LENGTH_LONG).show();
                break;
            case OnBLEService.BLE_DISCONNECTED:
                bleOn = false;
                setStatus(getString(R.string.title_not_connected));
                Toast.makeText(getActivity(), "蓝牙未连接", Toast.LENGTH_LONG).show();
                break;
            case OnBLEService.BLE_NOTIFICATION_ENABLED:
                modual_init();
                break;
            case OnBLEService.BLE_NOTIFICATION_NOTENABLED:
                break;
            default:
                break;
        }

    }

    private void setBatteryUi(byte[] battery) {
        switch (battery[0]) {
            case battery_task.BATTERY_QUERY:
                Toast.makeText(getActivity(), "" + battery[1] + "%", Toast.LENGTH_LONG).show();
                break;
            case battery_task.BATTERY_CHARING:
                Toast.makeText(getActivity(), "充电中···", Toast.LENGTH_LONG).show();
                break;
            case battery_task.BATTERY_FULL:
                Toast.makeText(getActivity(), "充电完成", Toast.LENGTH_LONG).show();
            default:
                break;
        }
    }

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    private MaterialDialog mProgressDialog;
    private MaterialDialog mMeasuredProgressDialog;

    @BindView(R.id.pw_spinner)
    ProgressWheel mProgressWheel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
        mUrineBluetoothSPP = new BluetoothSPP(mBaseActivity, mSecure);

        if (mBaseActivity instanceof SecondMeasureActivity) {
            mSecondUser = ((SecondMeasureActivity) mBaseActivity).mSecondUser;
        } else {
            mSecondUser = ((HomeActivity) mBaseActivity).mSecondUser;
        }


        mGson = new Gson();

        bindDryBluetoothService();//绑定干式生化仪的Service
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_measure, container, false);
        unbinder = ButterKnife.bind(this, view);
        dbManager = new DBManager(getActivity());//实例化数据库
        mHealthApi = new HealthApi();
        mHealthApi.init(getActivity().getApplicationContext(), this, mbpHandler, mbtHandler,
                moxHandler, mbsHandler, this);
        mHealthApi.setBleCallBack(this);
        initUI();

        if (!mUrineBluetoothSPP.isBluetoothAvailable()) {
            ToastUtils.show(getActivity(), R.string.bluetooth_not_available);
        }

        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.show(getActivity(), R.string.ble_not_supported);
        }

        return view;
    }


    public void onStart() {
        super.onStart();
        if (!mUrineBluetoothSPP.isBluetoothEnabled()) {
            mUrineBluetoothSPP.enable();
        } else {
            if (!mUrineBluetoothSPP.isServiceAvailable()) {
                mUrineBluetoothSPP.setupService();
                mUrineBluetoothSPP.startService();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mDispatcher.register(this);
        mDispatcher.register(mUCenterStore);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDispatcher.unregister(this);
        mDispatcher.unregister(mUCenterStore);
    }

    private void initDependencies() {
        mDispatcher = Dispatcher.getInstance();
        mUCenterActionsCreator = UCenterActionsCreator.getInstance(UCenterActionsCreator.class);
        mUCenterStore = UCenterStore.getInstance(UCenterStore.class);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isStopRunning = false;

        disconectHealth();
        try {
            if (mHealthApi != null) {
                mHealthApi.recover();
            }

            if (batteryQueryTimer != null) {
                batteryQueryTimer.cancel();
            }
            batteryQueryTimer = null;

            if (mUrineBluetoothSPP != null) {
                unRegisterUrineAnalyzerListener();
                mUrineBluetoothSPP.stopService();
            }


            mBaseActivity.unregisterReceiver(mDryGattUpdateReceiver);
            mBaseActivity.unbindService(mDryServiceConnection);
            mDryBluetoothLeService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开健康检测仪
     */
    private void disconectHealth() {
        try {
            if (mHealthApi != null) {
                mHealthApi.bleDisconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableBluetooth() {
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        if (bta == null) {
            Toast.makeText(getActivity(), "不支持蓝牙4.0", Toast.LENGTH_SHORT)
                    .show();
        } else {
            if (!bta.isEnabled()) {
                startActivityForResult(new Intent(
                                "android.bluetooth.adapter.action.REQUEST_ENABLE"),
                        REQUEST_OPEN_BT);
            }
        }
    }


    private void initUI() {
        String[] shareTargets = {"血压", "血氧/心率", "体温", "心电"};
        List<String> shareTargetLists = Arrays.asList(shareTargets);
        TagAdapter<String> tagAdapter = new TagAdapter<String>(shareTargetLists) {
            @Override
            public View getView(FlowLayout parent, int position, String str) {
                //can use viewholder
                TextView propertySingleNameTv =
                        (TextView) LayoutInflater.from(mBaseActivity).inflate(R.layout.item_choose_detection_item, parent, false);
                propertySingleNameTv.setText(str);
                return propertySingleNameTv;
            }
        };
        mHealthDetectingTagflowlayout.setAdapter(tagAdapter);
        mHealthDetectingTagflowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet == null || selectPosSet.size() == 0) {
                    mHealthDetectingItemIndex = NO_SELECTED_ITEM;
                } else {
                    Iterator<Integer> it = selectPosSet.iterator();
                    while (it.hasNext()) {
                        int pos = it.next();
                        mHealthDetectingItemIndex = pos;
                    }
                }
            }
        });


        mLinkHealthInstrumentTv.setOnClickListener(this);
        mMeasureHealthInstrumentTv.setOnClickListener(this);

        mLinkDryBiochemicalAnalyzerTv.setOnClickListener(this);
        mMeasureDryBiochemicalAnalyzerTv.setOnClickListener(this);

        mLinkUrineAnalyzerTv.setOnClickListener(this);
        mMeasureUrineAnalyzerTv.setOnClickListener(this);

        mEcgViewContainer = View.inflate(AppApplication.getContext(), R.layout.layout_tip_content_horizontal, null);
        //mEcgView = (EcgView) mEcgViewContainer.findViewById(R.id.ecgView);

//        mEcgXYPlotView = (XYPlotView) mEcgViewContainer.findViewById(R.id.ecg_chart);
        mEcgXYPlotView = (PathView) mEcgViewContainer.findViewById(R.id.ecg_chart);

        XYSeriesCollection series = mEcgXYPlotView.getDataset();
        mEcgDefaultSeries = (XYSeries) series.getSeries().get(XYPlotView.DEFAULT_SERIES_INDEX);

        mPRMax = (TextView) mEcgViewContainer.findViewById(R.id.pr_max_value);
        mPRMin = (TextView) mEcgViewContainer.findViewById(R.id.pr_min_value);
        mHeartRate = (TextView) mEcgViewContainer.findViewById(R.id.heart_rate_colon_value);
        mHeartRateVar = (TextView) mEcgViewContainer.findViewById(R.id.heart_rate_variability_value);
        mMood = (TextView) mEcgViewContainer.findViewById(R.id.mood_value);
        mBreathingRate = (TextView) mEcgViewContainer.findViewById(R.id.breathing_rate_value);
        mStopMeasuring = (TextView) mEcgViewContainer.findViewById(R.id.stop_measuring);
        mScro = (MyScrollView) mEcgViewContainer.findViewById(R.id.mScro);//滚动布局
        mStopMeasuring.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mStopMeasuring.getText().toString();
                if (getString(R.string.stop_measuring).equals(text)) {
                    if (mHealthApi != null) {
                        mHealthApi.ecgStop();
                        mStartDetected = false;
                        mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
                        mEcgXYPlotView.isStoping = false;//停止心电图
                    }
                    mStopMeasuring.setText(R.string.save);
                } else {
                    BottomDialogFragment.removeDialog(SecondMeasureFragment.this);



                    //心电无网络条件下存储在数据库
                    if (WifiUtils.isNetworkConnected(getActivity())) {
                        mUCenterActionsCreator.toAddHealthDetectionData(mSecondUser.getId(), mDeviceUserId, mSessionKey, mSessionValue, mEcgMeasuredData);
                    }else {
                        String xinlv = mHeartRate.getText().toString();
                        String RRmax = mPRMax.getText().toString();
                        String RRmin = mPRMin.getText().toString();
                        String xinlvbianyi = mHeartRateVar.getText().toString();
                        String mood = mMood.getText().toString();
                        dbManager.addxindian(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid,xinlv,RRmax,RRmin,xinlvbianyi,mood);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.link_health_instrument_tv://健康检测仪连接
                findBluetoothDevices(BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_HEALTH_DETECT);
                break;

            case R.id.measure_health_instrument_tv://健康检测仪开始测量
                //检测设备是否连接
                if (!bleOn) {
                    ToastUtils.show(mBaseActivity, R.string.please_connect_the_device_first);
                    return;
                }

                if (mDetectType != DETECT_TYPE_HEALTH_DETECT) {
                    return;
                }

                if (mHealthDetectingItemIndex == NO_SELECTED_ITEM) {
                    ToastUtils.show(mBaseActivity, R.string.please_select_detect_item);
                    return;
                }

                startStopHealthMeasured();
                break;

            case R.id.link_dry_biochemical_analyzer_tv://干式生化仪连接
                findBluetoothDevices(BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_DRY_BIOCHEMICAL_ANALYZER);
                break;

            case R.id.measure_dry_biochemical_analyzer_tv://干式生化仪开始测量
                break;

            case R.id.link_urine_analyzer_tv://尿液分析仪连接
                findBluetoothDevices(BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_URINE_ANALYZER);
                break;

            case R.id.measure_urine_analyzer_tv://尿液分析仪开始测量
                break;
        }
    }



    private MyScrollView mScro;

    private void startStopHealthMeasured() {
        switch (mHealthDetectingItemIndex) {
            case 0://血压
                //开始、停止测量血压
                if (!mStartDetected) {
                    mHealthApi.startBP();
                    mStartDetected = true;
                    mMeasureHealthInstrumentTv.setText(R.string.stop_measuring);
                    showMeasuredProgressDialog();
                } else {
                    mHealthApi.stopBP();
                    mStartDetected = false;
                    mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
                }
                break;

            case 1://血氧/心率
                //开始、停止测量血氧
                if (!mStartDetected) {
                    mHealthApi.startOX();
                    mStartDetected = true;
                    mMeasureHealthInstrumentTv.setText(R.string.stop_measuring);
                    showMeasuredProgressDialog();
                } else {
                    mHealthApi.stopOX();
                    mStartDetected = false;
                    mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
                }
                break;

            case 2://体温
                //开始、停止测量体温
                if (!mStartDetected) {
                    mHealthApi.startBT();
                    mStartDetected = true;
                    mMeasureHealthInstrumentTv.setText(R.string.stop_measuring);
                    showMeasuredProgressDialog();
                } else {
                    mStartDetected = false;
                    mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
                }
                break;

            case 3://心电
                //开始、停止测量心电
                if (!mStartDetected) {

                    mEcgXYPlotView.arrast.clear();//心电图清空
                    mHealthApi.ecgStart();
                    mStartDetected = true;
                    mMeasureHealthInstrumentTv.setText(R.string.stop_measuring);
                    mStopMeasuring.setText(R.string.stop_measuring);
                    mEcgDefaultSeries.clear();
//                    mEcgXYPlotView.repaint();
                    //重置一些数据
                    mEcgMeasuredData.getHeartRate().setValue(0);
                    mEcgMeasuredData.getHeartRate().setUnit("-");
                    mEcgMeasuredData.getPrmax().setValue(0);
                    mEcgMeasuredData.getPrmax().setUnit("-");
                    mEcgMeasuredData.getPrmin().setValue(0);
                    mEcgMeasuredData.getPrmin().setUnit("-");
                    mEcgMeasuredData.getHeartRateVariability().setValue(0);
                    mEcgMeasuredData.getHeartRateVariability().setUnit("-");
                    mEcgMeasuredData.getMood().setValue(0);
                    mEcgMeasuredData.getMood().setUnit("-");
                    mEcgMeasuredData.getRespirationRate().setValue(0);
                    mEcgMeasuredData.getRespirationRate().setUnit("-");
                    //显示Dialog
//                    BottomDialogFragment.showDialog(this);
                    showXinDianDialog();








                } else {
                    mHealthApi.ecgStop();
                    mStartDetected = false;
                    mMeasureHealthInstrumentTv.setText(R.string.start_measuring);
                }
                break;
        }
    }

    public void showXinDianDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = View.inflate(getActivity(),R.layout.layout_tip_content_horizontal,null);
        startCeLiang = (TextView) view.findViewById(R.id.stop_measuring);
        builder.setCancelable(false);
        mEcgXYPlotView = (PathView) view.findViewById(R.id.ecg_chart);
        mood_value = (TextView) view.findViewById(R.id.mood_value);
        pr_max_value = (TextView) view.findViewById(R.id.pr_max_value);//RR最大值
        pr_min_value = (TextView) view.findViewById(R.id.pr_min_value);//RR最小值
        heart_rate_colon_value = (TextView) view.findViewById(R.id.heart_rate_colon_value);//心率
        heart_rate_variability_value = (TextView) view.findViewById(R.id.heart_rate_variability_value);//心率变异
        startCeLiang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.stop_measuring).equals(startCeLiang.getText().toString())){
                    startCeLiang.setText("保存");
                    mEcgXYPlotView.isStoping = false;//停止心电图
                }else {
                    dia.dismiss();
                    //心电无网络条件下存储在数据库
                    if (WifiUtils.isNetworkConnected(getActivity())) {
                        Log.e("XIN", "onClick: "+mDeviceUserId+"mSessionValue=="+mSessionValue );
                        mUCenterActionsCreator.toAddHealthDetectionData(mSecondUser.getId(), mDeviceUserId, mSessionKey, mSessionValue, mEcgMeasuredData);
                    }else {
                        String xinlv = mHeartRate.getText().toString();
                        String RRmax = mPRMax.getText().toString();
                        String RRmin = mPRMin.getText().toString();
                        String xinlvbianyi = mHeartRateVar.getText().toString();
                        String mood = mMood.getText().toString();
                        dbManager.addxindian(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid,xinlv,RRmax,RRmin,xinlvbianyi,mood);
                        Toast.makeText(getActivity(), "成功存入本地数据库", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setView(view);
        dia = builder.show();

    }

    /**
     * 查找蓝牙设备
     *
     * @param requestCode
     */

    private void findBluetoothDevices(int requestCode) {
        //断开连接的设备
        disconectAllDevices();

        //重置标题连接状态
        setStatus(getResources().getString(R.string.title_not_connected));

        switch (requestCode) {
            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_HEALTH_DETECT://健康检测仪
                mHealthBluetoothDevice = null;
                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_DRY_BIOCHEMICAL_ANALYZER://干式生化仪
                mDryBluetoothDevice = null;
                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_URINE_ANALYZER://连接尿液分析仪
                mUrineBluetoothDevice = null;
                break;
        }

        //连接设备
        Intent intent = new Intent(mBaseActivity, BluetoothDeviceListActivity.class);
        intent.putExtra(BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_TYPE_KEY, requestCode);
        startActivityForResult(intent, requestCode);

    }

    /**
     * 断开所有连接的设备
     */
    private void disconectAllDevices() {
        try {
            //断开健康检测仪
            disconectHealth();
            mHealthBluetoothDevice = null;

            //断开干式生化仪
            if (mDryBluetoothDevice != null) {
                mBaseActivity.unregisterReceiver(mDryGattUpdateReceiver);
                mDryBluetoothLeService.disconnect();
                mDryBluetoothDevice = null;
            }

            //断开尿液分析仪
            if (mUrineBluetoothSPP.getServiceState() == BluetoothState.STATE_CONNECTED) {
                mUrineBluetoothSPP.disconnect();
                unRegisterUrineAnalyzerListener();
            }
            mUrineBluetoothDevice = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_OPEN_BT://打开蓝牙
                if (resultCode == Activity.RESULT_OK) {
                    mUrineBluetoothSPP.setupService();
                    ToastUtils.show(mBaseActivity, "bt_turn_on");
                } else {
                    ToastUtils.show(mBaseActivity, "bt_turn_fail");
                }
                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_HEALTH_DETECT://健康检测仪
                toConnectBluetoothDevice(data, BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_HEALTH_DETECT);
                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_DRY_BIOCHEMICAL_ANALYZER://干式生化仪
                toConnectBluetoothDevice(data, BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_DRY_BIOCHEMICAL_ANALYZER);
                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_URINE_ANALYZER://连接尿液分析仪
                toConnectBluetoothDevice(data, BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_URINE_ANALYZER);
                break;
        }
    }

    /**
     * 连接蓝牙设备，健康检测仪、干式生化仪、尿液分析仪统一处理
     *
     * @param data
     */
    private synchronized void toConnectBluetoothDevice(Intent data, int requestCode) {
        String address = data.getExtras().getString(BluetoothState.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice bluetoothDevice = data.getExtras().getParcelable(BluetoothState.EXTRA_DEVICE);
        String deviceName = bluetoothDevice.getName();
        Logger.i("------toConnectBluetoothDevice()------>deviceName = " + deviceName + " Address = " + bluetoothDevice.getAddress());
        switch (requestCode) {
            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_HEALTH_DETECT://健康检测仪
                if (!mHealthApi.bleConnect(address)) {
                    Logger.e("------健康检测仪------>connect error");
                } else {
                    mHealthBluetoothDevice = bluetoothDevice;
                    mDetectType = DETECT_TYPE_HEALTH_DETECT;
                }

                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_DRY_BIOCHEMICAL_ANALYZER://干式生化仪
                if (!TextUtils.isEmpty(deviceName) && (Utils.string2Int(deviceName) > 0)) {
                    mBaseActivity.registerReceiver(mDryGattUpdateReceiver, makeGattUpdateIntentFilter());
                    final boolean result = mDryBluetoothLeService.connect(address);
                    Logger.i("Connect 干式生化仪 request result=" + result);
                    mDryBluetoothDevice = bluetoothDevice;
                    mDetectType = DETECT_TYPE_DRY_BIOCHEMICAL_ANALYZER;
                    showMeasuredProgressDialog();
                } else {
                    ToastUtils.show(mBaseActivity, R.string.non_dry_biochemical_analyzer_device);
                }
                break;

            case BluetoothDeviceListActivity.REQUEST_CONNECT_DEVICE_URINE_ANALYZER://尿液分析仪
                if (!TextUtils.isEmpty(deviceName) && deviceName.startsWith("BC")) {
                    registerUrineAnalyzerListener();//注册监听事件
                    mUrineBluetoothSPP.connect(data, mSecure);
                    mUrineBluetoothDevice = bluetoothDevice;
                    mDetectType = DETECT_TYPE_URINE_ANALYZER;
                    showMeasuredProgressDialog();
                } else {
                    ToastUtils.show(mBaseActivity, R.string.non_urine_analyzer_device);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUCenterStoreChange(UCenterStore.UCenterStoreChangeEvent event) {
        String currentStatus = event.getCurrentStatus();//获取当前状态

        if (TextUtils.isEmpty(currentStatus)) {
            return;
        }

        //开始请求
        if (TodoActions.UCENTER_LOADING_START.equals(currentStatus)) {
            showLoadingDialog();
            return;
        }

        //下面为接收到服务器返回响应

        //服务器连接异常
        if (TodoActions.UCENTER_SERVER_CONNECTION_ERROR.equals(currentStatus)) {
            hideLoadingDialog();
            HttpResult errorResp = event.getHttpResp();
//            ToastUtils.show(mBaseActivity, errorResp.getMsg());
            return;
        }

        //处理请求完成
        hideLoadingDialog();
        HttpResult resp = event.getHttpResp();
        if (resp == null) {
            return;
        }
        if (ErrorCode.SUCCESS.equals(resp.getStatus())) {
            switch (currentStatus) {
                case TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE: //健康检测仪上传数据
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    break;

                case TodoActions.UCENTER_UPLOAD_DRY_ANALYZER_DATA_COMPLETE: //干式生化仪上传数据
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    break;

                case TodoActions.UCENTER_ADD_URINE_DETECTION_DATA_COMPLETE: //尿液分析仪上传数据
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    break;
            }
        } else {
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
    }


    private void showMeasuredProgressDialog() {
        mMeasuredProgressDialog = new MaterialDialog(mBaseActivity);
        View view = LayoutInflater.from(mBaseActivity).inflate(R.layout.progressbar_container, null);
        mMeasuredProgressDialog.setTitle(getString(R.string.being_measured));
        mMeasuredProgressDialog.setView(view);
        mMeasuredProgressDialog.setPositiveButton(getString(R.string.stop_measuring), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    disconectAllDevices();
                    setStatus(getResources().getString(R.string.title_not_connected));
                    mMeasuredProgressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).show();
    }

    public void hideMeasuredProgressDialog() {
        if (mMeasuredProgressDialog != null) {
            mMeasuredProgressDialog.dismiss();
        }
    }


    /**
     * Updates the status on the action bar.
     * @param statusInfo status
     */
    private synchronized void setStatus(CharSequence statusInfo) {
        if (mDeviceStatusTv != null) {
            mDeviceStatusTv.setText(statusInfo);
        }
    }

    /**
     * 注册尿液分析仪监听
     */
    private void registerUrineAnalyzerListener() {
        mUrineBluetoothSPP.setBluetoothStateListener(mBluetoothStateListener);
        mUrineBluetoothSPP.setOnDataReceivedListener(mOnDataReceivedListener);
        mUrineBluetoothSPP.setBluetoothConnectionListener(mBluetoothConnectionListener);
        mUrineBluetoothSPP.setAutoConnectionListener(mAutoConnectionListener);
    }

    /**
     * 取消尿液分析仪监听
     */
    private void unRegisterUrineAnalyzerListener() {
        mUrineBluetoothSPP.setBluetoothStateListener(null);
        mUrineBluetoothSPP.setOnDataReceivedListener(null);
        mUrineBluetoothSPP.setBluetoothConnectionListener(null);
        mUrineBluetoothSPP.setAutoConnectionListener(null);
    }

    /**
     * 尿液分析仪 Listener when bluetooth connection has changed
     */
    BluetoothSPP.BluetoothStateListener mBluetoothStateListener = new BluetoothSPP.BluetoothStateListener() {
        public void onServiceStateChanged(int state) {
            if (state == BluetoothState.STATE_CONNECTED) {
                Logger.i("State : Connected");
            } else if (state == BluetoothState.STATE_CONNECTING) {
                setStatus(getString(R.string.title_connecting));
                Logger.i("State : Connecting");
            } else if (state == BluetoothState.STATE_LISTEN) {
                setStatus(getString(R.string.title_not_connected));
                Logger.i("State : Listen");
            } else if (state == BluetoothState.STATE_NONE) {
                setStatus(getResources().getString(R.string.title_not_connected));
                Logger.i("State : None");
            }
        }
    };

    /**
     * 尿液分析仪 Listener for data receiving
     */
    BluetoothSPP.OnDataReceivedListener mOnDataReceivedListener = new BluetoothSPP.OnDataReceivedListener() {
        @Override
        public void onDataReceived(byte[] data, String message) {
            Logger.i("Message : " + message);
            try {
                DevicePackManager devicePackManager = new DevicePackManager();
                byte _back = devicePackManager.arrangeMessage(data, data.length);
                switch (_back) {
                    case (byte) 0x02://校正成功
                        Logger.i("------校正成功------");
                        mUrineBluetoothSPP.send(DeviceCommand.Request_AllData(), false);
                        break;

                    case (byte) 0x05://返回数据
                        Logger.i("------返回数据------" + Utils.bytesToHexString(data));
                        mUrineData = devicePackManager.mBc401_Data;
                        //用户代码
                        if (mUrineData != null && mUrineData.Structs != null) {
                            hideMeasuredProgressDialog();
                            String jsonData = mGson.toJson(mUrineData.Structs);
                            Logger.i(jsonData);
                            UrineAnalyzerDataDialogFragment.newInstance(mUrineData.Structs).show(getFragmentManager(), "TAG");
                        }

                        if (devicePackManager.Percent == 100) {
                            //此时证明数据全部接收完毕
                            mUrineBluetoothSPP.send(DeviceCommand.Delete_AllData(), false);
                        }
                        break;

                    case (byte) 0x06://删除完毕
                        Logger.i("------删除完毕------");
                        break;

                    case (byte) 0x08://无新数据
                        Logger.i("------无新数据------");
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 尿液分析仪 Listener for connection atatus
     */
    BluetoothSPP.BluetoothConnectionListener mBluetoothConnectionListener = new BluetoothSPP.BluetoothConnectionListener() {
        public void onDeviceConnected(String name, String address) {
            setStatus(getString(R.string.title_connected_to, name));
            Logger.i("Device Connected!!");

            mUrineBluetoothSPP.send(DeviceCommand.Synchronous_Time(), false);
        }

        public void onDeviceDisconnected() {
            Logger.i("Device Disconnected!!");
        }

        public void onDeviceConnectionFailed() {
            Logger.i("Unable to Connected!!");
        }
    };

    /**
     * 尿液分析仪 Listener for auto connection
     */
    BluetoothSPP.AutoConnectionListener mAutoConnectionListener = new BluetoothSPP.AutoConnectionListener() {
        public void onNewConnection(String name, String address) {
            Logger.i("New Connection - " + name + " - " + address);
        }

        public void onAutoConnectionStarted() {
            Logger.i("Auto menu_connection started");
        }
    };



    /**
     * 绑定干式生化仪的Service
     */
    private void bindDryBluetoothService() {
        Intent gattServiceIntent = new Intent(mBaseActivity, BluetoothLeService.class);
        mBaseActivity.bindService(gattServiceIntent, mDryServiceConnection, Context.BIND_AUTO_CREATE);
    }

    // 干式生化仪 manage Service lifecycle.
    private final ServiceConnection mDryServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mDryBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mDryBluetoothLeService.initialize()) {
                Logger.e("Unable to initialize 干式生化仪 Bluetooth");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mDryBluetoothLeService = null;
        }
    };


    private final BroadcastReceiver mDryGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Logger.i("干式生化仪----->" + getString(R.string.title_connected_to, mDryBluetoothDevice.getName()));
                setStatus(getString(R.string.title_connected_to, mDryBluetoothDevice.getName()));
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Logger.i("干式生化仪----->" + getString(R.string.title_not_connected));
                setStatus(getString(R.string.title_not_connected));
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                List<BluetoothGattService> gattServices = mDryBluetoothLeService.getSupportedGattServices();
                for (BluetoothGattService gattService : gattServices) {
                    String serviceUUid = gattService.getUuid().toString();
                    if (BleGattAttributes.USR_SERVICE.equals(serviceUUid)) {
                        List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                        for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                            String characteristicUUid = gattCharacteristic.getUuid().toString();
                            if (BleGattAttributes.USR_CHARACTERISTIC_NOTIFY.equals(characteristicUUid)) {
                                mDryBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                                break;
                            }
                        }
                    }
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                try {
                    String result = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                    if (!TextUtils.isEmpty(result)) {
                        result = result.replaceAll("\\s*", "");//去掉空格
                        Logger.i(result);
                        if (result.contains(com.dashubio.utils.Constants.DRY_BIOCHEMICAL_ANALYZER_PACK_HEAD)) {
                            int frontStart = result.indexOf(com.dashubio.utils.Constants.DRY_BIOCHEMICAL_ANALYZER_PACK_HEAD);
                            mDryReceivedData = result.substring(frontStart, frontStart + 40);


                            Logger.i("mDryReceivedData = " + mDryReceivedData);
                        } else {
                            mDryReceivedData += result.substring(result.length() - 32, result.length());
                            Logger.i("mDryReceivedData = " + mDryReceivedData);
                            DryDetectResult dryDetectResult = DryDetectResult.parseData(mDryReceivedData);
                            hideMeasuredProgressDialog();
                            DryAnalyzerDataDialogFragment.newInstance(dryDetectResult, mUCenterActionsCreator).show(getFragmentManager(), "TAG");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


    public static class BottomDialogFragment extends DialogFragment {

        protected android.app.Dialog dialog;

        private static final String TAG = "BottomDialogFragment";

        public static BottomDialogFragment newInstance() {
            Bundle args = new Bundle();
            BottomDialogFragment fragment = new BottomDialogFragment();
            fragment.setArguments(args);
            return fragment;
        }



        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }


        @NonNull
        @Override
        public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.BottomDialog);

            ViewGroup parent = (ViewGroup) mEcgViewContainer.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            builder.setView(mEcgViewContainer);

            dialog = builder.create();

            dialog.setCanceledOnTouchOutside(false);

            // 设置宽度为屏宽、靠近屏幕底部。
            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            //设置没有效果
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(wlp);
            return dialog;
        }




        //防止重复弹出
        public static BottomDialogFragment showDialog(BaseFragment baseFragment) {
            FragmentManager fragmentManager = baseFragment.getFragmentManager();
            BottomDialogFragment bottomDialogFragment = (BottomDialogFragment) fragmentManager.findFragmentByTag(TAG);
            if (null == bottomDialogFragment) {
                bottomDialogFragment = newInstance();
            }

            if (!baseFragment.getActivity().isFinishing() && null != bottomDialogFragment && !bottomDialogFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .add(bottomDialogFragment, TAG)
                        .commitAllowingStateLoss();
            }

            return bottomDialogFragment;
        }








        public static void removeDialog(BaseFragment baseFragment) {
            try {
                FragmentTransaction ft = baseFragment.getFragmentManager().beginTransaction();
                // 指定一个系统转场动画
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                Fragment prev = baseFragment.getFragmentManager().findFragmentByTag(TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                ft.commit();
            } catch (Exception e) {
                //可能有Activity已经被销毁的异常
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //数据库保存干式生化仪数据
        if(WifiUtils.isNetworkConnected(getActivity())){
            mUCenterActionsCreator.toUploadDryAnalyzerData(mDeviceUserId, mSessionKey, mSessionValue, mSecondUser.getId(), mDryReceivedData);
        }else {
            dbManager.addGanshi(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid,mDryReceivedData);
            Log.d("ZZZ", "干式生化数据: "+mDryReceivedData);
            Toast.makeText(getActivity(), "成功存入本地数据库", Toast.LENGTH_SHORT).show();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UrineMessageEvent event) {

        //数据库保存尿液分析数据
        if (WifiUtils.isNetworkConnected(getActivity())){
            mUCenterActionsCreator.toAddUrineDetectionData(mSecondUser.getId(), mDeviceUserId, mSessionKey, mSessionValue, mUrineData.Structs);
        }else {
            for (int i = 0; i < mUrineData.Structs.size(); i++) {
                BC401_Struct bc = mUrineData.Structs.get(i);
                String URO = bc.URO + "";
                String BLD = bc.BLD + "";
                String BIL = bc.BIL + "";
                String KET = bc.KET + "";
                String GLU = bc.GLU + "";
                String PRO = bc.PRO + "";
                String PH = bc.PH + "";
                String NIT = bc.NIT + "";
                String LEU = bc.LEU + "";
                String SG = bc.SG + "";
                String VC = bc.VC + "";
                dbManager.addNiaoye(SecondUserEntityUtils.secondId,SecondUserEntityUtils.mid,URO,BLD,BIL,KET,GLU,PRO,PH,NIT,LEU,SG,VC);
                Toast.makeText(getActivity(), "成功存入本地数据库", Toast.LENGTH_SHORT).show();

            }
        }

    }

    ;

}
