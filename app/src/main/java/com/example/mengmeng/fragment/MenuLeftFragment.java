package com.example.mengmeng.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mengmeng.activity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuLeftFragment extends Fragment {


    public MenuLeftFragment() {
        // Required empty public constructor
    }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           return inflater.inflate(R.layout.layout_menu, container, false);

        }


    }

