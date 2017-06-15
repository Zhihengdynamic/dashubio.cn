package com.dashubio.ui.fragment;

import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dashubio.CameraManager;
import com.dashubio.R;
import com.dashubio.actions.TodoActions;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.card.utils.FileUtil;
import com.dashubio.card.utils.HttpUtil;
import com.dashubio.card.utils.NetUtil;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;
import com.dashubio.model.IDCard;
import com.dashubio.model.IDCardResult;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.stores.UCenterStore;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.fragment.base.BaseFragment;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.log.Logger;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

// 首页--登录界面
public class LoginFragment extends BaseFragment implements Callback {

    View view = null;
    Button btn_card, btn_search, btn_self;
    LinearLayout ll_btn, ll_login;
    private final String ACTION_NAME = "com.action.change";  // 通知activity切换fragment
    private CameraManager mCameraManager;
    private SurfaceView mSurfaceView;
    private ProgressBar pb;
    private ImageButton mShutter;
    private SurfaceHolder mSurfaceHolder;
    private String flashModel = Parameters.FLASH_MODE_OFF;
    private byte[] jpegData = null;
    public static final String action = "idcard.scan";

    //private XStream mXstream;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "拍照失败", Toast.LENGTH_SHORT).show();
                    mCameraManager.initPreView();
                    break;
                case 1:
                    jpegData = (byte[]) msg.obj;
                    if (jpegData != null && jpegData.length > 0) {
                        pb.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if ((jpegData.length > (1000 * 1024 * 5))) {
                                        mHandler.sendMessage(mHandler.obtainMessage(3, getResources().getString(R.string.photo_too_lage)));
                                        return;
                                    }
                                    String result = null;
                                    boolean isavilable = NetUtil.isNetworkConnectionActive(getActivity());
                                    if (isavilable) {
                                        result = Scan(action, jpegData, "jpg");
                                        Gson gson = new Gson();
                                        IDCardResult idCardResult = gson.fromJson(result, IDCardResult.class);
                                        if ("OK".equals(idCardResult.getStatus())) {
                                            IDCard idCard = idCardResult.getData().getItem();
                                            String cardno = idCard.getCardno();
                                            mUCenterActionsCreator.toUserLogin(mDeviceUserId, mSessionKey, mSessionValue, cardno);
                                        }

                                        if (result.equals("-2")) {
                                            result = "连接超时！";
                                            mHandler.sendMessage(mHandler.obtainMessage(3, result));
                                        } else if (HttpUtil.connFail.equals(result)) {
                                            mHandler.sendMessage(mHandler.obtainMessage(3, result));
                                        } else {
                                            mHandler.sendMessage(mHandler.obtainMessage(4, result));
                                        }
                                    } else {
                                        mHandler.sendMessage(mHandler.obtainMessage(3, "无网络，请确定网络是否连接!"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                    break;
                case 3:
                    pb.setVisibility(View.GONE);
                    String str = msg.obj + "";
                    Logger.i("3------>" + str);
                    Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                    mCameraManager.initPreView();
                    mShutter.setEnabled(true);
                    break;
                case 4:
                    mShutter.setEnabled(true);
                    pb.setVisibility(View.GONE);
                    String result = msg.obj + "";
                    Logger.i("4------>" + result);
                    // Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    String filePath = msg.obj + "";
                    byte[] data = FileUtil.getByteFromPath(filePath);
                    if (data != null && data.length > 0) {
                        mHandler.sendMessage(mHandler.obtainMessage(1, data));
                    } else {
                        mHandler.sendMessage(mHandler.obtainMessage(0));
                    }
                    break;
                case 6:
                    Toast.makeText(getActivity(), "请插入存储卡！", Toast.LENGTH_SHORT).show();
                    mCameraManager.initPreView();
                    break;
            }
        }

        ;
    };

    private Dispatcher mDispatcher;
    private UCenterActionsCreator mUCenterActionsCreator;
    private UCenterStore mUCenterStore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
        // mXstream = new XStream();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        mCameraManager = new CameraManager(getActivity(), mHandler);
        initView();
        return view;
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

    private void initView() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getActivity().getApplicationContext(), "请插入存储卡", Toast.LENGTH_LONG).show();
        }

        File dir = new File(CameraManager.strDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        ll_btn = (LinearLayout) view.findViewById(R.id.ll_btn);
        ll_login = (LinearLayout) view.findViewById(R.id.ll_login);
        btn_card = (Button) view.findViewById(R.id.btn_card);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_self = (Button) view.findViewById(R.id.btn_self);
        btn_card.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ll_btn.setVisibility(View.GONE);
                ll_login.setVisibility(View.VISIBLE);
            }
        });
        btn_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                BaseFragment fragment = new ManagerFragment();
                getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
            }
        });
        btn_self.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                BaseFragment fragment = new ManagerFragment();
                getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
            }
        });

        pb = (ProgressBar) view.findViewById(R.id.reco_recognize_bar);
        mSurfaceView = (SurfaceView) view.findViewById(R.id.camera_preview);
        mShutter = (ImageButton) view.findViewById(R.id.camera_shutter);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mShutter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCameraManager.requestFocuse();
                mShutter.setEnabled(false);
            }
        });

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCameraManager.openCamera(mSurfaceHolder);
            if (flashModel == null || !mCameraManager.isSupportFlash(flashModel)) {
                flashModel = mCameraManager.getDefaultFlashMode();
            }
            mCameraManager.setCameraFlashMode(flashModel);
        } catch (RuntimeException e) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.camera_open_error, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.camera_open_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (width > height) {
            mCameraManager.setPreviewSize(width, height);
        } else {
            mCameraManager.setPreviewSize(height, width);
        }
        mCameraManager.initPreView();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCameraManager.closeCamera();
    }

    public static String Scan(String type, byte[] file, String ext) {
        String xml = HttpUtil.getSendXML(type, ext);
        return HttpUtil.send(xml, file);
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
                case TodoActions.UCENTER_LOGIN_COMPLETE: //用户登录
                    ToastUtils.show(mBaseActivity, R.string.successful_operation);
                    SecondUser secondUser = (SecondUser) resp.getData();
                    if (secondUser == null || TextUtils.isEmpty(secondUser.getId())) {
                        ToastUtils.show(mBaseActivity, R.string.user_not_exist_please_register_first);
                        return;
                    }

                    HomeActivity.startWithTypeAndSecondUser(false, secondUser, mBaseActivity);
                    break;
            }
        } else {
            //ToastUtils.show(this, resp.getMsg());
            boolean resutlt = mBaseActivity.handleErrorResp(resp);
        }
    }

}
