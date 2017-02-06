package com.will.sxlib.search;

/**
 * Created by will on 2017/2/3.
 */

public class SearchUrlBuilder {

    private static final String SEARCH_HOST = "http://opac.lib.sx.cn/opac/search?curlibcode=0101";


    //检索途径,searchWay
    public static final String SEARCH_WAY_TITLE = "title";
    public static final String SEARCH_WAY_AUTHOR = "author";
    public static final String SEARCH_WAY_ISBN = "isbn";
    public static final String[] SEARCH_WAYS = {SEARCH_WAY_TITLE,SEARCH_WAY_AUTHOR,SEARCH_WAY_ISBN};
    //排序依据,sortWay
    public static final String SORT_WAY_SCORE = "score";
    public static final String SORT_WAY_DATE = "pubdate_sort";
    public static final String SORT_WAY_LOAN_COUNT = "loannum_sort";
    public static final String[] SORT_WAYS = {SORT_WAY_SCORE,SORT_WAY_DATE,SORT_WAY_LOAN_COUNT};
    //排列顺序,sortOrder
    public static final String SORT_ORDER_DESCEND = "desc";
    public static final String SORT_ORDER_ASCEND = "asc";
    public static final String[] SORT_ORDERS = {SORT_ORDER_DESCEND,SORT_ORDER_DESCEND};
    //每页条目,rows


    private String mSearchKey = null;
    private String mSearchWay = SEARCH_WAY_TITLE;
    private String mSortWay = SORT_WAY_SCORE;
    private String mSortOrder = SORT_ORDER_DESCEND;
    private int mPageItemCount = 10;
    private int mPageNumber = 1;

    public String build(){
        if(mSearchWay == null){
            throw new IllegalArgumentException("you have to set searchKey before build!");
        }
        return SEARCH_HOST + "&searchWay=" + mSearchWay + "&sortWay=" + mSortWay + "&sortOrder="
                + mSortOrder + "&rows=" + mPageItemCount + "&q="+mSearchKey + "&page=" + mPageNumber;
    }

    public SearchUrlBuilder searchWay(String searchWay){
        mSearchWay = searchWay;
        return this;
    }
    public SearchUrlBuilder searchWay(int which){
        mSearchWay = SEARCH_WAYS[which];
        return this;
    }

    public SearchUrlBuilder sortWay(String sortWay){
        mSortWay = sortWay;
        return this;
    }
    public SearchUrlBuilder sortWay(int which){
        mSortWay = SORT_WAYS[which];
        return this;
    }

    public SearchUrlBuilder sortOrder(String sortOrder){
        mSortOrder = sortOrder;
        return this;
    }
    public SearchUrlBuilder sortOrder(int which){
        mSortOrder = SORT_ORDERS[which];
        return this;
    }

    public SearchUrlBuilder pageItemCount(int pageItemCount){
        mPageItemCount = pageItemCount;
        return this;
    }
    public SearchUrlBuilder searchKey(String searchKey){
        mSearchKey = searchKey;
        return this;
    }
    public SearchUrlBuilder pageNumber(int pageNumber){
        mPageNumber = pageNumber;
        return this;
    }


}
