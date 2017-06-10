package com.will.sxlib.search;

import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.will.recyclerviewloadingadapter.AsyncLoadingAdapter;
import com.will.recyclerviewloadingadapter.BaseRecyclerViewHolder;
import com.will.sxlib.R;
import com.will.sxlib.search.bean.BookSearchResult;
import com.will.sxlib.decode.HtmlUtils;
import com.will.sxlib.net.OkHttpUtils;
import com.will.sxlib.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by will on 2017/5/14.
 */

public class SearchAdapter extends AsyncLoadingAdapter<BookSearchResult> {
    private boolean hasMoreData = true;
    private SearchUrlBuilder mUrlBuilder;

    public SearchAdapter(){
        super(R.layout.fragment_search_item);
    }

    public void start(SearchUrlBuilder builder,OnLoadingListener listener){
        mUrlBuilder = builder;
        load(true,listener);
    }


    @Override
    public Call obtainTargetCall(int page) {
        return OkHttpUtils.getInstance().wrapUrlToCall(mUrlBuilder.pageNumber(page).build());
    }

    @Override
    public List<BookSearchResult> getCorrespondingData(Response response) {
        String content = "";
        try{
            content = response.body().string();
        }catch (IOException i){
            i.printStackTrace();
        }
        List<BookSearchResult> list = HtmlUtils.getBookSearchResultFromHtml(content);
        loadBookCoverIntoItem(list);
        hasMoreData = list.size() == 10;
        return list;
    }

    @Override
    public boolean hasMoreData() {
        return hasMoreData;
    }

    @Override
    public void convert(BaseRecyclerViewHolder baseRecyclerViewHolder, BookSearchResult bookSearchResult) {
        baseRecyclerViewHolder.setText(R.id.search_item_title,bookSearchResult.getTitle());
        baseRecyclerViewHolder.setText(R.id.search_item_author,"作者："  + bookSearchResult.getAuthor());
        baseRecyclerViewHolder.setText(R.id.search_item_isbn,"ISBN：" + bookSearchResult.getIsbn());
        baseRecyclerViewHolder.setText(R.id.search_item_press,"出版社：" + bookSearchResult.getPress());
        baseRecyclerViewHolder.setText(R.id.search_item_publish_date,"出版日期：" + bookSearchResult.getPublishDate());

        if(bookSearchResult.getCoverUrl() != null){
            Picasso.with(getRecyclerView().getContext()).load(bookSearchResult.getCoverUrl()).placeholder(R.drawable.loading_image).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));
        }else{
            Picasso.with(getRecyclerView().getContext()).load(R.drawable.no_image_available).into((ImageView)baseRecyclerViewHolder.getView(R.id.search_item_cover));

        }
    }
    private void loadBookCoverIntoItem(List<BookSearchResult> list){
        //拼接获取Cover的Url
        String host = "http://api.interlib.com.cn/interlibopac/websearch/metares?glc=P1SXJ0351005&cmdACT=getImages&type=0&isbns=";
        String isbns = "";
        for(BookSearchResult result :list){
            isbns = isbns + "," + result.getIsbn().replaceAll("-","");
        }
        String url = host + isbns;
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        String responseStr = null;
        try{
            //因为本方法将执行于WorkerThread，所以此处为同步
            response = OkHttpUtils.getInstance().getClient().newCall(request).execute();
            responseStr = response.body().string();
        }catch (IOException i){
            i.printStackTrace();
            Log.e(getClass().getName(),"on load cover error.");
            Common.makeToast("加载封面失败：获取失败");
        }finally {
            if(response != null){
                response.close();
            }
        }
        if(responseStr != null){
            writeCoverDataIntoItem(responseStr,list);
        }
    }
    private void writeCoverDataIntoItem(String responseStr,List<BookSearchResult> list){
        String jsonStr = responseStr.replace("(","").replace(")","");
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
        }catch (JSONException e){
            e.printStackTrace();
            Log.e(getClass().getName(),"on resolve cover data error");
            Common.makeToast("加载封面失败：解析失败");
        }
    }
}
