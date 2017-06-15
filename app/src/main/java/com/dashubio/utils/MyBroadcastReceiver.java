package com.dashubio.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.contec.jar.BC401.BC401_Data;
import com.contec.jar.BC401.BC401_Struct;
import com.dashubio.R;
import com.dashubio.actions.DeviceActionsCreator;
import com.dashubio.actions.UCenterActionsCreator;
import com.dashubio.app.UserManager;
import com.dashubio.app.event.AutoRegisterMessageEvent;
import com.dashubio.gson.GsonJiexi;
import com.dashubio.gson.UserGet;
import com.dashubio.model.DetectItem;
import com.dashubio.model.IDCard;
import com.dashubio.model.health.BloodPressureMeasuredData;
import com.dashubio.model.health.EcgMeasuredData;
import com.dashubio.model.health.OxygenMeasuredData;
import com.dashubio.model.health.TemperatureMeasuredData;
import com.dashubio.model.ucenter.SecondUser;
import com.dashubio.model.ucenter.User;
import com.dashubio.request.ApiServiceUtils;
import com.dashubio.ui.activity.BaseActivity;
import com.dashubio.ui.activity.HomeActivity;
import com.dashubio.ui.activity.OneUser.SecondMeasureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * Created by zzb on 2017/4/25.
 * 静态注册广播监听网络状态
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private DBManager dbManager;
    private static final String TAG = "MyBroadcastReceiver";
    private UCenterActionsCreator mUCenterActionsCreator;
    public static String mDeviceUserId = "";
    public static String mSessionKey = "";
    public static String mSessionValue = "";
    public static String userUrl = "";
    private String loginUrl = ApiServiceUtils.BASE_URL+"Login/login";
    private EcgMeasuredData mEcgMeasuredData = new EcgMeasuredData();//心电数据
    private DeviceActionsCreator mDeviceActionsCreator;
    private static String uid = "";
    private static String sessionKey = "t_session_";
    private static String sessionValue = "";


    @Override
    public void onReceive(final Context context, Intent intent) {
        dbManager = new DBManager(context);

        if (WifiUtils.isNetworkConnected(context)){
            mDeviceActionsCreator = DeviceActionsCreator.getInstance(DeviceActionsCreator.class);

            //重新调用登录接口

            ArrayList<MemberInfo> infoList = new ArrayList<>();
            infoList = dbManager.searchLoginData();

            String name = "";
            String pwds = "";
            for (MemberInfo info : infoList) {
                name =  String.valueOf(info.name);
                pwds =  String.valueOf(info.pwds);
            }
            Log.e("DBM", "从数据库找出的账户密码:name="+name+"pwds="+pwds);

//            mDeviceActionsCreator.toDeviceLogin(name, pwds);


            OkHttpUtils.post().url(loginUrl)
                    .addParams("phone",name)
                    .addParams("password",pwds)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("DBM", "登录接口调用失败"+e );
                        }

                        @Override
                        public void onResponse(String response, int id) {

                            Log.e("DBM", "登录接口调用成功"+response );
                            LoginEntity loginEntity =  GsonJiexi.parserStateCode(response);
                            uid = loginEntity.getCode();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                loginEntity.setT_session_id(jsonObject.getString(sessionKey+uid));
                                sessionValue = loginEntity.getT_session_id();
                            } catch (JSONException e) {
                                Log.e("DBM", "失败"+e );

                            }

                            mDeviceUserId = uid;
                            mSessionKey = sessionKey+uid;
                            mSessionValue = sessionValue;
                            mUCenterActionsCreator = UCenterActionsCreator.getInstance(UCenterActionsCreator.class);
//                            userUrl = "http://dashubio.returnlive.com/Mobile/Ulogin/userlist/uid/"+mDeviceUserId+ "/" +mSessionKey+ "/" +mSessionValue+"/p/-1";
                            userUrl = "http://dashubio.cn/Mobile/Ulogin/userlist/uid/"+mDeviceUserId+ "/" +mSessionKey+ "/" +mSessionValue+"/p/-1";

                            addData(context);



                        }
                    });

    }else {
            Log.d(TAG, "无网络连接");

        }

    }

    private void addData(final Context context) {
        //再次加载一边

        new Thread(new Runnable() {
            @Override
            public void run() {
                zhuCe();

                dbManager.clearUser();
                Log.d(TAG, "清空数据库");

                ArrayList<SecondUser> listu = new ArrayList();
                listu = dbManager.searchUserData();
                String uu = "";
                for (int i = 0; i < listu.size(); i++) {
                    SecondUser secnd = listu.get(i);
                    uu = uu+String.valueOf(secnd.fullname);
                    uu = uu + "\n" + "------------------------------------------" + "\n";

                }

                Log.d(TAG, "清空后的数据库: =="+uu);

                OkHttpUtils.get().url(userUrl).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("ZZZ", "失败=="+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("MyBroadcastReceiver", "response=="+response);
                        List<UserGet.UserBean> userList = GsonJiexi.gsonUserData(response);
                        DBManager dbManager = new DBManager(context);
                        ArrayList<UserGet.UserBean> userList2 = new ArrayList<>();
                        userList2 = dbManager.searchUserData2();
                        String result = "";
                        for (UserGet.UserBean info : userList2) {
                            result = result + String.valueOf(info.card_id);
                            result = result + "\n" + "------------------------------------------" + "\n";
                        }

//						Log.d("ZZZ", "result: =="+result);



                        for (int i = userList.size()-1; i >-1; i--) {
                            UserGet.UserBean users = userList.get(i);
                            String userName = String.valueOf(users.name);
                            String userid = String.valueOf(users.id);
                            String card_id = String.valueOf(users.card_id);
                            String phone = "";
                            if (result.indexOf(card_id)!=-1){
//										Log.d("ZZZ", "数据库有数据");
                                //数据库有数据
                            }else {
                                dbManager.addUserName(userName,userid,card_id,phone);
                            }

                        }


                        pushData();
                        Toast.makeText(context, "离线数据上传中...", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }).start();
    }




    private void zhuCe(){
        ArrayList<IDCard> registered = new ArrayList<>();
        registered = dbManager.searchUserIDCard();
        String user="";
        for (int i = 0; i < registered.size(); i++) {
            IDCard idCard = registered.get(i);
            user = user+idCard.getName()+idCard.getPhone()+idCard.getCardno();
            Map<String, RequestBody> txtParams = new HashMap<>();
            txtParams.put("card_id", RequestBody.create(null, idCard.getCardno()));
            txtParams.put("name", RequestBody.create(null, idCard.getName()));
            int sex = User.NO_SEX;
            txtParams.put("sex", RequestBody.create(null, String.valueOf(sex)));
            String birthdayStr = idCard.getBirthday();
            if (!TextUtils.isEmpty(birthdayStr)) {
                birthdayStr = birthdayStr.replace("年", "-");
                birthdayStr = birthdayStr.replace("月", "-");
                birthdayStr = birthdayStr.replace("日", "");
            }

            txtParams.put("birth", RequestBody.create(null, birthdayStr));
            txtParams.put("nation", RequestBody.create(null, idCard.getFolk()));
            txtParams.put("phone", RequestBody.create(null, idCard.getPhone()));
            txtParams.put("province", RequestBody.create(null, idCard.getProvince()));
            txtParams.put("city", RequestBody.create(null, idCard.getCity()));
            txtParams.put("district", RequestBody.create(null, idCard.getDistrict()));
            txtParams.put("phone_contacts", RequestBody.create(null, idCard.getMphone()));
            txtParams.put("address", RequestBody.create(null, idCard.getAddress()));
            txtParams.put("province", RequestBody.create(null, idCard.getProvince()));
            txtParams.put("city", RequestBody.create(null, idCard.getCity()));
            txtParams.put("district", RequestBody.create(null, idCard.getDistrict()));
            mUCenterActionsCreator.toUserRegister(mDeviceUserId, mSessionKey, mSessionValue, txtParams, null, "");
        }

        Log.d(TAG, "离线注册的用户=="+user);
    }

    private void pushData(){

            //重新查询数据库
            ArrayList<SecondUser2> listu2 = new ArrayList();
            listu2 = dbManager.searchUserAllId();
            String uu2 = "";
            for (int i = 0; i < listu2.size(); i++) {
                SecondUser2 secnd = listu2.get(i);
                uu2 = uu2 + String.valueOf(secnd.Fullname)+"user_id=="+secnd.user_id;
                uu2 = uu2 + "\n" + "------------------------------------------" + "\n";
            }

            Log.d(TAG, "重新查询的数据库: ==" + uu2);


            //血压本地数据库上传数据
            ArrayList<XurYaEntity> xueya = new ArrayList<>();
            xueya = dbManager.searchXueYa();
            String mXueYa = "";

            for (int i = xueya.size() - 1; i > -1; i--) {
                XurYaEntity zXueYa = xueya.get(i);
                mXueYa = mXueYa+"_id"+zXueYa._id + "user_id" + String.valueOf(zXueYa.user_id) + "高压" + String.valueOf(zXueYa.gaoya) + "低压" + String.valueOf(zXueYa.diya);
                mXueYa = mXueYa + "\n" + "------------------------------------------" + "\n";

                BloodPressureMeasuredData measuredData = new BloodPressureMeasuredData();
                //高压
                DetectItem highPressureItem = new DetectItem();
                highPressureItem.setValue(Float.valueOf(zXueYa.gaoya));
                highPressureItem.setUnit("mmHg");
                measuredData.setHighPressure(highPressureItem);
                //低压
                DetectItem lowPressureItem = new DetectItem();
                lowPressureItem.setValue(Float.valueOf(zXueYa.diya));
                lowPressureItem.setUnit("mmHg");
                measuredData.setLowPressure(lowPressureItem);

                ArrayList<SecondUser2> sedUserXueYa = new ArrayList<>();
                sedUserXueYa = dbManager.searchUserMid(String.valueOf(zXueYa._id));
                String midXueYa = "";
                for (int j = 0; j < sedUserXueYa.size(); j++) {
                    SecondUser2 secondUserXueYa = sedUserXueYa.get(j);
                    midXueYa = secondUserXueYa.user_id;
                }
                Log.d(TAG, "midXueYa:====== "+midXueYa);

                mUCenterActionsCreator.toAddHealthDetectionData(midXueYa, mDeviceUserId, mSessionKey, mSessionValue, measuredData);
            }

            Log.d(TAG, "搜索到的血压数据：===" + mXueYa);
            Log.d(TAG, "String.valueOf(zXueYa.user_id)：===" + mDeviceUserId);
            Log.d(TAG, "mSessionKey：===" + mSessionKey);
            Log.d(TAG, "mSessionValue：===" + mSessionValue);
            dbManager.clearDataXueYa();


            //血氧本地数据库上传数据
            ArrayList<XueYangEntity> xueyang = new ArrayList<>();
            xueyang = dbManager.searchXueYang();
            String xueyang2 = "";
            for (int i = xueyang.size() - 1; i > -1; i--) {
                XueYangEntity mXueYang = xueyang.get(i);
                xueyang2 = xueyang2+"血氧_id"+String.valueOf(mXueYang._id) + "血氧user_id==" + String.valueOf(mXueYang.user_id) + "血氧==" + String.valueOf(mXueYang.xueyang) + "心率==" + String.valueOf(mXueYang.xinlv);
                xueyang2 = xueyang2 + "\n" + "------------------------------------------" + "\n";

                OxygenMeasuredData measuredData = new OxygenMeasuredData();
                //血氧
                DetectItem oxygenItem = new DetectItem();
                oxygenItem.setValue(Float.valueOf(mXueYang.xueyang));
                oxygenItem.setUnit("");
                measuredData.setOxygen(oxygenItem);

                //心率
                DetectItem heartRateItem = new DetectItem();
                heartRateItem.setValue(Float.valueOf(mXueYang.xinlv));
                heartRateItem.setUnit("");
                measuredData.setHeartRate(heartRateItem);

                ArrayList<SecondUser2> sedUserXueYang = new ArrayList<>();
                sedUserXueYang = dbManager.searchUserMid(String.valueOf(mXueYang._id));
                String midXueYang = "";
                for (int j = 0; j < sedUserXueYang.size(); j++) {
                    SecondUser2 secondUserXueYang = sedUserXueYang.get(j);
                    midXueYang = secondUserXueYang.user_id;
                }
                Log.d(TAG, "midXueYang:====== "+midXueYang);

                mUCenterActionsCreator.toAddHealthDetectionData(midXueYang, mDeviceUserId, mSessionKey, mSessionValue, measuredData);
            }
            Log.d(TAG, "搜索到的血氧数据：===" + xueyang2);
            dbManager.clearDataXueYang();


            //体温数据库上传数据
            ArrayList<Tiwen> tiwens = new ArrayList<>();
            tiwens = dbManager.searchTiwenData();
            String mTiWen = "";

            for (int i = tiwens.size() - 1; i > -1; i--) {
                Tiwen zTiWen = tiwens.get(i);
                mTiWen = mTiWen+"_id"+String.valueOf(zTiWen._id) + "user_id" + String.valueOf(zTiWen.user_id) + "体温" + String.valueOf(zTiWen.tiwen);
                mTiWen = mTiWen + "\n" + "------------------------------------------" + "\n";

                TemperatureMeasuredData measuredData = new TemperatureMeasuredData();
                //体温
                DetectItem temperatureItem = new DetectItem();
                temperatureItem.setValue(Float.valueOf(zTiWen.tiwen));
                temperatureItem.setUnit("℃");
                measuredData.setTemperature(temperatureItem);
                Log.d(TAG, "(zTiWen.user_id)==="+(zTiWen._id));
                    //根据_id查询user表中的mid
                    ArrayList<SecondUser2> sedUserTiWen = new ArrayList<>();
                    sedUserTiWen = dbManager.searchUserMid(String.valueOf(zTiWen._id));
                    String midTiWen = "";
                    for (int j = 0; j < sedUserTiWen.size(); j++) {
                        SecondUser2 secondUserTiWen = sedUserTiWen.get(j);
                        midTiWen = secondUserTiWen.user_id;
                    }

                    Log.d(TAG, "midTiWen:====== "+midTiWen);
                    mUCenterActionsCreator.toAddHealthDetectionData(midTiWen, mDeviceUserId, mSessionKey, mSessionValue, measuredData);

            }

            Log.d(TAG, "数据库中体温数据:==" + mTiWen);
            Log.d(TAG, "数据库中UID:==" + mDeviceUserId);
            dbManager.clearDataTiWen();

            //心电本地数据库上传数据
            ArrayList<XinDianEntity> xindian = new ArrayList<>();
            xindian = dbManager.searchXinDidan();
            String xindian2 = "";
            for (int i = xindian.size() - 1; i > -1; i--) {
                XinDianEntity mXinDian = xindian.get(i);
                xindian2 = xindian2+"心电_id"+mXinDian._id + "心电uid==" + String.valueOf(mXinDian.user_id) + "心率==" + String.valueOf(mXinDian.xinlv) + "RRmax==" + String.valueOf(mXinDian.RRmax) + "RRmin==" + String.valueOf(mXinDian.RRmin) + "心率变异==" + String.valueOf(mXinDian.xinlvbianyi) + "心情==" + String.valueOf(mXinDian.mood);
                xindian2 = xindian2 + "\n" + "------------------------------------------" + "\n";
               if ((mXinDian.xinlv).equals("")){
                   mEcgMeasuredData.getHeartRate().setValue(Float.valueOf(0));
               }else {
                   mEcgMeasuredData.getHeartRate().setValue(Float.valueOf(mXinDian.xinlv));
               }


                if ((mXinDian.RRmax).equals("")){
                    mEcgMeasuredData.getPrmax().setValue(Float.valueOf(0));
                }else {
                    mEcgMeasuredData.getPrmax().setValue(Float.valueOf(mXinDian.RRmax));
                }

                if ((mXinDian.RRmin).equals("")){
                    mEcgMeasuredData.getPrmin().setValue(Float.valueOf(0));
                }else {
                    mEcgMeasuredData.getPrmin().setValue(Float.valueOf(mXinDian.RRmin));
                }


                if ((mXinDian.xinlvbianyi).equals("")){
                    mEcgMeasuredData.getHeartRateVariability().setValue(Float.valueOf(0));
                }else {
                    mEcgMeasuredData.getHeartRateVariability().setValue(Float.valueOf(mXinDian.xinlvbianyi));
                }


                if ((mXinDian.mood).equals("")){
                    mEcgMeasuredData.getMood().setValue(Float.valueOf(0));
                }else {
                    mEcgMeasuredData.getMood().setValue(Float.valueOf(mXinDian.mood));
                }



                ArrayList<SecondUser2> sedUserXinDian = new ArrayList<>();
                sedUserXinDian = dbManager.searchUserMid(String.valueOf(mXinDian._id));
                String midXinDian = "";
                for (int j = 0; j < sedUserXinDian.size(); j++) {
                    SecondUser2 secondUserTiWen = sedUserXinDian.get(j);
                    midXinDian = secondUserTiWen.user_id;
                }

                Log.d(TAG, "midXinDian:====== "+midXinDian);



                mUCenterActionsCreator.toAddHealthDetectionData(midXinDian, mDeviceUserId, mSessionKey, mSessionValue, mEcgMeasuredData);

            }
            Log.d(TAG, "数据库中心电数据:==" + xindian2);
            dbManager.clearDataXinDian();


            //干式生化仪本地数据库上传数据
            ArrayList<GanShi> ganshi = new ArrayList<>();
            ganshi = dbManager.searchGanShi();
            String ganshi2 = "";
            for (int i = ganshi.size() - 1; i > -1; i--) {
                GanShi mGanShi = ganshi.get(i);
                ganshi2 = ganshi2+"_id"+mGanShi._id + "干式生化仪user_id==" + String.valueOf(mGanShi.user_id) + "干式生化仪信息==" + String.valueOf(mGanShi.message);
                ganshi2 = ganshi2 + "\n" + "------------------------------------------" + "\n";

                Log.d(TAG, "mGanShi._id: =="+mGanShi._id);
                ArrayList<SecondUser2> sedUserGanShi = new ArrayList<>();
                sedUserGanShi = dbManager.searchUserMid(mGanShi._id);
                String midGanShi = "";
                for (int j = 0; j < sedUserGanShi.size(); j++) {
                    SecondUser2 secondUserGanShi = sedUserGanShi.get(j);
                    midGanShi = secondUserGanShi.user_id;
                }

                Log.d(TAG, "midGanShi:====== "+midGanShi);



                mUCenterActionsCreator.toUploadDryAnalyzerData(mDeviceUserId, mSessionKey, mSessionValue, midGanShi, String.valueOf(mGanShi.message));
            }

            Log.d(TAG, "数据库干式生化仪数据:==" + ganshi2);
            dbManager.clearDataGanShi();

            BC401_Data niaoye = new BC401_Data();
            niaoye.Structs = dbManager.searchNiaoYe();
            String niaoye2 = "";
            for (int i = niaoye.Structs.size() - 1; i > -1; i--) {
                BC401_Struct mNiaoYe = niaoye.Structs.get(i);
                niaoye2 = niaoye2 + "_id==" + String.valueOf(mNiaoYe.ID) + "URO==" + String.valueOf(mNiaoYe.URO) + "BLD==" + String.valueOf(mNiaoYe.BLD) + "BIL==" + String.valueOf(mNiaoYe.BIL) + "KET==" + String.valueOf(mNiaoYe.KET) + "GLU==" + String.valueOf(mNiaoYe.GLU) + "PRO==" + String.valueOf(mNiaoYe.PRO) + "PH==" + String.valueOf(mNiaoYe.PH) + "NIT==" + String.valueOf(mNiaoYe.NIT) + "LEU==" + String.valueOf(mNiaoYe.LEU) + "SG==" + String.valueOf(mNiaoYe.SG) + "VC==" + String.valueOf(mNiaoYe.VC);
                niaoye2 = niaoye2 + "\n" + "------------------------------------------" + "\n";


                ArrayList<SecondUser2> sedUserNiaoYe = new ArrayList<>();
                sedUserNiaoYe = dbManager.searchUserMid(String.valueOf(mNiaoYe.ID));
                String midNiaoYe = "";
                for (int j = 0; j < sedUserNiaoYe.size(); j++) {
                    SecondUser2 secondUserNiaoYe = sedUserNiaoYe.get(j);
                    midNiaoYe = secondUserNiaoYe.user_id;
                }

                Log.d(TAG, "midGanShi:====== "+midNiaoYe);



                mUCenterActionsCreator.toAddUrineDetectionData(midNiaoYe, mDeviceUserId, mSessionKey, mSessionValue, niaoye.Structs);
            }
            Log.d(TAG, "数据尿液分析仪数据:==" + niaoye2);
            dbManager.clearDataNiaoYe();

    }

}
