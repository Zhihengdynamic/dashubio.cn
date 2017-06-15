package com.dashubio.actions;

/**
 * Created by lgvalle on 02/08/15.
 */
public interface TodoActions {

    //key
    String KEY_DATA = "key-data";
    String KEY_DATA2 = "key-data";
    String KEY_ID = "key-id";

    //type

    //设备
    String DEVICE_LOADING_START = "DeviceLoadingStart";//开始请求
    String DEVICE_LOGIN_FETCH_DATA_COMPLETE = "DeviceLoginFetchDataComplete";//设备登录
    String GET_VERIFICATION_CODE_COMPLETE = "getVerificationCodeComplete";//发送短信验证码接口
    String DEVICE_REGISTER_FETCH_DATA_COMPLETE = "DeviceRegisterFetchDataComplete";//设备注册
    String DEVICE_ADD_USER_DETAIL_COMPLETE = "DeviceAddUserDetailComplete";//设备持有者详细信息
    String DEVICE_SERVER_CONNECTION_ERROR = "DeviceFetchDataError";//服务器连接异常


    //用户
    String UCENTER_LOADING_START = "UCenterLoadingStart";//开始请求
    String UCENTER_REGISTER_COMPLETE = "UCenterRegisterComplete";//用户注册
    String UCENTER_LOGIN_COMPLETE = "UCenterLoginComplete";//用户登录
    String UCENTER_GET_SECOND_USER_LIST_COMPLETE = "UCenterGetSecondUserListComplete";//用户列表
    String UCENTER_GET_USER_INFO_COMPLETE = "UCenterGetUserInfoComplete";//获取个人信息
    String UCENTER_GET_EARLY_WARNING_LIST_COMPLETE = "UCenterGetEarlyWarningListComplete";//获取健康预警列表
    String UCENTER_SET_EARLY_WARNING_COMPLETE = "UCenterSetEarlyWarningComplete";//健康预警设置
    String UCENTER_GET_HELP_LIST_COMPLETE = "UCenterGetHelpListComplete";//帮助列表
    String UCENTER_GET_HELP_DETAIL_COMPLETE = "UCenterGetHelpDetailComplete";//帮助详情
    String UCENTER_GET_MESSAGE_LIST_COMPLETE = "UCenterGetMessageListComplete";//留言列表
    String UCENTER_GET_MESSAGE_DETAIL_COMPLETE = "UCenterGetMessageDetailComplete";//留言列表详情
    String UCENTER_SUBMIT_LEAVING_MESSAGE_COMPLETE = "UCenterSubmitLeavingMessageComplete";//提交留言
    String UCENTER_GET_YESTERDAY_MEASURED_DATA_COMPLETE = "UCenterGetYesterdayMeasuredDataComplete";//前一天检测数据接口
    String UCENTER_ADD_HEALTH_DETECTION_DATA_COMPLETE = "UCenterAddHealthDetectionDataComplete";//健康检测仪上传数据
    String UCENTER_UPLOAD_DRY_ANALYZER_DATA_COMPLETE = "UCenterUploadDryAnalyzerDataComplete";//干式生化仪上传数据
    String UCENTER_ADD_URINE_DETECTION_DATA_COMPLETE = "UCenterAddUrineDetectionDataComplete";//尿液分析仪上传数据
    String UCENTER_GET_HISTORICAL_DATA_CATEGORY_LIST_COMPLETE = "UCenterGetHistoricalDataCategoryListComplete";//获取历史数据类别
    String UCENTER_GET_HISTORICAL_DATA_LIST_COMPLETE = "UCenterGetHistoricalDataListComplete";//获取历史数据
    String UCENTER_GET_HEALTH_WARNING_CATEGORY_LIST_COMPLETE = "UCenterGetHealthWarningCategoryListComplete";//获预警标题接口
    String UCENTER_GET_HEALTH_WARNING_DATA_LIST_COMPLETE = "UCenterGetHealthWarningDataListComplete";//预警项目列表接口
    String UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE = "UCenterGetHealthReportListComplete";//健康报告列表接口
    String UCENTER_GET_HEALTH_REPORT_LIST_COMPLETE2 = "UCenterGetHealthReportListComplete2";//健康报告列表接口2
    String UCENTER_GET_HEALTH_REPORT_DATA_COMPLETE = "UCenterGetHealthReportDataComplete";//健康报告接口
    String UCENTER_SERVER_CONNECTION_ERROR = "UCenterDeviceFetchDataError";//服务器连接异常


}
