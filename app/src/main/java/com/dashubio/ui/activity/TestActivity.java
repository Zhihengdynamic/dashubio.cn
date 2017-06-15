package com.dashubio.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dashubio.R;
import com.dashubio.actions.DeviceActionsCreator;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;
import com.dashubio.stores.DeviceStore;
import com.dashubio.utils.ToastUtils;
import com.dashubio.utils.log.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    public final static String TAG = "TAG";

    private EditText mIDEt;
    private EditText mFirstNameEt;
    private EditText mLastNameEt;
    private Button mSaveBtn;
    private Button mReadBtn;

    private ImageView mImageView;

    private Button mBtn;

    private Dispatcher mDispatcher;
    private DeviceActionsCreator mUCenterActionsCreator;
    private DeviceStore mUCenterStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initDependencies();

        mIDEt = (EditText) findViewById(R.id.id_edittext);
        mIDEt.setText("15850523068");
        mFirstNameEt = (EditText) findViewById(R.id.firstname_edittext);
        mLastNameEt = (EditText) findViewById(R.id.lastname_edittext);
        mSaveBtn = (Button) findViewById(R.id.save_btn);
        mSaveBtn.setOnClickListener(this);
        mReadBtn = (Button) findViewById(R.id.read_btn);
        mReadBtn.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.imageview);

        mBtn = (Button) this.findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephone = mIDEt.getText().toString();
                if (TextUtils.isEmpty(telephone)) {
                    ToastUtils.show(TestActivity.this, R.string.input_reg_telphone);
                    return;
                }
                mUCenterActionsCreator.toGetVerificationCode(telephone);
//                mUCenterActionsCreator.toLogin("crs111", "123123");

              //  threadVerifyCode();

                ResponseBody ResponseBody;
            }
        });
    }

    private void threadVerifyCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uriAPI = "http://dashubio.returnlive.com/Mobile/Login/sendCode";
    /*建立HTTP Post连线*/
                HttpPost httpRequest = new HttpPost(uriAPI);
                //Post运作传送变数必须用NameValuePair[]阵列储存
                //传参数 服务端获取的方法为request.getParameter("name")
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("phone", "15850523068"));
                try {
                    //发出HTTP request
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    //取得HTTP response
                    HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

                    //若状态码为200 ok
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //取出回应字串
                        String strResult = EntityUtils.toString(httpResponse.getEntity());
                        Logger.i("threadVerifyCode()------>" + strResult);
                    } else {
                        Logger.i("Error Response" + httpResponse.getStatusLine().toString());
                        //textView1.setText("Error Response"+httpResponse.getStatusLine().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
        mUCenterActionsCreator = DeviceActionsCreator.getInstance(DeviceActionsCreator.class);
        mUCenterStore = DeviceStore.getInstance(DeviceStore.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                break;

            case R.id.read_btn:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUCenterStoreChange(DeviceStore.DeviceStoreChangeEvent event) {
        String currentStatus = event.getCurrentStatus();//获取当前状态

        if (TextUtils.isEmpty(currentStatus)) {
            return;
        }

        //开始请求
//        if (TodoActions.UCENTER_LOADING_START.equals(currentStatus)) {
//            showLoadingDialog();
//            return;
//        }

        //下面为接收到服务器返回响应

        //服务器连接异常
//        if (TodoActions.UCENTER_SERVER_CONNECTION_ERROR.equals(currentStatus)) {
//            hideLoadingDialog();
//            HttpResult errorResp = event.getHttpResp();
//            ToastUtils.show(this, errorResp.getMsg());
//            return;
//        }

        //处理请求完成
        hideLoadingDialog();
        HttpResult resp = event.getHttpResp();
        if (resp == null) {
            return;
        }
        if (ErrorCode.SUCCESS == resp.getStatus()) {
            Logger.i(resp.getStatus() + resp.getErrorCode());
        } else {
            //ToastUtils.show(this, resp.getMsg());
        }
    }


}
