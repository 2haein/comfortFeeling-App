package com.example.frontend.ui.graph;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.frontend.LoginActivity;
import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.databinding.FragmentGraphBinding;
import com.example.frontend.http.CommonMethod;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphFragment extends Fragment {

    private FragmentGraphBinding binding;
    private LineChart lineChart;
    private String userId;
    private String todayScore = "0";
    private String todayDate;
    private String monthDate;
    private List<HashMap> arrayDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = (LineChart) binding.chart;
        List<Entry> entries = new ArrayList<>();
        lineChart.clear();

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        final TextView textView = binding.representTextView;
        TextView textView2 = binding.scoreText;
        textView2.setText("아직 오늘의 감정이 기록되지 않았습니다!");
        TextView dateView = binding.DateView;
        MainActivity activity = (MainActivity) getActivity();
        userId = activity.getUserId();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = sdf2.format(date);

        /**
         * 그래프 API1: Score 점수 받아오기
         * */
        textView.setText("오늘의 감정 점수");
        if(userId != null){
            todayScore = getTodayScore(userId, todayDate);
        }
        if(todayScore == "0" || todayScore == null) {
            textView2.setText("아직 오늘의 감정이 기록되지 않았습니다!");
        } else {
            textView2.setText(todayScore+"점");
            textView2.setTextSize(20);
            textView2.setTextColor(Color.parseColor("#ff8d07"));
        }

        /**
         * 그래프 API2: 월일별 Score 점수 받아오기
         * @param: String userId
         * @param: String month (yyyy-MM)
         * */
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM");
        monthDate = sdf3.format(date);
        String MonthScore   ="init";
        if(userId != null){
            MonthScore = getMonthScore(userId, monthDate);
        }
        /**
         * Input String
         * [
         *    {
         *       "userName": "sandeep",
         *       "age": 30
         *    }
         * ]
         * Simple Way to Convert String to JSON
        * */
        JSONArray jsonArr = null;
        if(MonthScore != null) {
            try {
                jsonArr = new JSONArray(MonthScore);

                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonMonthObj = null;
                    try {
                        jsonMonthObj = jsonArr.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Iterator iterator = jsonMonthObj.keys(); // key값들을 모두 얻어옴.
                    String dayScore = "";
                    String day = "";
                    while (iterator.hasNext()) {
                        day = iterator.next().toString();
                        try {
                            dayScore = jsonMonthObj.getString(day);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    entries.add(new Entry(Integer.parseInt(day), Integer.parseInt(dayScore)));
                    System.out.println(jsonMonthObj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        arrayDate = MonthScore;
//        if(TodayScore== null || TodayScore == "0") {
//            textView2.setText("아직 오늘의 감정이 기록되지 않았습니다!");
//        } else {
//            textView2.setText(TodayScore+"점");
//            textView2.setTextSize(20);
//            textView2.setTextColor(Color.parseColor("#ff8d07"));
//        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        String getTime = sdf.format(date);
        dateView.setText(getTime);


//        for (Record record : records) { //values에 데이터를 담는 과정
//            long dateTime = record.getDateTime();
//            float weight = (float) record.getWeight();
//            values.add(new Entry(dateTime, weight));
//        }


        entries.add(new Entry(0, 0));
//        entries.add(new Entry(2, 2));
//        entries.add(new Entry(3, 3));
//        entries.add(new Entry(4, 2));
//        entries.add(new Entry(5, 5));
        LineDataSet lineDataSet = new LineDataSet(entries, "감정 점수");
        lineDataSet.setLineWidth(3);
        lineDataSet.setCircleRadius(8);
        lineDataSet.setCircleColor(Color.parseColor("#FFBB86FC"));
        lineDataSet.setCircleColorHole(Color.parseColor("#FF6200EE"));
        lineDataSet.setColor(Color.parseColor("#FFBB86FC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);
        //to make the smooth line as the graph is adrapt change so smooth curve
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.setCubicIntensity(0.2f);

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisMinimum(1);
        xAxis.setGranularity(1.0f);
        xAxis.enableGridDashedLine(8, 24, 0);
        xAxis.setLabelCount(10, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis.setAxisMaximum(31);


        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yLAxis.setAxisMaximum(5);
        yLAxis.setAxisMinimum(0);
        yLAxis.setTextSize(12);
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        yLAxis.setGranularity(1.0f);
        yLAxis.setGranularityEnabled(true);

        Description description = new Description();
        description.setText("(일)/Day");
        description.setTextSize(10);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(1500, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

        lineChart.setData(lineData);
        MyMarkerView marker = new MyMarkerView(this, R.layout.markerviewtext);

//        MyMarkerView marker2 = new MyMarkerView(this, R.layout.fragment_graph);
        marker.setChartView(lineChart);
//        marker2.setChartView(lineChart);
        lineChart.setMarker(marker);
//        lineChart.setMarker(marker2);


//        CandleEntry ce = null;
//        textView2.setText((int) ce.getY());

        return root;
    }

    // 서버와 연동하기
    public String getTodayScore(String userId, String todayDate) {
        Log.w("graphFragment","오늘의 감정 기록 점수 가져오는 중");
        String result ="";
        try {
            Log.w("앱에서 보낸값",userId+", "+todayDate);
            String api = "/api/graph";
            GraphFragment.CustomTask task = new GraphFragment.CustomTask();
            result = task.execute(api,userId,todayDate).get();

            Log.w("받은값",result);


        } catch (Exception e) {
            Log.w("감정 기록 점수 에러", e);
        }
        return result;
    }

    // 서버와 연동하기
    public String getMonthScore(String userId, String monthDate) {
        Log.w("graphFragment","월 스코어 가져오는 중");
        String result ="";
        try {
            Log.w("앱에서 보낸값",userId+", "+monthDate);
            String api = "/api/graphMonth";
            GraphFragment.CustomTask task = new GraphFragment.CustomTask();
            result = task.execute(api,userId,monthDate).get();

            Log.w("받은값",result);


        } catch (Exception e) {
            Log.w("감정 월별 점수 에러", e);
        }
        return result;
    }


    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        // doInBackground의 매개변수 값이 여러개일 경우를 위해 배열로
        protected String doInBackground(String... strings) {
            try {
                String str;

                URL url = new URL( CommonMethod.ipConfig+strings[0]);  // 어떤 서버에 요청할지(localhost 안됨.)
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                if(strings[0]=="/api/graph") {
                    sendMsg = "userId=" + strings[1] + "&publishDate=" + strings[2];
                } else {
                    sendMsg = "userId=" + strings[1] + "&month=" + strings[2];
                }
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();
                Log.i("통신 중", "test");
                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");

                    BufferedReader reader = new BufferedReader(tmp);

                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("통신 결과", receiveMsg);
                } else {    // 통신이 실패한 이유를 찍기위한 로그
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                    return null;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 서버에서 보낸 값을 리턴합니다.
            return receiveMsg;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}