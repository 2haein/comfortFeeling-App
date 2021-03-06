package com.comfortfeeling.frontend;

import static android.app.Activity.RESULT_OK;
import static android.service.controls.ControlsProviderService.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.comfortfeeling.frontend.callback.SessionCallback;
import com.comfortfeeling.frontend.common.ProfileData;
import com.comfortfeeling.frontend.databinding.FragmentHistoryBinding;
import com.comfortfeeling.frontend.databinding.FragmentPostBinding;
import com.comfortfeeling.frontend.http.CommonMethod;

import org.json.JSONObject;

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


        /*camera_btn = (ImageView)root.findViewById(R.id.cameraView);
        camera_image = (ImageView)root.findViewById(R.id.cameraImage);*/

        Bundle bundle = getArguments();  //?????? ??????. getArguments() ???????????? ??????.

        if(bundle != null){
            lat = bundle.getDouble("lat");
            lon = bundle.getDouble("lon");

        }

        /*camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 6.0 ??????????????? ????????? ???????????? ?????? ?????? ??? ?????? ??????
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "?????? ?????? ??????");
                        capture();
                    } else {
                        Log.d(TAG, "?????? ?????? ??????");
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
            }
        });*/


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
                //REST API ??????
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
                    ProfileData.setMapFlag(true);
                    Toast.makeText(getActivity().getApplicationContext(), "?????? ?????????????????????", Toast.LENGTH_LONG).show();

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
            //camera_image.setImageURI(selectedImageUri);

        }

    }

}
