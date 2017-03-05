package com.will.sxlib.bean;

/**
 * Created by will on 2017/2/4.
 */

public class BookSearchResult {
    private String title;
    private String author;
    private String publishDate;
    private String coverUrl;
    private String press;
    private String isbn;
    private int resultNumber;
    //类似书籍数据库识别号之类的东西，用于索引书籍详情界面，书籍在馆状态等.
    private int recno;
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }


    public int getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }

    public int getRecno() {
        return recno;
    }

    public void setRecno(int recno) {
        this.recno = recno;
    }
}
