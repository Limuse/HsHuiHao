package com.huihao.entity;

import java.util.List;

/**
 * Created by huisou on 2015/8/29.
 */
public class MyEntiy {

    /**
     * head_img : http://huihaowfx.huisou.com/Uploads/appavater/14408301215897.jpg
     * meassage : 0
     * username : 佚名
     */

    private String head_img;
    private String meassage;
    private String username;
    /**
     * status_list : [{"state":"0","total_num":"86"},{"state":"1","total_num":"6"},{"state":"2","total_num":"1"}]
     */

    private List<StatusListEntity> status_list;


    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public void setMeassage(String meassage) {
        this.meassage = meassage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_img() {
        return head_img;
    }

    public String getMeassage() {
        return meassage;
    }

    public String getUsername() {
        return username;
    }

    public void setStatus_list(List<StatusListEntity> status_list) {
        this.status_list = status_list;
    }

    public List<StatusListEntity> getStatus_list() {
        return status_list;
    }

    public static class StatusListEntity {
        /**
         * state : 0
         * total_num : 86
         */

        private String state;
        private String total_num;

        public void setState(String state) {
            this.state = state;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public String getState() {
            return state;
        }

        public String getTotal_num() {
            return total_num;
        }
    }
}
