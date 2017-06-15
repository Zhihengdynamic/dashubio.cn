package com.dashubio.actions;

import android.util.Log;

import com.contec.jar.BC401.BC401_Struct;
import com.dashubio.app.ErrorCode;
import com.dashubio.dispatcher.Dispatcher;
import com.dashubio.model.EarlyWarning;
import com.dashubio.model.GetMeasuredData;
import com.dashubio.model.HealthReportItem;
import com.dashubio.model.HealthWarningCategory;
import com.dashubio.model.HelpItem;
import com.dashubio.model.HistoricalData;
import com.dashubio.model.HistoricalDataCategory;
import com.dashubio.model.HttpResult;
import com.dashubio.model.LeavingMessage;
import com.dashubio.model.health.BloodPressureMeasuredData;
import com.dashubio.model.health.EcgMeasuredData;
import com.dashubio.model.health.OxygenMeasuredData;
import com.dashubio.model.health.TemperatureMeasuredData;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.request.ApiServiceUtils;
import com.dashubio.utils.log.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Query;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 用户管理
 * User: Xinbin Zhang
 * Date: 2016-08-06
 * Time: 12:45
 */
public class UCenterActionsCreator extends ActionsCreator {

    /**
     * 用户注册
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param txtParams
     * @param fileParts
     */
    public void toUserRegister(String uid, String sessionKey, String sessionValue, Map<String, RequestBody> txtParams, List<MultipartBody.Part> fileParts, String userId) {
        Logger.i("toUserRegister------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " txtParams = " + txtParams + " fileParts = " + fileParts + " userId = " + userId);
        ApiServiceUtils.getInstance().getApiService().userRegister(uid, sessionKey, sessionValue, txtParams, fileParts, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_REGISTER_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 用户登录
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param idCardNumber
     */
    public void toUserLogin(String uid, String sessionKey, String sessionValue, String idCardNumber) {
        Logger.i("toUserLogin------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " idCardNumber = " + idCardNumber);
        ApiServiceUtils.getInstance().getApiService().userLogin(uid, sessionKey, sessionValue, idCardNumber)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<SecondUser>>() {
                    @Override
                    public void call(HttpResult<SecondUser> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOGIN_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 用户列表接口
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param pageIndex
     * @param query
     */
    public void toGetUserList(String uid, String sessionKey, String sessionValue,
                              @Query("p") int pageIndex, @Query("search") String query) {
        Logger.i("toGetUserList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " pageIndex = " + pageIndex + " query = " + query);
        Log.d("ZZZ", "用户请求的URL=="+" uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " pageIndex = " + pageIndex + " query = " + query);
        ApiServiceUtils.getInstance().getApiService().getUserList(uid, sessionKey, sessionValue, pageIndex, query)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<SecondUser>>>() {
                    @Override
                    public void call(HttpResult<List<SecondUser>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_SECOND_USER_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 获取个人信息
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param userId
     */
    public void toGetUserInfo(String uid, String sessionKey, String sessionValue, String userId) {
        Logger.i("toGetUserInfo------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " userId = " + userId);
        ApiServiceUtils.getInstance().getApiService().getUserInfo(uid, sessionKey, sessionValue, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<SecondUser>>() {
                    @Override
                    public void call(HttpResult<SecondUser> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_USER_INFO_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 健康预警列表
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     */
    public void toGetEarlyWarningList(String uid, String sessionKey, String sessionValue) {
        Logger.i("toGetEarlyWarningList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue);
        ApiServiceUtils.getInstance().getApiService().getEarlyWarningList(uid, sessionKey, sessionValue)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<EarlyWarning>>>() {
                    @Override
                    public void call(HttpResult<List<EarlyWarning>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_EARLY_WARNING_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 健康预警设置
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param options
     */
    public void toSetEarlyWarning(String uid, String sessionKey, String sessionValue, Map<String, String> options) {
        Logger.i("toSetEarlyWarning------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " options = " + options);
        ApiServiceUtils.getInstance().getApiService().setEarlyWarning(uid, sessionKey, sessionValue, options)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SET_EARLY_WARNING_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 帮助列表
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param pageIndex
     */
    public void toGetHelpList(String uid, String sessionKey, String sessionValue, int pageIndex) {
        Logger.i("toGetHelpList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " pageIndex = " + pageIndex);
        ApiServiceUtils.getInstance().getApiService().getHelpList(uid, sessionKey, sessionValue, pageIndex)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HelpItem>>>() {
                    @Override
                    public void call(HttpResult<List<HelpItem>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HELP_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 帮助详情
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param helpId
     */
    public void toGetHelpDetail(String uid, String sessionKey, String sessionValue, String helpId) {
        Logger.i("toGetHelpDetail------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " helpId = " + helpId);
        ApiServiceUtils.getInstance().getApiService().getHelpDetail(uid, sessionKey, sessionValue, helpId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HELP_DETAIL_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 留言列表
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param pageIndex
     */
    public void toGetMessageList(String uid, String sessionKey, String sessionValue, int pageIndex) {
        Logger.i("toGetMessageList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " pageIndex = " + pageIndex);
        ApiServiceUtils.getInstance().getApiService().getMessageList(uid, sessionKey, sessionValue, pageIndex)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<LeavingMessage>>>() {
                    @Override
                    public void call(HttpResult<List<LeavingMessage>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_MESSAGE_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 留言列表详情
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param msgId
     */
    public void toGetMessageDetail(String uid, String sessionKey, String sessionValue, String msgId) {
        Logger.i("toGetMessageDetail------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " msgId = " + msgId);
        ApiServiceUtils.getInstance().getApiService().getMessageDetail(uid, sessionKey, sessionValue, msgId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_MESSAGE_DETAIL_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 提交留言
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param options
     */
    public void toSubmitLeavingMessage(String uid, String sessionKey, String sessionValue, Map<String, String> options) {
        Logger.i("toSubmitLeavingMessage------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " options = " + options);
        ApiServiceUtils.getInstance().getApiService().submitLeavingMessage(uid, sessionKey, sessionValue, options)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SUBMIT_LEAVING_MESSAGE_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 前一天检测数据接口
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param userId
     */
    public void toGetYesterdayMeasuredData(String uid, String sessionKey, String sessionValue, String userId) {
        Logger.i("toGetYesterdayMeasuredData------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " userId = " + userId);
        ApiServiceUtils.getInstance().getApiService().getYesterdayMeasuredData(uid, sessionKey, sessionValue, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<GetMeasuredData>>>() {
                    @Override
                    public void call(HttpResult<List<GetMeasuredData>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_YESTERDAY_MEASURED_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 干式生化仪上传数据
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     * @param data
     */
    public void toUploadDryAnalyzerData(String uid, String sessionKey, String sessionValue, String userId, String data) {
        Logger.i("toUploadDryAnalyzerData------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " userId = " + userId + " data = " + data);
        ApiServiceUtils.getInstance().getApiService().uploadDryAnalyzerData(uid, sessionKey, sessionValue, userId, data)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_UPLOAD_DRY_ANALYZER_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 健康检测仪------血压
     *
     * @param uid
     * @param deviceUserId
     * @param sessionKey
     * @param sessionValue
     * @param measuredData
     */
    public void toAddHealthDetectionData(String uid, String deviceUserId, String sessionKey, String sessionValue, BloodPressureMeasuredData measuredData) {
        Logger.i("toAddHealthDetectionData------>" + " uid = " + uid + " deviceUserId = " + deviceUserId + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " measuredData = " + measuredData);
        ApiServiceUtils.getInstance().getApiService().addHealthDetectionData(uid, deviceUserId, sessionKey, sessionValue, measuredData)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 健康检测仪------血氧
     *
     * @param uid
     * @param deviceUserId
     * @param sessionKey
     * @param sessionValue
     * @param measuredData
     */
    public void toAddHealthDetectionData(String uid, String deviceUserId, String sessionKey, String sessionValue, OxygenMeasuredData measuredData) {
        Logger.i("toAddHealthDetectionData------>" + " uid = " + uid + " deviceUserId = " + deviceUserId + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " measuredData = " + measuredData);
        ApiServiceUtils.getInstance().getApiService().addHealthDetectionData(uid, deviceUserId, sessionKey, sessionValue, measuredData)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 健康检测仪------体温
     *
     * @param uid
     * @param deviceUserId
     * @param sessionKey
     * @param sessionValue
     * @param measuredData
     */
    public void toAddHealthDetectionData(String uid, String deviceUserId, String sessionKey, String sessionValue, TemperatureMeasuredData measuredData) {
        Logger.i("toAddHealthDetectionData------>" + " uid = " + uid + " deviceUserId = " + deviceUserId + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " measuredData = " + measuredData);
        ApiServiceUtils.getInstance().getApiService().addHealthDetectionData(uid, deviceUserId, sessionKey, sessionValue, measuredData)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 健康检测仪------心电
     *
     * @param uid
     * @param deviceUserId
     * @param sessionKey
     * @param sessionValue
     * @param measuredData
     */
    public void toAddHealthDetectionData(String uid, String deviceUserId, String sessionKey, String sessionValue, EcgMeasuredData measuredData) {
        Logger.i("toAddHealthDetectionData------>" + " uid = " + uid + " deviceUserId = " + deviceUserId + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " measuredData = " + measuredData);
        ApiServiceUtils.getInstance().getApiService().addHealthDetectionData(uid, deviceUserId, sessionKey, sessionValue, measuredData)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }



    /**
     * 尿液分析仪
     *
     * @param uid
     * @param deviceUserId
     * @param sessionKey
     * @param sessionValue
     * @param urineData
     */
    public void toAddUrineDetectionData(String uid, String deviceUserId, String sessionKey, String sessionValue, ArrayList<BC401_Struct> urineData) {
        Logger.i("toAddUrineDetectionData------>" + " uid = " + uid + " deviceUserId = " + deviceUserId + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " urineData = " + urineData);
        ApiServiceUtils.getInstance().getApiService().addUrineDetectionData(uid, deviceUserId, sessionKey, sessionValue, urineData)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_ADD_URINE_DETECTION_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    public void toAddHealthDetectionData2(String uid, String deviceUserId, String sessionKey, String sessionValue, String data) {
        Logger.i("toAddHealthDetectionData------>" + " uid = " + uid + " deviceUserId = " + deviceUserId + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " data = " + data);
        ApiServiceUtils.getInstance().getApiService().addHealthDetectionData2(uid, deviceUserId, sessionKey, sessionValue, data)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult>() {
                    @Override
                    public void call(HttpResult resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 历史数据目录
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     */
    public void toGetHistoricalDataCategoryList(String uid, String sessionKey, String sessionValue, String userId) {
        Logger.i("toGetHistoricalDataCategoryList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue + " userId = " + userId);
        ApiServiceUtils.getInstance().getApiService().getHistoricalDataCategoryList(uid, sessionKey, sessionValue, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HistoricalDataCategory>>>() {
                    @Override
                    public void call(HttpResult<List<HistoricalDataCategory>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HISTORICAL_DATA_CATEGORY_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 历史数据
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     */
    public void toGetHistoricalDataList(String uid, String sessionKey, String sessionValue, String userId, String categoryId, String subItemId) {
        Logger.i("toGetHistoricalDataList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " userId = " + userId + " categoryId = " + categoryId + " subItemId = " + subItemId);
        ApiServiceUtils.getInstance().getApiService().getHistoricalDataList(uid, sessionKey, sessionValue, userId, categoryId, subItemId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HistoricalData>>>() {
                    @Override
                    public void call(HttpResult<List<HistoricalData>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HISTORICAL_DATA_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 预警标题接口
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     */
    public void toGetHealthWarningCategoryList(String uid, String sessionKey, String sessionValue, String userId) {
        Logger.i("toGetHealthWarningCategoryList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " userId = " + userId);
        ApiServiceUtils.getInstance().getApiService().getHealthWarningCategoryList(uid, sessionKey, sessionValue, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HealthWarningCategory>>>() {
                    @Override
                    public void call(HttpResult<List<HealthWarningCategory>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HEALTH_WARNING_CATEGORY_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }


    /**
     * 预警项目列表接口
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     */
    public void toGetHealthWarningDataList(String uid, String sessionKey, String sessionValue, String userId, String categoryId) {
        Logger.i("toGetHealthWarningDataList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " userId = " + userId + " categoryId = " + categoryId);
        ApiServiceUtils.getInstance().getApiService().getHealthWarningDataList(uid, sessionKey, sessionValue, userId, categoryId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HistoricalData>>>() {
                    @Override
                    public void call(HttpResult<List<HistoricalData>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HEALTH_WARNING_DATA_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    /**
     * 健康报告列表接口
     *
     * @param uid
     * @param sessionKey
     * @param sessionValue
     */
    public void toGetHealthReportList(String uid, String sessionKey, String sessionValue, String userId) {
        Logger.i("toGetHealthReportList------>" + " uid = " + uid + " sessionKey = " + sessionKey + " sessionValue = " + sessionValue
                + " userId = " + userId);
        ApiServiceUtils.getInstance().getApiService().getHealthReportList(uid, sessionKey, sessionValue, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HealthReportItem>>>() {
                    @Override
                    public void call(HttpResult<List<HealthReportItem>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE, TodoActions.KEY_DATA, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

    public void toGetHealthReportList2(String uid, String sessionKey, String sessionValue, String userId) {

        ApiServiceUtils.getInstance().getApiService().getHealthReportList2(uid, sessionKey, sessionValue, userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<List<HealthReportItem>>>() {
                    @Override
                    public void call(HttpResult<List<HealthReportItem>> resp) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE2, TodoActions.KEY_DATA2, resp);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA2, throwable);
                    }
                });
    }


    /**
     * 健康报告接口
     *
     * @param url
     * @param userId
     * @param proId
     * @param year
     * @param month
     */
    public void toGetHealthReportData(String url, String userId, String proId, String year, String month) {
        Logger.i("toGetHealthReportData------>" + " url = " + url
                + " userId = " + userId + " proId = " + proId + " year = " + year + " month = " + month);
        ApiServiceUtils.getInstance().getApiService().getHealthReportData(url, userId, proId, year, month)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_LOADING_START);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpResult<String>>() {
                    @Override
                    public void call(HttpResult<String> respObj) {
                        HttpResult<String> httpResult = new HttpResult();
                        httpResult.setStatus(ErrorCode.SUCCESS);
                        httpResult.setData(respObj.toString());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_GET_HEALTH_REPORT_DATA_COMPLETE, TodoActions.KEY_DATA, httpResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("an error occured when request server------>" + throwable.getMessage());
                        Dispatcher.getInstance().dispatch(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, TodoActions.KEY_DATA, throwable);
                    }
                });
    }

}
