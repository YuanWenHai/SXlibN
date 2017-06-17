package com.will.sxlib.myBook.bean;

/**
 * Created by Will on 2017/6/17.
 */

public class MyBookItem {
    //条码号
    private String barCode = "";
    //题名
    private String title = "";

    //索书号
    private String callNo = "";
    //馆藏地点
    private String location = "";
    //文献类型
    private String type = "";


    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
