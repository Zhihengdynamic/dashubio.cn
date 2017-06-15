package com.dashubio.actions;

import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.HttpResult;
import com.dashubio.model.ucenter.User;
import com.dashubio.request.ApiServiceUtils;
import com.dashubio.utils.Utils;
import com.dashubio.utils.log.Logger;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 该设备的相关操作（登录、注册、设备持有者详细信息等）
 * User: Xinbin Zhang
 * Date: 2016-08-06
 * Time: 12:45
 */
public class DeviceActionsCreator extends ActionsCreator {

    /**
     * 登录
     *
     * @param phone
     * @param password
     */
    public void toDeviceLogin(String phone, String password) {
        ApiServiceUtils.getInstance().getApiService().deviceLogin(phone, password)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonObject>() {
                    @Override
                    public void call(JsonObject respObj) {
                        HttpResult httpResult = new HttpResult();
                        try {
                            JSONObject jsonObject = new JSONObject(respObj.toString());
                            String status = jsonObject.getString("status");
                            httpResult.setStatus(status);
                            if (ErrorCode.SUCCESS.equals(status)) {
                                User user = new User();
                                user.setUserid(jsonObject.getString("code"));
                                user.setT_session(jsonObject.getString(Utils.T_SESSION + user.getUserid()));
                                user.setCompany(jsonObject.getString("company"));
                                httpResult.setData(user);
                            } else {
                                httpResult.setErrorCode(jsonObject.getInt("code"));
                            }
                            Dispatcher.getInstance().dispatch(TodoActions.DEVICE_LOGIN_FETCH_DATA_COMPLETE, TodoActions.KEY_DATA, httpResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Dispatcher.getInstance().dispatch(TodoActions.DEVICE_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, e);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 发送短信验证码接口
     *
     * @param telphone
     */
    public void toGetVerificationCode(String telphone) {
        ApiServiceUtils.getInstance().getApiService().getVerificationCode(telphone)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<String>>() {
                    @Override
                    public void call(HttpResult<String> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.GET_VERIFICATION_CODE_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 设备注册
     *
     * @param nid
     * @param phone
     * @param password
     * @param msgcode
     */
    public void toDeviceRegister(String nid, String phone, String password, String msgcode) {
        Logger.i("toDeviceRegister------>" + " nid = " + nid + " phone = " + phone + " password = " + password + " msgcode = " + msgcode);
        ApiServiceUtils.getInstance().getApiService().deviceRegister(nid, phone, password, msgcode)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_REGISTER_FETCH_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 设备持有者详细信息
     *
     * @param options
     */
    public void toDeviceAddUserDetail(Map<String, String> options) {
        Logger.i("toDeviceAddUserDetail------>" + " options = " + options);
        ApiServiceUtils.getInstance().getApiService().deviceAddUserDetail(options)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_ADD_USER_DETAIL_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.DEVICE_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


}
