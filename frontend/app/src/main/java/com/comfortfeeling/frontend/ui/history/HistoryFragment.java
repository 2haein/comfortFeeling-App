package com.comfortfeeling.frontend.ui.history;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.comfortfeeling.frontend.MainActivity;
import com.comfortfeeling.frontend.R;
import com.comfortfeeling.frontend.RequestHttpURLConnection;
import com.comfortfeeling.frontend.common.ProfileData;
import com.comfortfeeling.frontend.databinding.FragmentHistoryBinding;
import com.comfortfeeling.frontend.http.CommonMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryFragment extends Fragment {

    // 로그에 사용할 TAG 변수
    final private String TAG = getClass().getSimpleName();
    private FragmentHistoryBinding binding;


    // 사용할 컴포넌트 선언
    ListView listView;
    private String userId;

    // 리스트뷰에 사용할 내용 배열
    ArrayList<String> titleList = new ArrayList<>();

    // 리스트뷰에 사용할 날짜 배열
    ArrayList<String> dateList = new ArrayList<>();

    // 게시물 번호를 담기 위한 배열
    ArrayList<String> seqList = new ArrayList<>();

    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 컴포넌트 초기화

        listView = (ListView) root.findViewById(R.id.listView);

        HistoryListAdapter adapter = new HistoryListAdapter();
        listView.setAdapter(adapter);

        // 배열들 초기화
        titleList.clear();
        dateList.clear();
        seqList.clear();

        userId = ProfileData.getUserId();

        try {

            // 결과물이 JSONArray 형태로 넘어오기 때문에 파싱
            JSONArray jsonArray = new JSONArray(getHistoryList());

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String title = jsonObject.optString("text");
                String publishDate = jsonObject.optString("publishDate");
                int score = Integer.parseInt(jsonObject.optString("score"));
                String seq = jsonObject.optString("id");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date pDate = inputFormat.parse(publishDate);
                inputFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
                String newDateForm = inputFormat.format(pDate);

                // title, seq 값을 변수로 받아서 배열에 추가
                titleList.add(title);
                //dateList.add(publishDate);
                seqList.add(seq);

                switch (score) {
                    case 1 : adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.color_emoji1), seq, title, "감정점수 = "+score, "  등록일 = "+newDateForm);
                            break;
                    case 2 : adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.color_emoji2), seq, title, "감정점수 = "+score, "  등록일 = "+newDateForm);
                        break;
                    case 3 : adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.color_emoji3), seq, title, "감정점수 = "+score, "  등록일 = "+newDateForm);
                        break;
                    case 4 : adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.color_emoji4), seq, title, "감정점수 = "+score, "  등록일 = "+newDateForm);
                        break;
                    case 5 : adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.color_emoji5), seq, title, "감정점수 = "+score, "  등록일 = "+newDateForm);
                        break;
                    default:adapter.addItem(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.x_button), seq, title, "감정점수 = "+score, "  등록일 = "+newDateForm);
                        break;
                }
            }

            //ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, titleList);
            //listView.setAdapter(arrayAdapter);

            //arrayAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View root, int position, long l) {
                HistoryListView item = (HistoryListView) adapterView.getItemAtPosition(position);

                String idStr = item.getId();
                System.out.println("idStr="+idStr);


                //프레그먼트끼리 seq넘기기 위한 bundle
                activity.onFragmentChange(3, idStr);

            }
        });

        return root;
    }


    //당일 글 갯수
    public String getHistoryList(){
        String rtnStr="";
        String url = CommonMethod.ipConfig +"/api/loadHistoryList";

        try{
            String jsonString = new JSONObject()
                    .put("userId", userId)
                    .toString();

            //REST API
            RequestHttpURLConnection.NetworkAsyncTask networkTask = new RequestHttpURLConnection.NetworkAsyncTask(url, jsonString);
            rtnStr = networkTask.execute().get();

        }catch(Exception e){
            e.printStackTrace();
        }

        return rtnStr;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}