package com.example.frontend;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.Random;

public class PostActivity extends AppCompatActivity {
    public TextView restapi_output;
    public EditText edit_text;
    public Button submit_btn;
    public RatingBar feel_rate;
    public static String userId;
    public static String text;
    public static Date publishDate;
    public static int score;
    public static float xcoord, ycoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        restapi_output = (TextView)findViewById(R.id.restapi_output);
        edit_text = (EditText)findViewById(R.id.editTextTextMultiLine);
        feel_rate = (RatingBar)findViewById(R.id.ratingBar);
        submit_btn = (Button)findViewById(R.id.button);

        int rating = (int) feel_rate.getRating();
        Intent secondIntent = getIntent();
        double lat = secondIntent.getExtras().getDouble("lat");
        double lon = secondIntent.getExtras().getDouble("lon");


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId= String.valueOf(new Random());
                text=edit_text.getText().toString();
                score=rating;
                publishDate= new Date();
                xcoord=(float)lat;
                ycoord=(float)lon;
                //REST API 주소
                String url = "http://192.168.0.32:8080/api/insert";
                //String url = "http://본인IP주소:8080/api/insert";

                //AsyncTask
                NetworkAsyncTask networkTask = new NetworkAsyncTask(url, null);
                networkTask.execute();
                

            }
        });
    }

    public class NetworkAsyncTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkAsyncTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

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
