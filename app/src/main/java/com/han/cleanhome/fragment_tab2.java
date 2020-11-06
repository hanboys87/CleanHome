package com.han.cleanhome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class fragment_tab2 extends Fragment {

    Context mContext;
    public fragment_tab2() {
        // Required empty public constructor
    }

    public fragment_tab2(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alam, container, false);
        return view;
    }

}