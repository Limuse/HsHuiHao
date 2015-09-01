package com.huihao.entity;

import java.util.List;
/**
 * Created by huisou on 2015/8/5.
 * update by9/1
 */
public class AllOrderEntity {

    /**
     * id : 2008080884
     * state : 0
     * total_price : 1588.00
     * pay_price : 1588.00
     * _child : [{"orderid":"2008080884","title":"卫生巾测试商品","spec_1":"夜用5盒+护垫1盒","spec_2":"","newprice":"122.00","num":"4","picurl":"http://huihaowfx.huisou.com/Uploads/1/image/20150812/20150812155818_18384.png"},{"orderid":"2008080884","title":"测试商品1","spec_1":"瓶（200ml）","spec_2":"","newprice":"100.00","num":"11","picurl":"http://huihaowfx.huisou.com/Uploads/1/image/20150729/20150729202451_51530.jpg"}]
     */

    private String id;
    private String state;
    private String total_price;
    private String pay_price;
    private List<ChildEntity> _child;

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public void set_child(List<ChildEntity> _child) {
        this._child = _child;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getPay_price() {
        return pay_price;
    }

    public List<ChildEntity> get_child() {
        return _child;
    }

    public static class ChildEntity {
        /**
         * orderid : 2008080884
         * title : 卫生巾测试商品
         * spec_1 : 夜用5盒+护垫1盒
         * spec_2 :
         * newprice : 122.00
         * num : 4
         * picurl : http://huihaowfx.huisou.com/Uploads/1/image/20150812/20150812155818_18384.png
         */

        private String orderid;
        private String title;
        private String spec_1;
        private String spec_2;
        private String newprice;
        private String num;
        private String picurl;
        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSpec_1(String spec_1) {
            this.spec_1 = spec_1;
        }

        public void setSpec_2(String spec_2) {
            this.spec_2 = spec_2;
        }

        public void setNewprice(String newprice) {
            this.newprice = newprice;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getOrderid() {
            return orderid;
        }

        public String getTitle() {
            return title;
        }

        public String getSpec_1() {
            return spec_1;
        }

        public String getSpec_2() {
            return spec_2;
        }

        public String getNewprice() {
            return newprice;
        }

        public String getNum() {
            return num;
        }

        public String getPicurl() {
            return picurl;
        }
    }
}
