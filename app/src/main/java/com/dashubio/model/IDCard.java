package com.dashubio.model;

import java.io.Serializable;

/**
 * Created by Xinbin Zhang on 2016/12/4.
 */
public class IDCard implements Serializable {
	public String name;//姓名
	public String cardno;//身份证号
	public String sex;//性别
	public String folk;//民族
	public String birthday;//生日
	public String address;//地址
	public String province;//省
	public String city;//城市
	public String district;//地区



	private String[] issue_authority;//签发单位
	private String[] valid_period;//有效期
	private String[] header_pic;//头像

	public String phone = "";//手机号码

	public String mphone = "";//常用联系人手机号码

	public IDCard() {

	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String[] getIssue_authority() {
		return issue_authority;
	}

	public void setIssue_authority(String[] issue_authority) {
		this.issue_authority = issue_authority;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String[] getValid_period() {
		return valid_period;
	}

	public void setValid_period(String[] valid_period) {
		this.valid_period = valid_period;
	}

	public String[] getHeader_pic() {
		return header_pic;
	}

	public void setHeader_pic(String[] header_pic) {
		this.header_pic = header_pic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

