package com.huihao.entity;

/**
 * Created by huisou on 2015/8/11.
 */
public class OrdersNewsEntity {

    /**
     * orderid : 2008080880
     * user_info : huanglei 15312341234
     * total_price : 1500.00
     * overdue : 740.00
     * ctime : 1440832316
     * status : 1
     */
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String orderid;
    private String user_info;
    private String total_price;
    private String overdue;
    private String ctime;
    private String status;

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getUser_info() {
        return user_info;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getOverdue() {
        return overdue;
    }

    public String getCtime() {
        return ctime;
    }

    public String getStatus() {
        return status;
    }
}
