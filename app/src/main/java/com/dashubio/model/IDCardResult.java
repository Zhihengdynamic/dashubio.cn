package com.dashubio.model;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class IDCardResult {

    private String status;//
    private IDCardData data;//

    public static class IDCardData {
        private String facade;//
        private IDCard item;//

        public String getFacade() {
            return facade;
        }

        public void setFacade(String facade) {
            this.facade = facade;
        }

        public IDCard getItem() {
            return item;
        }

        public void setItem(IDCard item) {
            this.item = item;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public IDCardData getData() {
        return data;
    }

    public void setData(IDCardData data) {
        this.data = data;
    }
}
