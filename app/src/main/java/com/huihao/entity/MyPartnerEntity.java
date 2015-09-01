package com.huihao.entity;

import java.util.List;

/**
 * Created by huisou on 2015/8/4.
 */
public class MyPartnerEntity {
//    public int pid;
//    public String names;
//    public String moneys;


    private String total;
    private String child;
    private List<ChildList> childList;

    public List<ChildList> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildList> childList) {
        this.childList = childList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

   public static class ChildList {
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        private String username;
        private String amount;
    }
}
