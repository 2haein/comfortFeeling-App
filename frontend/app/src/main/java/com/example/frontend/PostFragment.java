package com.example.frontend;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.frontend.callback.SessionCallback;
import com.example.frontend.common.ProfileData;
import com.example.frontend.databinding.FragmentHistoryBinding;
import com.example.frontend.databinding.FragmentPostBinding;
import com.example.frontend.http.CommonMethod;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostFragment extends Fragment {
    public TextView restapi_output;
    public EditText edit_text;
    public Button back_btn;
    public Button submit_btn;
    public ImageView camera_btn;
    public ImageView camera_image;
    public RatingBar feel_rate;
    public ImageView feel_btn1, feel_btn2, feel_btn3, feel_btn4, feel_btn5;
    private Switch commentYN;
    private int comment = 1;
    private SessionCallback sessionCallback = new SessionCallback();
    private int rating;
    private FragmentPostBinding binding;
    private Double lat, lon;
    File file;


    @Override
    public void onResume() {
        super.onResume();
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edit_text = (EditText)root.findViewById(R.id.editTextTextMultiLine);
        feel_btn1 = (ImageView)root.findViewById(R.id.imageView1);
        feel_btn2 = (ImageView)root.findViewById(R.id.imageView2);
        feel_btn3 = (ImageView)root.findViewById(R.id.imageView3);
        feel_btn4 = (ImageView)root.findViewById(R.id.imageView4);
        feel_btn5 = (ImageView)root.findViewById(R.id.imageView5);
        commentYN = (Switch) root.findViewById(R.id.switch1);
        back_btn = (Button)root.findViewById(R.id.back_btn);
        submit_btn = (Button)root.findViewById(R.id.button);


//        File sdcard = Environment.getExternalStorageDirectory();
//        file = new File(sdcard, "capture.jpg");
        camera_btn = (ImageView)root.findViewById(R.id.cameraView);
        camera_image = (ImageView)root.findViewById(R.id.cameraImage);

        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if(bundle != null){
            lat = bundle.getDouble("lat");
            lon = bundle.getDouble("lon");

        }

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }
        });

        feel_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setImageResource(R.drawable.color_emoji1);
                feel_btn2.setImageResource(R.drawable.emotion2);
                feel_btn3.setImageResource(R.drawable.emotion3);
                feel_btn4.setImageResource(R.drawable.emotion4);
                feel_btn5.setImageResource(R.drawable.emotion5);
                rating = 1;
            }
        });
        feel_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setImageResource(R.drawable.emotion1);
                feel_btn2.setImageResource(R.drawable.color_emoji2);
                feel_btn3.setImageResource(R.drawable.emotion3);
                feel_btn4.setImageResource(R.drawable.emotion4);
                feel_btn5.setImageResource(R.drawable.emotion5);
                rating = 2;
            }
        });
        feel_btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setImageResource(R.drawable.emotion1);
                feel_btn2.setImageResource(R.drawable.emotion2);
                feel_btn3.setImageResource(R.drawable.color_emoji3);
                feel_btn4.setImageResource(R.drawable.emotion4);
                feel_btn5.setImageResource(R.drawable.emotion5);
                rating = 3;
            }
        });
        feel_btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setImageResource(R.drawable.emotion1);
                feel_btn2.setImageResource(R.drawable.emotion2);
                feel_btn3.setImageResource(R.drawable.emotion3);
                feel_btn4.setImageResource(R.drawable.color_emoji4);
                feel_btn5.setImageResource(R.drawable.emotion5);
                rating = 4;
            }
        });
        feel_btn5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                feel_btn1.setImageResource(R.drawable.emotion1);
                feel_btn2.setImageResource(R.drawable.emotion2);
                feel_btn3.setImageResource(R.drawable.emotion3);
                feel_btn4.setImageResource(R.drawable.emotion4);
                feel_btn5.setImageResource(R.drawable.color_emoji5);
                rating = 5;
            }
        });

        commentYN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    comment = 1;

                }else{
                    comment = 0;
                }
            }
        });



//        LayerDrawable stars = (LayerDrawable) feel_rate.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.parseColor("#e7c31b"), PorterDuff.Mode.SRC_ATOP); // for filled stars
//        stars.getDrawable(1).setColorFilter(Color.parseColor("#e7c31b"), PorterDuff.Mode.SRC_ATOP); // for half filled stars
//        stars.getDrawable(0).setColorFilter(Color.parseColor("#c2c2c2"), PorterDuff.Mode.SRC_ATOP); // for empty stars

        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(PostFragment.this).commit();
                fragmentManager.popBackStack();
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
                    .put("userId", ProfileData.getUserId())
                    .put("text", edit_text.getText().toString())
                    .put("score", rating)
                    .put("publishDate", getTime)
                    .put("xcoord", lat)
                    .put("ycoord", lon)
                    .put("comment", comment)
                    .toString();

                    //REST API
                    RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
                    networkTask.execute();

                    Toast.makeText(getActivity().getApplicationContext(), "글이 등록되었습니다", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);

                    startActivity(intent);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        return root;

    }

    public void capture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            camera_image.setImageURI(selectedImageUri);

        }

    }




}
