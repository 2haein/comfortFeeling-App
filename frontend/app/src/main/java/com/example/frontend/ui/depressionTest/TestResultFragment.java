package com.example.frontend.ui.depressionTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.databinding.FragmentTestresultBinding;

public class TestResultFragment extends Fragment {
    FragmentTestresultBinding binding;
    final private String TAG = getClass().getSimpleName();
    int count;
    TextView title, explanation;

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

        Bundle bundle = getArguments();
        if(bundle != null){
            count = bundle.getInt("key");
            Log.i(TAG, "tag값" + count);
        }


        if(count >= 5){
            explanation.setText("2주 이상 이 현상이 지속되면 초기 우울증으로 간주합니다.");
        }
        else{
            explanation.setText("회원님은 자연스러운 우울감을 느끼고 있는 상태입니다");
        }


        return root;
    }

}
