package com.huihao.entity;

/**
 * Created by huisou on 2015/8/11.
 */
public class SystemNewsEntity {

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    /**
     * title : 测试2
     * picture : http://huihaowfx.huisou.com/Uploads/1/image/20150819/20150819162548_50983.jpg
     * content : fasgdfadfasdfasdfasd
     * linkurl : https://www.baidu.com/
     * add_time : 2015-08-16 11:07:36
     * update_time : 0
     * status : 1
     */
    private String ids;


    private String title;
    private String picture;
    private String content;
    private String linkurl;
    private String add_time;
    private String update_time;
    private String status;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }

    public String getContent() {
        return content;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public String getAdd_time() {
        return add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getStatus() {
        return status;
    }
}
