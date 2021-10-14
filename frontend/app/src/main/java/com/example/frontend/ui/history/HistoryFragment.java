package com.example.frontend.ui.history;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.frontend.R;
import com.example.frontend.RequestHttpURLConnection;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        // xml의 listview id를 반드시 "@android:id/list"로 해줘야 한다.
        String[] values = new String[] {"홍길동", "이순신", "강감찬", "유관순", "김유신","을지문덕", "김춘추"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        return rootView;
    }

    //글 기록 확인
    public List<HashMap> listPostHistory(){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String getTime = sformat.format(now);
        String rtnStr="";
        int count = 0;
        //REST API 주소
        String url = "http://192.168.0.32:8080/api/history2";
        //String url = "http://본인IP주소:8080/api/history2";

        List<HashMap> List = new ArrayList<HashMap>();

        try{
            String jsonString = new JSONObject()
                    .put("userId", "userId")
                    .put("publishDate", getTime)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

            //리스트 길이 확인



        }catch(Exception e){
            e.printStackTrace();
        }

        return List;
    }

}