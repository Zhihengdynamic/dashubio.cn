package com.dashubio.model;

/**
 * Created by tangjie on 2015/8/7.
 */
public class ResponseEntity extends Entity {

    private String state = "";
    private String flowmark ="";
    private String imei = "";

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getFlowmark() {
        return flowmark;
    }

    public void setFlowmark(String flowmark) {
        this.flowmark = flowmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
