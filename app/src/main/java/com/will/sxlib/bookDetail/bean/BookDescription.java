package com.will.sxlib.bookDetail.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by will on 2017/3/12.
 */

public class BookDescription implements Serializable{
    private Rating rating;
    private String catalog = "";
    private String summary = "";
    private String authorIntro = "";
    private String price;
    private String pages = "";


    public BookDescription(String jsonString){
        dealJsonString(jsonString);
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
        rating = new Rating();
        try{
            JSONObject baseObject = new JSONObject(jsonString);
            JSONObject ratingObject = baseObject.getJSONObject("rating");
            //不确定是否每次都可以获取到有内容的rating对象，故做一个简单的判断
            if(ratingObject.has("max")){
                rating.max = ratingObject.getInt("max");
                rating.min = ratingObject.getInt("min");
                rating.numRaters = ratingObject.getInt("numRaters");
                rating.average = ratingObject.getString("average");
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


    class Rating implements Serializable{
         int max;
         int min;
         int numRaters;
         String average = "0";
    }
    private String getFormattedCatalog(String originalCatalog){
        String[] splited = originalCatalog.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(String chapter : splited){
            builder.append(chapter).append("\n");
        }
        return builder.toString();
    }

}
