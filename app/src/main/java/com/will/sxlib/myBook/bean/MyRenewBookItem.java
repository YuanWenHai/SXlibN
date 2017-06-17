package com.will.sxlib.myBook.bean;

/**
 * Created by Will on 2017/6/17.
 */

public class MyRenewBookItem extends MyBookItem {
    //借出时间
    private String loanDate = "";
    //应还时间
    private String returnDate = "";
    //续借次数
    private String renewCount = "0";

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(String renewCount) {
        this.renewCount = renewCount;
    }
}
