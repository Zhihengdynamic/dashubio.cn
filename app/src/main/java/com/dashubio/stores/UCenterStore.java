package com.dashubio.stores;


import com.dashubio.R;
import com.dashubio.actions.Action;
import com.dashubio.actions.TodoActions;
import com.dashubio.app.AppApplication;
import com.dashubio.model.HttpResult;

import org.greenrobot.eventbus.Subscribe;

/**
 * 用户Store
 */
public class UCenterStore extends Store {

    private StoreChangeEvent changeEvent;

    @Override
    @Subscribe
    @SuppressWarnings("unchecked")
    public void onAction(Action action) {
        HttpResult resp;
        switch (action.getType()) {
            case TodoActions.UCENTER_LOADING_START: //开始请求
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_LOADING_START);
                emitStoreChange();
                break;

            //用户注册
            case TodoActions.UCENTER_REGISTER_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_REGISTER_COMPLETE, resp);
                emitStoreChange();
                break;

            //用户登录
            case TodoActions.UCENTER_LOGIN_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_LOGIN_COMPLETE, resp);
                emitStoreChange();
                break;

            //用户列表
            case TodoActions.UCENTER_GET_SECOND_USER_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_SECOND_USER_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //获取个人信息
            case TodoActions.UCENTER_GET_USER_INFO_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_USER_INFO_COMPLETE, resp);
                emitStoreChange();
                break;

            //健康预警列表
            case TodoActions.UCENTER_GET_EARLY_WARNING_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_EARLY_WARNING_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //帮助列表
            case TodoActions.UCENTER_GET_HELP_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HELP_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //帮助详情
            case TodoActions.UCENTER_GET_HELP_DETAIL_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HELP_DETAIL_COMPLETE, resp);
                emitStoreChange();
                break;

            //留言列表
            case TodoActions.UCENTER_GET_MESSAGE_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_MESSAGE_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //留言列表详情
            case TodoActions.UCENTER_GET_MESSAGE_DETAIL_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_MESSAGE_DETAIL_COMPLETE, resp);
                emitStoreChange();
                break;

            //提交留言
            case TodoActions.UCENTER_SUBMIT_LEAVING_MESSAGE_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_SUBMIT_LEAVING_MESSAGE_COMPLETE, resp);
                emitStoreChange();
                break;

            //健康预警设置
            case TodoActions.UCENTER_SET_EARLY_WARNING_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_SET_EARLY_WARNING_COMPLETE, resp);
                emitStoreChange();
                break;

            //健康检测仪上传数据
            case TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            //前一天检测数据接口
            case TodoActions.UCENTER_GET_YESTERDAY_MEASURED_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_YESTERDAY_MEASURED_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            //干式生化仪上传数据
            case TodoActions.UCENTER_UPLOAD_DRY_ANALYZER_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_UPLOAD_DRY_ANALYZER_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            //尿液分析仪上传数据
            case TodoActions.UCENTER_ADD_URINE_DETECTION_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_ADD_URINE_DETECTION_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            //历史数据日期
            case TodoActions.UCENTER_GET_HISTORICAL_DATA_CATEGORY_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HISTORICAL_DATA_CATEGORY_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //历史数据
            case TodoActions.UCENTER_GET_HISTORICAL_DATA_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HISTORICAL_DATA_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //预警标题接口
            case TodoActions.UCENTER_GET_HEALTH_WARNING_CATEGORY_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HEALTH_WARNING_CATEGORY_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //预警项目列表接口
            case TodoActions.UCENTER_GET_HEALTH_WARNING_DATA_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HEALTH_WARNING_DATA_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //健康报告列表接口
            case TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE, resp);
                emitStoreChange();
                break;

            //健康报告列表接口2
            case TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE2://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA2);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE2, resp);
                emitStoreChange();
                break;

            //健康报告接口
            case TodoActions.UCENTER_GET_HEALTH_REPORT_DATA_COMPLETE://请求数据完成（包括成功和失败，但都连接上服务器）
                resp = (HttpResult) action.getData().get(TodoActions.KEY_DATA);
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_GET_HEALTH_REPORT_DATA_COMPLETE, resp);
                emitStoreChange();
                break;

            case TodoActions.UCENTER_SERVER_CONNECTION_ERROR://服务器连接异常
                mThrowable = (Throwable) action.getData().get(TodoActions.KEY_DATA);
                resp = new HttpResult();
                resp.setMsg(AppApplication.getContext().getString(R.string.network_exception_please_try_again_later));
                changeEvent = new UCenterStoreChangeEvent(TodoActions.UCENTER_SERVER_CONNECTION_ERROR, resp);
                emitStoreChange();
                break;
        }
    }


    @Override
    protected StoreChangeEvent changeEvent() {
        return changeEvent;
    }

    public class UCenterStoreChangeEvent extends StoreChangeEvent {
        public UCenterStoreChangeEvent(String status) {
            super(status);
        }

        public UCenterStoreChangeEvent(String status, HttpResult resp) {
            super(status, resp);
        }
    }

}
