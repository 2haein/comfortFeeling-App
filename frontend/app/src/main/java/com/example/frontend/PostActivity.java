package com.example.frontend;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.callback.SessionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class PostActivity extends AppCompatActivity {
    public TextView restapi_output;
    public EditText edit_text;
    public Button submit_btn;
    public RatingBar feel_rate;
    private SessionCallback sessionCallback = new SessionCallback();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        restapi_output = (TextView)findViewById(R.id.restapi_output);
        edit_text = (EditText)findViewById(R.id.editTextTextMultiLine);
        feel_rate = (RatingBar)findViewById(R.id.ratingBar);
        submit_btn = (Button)findViewById(R.id.button);

        int rating = (int) feel_rate.getRating();
        Intent intent = getIntent();


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REST API 주소
                String url = "http://localhost:8080/api/insert";
                //String url = "http://본인IP주소:8080/api/insert";

                SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                Date now = new Date();
                String getTime = sformat.format(now);

                try{
                    String jsonString = new JSONObject()
                    .put("userId", intent.getStringExtra("userId"))
                    .put("text", edit_text.getText().toString())
                    .put("score", rating)
                    .put("publishDate", getTime)
                    .put("xcoord", intent.getExtras().getFloat("lat"))
                    .put("ycoord", intent.getExtras().getFloat("lon"))
                    .toString();

                    //REST API
                    NetworkAsyncTask networkTask = new NetworkAsyncTask(url, jsonString);
                    networkTask.execute();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public class NetworkAsyncTask extends AsyncTask<Void, Void, String> {

        private String url;
        private String jsonValue;

        public NetworkAsyncTask(String url, String jsonValue) {

            this.url = url;
            this.jsonValue = jsonValue;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.sendREST(url, jsonValue); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            restapi_output.setText(s);
        }

    }




}
