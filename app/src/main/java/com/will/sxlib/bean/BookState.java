package com.will.sxlib.bean;

/**
 * Created by will on 2017/3/5.
 */

public class BookState {
    //索书号
    private String callno;
    //条码号
    private String barcode;
    //状态
    private String state;
    //应还日期:yyyy-MM-dd
    private String returnDate;
    //借阅日期
    private String loadDate;
    //所在位置
    private String local;
    //书籍类型
    private String type;
    //借阅次数
    private int loanNumber;
    //续借次数
    private int renewNumber;

    public String getCallno() {
        return callno;
    }

    public void setCallno(String callno) {
        this.callno = callno;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(int loanNumber) {
        this.loanNumber = loanNumber;
    }

    public int getRenewNumber() {
        return renewNumber;
    }

    public void setRenewNumber(int renewNumber) {
        this.renewNumber = renewNumber;
    }
}
