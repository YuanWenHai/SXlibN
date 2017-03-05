package com.will.sxlib.decode;

import com.will.sxlib.bean.BookState;

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
           }

       }catch (JSONException j){
           throw new RuntimeException("json cast error!");
       }

    }
}
