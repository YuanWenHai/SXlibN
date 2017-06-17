package com.will.sxlib.myBook.bean;

/**
 * Created by Will on 2017/6/17.
 */

public class MyHistoricalBookItem extends MyBookItem {
    //操作类型
    private String operationType = "";
    //处理时间
    private String operationDate = "";
    //条目数量
    private String totalCount = "";
    //著者
    private String author = "";
    private int currentPageIndex;
    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
