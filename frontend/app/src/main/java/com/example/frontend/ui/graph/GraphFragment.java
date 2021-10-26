package com.example.frontend.ui.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.frontend.R;
import com.example.frontend.databinding.FragmentGraphBinding;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphFragment extends Fragment {

    private FragmentGraphBinding binding;
    private LineChart lineChart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.representTextView;
        TextView textView2 = binding.scoreText;
        textView.setText("오늘의 감정 점수");
        textView2.setText("3점");

        ArrayList<Integer> jsonList = new ArrayList<>(); // ArrayList 선언
        ArrayList<String> labelList = new ArrayList<>(); // ArrayList 선언
        lineChart = (LineChart) binding.chart;

        List<Entry> entries = new ArrayList<>();
        lineChart.clear();

//        for (Record record : records) { //values에 데이터를 담는 과정
//            long dateTime = record.getDateTime();
//            float weight = (float) record.getWeight();
//            values.add(new Entry(dateTime, weight));
//        }


        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 3));
        entries.add(new Entry(4, 2));
        entries.add(new Entry(5, 5));
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

        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.enableGridDashedLine(8, 24, 0);
        xAxis.setLabelCount(10, true); //X축의 데이터를 최대 몇개 까지 나타낼지에 대한 설정 5개 force가 true 이면 반드시 보여줌
        xAxis.setAxisMaximum(31);


        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yLAxis.setAxisMaximum(5);
//        yLAxis.setAxisMinimum(1);
        yLAxis.setTextSize(12);
        yRAxis.setDrawLabels(true);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}