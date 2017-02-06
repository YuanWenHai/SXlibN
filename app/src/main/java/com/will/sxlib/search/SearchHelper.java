package com.will.sxlib.search;

import com.will.sxlib.bean.BookSearchResult;
import com.will.sxlib.decode.HtmlUtils;
import com.will.sxlib.net.OkHttpUtils;
import com.will.sxlib.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by will on 2017/2/4.
 */

public class SearchHelper {
    private int pageNumber = 1;
    private SearchUrlBuilder searchKey;

    public void search(SearchUrlBuilder builder,RequestCallback callback){
        searchKey = builder;
        pageNumber = 1;
        requestSearchResultWithUrl(builder.pageNumber(pageNumber).build(),callback);
    }

    public void nextPage(RequestCallback callback){
        requestSearchResultWithUrl(searchKey.pageNumber(pageNumber).build(),callback);
    }

    public void toSpecificPage(int pageNumber,RequestCallback callback){
        requestSearchResultWithUrl(searchKey.pageNumber(pageNumber).build(),callback);
    }

    public void searchWithUrl(String url,RequestCallback callback){
        requestSearchResultWithUrl(url,callback);
    }

    private void requestSearchResultWithUrl(String url,final RequestCallback callback){
        OkHttpUtils.getInstance().requestFromUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<BookSearchResult> list = HtmlUtils.getBookSearchResultFromHtml(response.body().string());
                requestCoversWithISBNs(list,callback);
                pageNumber++;
            }
        });

    }




    private void requestCoversWithISBNs(final List<BookSearchResult> list, final RequestCallback callback){
        String host = "http://api.interlib.com.cn/interlibopac/websearch/metares?glc=P1SXJ0351005&cmdACT=getImages&type=0&isbns=";
        String isbns = "";
        for(BookSearchResult result :list){
            isbns = isbns + "," + result.getIsbn().replaceAll("-","");
        }
        String url = host + isbns;
        OkHttpUtils.getInstance().requestFromUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onSuccess(list);
                Common.makeToast("获取书籍封面失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string().replace("(","").replace(")","");
                JSONObject j;
                BookSearchResult b;
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray results = jsonObject.getJSONArray("result");
                    for(int i=0;i<list.size();i++){

                        b = list.get(i);
                        for(int q=0;q<results.length();q++){
                            j = results.getJSONObject(q);
                            if(j.get("isbn").equals(b.getIsbn().replaceAll("-",""))){
                                b.setCoverUrl(j.getString("coverlink"));
                                break;
                            }
                        }

                    }
                    callback.onSuccess(list);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    public interface RequestCallback {
        void onSuccess(List<BookSearchResult> list);
        void onFailure();
    }
}
