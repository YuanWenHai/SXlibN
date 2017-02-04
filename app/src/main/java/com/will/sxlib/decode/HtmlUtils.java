package com.will.sxlib.decode;

import com.will.sxlib.bean.BookSearchResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/2/4.
 */

public class HtmlUtils {

    public static List<BookSearchResult> getBookSearchResultFromHtml(String html){
        List<BookSearchResult> list = new ArrayList<>();
        BookSearchResult searchResult;
        Elements  results = Jsoup.parse(html).select("table.resultTable").select("tr");
        for(Element result : results){
            searchResult = new BookSearchResult();
            searchResult.setIsbn(result.select(".bookcover_img").attr("isbn"));
            searchResult.setTitle(result.select(".bookmetaTitle").text());
            Elements divs = result.select("div");
            searchResult.setAuthor(divs.get(2).select("a").text());
            searchResult.setPress(divs.get(3).select("a").text());
            String wholeString = divs.get(3).text();
            searchResult.setPublishDate(wholeString.substring(wholeString.lastIndexOf(":")));
            list.add(searchResult);
        }
        return list;
    }
}
