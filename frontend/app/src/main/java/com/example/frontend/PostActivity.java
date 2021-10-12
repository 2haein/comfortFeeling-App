package com.example.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.callback.SessionCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                    RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
                    networkTask.execute();

                    Toast.makeText(getApplicationContext(), "글이 등록되었습니다", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(intent);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }






}
