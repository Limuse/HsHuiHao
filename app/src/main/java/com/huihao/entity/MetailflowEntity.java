package com.huihao.entity;

import java.util.List;

/**
 * Created by huisou on 2015/8/10.
 */
public class MetailflowEntity {

    /**
     * title : 默认天天快递(包邮)
     * status_name : 已发出
     * exress_no : 580185109239
     * exress_detail : [{"time":"2015-07-23 10:42:29","context":"快件已签收,签收人是草签，签收网点是宁波海曙营业部(0574-87451908，87451907)","ftime":"2015-07-23 10:42:29"},{"time":"2015-07-23 08:25:49","context":"宁波海曙营业部(0574-87451908，87451907)的陈林富15057457975正在派件","ftime":"2015-07-23 08:25:49"},{"time":"2015-07-23 08:17:54","context":"快件到达宁波海曙营业部(0574-87451908，87451907)，上一站是宁波(0574-87476999)扫描员是陈林富15057457975","ftime":"2015-07-23 08:17:54"},{"time":"2015-07-23 04:08:32","context":"快件由宁波(0574-87476999)发往宁波海曙营业部(0574-87451908，87451907)","ftime":"2015-07-23 04:08:32"},{"time":"2015-07-23 03:43:55","context":"快件到达宁波(0574-87476999)，上一站是宁波分拨中心扫描员是李长龙","ftime":"2015-07-23 03:43:55"},{"time":"2015-07-23 03:43:42","context":"快件由宁波分拨中心发往宁波(0574-87476999)","ftime":"2015-07-23 03:43:42"},{"time":"2015-07-22 23:10:36","context":"快件由杭州集散发往宁波分拨中心","ftime":"2015-07-22 23:10:36"},{"time":"2015-07-22 21:35:51","context":"快件由杭州客运中心(0571-85092881)发往杭州集散","ftime":"2015-07-22 21:35:51"},{"time":"2015-07-22 21:35:50","context":"杭州客运中心(0571-85092881)已进行装袋扫描","ftime":"2015-07-22 21:35:50"},{"time":"2015-07-22 20:01:19","context":"杭州客运中心(0571-85092881)的公司扫描已收件，扫描员是公司扫描","ftime":"2015-07-22 20:01:19"}]
     */

    private String title;
    private String status_name;
    private String exress_no;
    private List<ExressDetailEntity> exress_detail;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public void setExress_no(String exress_no) {
        this.exress_no = exress_no;
    }

    public void setExress_detail(List<ExressDetailEntity> exress_detail) {
        this.exress_detail = exress_detail;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getExress_no() {
        return exress_no;
    }

    public List<ExressDetailEntity> getExress_detail() {
        return exress_detail;
    }

    public static class ExressDetailEntity {
        /**
         * time : 2015-07-23 10:42:29
         * context : 快件已签收,签收人是草签，签收网点是宁波海曙营业部(0574-87451908，87451907)
         * ftime : 2015-07-23 10:42:29
         */

        private String time;
        private String context;
        private String ftime;

        public void setTime(String time) {
            this.time = time;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getTime() {
            return time;
        }

        public String getContext() {
            return context;
        }

        public String getFtime() {
            return ftime;
        }
    }
}
