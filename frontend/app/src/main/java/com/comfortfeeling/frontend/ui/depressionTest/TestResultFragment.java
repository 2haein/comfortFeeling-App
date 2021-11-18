package com.comfortfeeling.frontend.ui.depressionTest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.comfortfeeling.frontend.MainActivity;
import com.comfortfeeling.frontend.R;
import com.comfortfeeling.frontend.databinding.FragmentTestresultBinding;

public class TestResultFragment extends Fragment {
    FragmentTestresultBinding binding;
    final private String TAG = getClass().getSimpleName();
    int count;
    TextView title, explanation, explanation2;

    MainActivity activity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestresultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        title = (TextView) root.findViewById(R.id.title);
        explanation = (TextView) root.findViewById((R.id.myResult));
        explanation2 = (TextView) root.findViewById((R.id.myResult2));

        Bundle bundle = getArguments();
        if(bundle != null){
            count = bundle.getInt("key");
            Log.i(TAG, "tag값" + count);
        }


        if(count >= 6){
            explanation.setText("2주 이상 이 현상이 지속되셨나요? 우울감이 높은 상태입니다.");
            explanation2.setText("전문가와 반드시 상담을 받아보세요");
        }
        else if(count>=4){
            explanation.setText("가벼운 우울감이 있는 상태입니다.");
            explanation2.setText("우울증 예방을 위해 운동, 여가활동, 대인관계를 맺어보세요");

        }
        else{
            explanation.setText("회원님은 자연스러운 상태입니다.");
            explanation2.setText("너무 걱정마세요!");

        }


        return root;
    }

}
