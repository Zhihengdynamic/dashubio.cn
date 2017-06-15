package com.dashubio.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;


public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	// 保存用户UID
	public void setUID(String UID) {
		editor.putString("UID", UID);
		editor.commit();
	}
	
	public String getUID() {
		return sp.getString("UID", "");
	}
	
	// 保存用户手机号  即账号
	public void setAccount(String Account) {
		editor.putString("Account", Account);
		editor.commit();
	}
	
	public String getAccount() {
		return sp.getString("Account", "");
	}
	
	// 保存用户密码
	public void setPassword(String Password) {
		editor.putString("Password", Password);
		editor.commit();
	}
	
	public String getPassword() {
		return sp.getString("Password", "");
	}

	/*
	 * 保存经纬�?
	 * */
	public void setLatitude(String jingdu) {
		editor.putString("jingdu", jingdu);
		editor.commit();
	}

	public String getLatitude() {
		return sp.getString("jingdu", "");
	} 
	
	public void setLongitude(String weidu) {
		editor.putString("weidu", weidu);
		editor.commit();
	}

	public String getLongitude() {
		return sp.getString("weidu", "");
	}
	
	// 保存发货地和收货地经纬度
	public void setReceiveLatitude(String Latitude) {
		editor.putString("Latitude", Latitude);
		editor.commit();
	}

	public String getReceiveLatitude() {
		return sp.getString("Latitude", "");
	} 
	
	public void setReceiveLongitude(String Longitude) {
		editor.putString("Longitude", Longitude);
		editor.commit();
	}

	public String getReceiveLongitude() {
		return sp.getString("Longitude", "");
	}
	
	// 保存所选车名
	public void setCarName(String carNmae) {
		editor.putString("carNmae", carNmae);
		editor.commit();
	}
	
	public String getCarName() {
		return sp.getString("carNmae", "");
	}
	

	// 保存所选车id
	public void setCarId(String id) {
		editor.putString("id", id);
		editor.commit();
	}
	
	public String getCarId() {
		return sp.getString("id", "");
	}
	
	// 保存所选城市
	public void setSelectCity(String city) {
		editor.putString("city", city);
		editor.commit();
	}
	
	public String getSelectCity() {
		return sp.getString("city", "");
	}
	
	// 保存所选城市编码
	public void setSelectCityCode(String cityCode) {
		editor.putString("cityCode", cityCode);
		editor.commit();
	}
	
	public String getSelectCityCode() {
		return sp.getString("cityCode", "");
	}
	
	// 保存所选城市编号
//	public void setSelectCityId(String CityId) {
//		editor.putString("CityId", CityId);
//		editor.commit();
//	}
//	
//	public String getSelectCityId() {
//		return sp.getString("CityId", "");
//	}
	
	// 保存所选的地址名字
	public void setSelectAddressName(String AddressName) {
		editor.putString("AddressName", AddressName);
		editor.commit();
	}
	
	public String getSelectAddressName() {
		return sp.getString("AddressName", "");
	}
	
	// 保存所选的地址的详细地址
	public void setSelectAddressDetail(String AddressDetail) {
		editor.putString("AddressDetail", AddressDetail);
		editor.commit();
	}
	
	public String getSelectAddressDetail() {
		return sp.getString("AddressDetail", "");
	}
	
	// 保存用户姓名
	public void setPeopleName(String name) {
		editor.putString("name", name);
		editor.commit();
	}
	
	public String getPeopleName() {
		return sp.getString("name", "");
	}
	
	// 保存用户手机号码
	public void setPeopleTel(String tel) {
		editor.putString("tel", tel);
		editor.commit();
	}
	
	public String getPeopleTel() {
		return sp.getString("tel", "");
	}
	
	// 保存货物体积
	public void setGoodsVolume(String volume) {
		editor.putString("volume", volume);
		editor.commit();
	}
	
	public String getGoodsVolume() {
		return sp.getString("volume", "");
	}
	
	// 保存货物数量
	public void setGoodsNum(String num) {
		editor.putString("num", num);
		editor.commit();
	}
	
	public String getGoodsNum() {
		return sp.getString("num", "");
	}
	
	// 保存用户要求 如： 需回单  需搬卸等···  
	public void setUserRequire(String require) {
		editor.putString("require", require);
		editor.commit();
	}
	
	public String getUserRequire() {
		return sp.getString("require", "");
	}
	
	// 保存用户想说的话  
	public void setUserMsg(String msg) {
		editor.putString("msg", msg);
		editor.commit();
	}
	
	public String getUserMsg() {
		return sp.getString("msg", "");
	}
	
	// 保存货物名称
	public void setGoodsName(String goods) {
		editor.putString("goods", goods);
		editor.commit();
	}
		
	public String getGoodsName() {
		return sp.getString("goods", "");
	}
	
	// 保存是否是预约叫车   1 现在用车  2 预约
	public void setBookOrNot(String flag) {
		editor.putString("flag", flag);
		editor.commit();
	}

	public String getBookOrNot() {
		return sp.getString("flag", "");
	}
	
	// 保存添加的地址是收货地址还是发货地址
	public void setWhichAddress(String which) {
		editor.putString("which", which);
		editor.commit();
	}

	public String getWhichAddress() {
		return sp.getString("which", "");
	}
	
	// 是否更改所选城市
	public void setIsChangeCity(String change) {
		editor.putString("change", change);
		editor.commit();
	}
	
	public String getIsChangeCity() {
		return sp.getString("change", "");
	}
	
	// 是否更改收发货地址
	public void setIsChangeAddress(String ica) {
		editor.putString("ica", ica);
		editor.commit();
	}
	
	public String getIsChangeAddress() {
		return sp.getString("ica", "");
	}

}
