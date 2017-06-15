package com.dashubio.utils;

/**
 * Created by zzb on 2017/2/15.
 */

public class ADTitle {

    /**
     * status : success
     * data : {"id":"3","c_id":"0","d_id":"0","con":"在condition属性中可以支持eq等判断表达式，同上面的比较标签，但是不支持带有\u201d&gt;\u201d、\u201d&lt;\u201d等符号的用法，因为会混淆模板解析","status":"2","addtime":"1486628787"}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * c_id : 0
         * d_id : 0
         * con : 在condition属性中可以支持eq等判断表达式，同上面的比较标签，但是不支持带有”&gt;”、”&lt;”等符号的用法，因为会混淆模板解析
         * status : 2
         * addtime : 1486628787
         */

        private String id;
        private String c_id;
        private String d_id;
        private String con;
        private String status;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getD_id() {
            return d_id;
        }

        public void setD_id(String d_id) {
            this.d_id = d_id;
        }

        public String getCon() {
            return con;
        }

        public void setCon(String con) {
            this.con = con;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
