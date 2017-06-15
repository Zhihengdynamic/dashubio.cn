package com.dashubio.model.entity;

// 设备注册实体类
public class DeviceRegister {

	private String status;
	private int code;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "DeviceRegister [status=" + status + ", code=" + code + "]";
	}
	
	
}
