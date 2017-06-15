package com.dashubio.request;

import com.contec.jar.BC401.BC401_Struct;
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
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Android Studio
 * User: XXX
 * Date: 2016-08-06
 * Time: 09:35
 */
public interface ApiService {

    //登录
    @FormUrlEncoded
    @POST("Login/login")
    Observable<JsonObject> deviceLogin(@Field("phone") String phone, @Field("password") String password);

    //发送短信验证码接口
    @FormUrlEncoded
    @POST("Login/sendCode")
    Observable<HttpResult<String>> getVerificationCode(@Field("phone") String telphone);

    //设备注册
    @FormUrlEncoded
    @POST("Login/register")
    Observable<HttpResult> deviceRegister(@Field("nid") String nid, @Field("phone") String phone,
                                          @Field("password") String password, @Field("msgcode") String msgcode);

    //设备持有者详细信息
    @FormUrlEncoded
    @POST("Login/udetail")
    Observable<HttpResult> deviceAddUserDetail(@FieldMap Map<String, String> options);

    //用户注册
    @Multipart
    @POST("Ulogin/register/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> userRegister(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                        @PartMap Map<String, RequestBody> txtParams, @Part List<MultipartBody.Part> fileParts, @Query("m_id") String userId);

    //用户登录
    @GET("Ulogin/userlogin/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<SecondUser>> userLogin(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                 @Query("card_id") String idCardNumber);

    //用户列表接口
    @GET("Ulogin/userlist/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<SecondUser>>> getUserList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                         @Query("p") int pageIndex, @Query("search") String query);

    //获取个人信息
    @GET("Ulogin/perinfo/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<SecondUser>> getUserInfo(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                   @Query("m_id") String userId);

    //健康预警列表
    @GET("Index/hwarning/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<EarlyWarning>>> getEarlyWarningList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue);

    //健康预警设置
    @FormUrlEncoded
    @POST("Index/hwarningset/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<EarlyWarning>>> setEarlyWarning(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                               @FieldMap Map<String, String> options);

    //帮助列表
    @GET("Index/help/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HelpItem>>> getHelpList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                       @Query("p") int pageIndex);

    //帮助详情
    @GET("Index/helpdetail/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> getHelpDetail(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                         @Query("num") String helpId);

    //留言列表
    @GET("Message/msglist/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<LeavingMessage>>> getMessageList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                @Query("p") int pageIndex);

    //留言列表详情
    @GET("Message/msgdetail/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> getMessageDetail(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                            @Query("id") String msgId);

    //提交留言
    @FormUrlEncoded
    @POST("Message/msgreply/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> submitLeavingMessage(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                @FieldMap Map<String, String> options);

    //健康检测仪上传数据--血压
    //@FormUrlEncoded
    // @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("Testing/check/m_id/{uid}/uid/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> addHealthDetectionData(@Path("uid") String uid, @Path("deviceUserId") String deviceUserId, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                  @Body BloodPressureMeasuredData measuredData);

    //健康检测仪上传数据--血氧
    @POST("Testing/check/m_id/{uid}/uid/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> addHealthDetectionData(@Path("uid") String uid, @Path("deviceUserId") String deviceUserId, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                  @Body OxygenMeasuredData measuredData);

    //健康检测仪上传数据--体温
    @POST("Testing/check/m_id/{uid}/uid/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> addHealthDetectionData(@Path("uid") String uid, @Path("deviceUserId") String deviceUserId, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                  @Body TemperatureMeasuredData measuredData);

    //健康检测仪上传数据--心电
    @POST("Testing/check/m_id/{uid}/uid/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> addHealthDetectionData(@Path("uid") String uid, @Path("deviceUserId") String deviceUserId, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                  @Body EcgMeasuredData measuredData);

    //健康检测仪上传数据
    @FormUrlEncoded
    @POST("Testing/check/m_id/{uid}/uid/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> addHealthDetectionData2(@Path("uid") String uid, @Path("deviceUserId") String deviceUserId, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                   @Field("") String data);

    //尿液分析仪 上传数据
//    @FormUrlEncoded
    @POST("Testing/urine/m_id/{uid}/uid/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult> addUrineDetectionData(@Path("uid") String uid, @Path("deviceUserId") String deviceUserId, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                 @Body ArrayList<BC401_Struct> urineData);

    //前一天检测数据接口
    @GET("Testing/tests/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<GetMeasuredData>>> getYesterdayMeasuredData(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                           @Query("m_id") String userId);

    //干式生化仪上传数据
    @FormUrlEncoded
    @POST("Testing/dry/uid/{uid}/{sessionKey}/{sessionValue}/m_id/{m_id}")
    Observable<HttpResult> uploadDryAnalyzerData(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                 @Path("m_id") String userId, @Field("val") String data);

    //历史数据类别
    @GET("Testing/index/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HistoricalDataCategory>>> getHistoricalDataCategoryList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                                       @Query("m_id") String userId);

    //历史数据
    @FormUrlEncoded
    @POST("Testing/history/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HistoricalData>>> getHistoricalDataList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                       @Query("m_id") String userId, @Field("fa_id") String categoryId, @Field("so_id") String subItemId);

    //预警标题接口
    @GET("Testing/warnitem/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HealthWarningCategory>>> getHealthWarningCategoryList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                                     @Query("m_id") String userId);

    //预警项目列表接口
    @FormUrlEncoded
    @POST("Testing/warninfo/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HistoricalData>>> getHealthWarningDataList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                          @Query("m_id") String userId, @Field("so_id") String categoryId);

    //健康报告列表接口
    @GET("Testing/reportlist/uid/{uid}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HealthReportItem>>> getHealthReportList(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                       @Query("m_id") String userId);
    //健康报告列表接口2
    @GET("Testing/compiled/uid/{uid}/m_id/{deviceUserId}/{sessionKey}/{sessionValue}")
    Observable<HttpResult<List<HealthReportItem>>> getHealthReportList2(@Path("uid") String uid, @Path("sessionKey") String sessionKey, @Path("sessionValue") String sessionValue,
                                                                       @Query("m_id") String userId);

    //健康报告接口
    @FormUrlEncoded
    //@POST("Testing/report/uid/{uid}/{sessionKey}/{sessionValue}")
    @POST
    Observable<HttpResult<String>> getHealthReportData(@Url String url,
                                                       @Field("mid") String userId, @Field("pro") String proId, @Field("year") String year, @Field("month") String month);

}
