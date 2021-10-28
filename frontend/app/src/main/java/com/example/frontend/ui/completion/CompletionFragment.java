
package com.example.frontend.ui.completion;
/*
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.common.ProfileData;
import com.example.frontend.databinding.FragmentCompletionBinding;
import com.example.frontend.databinding.FragmentDetailBinding;
import com.example.frontend.databinding.FragmentHistoryBinding;
import com.example.frontend.ui.history.DetailFragment;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompletionFragment extends Fragment {

    final private String TAG = getClass().getSimpleName();
    private @NonNull FragmentCompletionBinding binding;
    private MapView mapView;
    ViewGroup mapViewContainer;

    // 사용할 컴포넌트 선언
    TextView content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;
    ImageView feel_btn1, feel_btn2, feel_btn3, feel_btn4, feel_btn5;

    String board_seq;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletionBinding.inflate(inflater, container, false);
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


/*
        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if(bundle != null){
            board_seq = bundle.getString("seq");
            System.out.println("seq=" + board_seq); //확인
        }

        try {
            // 결과값이 JSONArray 형태로 넘어오기 때문에
            // JSONArray, JSONObject 를 사용해서 파싱
            JSONObject jsonObject = null;
            jsonObject = new JSONObject();

            // Database 의 데이터들을 변수로 저장한 후 해당 TextView 에 데이터 입력
            String content = jsonObject.optString("text");
            String publishDate = jsonObject.optString("publishDate");
            double xcoord = Double.parseDouble(jsonObject.optString("xcoord"));
            double ycoord = Double.parseDouble(jsonObject.optString("ycoord"));
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date pDate = inputFormat.parse(publishDate);
            inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            String newDateForm = inputFormat.format(pDate);

            int score = Integer.parseInt(jsonObject.optString("score"));

            content_tv.setText(content);
            date_tv.setText(newDateForm);


        return root;
    }

    public void onPause() {
        super.onPause();
        mapViewContainer.removeAllViews();
    }

*/





