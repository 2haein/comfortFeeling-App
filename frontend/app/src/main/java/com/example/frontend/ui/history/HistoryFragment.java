package com.example.frontend.ui.history;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.RequestHttpURLConnection;
import com.example.frontend.databinding.FragmentGraphBinding;
import com.example.frontend.databinding.FragmentHistoryBinding;
import com.example.frontend.databinding.FragmentHomeBinding;
import com.example.frontend.http.CommonMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class HistoryFragment extends Fragment {

    // 로그에 사용할 TAG 변수
    final private String TAG = getClass().getSimpleName();
    private FragmentHistoryBinding binding;


    // 사용할 컴포넌트 선언
    ListView listView;
    private String userId;

    // 리스트뷰에 사용할 내용 배열
    ArrayList<String> titleList = new ArrayList<>();

    // 리스트뷰에 사용할 날짜 배열
    ArrayList<String> dateList = new ArrayList<>();

    // 게시물 번호를 담기 위한 배열
    ArrayList<String> seqList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 컴포넌트 초기화

        listView = (ListView) root.findViewById(R.id.listView);

        HistoryListAdapter adapter = new HistoryListAdapter();
        listView.setAdapter(adapter);

        // 배열들 초기화
        titleList.clear();
        dateList.clear();
        seqList.clear();

        MainActivity activity = (MainActivity) getActivity();
        userId = activity.getUserId();

        try {

            // 결과물이 JSONArray 형태로 넘어오기 때문에 파싱
            JSONArray jsonArray = new JSONArray(getHistoryList());

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.optString("text");
                String publishDate = jsonObject.optString("publishDate");
                String score = jsonObject.optString("score");
                String seq = jsonObject.optString("id");

                // title, seq 값을 변수로 받아서 배열에 추가
                titleList.add(title);
                //dateList.add(publishDate);
                seqList.add(seq);

                adapter.addItem(seq, title, score, publishDate) ;



            }

            // ListView 에서 사용할 arrayAdapter를 생성하고, ListView 와 연결
            //ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, titleList);
            //listView.setAdapter(arrayAdapter);

            // arrayAdapter의 데이터가 변경되었을때 새로고침
            //arrayAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View root, int position, long l) {
                HistoryListView item = (HistoryListView) adapterView.getItemAtPosition(position);

                String idStr = item.getId();
                System.out.println("idStr="+idStr);


                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DetailFragment detailFragment = new DetailFragment();

                //프레그먼트끼리 rfgName넘기기 위한 bundle
                Bundle bundle = new Bundle();
                bundle.putString("seq", idStr);
                detailFragment.setArguments(bundle); //Name 변수 값 전달. 반드시 setArguments() 메소드를 사용하지 않으면, 받는 쪽에서 null 값으로 받음.
                //버튼을 눌렀을 때 RE-Fr자바를 탈 수 있도록 함
                transaction.replace(R.id.history_fragment, detailFragment); //프레임 레이아웃에서 프레그먼트 1로 변경(replace)해라
                transaction.addToBackStack(null);
                transaction.commit(); //저장해라 commit

            }
        });

        return root;
    }


    //당일 글 갯수
    public String getHistoryList(){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String getTime = sformat.format(now);
        String rtnStr="";
        int postNum=0;
        String url = CommonMethod.ipConfig +"/api/loadHistoryList";

        try{
            String jsonString = new JSONObject()
                    .put("userId", userId)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

        }catch(Exception e){
            e.printStackTrace();
        }

        return rtnStr;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


/*    @Override
    public void onResume() {
        super.onResume();
    // 해당 액티비티가 활성화 될 때, 게시물 리스트를 불러오는 함수를 호출
        GetBoard getBoard = new GetBoard();
        getBoard.execute();
    }


    // 게시물 리스트를 읽어오는 함수
    class GetBoard extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);

            // 배열들 초기화
            titleList.clear();
            dateList.clear();
            seqList.clear();

            try {

                // 결과물이 JSONArray 형태로 넘어오기 때문에 파싱
                JSONArray jsonArray = new JSONArray(result);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String title = jsonObject.optString("text");
                    String publishDate = jsonObject.optString("publishDate");
                    String seq = jsonObject.optString("_id");

                // title, seq 값을 변수로 받아서 배열에 추가
                    titleList.add(title);
                    dateList.add(publishDate);
                    seqList.add(seq);

                }

                // ListView 에서 사용할 arrayAdapter를 생성하고, ListView 와 연결
                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, titleList);
                listView.setAdapter(arrayAdapter);

                // arrayAdapter의 데이터가 변경되었을때 새로고침
                arrayAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        @Override
        protected String doInBackground(String... params) {


            String server_url = CommonMethod.ipConfig +"/api/loadHistory";


            URL url;
            String response = "";
            try {
                url = new URL(server_url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userId", activity.getUserId());
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }
    } */
}