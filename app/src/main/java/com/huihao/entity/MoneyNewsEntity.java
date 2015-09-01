package com.huihao.entity;

/**
 * Created by huisou on 2015/8/11.
 */
public class MoneyNewsEntity {


    /**
     * content : 您通过2008080878订单获得了740
     * ctime : 2015-08-29 15:10:11
     * status : 1
     */

    private String content;
    private String ctime;
    private String status;

    public void setContent(String content) {
        this.content = content;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public String getCtime() {
        return ctime;
    }

    public String getStatus() {
        return status;
    }
}
