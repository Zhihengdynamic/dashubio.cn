package com.dashubio.utils;/**
 * Created by zzb on 2017/5/15.
 */

/**
 * @autor 张梓彬
 * Date: 2017/5/15
 * Time: 14:07
 */

public class LoginEntity {

    /**
     * status : success
     * code : 5
     * t_session_5 : f26087a3a14e9e2932b11df7ed26b256
     * company : 大树科技
     */

    private String status;
    private String code;
    private String t_session_id;
    private String company;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getT_session_id() {
        return t_session_id;
    }

    public void setT_session_id(String t_session_id) {
        this.t_session_id = t_session_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
