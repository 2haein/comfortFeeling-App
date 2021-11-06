package com.example.frontend.ui.completion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class CompletionFragment extends Fragment{

    final private String TAG = getClass().getSimpleName();
    private @NonNull
    FragmentCompletionBinding binding;

    // 사용할 컴포넌트 선언
    TextView content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;
    ImageView feel_btn1, feel_btn2, feel_btn3, feel_btn4, feel_btn5;

    int tag;
    ArrayList<String> uid = new ArrayList<String>();
    ArrayList<String> postid = new ArrayList<String>();
    ArrayList<String> text = new ArrayList<String>();
    ArrayList<String> pubDate = new ArrayList<String>();
    ArrayList<Integer> oscore = new ArrayList<Integer>();


    String board_seq;
    String userId;
    HomeFragment homeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeFragment = new HomeFragment();
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
        comment_et = (EditText) root.findViewById(R.id.comment_et);
        reg_button = (Button) root.findViewById(R.id.reg_button);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegCmt regCmt = new RegCmt();
                regCmt.execute(userId, comment_et.getText().toString(), board_seq);

            }
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            tag = bundle.getInt("key"); //마커 눌렀을 때 태그(userId)가져옴
            board_seq = bundle.getString("seq");
            System.out.println("seq=" + board_seq); //확인
        }
        Log.i(TAG, "tag값" + tag);

        //일단 모든 글 파싱
        String allPostInfo = getTodayAllHistory();
        Log.i(TAG, "AllPostInfo값" + allPostInfo);
        try {
            JSONArray jsonArray = new JSONArray(allPostInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String uuid = jsonObject.optString("userId");
                String idid = jsonObject.optString("id");
                String content = jsonObject.optString("text");
                String publishDate = jsonObject.optString("publishDate");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date pDate = inputFormat.parse(publishDate);
                inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                String newDateForm = inputFormat.format(pDate);
                int score = Integer.parseInt(jsonObject.optString("score"));

                uid.add(uuid);
                postid.add(idid);
                text.add(content);
                pubDate.add(newDateForm);
                oscore.add(score);
                Log.i(TAG, String.format("All_uid: %s", uid.get(i)));
                Log.i(TAG, String.format("All_id: %s", postid.get(i)));
                Log.i(TAG, String.format("All_content: %s", text.get(i)));
                Log.i(TAG, String.format("All_pubDate: %s", pubDate.get(i)));
                Log.i(TAG, String.format("All_score: %d", oscore.get(i)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //글 주인이 자신의 글을 보려고 할 때 실행
       if(tag==0){
           try {
               // 결과값이 JSONArray 형태로 넘어오기 때문에
               // JSONArray, JSONObject 를 사용해서 파싱
               JSONObject jsonObject = new JSONObject(getTodayHistory());
               // Database 의 데이터들을 변수로 저장한 후 해당 TextView 에 데이터 입력
               board_seq = jsonObject.optString("id");
               String content = jsonObject.optString("text");
               String publishDate = jsonObject.optString("publishDate");
               SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
               Date pDate = inputFormat.parse(publishDate);
               inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
               String newDateForm = inputFormat.format(pDate);
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
               LoadCmt loadCmt = new LoadCmt();
               loadCmt.execute(board_seq);


           } catch (ParseException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }
       else {
           String temp = Integer.toString(tag);
           int cnt = uid.indexOf(temp);
           if (cnt != -1) { //찾는 값이 존재하면
               try {
                   board_seq = postid.get(cnt);
                   content_tv.setText(text.get(cnt));
                   date_tv.setText(pubDate.get(cnt));

                   switch (oscore.get(cnt)) {
                       case 1:
                           feel_btn1.setImageResource(R.drawable.color_emoji1);
                           break;
                       case 2:
                           feel_btn2.setImageResource(R.drawable.color_emoji2);
                           break;
                       case 3:
                           feel_btn3.setImageResource(R.drawable.color_emoji3);
                           break;
                       case 4:
                           feel_btn4.setImageResource(R.drawable.color_emoji4);
                           break;
                       case 5:
                           feel_btn5.setImageResource(R.drawable.color_emoji5);
                           break;
                       default:
                           feel_btn1.setBackgroundColor(Color.WHITE);
                           feel_btn2.setBackgroundColor(Color.WHITE);
                           feel_btn3.setBackgroundColor(Color.WHITE);
                           feel_btn4.setBackgroundColor(Color.WHITE);
                           feel_btn5.setBackgroundColor(Color.WHITE);
                           break;
                   }

                   LoadCmt loadCmt = new LoadCmt();
                   loadCmt.execute(board_seq);

               } catch (Exception e) {
                   e.printStackTrace();
               }


           }
       }

        Log.i(TAG, "board값" + board_seq);
        return root;

    }


    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void deleteCmtDialog(String _id) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("댓글을 삭제하시겠습니까?")
                .setMessage("댓글을 삭제합니다")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteCmt(_id);
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

// 댓글을 뿌릴 LinearLayout 자식뷰 모두 제거F
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

                    String _id = jsonObject.optString("id");
                    String feeling_id = jsonObject.optString("feeling_id");
                    String content = jsonObject.optString("context");
                    String crt_dt = jsonObject.optString("publishDate");
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    Date pDate = inputFormat.parse(crt_dt);
                    inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                    String newDateForm = inputFormat.format(pDate);

                    ((TextView)customView.findViewById(R.id.cmt_content_tv)).setText(content);
                    ((TextView)customView.findViewById(R.id.cmt_date_tv)).setText(newDateForm);
                    if(jsonObject.optString("userId").equals(userId)){
                        customView.findViewById(R.id.cmt_remove_btn).setVisibility(View.VISIBLE);
                        TextView removeCmt_btn = customView.findViewById(R.id.cmt_remove_btn);
                        removeCmt_btn.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View view){
                                deleteCmtDialog(_id);
                            }
                        });
                    }
                    TextView textView_btn;
                    textView_btn = customView.findViewById(R.id.cmt_report_btn);
                    textView_btn.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view) {
                            if(checkReportCmt(_id, feeling_id) == 0) {
                                reportCmt(jsonObject);
                                Toast.makeText(view.getContext(), "신고 완료", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(view.getContext(), "이미 신고하셨습니다", Toast.LENGTH_LONG).show();

                        }
                    });

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

    // 사용자 당일의 기록 가져오기
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
            Log.i(TAG, String.format("getTodayHistory: %s", rtnStr));

        }catch(Exception e){
            e.printStackTrace();
        }

        return rtnStr;

    }

    // 오늘 존재하는 모든 감정기록 가져오기(다른 사람 것도)
    public String getTodayAllHistory(){
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String getTime = sformat.format(now);
        String rtnStr="";

        String url = CommonMethod.ipConfig + "/api/loadData"; // 글 정보
        try{
            String jsonString = new JSONObject()
                    .put("publishDate", getTime)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

            Log.i(TAG, String.format("getTodayAllHistory: %s", rtnStr));


        }catch(Exception e){
            e.printStackTrace();
        }

        return rtnStr;

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
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    response="success";
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

    public void deleteCmt(String _id) {
        String url = CommonMethod.ipConfig + "/api/deleteCmt";
        try {
            String jsonString = new JSONObject()
                    .put("comment_id", _id)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            networkTask.execute();

            LoadCmt loadCmt = new LoadCmt();
            loadCmt.execute(board_seq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //댓글 신고 중복 확인
    public int checkReportCmt(String _id, String feeling_id) {
        String url = CommonMethod.ipConfig +"/api/checkReportCmt";
        int count = 0;
        String rtnStr = "0";
        try{
            String jsonString = new JSONObject()
                    .put("comment_id", _id)
                    .put("feeling_id", feeling_id)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

            //리스트 길이 확인
            count = Integer.parseInt(rtnStr);

        }catch(Exception e){
            e.printStackTrace();
        }

        return count;
    }

    //댓글 신고
    public void reportCmt(JSONObject jsonObject){
        String url = CommonMethod.ipConfig +"/api/reportCmt";
        try{
            String jsonString = new JSONObject()
                    .put("feeling_id", jsonObject.optString("feeling_id"))
                    .put("comment_id", jsonObject.optString("id"))
                    .put("userId", userId)
                    .put("report", 1)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            networkTask.execute();

            LoadCmt loadCmt = new LoadCmt();
            loadCmt.execute(board_seq);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}