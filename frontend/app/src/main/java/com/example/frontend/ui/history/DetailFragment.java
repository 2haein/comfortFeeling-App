package com.example.frontend.ui.history;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.MainActivity;
import com.example.frontend.PostFragment;
import com.example.frontend.R;
import com.example.frontend.RequestHttpURLConnection;
import com.example.frontend.common.ProfileData;
import com.example.frontend.databinding.FragmentDetailBinding;
import com.example.frontend.http.CommonMethod;

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

public class DetailFragment extends Fragment {

    // 로그에 사용할 TAG
    final private String TAG = getClass().getSimpleName();
    private FragmentDetailBinding binding;

    /**
     * KAKAO MAP 선언
     * */
    private MapView mapView;
    ViewGroup mapViewContainer;

    // 사용할 컴포넌트 선언
    TextView content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button, delete_btn;
    ImageView feel_btn1, feel_btn2, feel_btn3, feel_btn4, feel_btn5;

    String board_seq;
    String userId;
    String publishDate;
    String newDateForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity activity = (MainActivity) getActivity();


        userId = ProfileData.getUserId();

        // 컴포넌트 초기화
        content_tv = (TextView) root.findViewById(R.id.content_tv);
        date_tv = (TextView) root.findViewById(R.id.date_tv);
        feel_btn1 = (ImageView)root.findViewById(R.id.imageView1);
        feel_btn2 = (ImageView)root.findViewById(R.id.imageView2);
        feel_btn3 = (ImageView)root.findViewById(R.id.imageView3);
        feel_btn4 = (ImageView)root.findViewById(R.id.imageView4);
        feel_btn5 = (ImageView)root.findViewById(R.id.imageView5);
        comment_layout = (LinearLayout) root.findViewById(R.id.comment_layout);
        comment_et = (EditText) root.findViewById(R.id.comment_et);
        reg_button = (Button) root.findViewById(R.id.reg_button);
        delete_btn = (Button) root.findViewById(R.id.delete_btn);

        // 등록하기 버튼을 눌렀을 때 댓글 등록 함수 호출
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegCmt regCmt = new RegCmt();
                regCmt.execute(userId, comment_et.getText().toString(), board_seq);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if(bundle != null){
            board_seq = bundle.getString("seq");
            System.out.println("seq=" + board_seq); //확인
        }
        Log.i(TAG, "board값" + board_seq);
        try {
            // 결과값이 JSONArray 형태로 넘어오기 때문에
            // JSONArray, JSONObject 를 사용해서 파싱
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(getHistory());

            // Database 의 데이터들을 변수로 저장한 후 해당 TextView 에 데이터 입력
            String content = jsonObject.optString("text");
            publishDate = jsonObject.optString("publishDate");
            double xcoord = Double.parseDouble(jsonObject.optString("xcoord"));
            double ycoord = Double.parseDouble(jsonObject.optString("ycoord"));
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date pDate = inputFormat.parse(publishDate);
            inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            newDateForm = inputFormat.format(pDate);

            int score = Integer.parseInt(jsonObject.optString("score"));

            content_tv.setText(content);
            date_tv.setText(newDateForm);

            switch (score) {
                case 1: feel_btn1.setImageResource(R.drawable.color_emoji1);
                    break;
                case 2 : feel_btn2.setImageResource(R.drawable.color_emoji2);
                    break;
                case 3 : feel_btn3.setImageResource(R.drawable.color_emoji3);
                    break;
                case 4 : feel_btn4.setImageResource(R.drawable.color_emoji4);
                    break;
                case 5 : feel_btn5.setImageResource(R.drawable.color_emoji5);
                    break;
                default : feel_btn1.setBackgroundColor(Color.WHITE);
                    feel_btn2.setBackgroundColor(Color.WHITE);
                    feel_btn3.setBackgroundColor(Color.WHITE);
                    feel_btn4.setBackgroundColor(Color.WHITE);
                    feel_btn5.setBackgroundColor(Color.WHITE);
                    break;
            }

            mapView = new MapView(getActivity());
            mapViewContainer = (ViewGroup) binding.mapView;
            mapViewContainer.addView(mapView);

            // 중심점 변경 + 줌 레벨 변경
            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(xcoord, ycoord), 3, true);


            // 해당 게시물에 대한 댓글 불러오는 함수 호출, 파라미터로 게시물 번호 넘김
            LoadCmt loadCmt = new LoadCmt();
            loadCmt.execute(board_seq);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return root;
    }

    public void onPause() {
        super.onPause();
        mapViewContainer.removeAllViews();
    }

    //당일 글 갯수
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

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("글을 삭제하시겠습니까?")
                .setMessage("나의 히스토리 글을 삭제합니다")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteHistory();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        HistoryFragment historyFragment = new HistoryFragment();
                        transaction.replace(R.id.detail_fragment, historyFragment); //프레임 레이아웃에서 detailFragment로 변경
                        transaction.addToBackStack(null);
                        transaction.commit(); //저장해라 commit


                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });

        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
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
                comment_et.setText("");

            // 소프트 키보드 숨김처리
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);

            // 토스트메시지 출력
                Toast.makeText(getActivity(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();

// 댓글 불러오는 함수 호출
                LoadCmt loadCmt = new LoadCmt();
                loadCmt.execute(board_seq);
            }else
            {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
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

    //댓글 삭제
    public void deleteHistory(){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date pDate = null;
        String nDateForm;
        try {
            pDate = inputFormat.parse(publishDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        inputFormat.applyPattern("yyyy-MM-dd");
        nDateForm = inputFormat.format(pDate);

        String url = CommonMethod.ipConfig +"/api/remove";
        try{
            String jsonString = new JSONObject()
                    .put("userId", userId)
                    .put("publishDate", nDateForm)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            networkTask.execute();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}