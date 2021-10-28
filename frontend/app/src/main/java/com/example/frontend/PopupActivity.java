package com.example.frontend;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.http.CommonMethod;
import com.example.frontend.ui.history.DetailFragment;

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

public class PopupActivity extends AppCompatActivity {
    public Button back_btn;
    public Button submit_btn;
    public EditText edit_comment;
    LinearLayout comment_layout;
    public TextView date,score, emotion_text;

    String board_seq;
    String userId;

    final private String TAG = getClass().getSimpleName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        back_btn = (Button)findViewById(R.id.back_btn);
        submit_btn = (Button)findViewById(R.id.submit_btn);
        date = (TextView)findViewById(R.id.textView);
        score = (TextView)findViewById(R.id.textView2);
        emotion_text = (TextView)findViewById(R.id.textView3);
        edit_comment = (EditText)findViewById(R.id.editTextTextMultiLine);
        comment_layout = findViewById(R.id.comment_layout);




        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        try {
            // 결과값이 JSONArray 형태로 넘어오기 때문에
            // JSONArray, JSONObject 를 사용해서 파싱
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(getHistory());

            // Database 의 데이터들을 변수로 저장한 후 해당 TextView 에 데이터 입력
            String content = jsonObject.optString("text");
            String publishDate = jsonObject.optString("publishDate");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date pDate = inputFormat.parse(publishDate);
            inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            String newDateForm = inputFormat.format(pDate);


            int score_val = Integer.parseInt(jsonObject.optString("score"));

            emotion_text.setText(content);
            score.setText(score_val);
            date.setText(newDateForm);

            // 해당 게시물에 대한 댓글 불러오는 함수 호출, 파라미터로 게시물 번호 넘김
            LoadCmt loadCmt = new LoadCmt();
            loadCmt.execute(board_seq);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //댓글등록 - 후에 listview로 띄울 예정
        submit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = CommonMethod.ipConfig;
                Date now = new Date();

                try{
                    String jsonString = new JSONObject()
                            .put("comment", edit_comment.getText().toString())
                            .toString();

                    RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
                    networkTask.execute();
                    Toast.makeText(getApplicationContext(), "댓글등록 성공!", Toast.LENGTH_LONG).show();

                }catch(Exception e){
                    e.printStackTrace();
                }

            }


        });

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
                LayoutInflater layoutInflater = LayoutInflater.from(PopupActivity.this);

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

    // 댓글을 등록하는 함수
    class RegCmt extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);

            // 결과값이 성공으로 나오면
            if(result.equals("success")){

                //댓글 입력창의 글자는 공백으로 만듦
                edit_comment.setText("");

                // 소프트 키보드 숨김처리
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_comment.getWindowToken(), 0);

                // 토스트메시지 출력
                Toast.makeText(PopupActivity.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();

// 댓글 불러오는 함수 호출
                LoadCmt loadCmt = new LoadCmt();
                loadCmt.execute(board_seq);
            }else
            {
                Toast.makeText(PopupActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String userid = params[0];
            String content = params[1];
            String board_seq = params[2];

            SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date now = new Date();
            String getTime = sformat.format(now);

            String server_url =  CommonMethod.ipConfig + "/api/addCmt";


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
                        .appendQueryParameter("userId", userid)
                        .appendQueryParameter("context", content)
                        .appendQueryParameter("feeling_id", board_seq)
                        .appendQueryParameter("publishDate", getTime)
                        .appendQueryParameter("show", "1");
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



}
