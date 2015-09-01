package com.huihao.entity;

import android.widget.CheckBox;

/**
 * Created by huisou on 2015/7/23.
 */
public class ShopItemEntity {
    private String title;
    private String id;
    private float danjia;
    private float sale;
    private String specid;

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public float getDanjia() {
        return danjia;
    }

    public void setDanjia(float danjia) {
        this.danjia = danjia;
    }

    private boolean isCheck;//是否选中

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    /**
     * 标示是否选中
     */
    private boolean canChoose = true;
    /**
     * 图片
     */
    private String pic;
    /**
     * 颜色
     */
    private String color;
    /**
     * 型号，大小
     */
    private String size;
    /**
     * 材质
     */
    private String material;

    private int num;
    private String money;

    public ShopItemEntity() {
    }

    public ShopItemEntity(int id, String title, String pic, String color, String size, String material, int num, String money, boolean ischeck, float danjia) {
        this.title = title;
        this.pic = pic;
        this.color = color;
        this.size = size;
        this.material = material;
        this.num = num;
        this.money = money;
        this.isCheck = isCheck;
        this.danjia = danjia;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isCanChoose() {
        return canChoose;

    }

    public void setCanChoose(boolean canChoose) {
        this.canChoose = canChoose;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }


}
