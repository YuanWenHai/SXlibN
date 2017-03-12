package com.will.sxlib.decode;

import com.will.sxlib.bookDetail.bean.BookState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/3/5.
 */

public class JsonDecoder {
    public static List<BookState> getBookStateFromJsonString(String jsonStr){
        List<BookState> list = new ArrayList<>();
       try{
           JSONObject baseObject = new JSONObject(jsonStr);
           JSONArray holdingList = baseObject.getJSONArray("holdingList");
           BookState bookState;
           for(int i=0;i<holdingList.length();i++){
               JSONObject item = holdingList.getJSONObject(i);
               bookState = new BookState();
               bookState.setBarcode(item.getString("barcode"));
               bookState.setCallno(item.getString("callno"));
               bookState.setLoanNumber(item.getInt("totalLoanNum"));
               bookState.setRenewNumber(item.getInt("totalRenewNum"));
               //根据字典查询内容随后填充
               bookState.setState(getBookStateWithCode(baseObject,item.getInt("state")));
               bookState.setType(getBookTypeWithCode(baseObject,item.getString("cirtype")));
               bookState.setLocal(getBookLocalWithCode(baseObject,item.getString("orglocal")));
               insertLoanData(baseObject,bookState);
               list.add(bookState);
           }

       }catch (JSONException j){
           j.printStackTrace();
           return list;
       }
        return list;
    }
    private static String getBookLocalWithCode(JSONObject jsonObject, String code){
        try{
            return jsonObject.getJSONObject("localMap").getString(code);
        }catch (JSONException j){
            j.printStackTrace();
        }
        return "";
    }
    private static String getBookStateWithCode(JSONObject jsonObject,int code){
        try{
            return jsonObject.getJSONObject("holdStateMap").getJSONObject(String.valueOf(code)).getString("stateName");
        }catch (JSONException j){
            j.printStackTrace();
        }
        return "";
    }
    private static String getBookTypeWithCode(JSONObject jsonobject,String code){
        try{
            return jsonobject.getJSONObject("pBCtypeMap").getJSONObject(code).getString("name");
        }catch (JSONException j){
            j.printStackTrace();
        }
        return "";
    }
    private static void insertLoanData(JSONObject jsonObject,BookState bookState){
        try{
            JSONObject loanWorkMap = jsonObject.getJSONObject("loanWorkMap");
            if(loanWorkMap.has(bookState.getBarcode())){
                bookState.setLoanDate(loanWorkMap.getJSONObject(bookState.getBarcode()).getLong("loanDate"));
                bookState.setReturnDate(loanWorkMap.getJSONObject(bookState.getBarcode()).getLong("returnDate"));
            }
        }catch (JSONException j){
            j.printStackTrace();
        }
    }
}
