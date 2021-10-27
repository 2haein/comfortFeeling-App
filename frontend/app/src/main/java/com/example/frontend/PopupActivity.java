package com.example.frontend;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.http.CommonMethod;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PopupActivity extends AppCompatActivity {
    public Button back_btn;
    public Button submit_btn;
    public EditText edit_comment;
    public TextView date,score, emotion_text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        back_btn = (Button)findViewById(R.id.back_btn);
        submit_btn = (Button)findViewById(R.id.submit_btn);
        edit_comment = (EditText)findViewById(R.id.editTextTextMultiLine);



        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }

        });

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




}
