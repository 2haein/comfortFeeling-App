package com.example.frontend.ui.depressionTest;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.databinding.FragmentTestBinding;


public class DepressionTestFragment extends Fragment{
    private @NonNull
    FragmentTestBinding binding;

    TextView title, explanation;
    Button result_button;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    private int count = 0;

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
        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        title = (TextView) root.findViewById(R.id.title);
        explanation = (TextView) root.findViewById((R.id.explanation));
        result_button = (Button) root.findViewById(R.id.resultBtn);
        cb1 = (CheckBox)root.findViewById(R.id.checkList1);
        cb2 = (CheckBox)root.findViewById(R.id.checkList2);
        cb3 = (CheckBox)root.findViewById(R.id.checkList3);
        cb4 = (CheckBox)root.findViewById(R.id.checkList4);
        cb5 = (CheckBox)root.findViewById(R.id.checkList5);
        cb6 = (CheckBox)root.findViewById(R.id.checkList6);
        cb7 = (CheckBox)root.findViewById(R.id.checkList7);
        cb8 = (CheckBox)root.findViewById(R.id.checkList8);
        cb9 = (CheckBox)root.findViewById(R.id.checkList9);


        result_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(cb1.isChecked())
                    count++;
                if(cb2.isChecked())
                    count++;
                if(cb3.isChecked())
                    count++;
                if(cb4.isChecked())
                    count++;
                if(cb5.isChecked())
                    count++;
                if(cb6.isChecked())
                    count++;
                if(cb7.isChecked())
                    count++;
                if(cb8.isChecked())
                    count++;
                if(cb9.isChecked())
                    count++;

                activity.onFragmentChange(4,  count);


            }
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
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