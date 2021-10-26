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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
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
        textView.setText("이 날의 대표 감정");


        lineChart = (LineChart) binding.chart;

        List<Entry> entries = new ArrayList<>();


//        for (Record record : records) { //values에 데이터를 담는 과정
//            long dateTime = record.getDateTime();
//            float weight = (float) record.getWeight();
//            values.add(new Entry(dateTime, weight));
//        }


        entries.add(new Entry(1, 1));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 0));
        entries.add(new Entry(4, 4));
        entries.add(new Entry(5, 3));
        LineDataSet lineDataSet = new LineDataSet(entries, "감정 점수");
        lineDataSet.setLineWidth(3);
        lineDataSet.setCircleRadius(10);
        lineDataSet.setCircleColor(Color.parseColor("#FFBB86FC"));
        lineDataSet.setCircleColorHole(Color.parseColor("#FF6200EE"));
        lineDataSet.setColor(Color.parseColor("#FFBB86FC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(5);
        xAxis.enableGridDashedLine(8, 24, 0);
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(1500, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

        MyMarkerView marker = new MyMarkerView(this, R.layout.markerviewtext);
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);

        System.out.println("marker123123"+ marker.getContext());

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