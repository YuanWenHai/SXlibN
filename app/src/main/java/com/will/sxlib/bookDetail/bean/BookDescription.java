package com.will.sxlib.bookDetail.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by will on 2017/3/12.
 */

public class BookDescription implements Serializable{
    private Rating rating = new Rating();
    private String catalog = "";
    private String summary = "";
    private String authorIntro = "";
    private String price;
    private String pages = "";


    public BookDescription(String jsonString){
        //如果无对应豆瓣数据，则返回空对象.
        if(!jsonString.contains("book_not_found")){
            dealJsonString(jsonString);
        }
    }

    public Rating getRating() {
        return rating;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public String getPrice() {
        return price;
    }

    public String getPages() {
        return pages;
    }

    private void dealJsonString(String jsonString){
        try{
            JSONObject baseObject = new JSONObject(jsonString);
            JSONObject ratingObject = baseObject.getJSONObject("rating");
            //不确定是否每次都可以获取到有内容的rating对象，故做一个简单的判断
            if(ratingObject.has("max")){
                rating.max = ratingObject.getInt("max");
                rating.min = ratingObject.getInt("min");
                rating.numRaters = ratingObject.getInt("numRaters");
                rating.average = ratingObject.getString("average");
            }else{
                rating.isEmpty(true);
            }

            String catalog = baseObject.getString("catalog");
            if(!catalog.isEmpty()){
                this.catalog = getFormattedCatalog(catalog);
            }
            summary = baseObject.getString("summary");
            price = baseObject.getString("price");
            pages = baseObject.getString("pages");
            authorIntro = baseObject.getString("author_intro");

        }catch (JSONException j ){
            j.printStackTrace();
        }
    }


    public class Rating implements Serializable{
         public int max;
         public int min;
         public int numRaters;
         public String average = "0";
        private boolean empty;
        private void isEmpty(boolean which){
            empty = which;
        }
        public boolean isEmpty(){
            return empty;
        }
    }
    private String getFormattedCatalog(String originalCatalog){
        String[] splited = originalCatalog.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(String chapter : splited){
            builder.append(chapter).append("\n");
        }
        return builder.toString();
    }


    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
