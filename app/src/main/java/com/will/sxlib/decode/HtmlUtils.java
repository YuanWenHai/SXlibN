package com.will.sxlib.decode;

import android.util.Log;

import com.will.sxlib.myBook.bean.MyHistoricalBookItem;
import com.will.sxlib.myBook.bean.MyRenewBookItem;
import com.will.sxlib.search.bean.BookSearchResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/2/4.
 * 处理html数据
 */

public class HtmlUtils {

    public static List<BookSearchResult> getBookSearchResultFromHtml(String html){
        List<BookSearchResult> list = new ArrayList<>();
        try{
            BookSearchResult searchResult;
            Document document = Jsoup.parse(html);
            Elements elements = document.select("#curlibcodeFacetUL").select("li");
            int resultNumber = 0;
            if(elements.size() > 0){
                resultNumber = Integer.valueOf(elements.get(0).select("span.facetCount").text().replaceAll("\\(","").replaceAll("\\)",""));
            }
            Elements  results = document.select("table.resultTable").select("tr");
            for(Element result : results){
                searchResult = new BookSearchResult();
                searchResult.setResultNumber(resultNumber);
                searchResult.setRecno(result.select(".bookmeta").attr("bookrecno"));
                searchResult.setIsbn(result.select(".bookcover_img").attr("isbn"));
                searchResult.setTitle(result.select(".bookmetaTitle").text());
                Elements divs = result.select("div");
                searchResult.setAuthor(divs.get(2).select("a").text());
                searchResult.setPress(divs.get(3).select("a").text());
                String wholeString = divs.get(3).text();
                searchResult.setPublishDate(wholeString.substring(wholeString.lastIndexOf(":") +1).replaceAll(" ",""));
                list.add(searchResult);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("HtmlUtils","on resolve html data error!");
        }
        return list;
    }

    public static List<MyRenewBookItem> getMyRenewBookItemFromHtml(String html){
        List<MyRenewBookItem> list = new ArrayList<>();
        try{
           Document document = Jsoup.parse(html);
           Element table = document.getElementById("contentTable");
           Elements items = table.getElementsByTag("tr");
           MyRenewBookItem itemObj;
           for(int i=0;i<items.size();i++){
               Element item = items.get(i);
               if(item.id().equals("contentHeader")){
                   continue;
               }
               Elements attrs = item.getElementsByTag("td");
               attrs.remove(0);
               itemObj = new MyRenewBookItem();
               itemObj.setBarCode(attrs.get(0).text());
               itemObj.setTitle(attrs.get(1).text());
               itemObj.setCallNo(attrs.get(2).text());
               itemObj.setLocation(attrs.get(3).text());
               itemObj.setType(attrs.get(4).text());
               itemObj.setLoanDate(attrs.get(6).text());
               itemObj.setReturnDate(attrs.get(7).text());
               itemObj.setRenewCount(attrs.get(8).text());
               list.add(itemObj);
           }


       }catch ( Exception e){
           e.printStackTrace();
           Log.e("HtmlUtils","on resolve html data error!");
       }
       return list;
    }

    public static List<MyHistoricalBookItem> getMyHistoricalItemFromHtml(String html){
        List<MyHistoricalBookItem> list = new ArrayList<>();
        try{
            Document document = Jsoup.parse(html);
            Element table = document.getElementById("contentTable");
            Elements items = table.getElementsByTag("tr");
            MyHistoricalBookItem itemObj;
            for(int i=0;i<items.size();i++){
                Element item = items.get(i);
                if(item.id().equals("contentHeader")){
                    continue;
                }
                Elements attrs = item.getElementsByTag("td");
                itemObj = new MyHistoricalBookItem();
                itemObj.setOperationType(attrs.get(0).text());
                itemObj.setBarCode(attrs.get(1).text());
                itemObj.setTitle(attrs.get(2).text());
                itemObj.setAuthor(attrs.get(3).text());
                itemObj.setCallNo(attrs.get(4).text());
                itemObj.setLocation(attrs.get(5).text());
                itemObj.setType(attrs.get(6).text());
                itemObj.setOperationDate(attrs.get(7).text());
                list.add(itemObj);
            }


        }catch ( Exception e){
            e.printStackTrace();
            Log.e("HtmlUtils","on resolve html data error!");
        }
        return list;
    }

}
