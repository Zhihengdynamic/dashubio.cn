package com.dashubio.stores;


import com.dashubio.R;
import com.dashubio.actions.Action;
import com.dashubio.actions.TodoActions;
import com.dashubio.app.AppApplication;
import com.dashubio.model.HttpResult;

import org.greenrobot.eventbus.Subscribe;

/**
 * 设备Store
 */
public class DeviceStore extends Store {

    private StoreChangeEvent changeEvent;

    @Override
    @Subscribe
    @SuppressWarnings("unchecked")
    public void onAction(Action action) {
        HttpResult resp;
        switch (action.getType()) {
            case TodoActions.DEVICE_LOADING_START: //开始请求
                changeEvent = new DeviceStoreChangeEvent(TodoActions.DEVICE_LOADING_START);
                emitStoreChange();
                break;

            //登录
            case TodoActions.DEVICE_LOGIN_FETCH_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new DeviceStoreChangeEvent(TodoActions.DEVICE_LOGIN_FETCH_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            //发送短信验证码接口
            case TodoActions.GET_VERIFICATION_CODE_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new DeviceStoreChangeEvent(TodoActions.GET_VERIFICATION_CODE_COMPLETE, resp);
                emitStoreChange();
                break;

            //设备注册
            case TodoActions.DEVICE_REGISTER_FETCH_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new DeviceStoreChangeEvent(TodoActions.DEVICE_REGISTER_FETCH_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            //设备持有者详细信息
            case TodoActions.DEVICE_ADD_USER_DETAIL_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new DeviceStoreChangeEvent(TodoActions.DEVICE_ADD_USER_DETAIL_COMPLETE, resp);
                emitStoreChange();
                break;

            case TodoActions.DEVICE_SERVER_CONNECTION_ERROR://服务器连接异常
                mThrowable = (Throwable) action.getData().get(TodoActions.KEY_DATA);
                resp = new HttpResult();
                resp.setMsg(AppApplication.getContext().getString(R.string.network_exception_please_try_again_later));
                changeEvent = new DeviceStoreChangeEvent(TodoActions.DEVICE_SERVER_CONNECTION_ERROR, resp);
                emitStoreChange();
                break;
        }
    }


    @Override
    protected StoreChangeEvent changeEvent() {
        return changeEvent;
    }

    public class DeviceStoreChangeEvent extends StoreChangeEvent {
        public DeviceStoreChangeEvent(String status) {
            super(status);
        }

        public DeviceStoreChangeEvent(String status, HttpResult resp) {
            super(status, resp);
        }

        public DeviceStoreChangeEvent(String status, String resp) {
            super(status, null);
        }
    }

}
