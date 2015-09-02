package com.huihao.entity;

/**
 * Created by huisou on 2015/8/4.
 */
public class RebateDetailEntity {

    /**
     * orderid : 2008080871
     * total_price : 100
     * money : 22.5
     * username : 张三
     * state : 未付款
     */

    private String orderid;
    private String total_price;
    private String money;
    private String username;
    private String state;


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getUsername() {
        return username;
    }

    public String getState() {
        return state;
    }
}
