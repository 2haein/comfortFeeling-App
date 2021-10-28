package com.example.frontend.ui.completion;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.RequestHttpURLConnection;
import com.example.frontend.common.ProfileData;
import com.example.frontend.databinding.FragmentCompletionBinding;
import com.example.frontend.databinding.FragmentDetailBinding;
import com.example.frontend.databinding.FragmentHistoryBinding;
import com.example.frontend.http.CommonMethod;
import com.example.frontend.ui.history.DetailFragment;
import com.example.frontend.ui.main.HomeFragment;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class CompletionFragment extends Fragment {

    final private String TAG = getClass().getSimpleName();
    private @NonNull
    FragmentCompletionBinding binding;
    private MapView mapView;
    ViewGroup mapViewContainer;

    // 사용할 컴포넌트 선언
    TextView content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;
    ImageView feel_btn1, feel_btn2, feel_btn3, feel_btn4, feel_btn5;

    String board_seq;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity activity = (MainActivity) getActivity();
        HomeFragment homeFragment = new HomeFragment();
        board_seq = homeFragment.sendBoardseq();

        userId = ProfileData.getUserId();

        // 컴포넌트 초기화
        content_tv = (TextView) root.findViewById(R.id.content_tv);
        date_tv = (TextView) root.findViewById(R.id.date_tv);
        feel_btn1 = (ImageView) root.findViewById(R.id.imageView1);
        feel_btn2 = (ImageView) root.findViewById(R.id.imageView2);
        feel_btn3 = (ImageView) root.findViewById(R.id.imageView3);
        feel_btn4 = (ImageView) root.findViewById(R.id.imageView4);
        feel_btn5 = (ImageView) root.findViewById(R.id.imageView5);
        comment_layout = (LinearLayout) root.findViewById(R.id.comment_layout);

        /*
        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if (bundle != null) {
            board_seq = bundle.getString("seq");
            System.out.println("seq=" + board_seq); //확인
        }

         */



        try {
            // 결과값이 JSONArray 형태로 넘어오기 때문에
            // JSONArray, JSONObject 를 사용해서 파싱
            JSONObject jsonObject = null;
            jsonObject = new JSONObject( getTodayHistory());

            // Database 의 데이터들을 변수로 저장한 후 해당 TextView 에 데이터 입력
            String content = jsonObject.optString("text");
            String publishDate = jsonObject.optString("publishDate");
            double xcoord = Double.parseDouble(jsonObject.optString("xcoord"));
            double ycoord = Double.parseDouble(jsonObject.optString("ycoord"));
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date pDate = inputFormat.parse(publishDate);
            inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            String newDateForm = inputFormat.format(pDate);
            int score = Integer.parseInt(jsonObject.optString("score"));

            content_tv.setText(content);
            date_tv.setText(newDateForm);

            switch (score) {
                case 1: feel_btn1.setBackgroundColor(Color.YELLOW);
                    feel_btn2.setBackgroundColor(Color.WHITE);
                    feel_btn3.setBackgroundColor(Color.WHITE);
                    feel_btn4.setBackgroundColor(Color.WHITE);
                    feel_btn5.setBackgroundColor(Color.WHITE);
                    break;
                case 2 : feel_btn1.setBackgroundColor(Color.WHITE);
                    feel_btn2.setBackgroundColor(Color.YELLOW);
                    feel_btn3.setBackgroundColor(Color.WHITE);
                    feel_btn4.setBackgroundColor(Color.WHITE);
                    feel_btn5.setBackgroundColor(Color.WHITE);
                    break;
                case 3 : feel_btn1.setBackgroundColor(Color.WHITE);
                    feel_btn2.setBackgroundColor(Color.WHITE);
                    feel_btn3.setBackgroundColor(Color.YELLOW);
                    feel_btn4.setBackgroundColor(Color.WHITE);
                    feel_btn5.setBackgroundColor(Color.WHITE);
                    break;
                case 4 : feel_btn1.setBackgroundColor(Color.WHITE);
                    feel_btn2.setBackgroundColor(Color.WHITE);
                    feel_btn3.setBackgroundColor(Color.WHITE);
                    feel_btn4.setBackgroundColor(Color.YELLOW);
                    feel_btn5.setBackgroundColor(Color.WHITE);
                    break;
                case 5 : feel_btn1.setBackgroundColor(Color.WHITE);
                    feel_btn2.setBackgroundColor(Color.WHITE);
                    feel_btn3.setBackgroundColor(Color.WHITE);
                    feel_btn4.setBackgroundColor(Color.WHITE);
                    feel_btn5.setBackgroundColor(Color.YELLOW);
                    break;
                default : feel_btn1.setBackgroundColor(Color.WHITE);
                    feel_btn2.setBackgroundColor(Color.WHITE);
                    feel_btn3.setBackgroundColor(Color.WHITE);
                    feel_btn4.setBackgroundColor(Color.WHITE);
                    feel_btn5.setBackgroundColor(Color.WHITE);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;

    }

    public String getHistory(){
        String rtnStr="";
        int postNum=0;
        String url = CommonMethod.ipConfig +"/api/loadHistory";

        try{
            String jsonString = new JSONObject()
                    .put("_id", board_seq)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

        }catch(Exception e){
            e.printStackTrace();
        }

        return rtnStr;
    }

    public void onPause() {
        super.onPause();
    }

    // 게시물의 댓글을 읽어오는 함수
    class LoadCmt extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);

// 댓글을 뿌릴 LinearLayout 자식뷰 모두 제거
            comment_layout.removeAllViews();

            try {

// JSONArray, JSONObject 로 받은 데이터 파싱
                JSONArray jsonArray = null;
                jsonArray = new JSONArray(result);

// custom_comment 를 불러오기 위한 객체
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                for(int i=0;i<jsonArray.length();i++){

// custom_comment 의 디자인을 불러와서 사용
                    View customView = layoutInflater.inflate(R.layout.history_comment, null);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String content = jsonObject.optString("context");
                    String crt_dt = jsonObject.optString("publishDate");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    Date pDate = inputFormat.parse(crt_dt);
                    inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                    String newDateForm = inputFormat.format(pDate);

                    ((TextView)customView.findViewById(R.id.cmt_content_tv)).setText(content);
                    ((TextView)customView.findViewById(R.id.cmt_date_tv)).setText(newDateForm);

// 댓글 레이아웃에 custom_comment 의 디자인에 데이터를 담아서 추가
                    comment_layout.addView(customView);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... params) {

            String server_url =  CommonMethod.ipConfig + "/api/loadCmt";


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
                        .appendQueryParameter("feeling_id", board_seq);
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
    }


    public String getTodayHistory(){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String getTime = sformat.format(now);
        String rtnStr="";

        String url = CommonMethod.ipConfig + "/api/loadTodayHistory"; // 글 정보
        try{
            String jsonString = new JSONObject()
                    .put("userId", userId)
                    .put("publishDate", getTime)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();
            Log.d(TAG, String.format("값알아보기 %s", rtnStr));

        }catch(Exception e){
            e.printStackTrace();
        }

        return rtnStr;

    }


}
