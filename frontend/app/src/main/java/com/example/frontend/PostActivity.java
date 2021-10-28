package com.example.frontend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.callback.SessionCallback;
import com.example.frontend.http.CommonMethod;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {
    public TextView restapi_output;
    public EditText edit_text;
    public Button back_btn;
    public Button submit_btn;
    public RatingBar feel_rate;
    public ImageView feel_btn1, feel_btn2, feel_btn3, feel_btn4, feel_btn5;
    private SessionCallback sessionCallback = new SessionCallback();
    private int rating;


    @Override
    protected void onResume() {
        super.onResume();
        //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        edit_text = (EditText)findViewById(R.id.editTextTextMultiLine);
//        feel_rate = (RatingBar)findViewById(R.id.ratingBar);
        feel_btn1 = (ImageView)findViewById(R.id.imageView1);
        feel_btn2 = (ImageView)findViewById(R.id.imageView2);
        feel_btn3 = (ImageView)findViewById(R.id.imageView3);
        feel_btn4 = (ImageView)findViewById(R.id.imageView4);
        feel_btn5 = (ImageView)findViewById(R.id.imageView5);
        back_btn = (Button)findViewById(R.id.back_btn);
        submit_btn = (Button)findViewById(R.id.button);

        Intent intent = getIntent();

        feel_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setBackgroundColor(Color.YELLOW);
                feel_btn2.setBackgroundColor(Color.WHITE);
                feel_btn3.setBackgroundColor(Color.WHITE);
                feel_btn4.setBackgroundColor(Color.WHITE);
                feel_btn5.setBackgroundColor(Color.WHITE);
                rating = 1;
            }
        });
        feel_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setBackgroundColor(Color.WHITE);
                feel_btn2.setBackgroundColor(Color.YELLOW);
                feel_btn3.setBackgroundColor(Color.WHITE);
                feel_btn4.setBackgroundColor(Color.WHITE);
                feel_btn5.setBackgroundColor(Color.WHITE);
                rating = 2;
            }
        });
        feel_btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setBackgroundColor(Color.WHITE);
                feel_btn2.setBackgroundColor(Color.WHITE);
                feel_btn3.setBackgroundColor(Color.YELLOW);
                feel_btn4.setBackgroundColor(Color.WHITE);
                feel_btn5.setBackgroundColor(Color.WHITE);
                rating = 3;
            }
        });
        feel_btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setBackgroundColor(Color.WHITE);
                feel_btn2.setBackgroundColor(Color.WHITE);
                feel_btn3.setBackgroundColor(Color.WHITE);
                feel_btn4.setBackgroundColor(Color.YELLOW);
                feel_btn5.setBackgroundColor(Color.WHITE);
                rating = 4;
            }
        });
        feel_btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setBackgroundColor(Color.WHITE);
                feel_btn2.setBackgroundColor(Color.WHITE);
                feel_btn3.setBackgroundColor(Color.WHITE);
                feel_btn4.setBackgroundColor(Color.WHITE);
                feel_btn5.setBackgroundColor(Color.YELLOW);
                rating = 5;
            }
        });

//        LayerDrawable stars = (LayerDrawable) feel_rate.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.parseColor("#e7c31b"), PorterDuff.Mode.SRC_ATOP); // for filled stars
//        stars.getDrawable(1).setColorFilter(Color.parseColor("#e7c31b"), PorterDuff.Mode.SRC_ATOP); // for half filled stars
//        stars.getDrawable(0).setColorFilter(Color.parseColor("#c2c2c2"), PorterDuff.Mode.SRC_ATOP); // for empty stars

        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REST API 주소
                String url = CommonMethod.ipConfig+ "/api/insert";
                SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                Date now = new Date();
                String getTime = sformat.format(now);

                try{
                    String jsonString = new JSONObject()
                    .put("userId", intent.getStringExtra("userId"))
                    .put("text", edit_text.getText().toString())
                    .put("score", rating)
                    .put("publishDate", getTime)
                    .put("xcoord", intent.getExtras().getDouble("lat"))
                    .put("ycoord", intent.getExtras().getDouble("lon"))
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

                finish();
            }
        });
    }







}
