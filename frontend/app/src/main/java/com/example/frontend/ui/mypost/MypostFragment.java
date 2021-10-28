package com.example.frontend.ui.mypost;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.frontend.MainActivity;
import com.example.frontend.R;
import com.example.frontend.databinding.FragmentGraphBinding;
import com.example.frontend.databinding.FragmentHistoryBinding;
import com.example.frontend.ui.history.DetailFragment;
import com.example.frontend.ui.history.HistoryListAdapter;
import com.example.frontend.ui.history.HistoryListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MypostFragment extends Fragment {


    private FragmentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {







        return inflater.inflate(R.layout.fragment_mypost, container, false);



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }






}