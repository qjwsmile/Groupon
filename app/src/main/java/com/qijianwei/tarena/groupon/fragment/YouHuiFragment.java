package com.qijianwei.tarena.groupon.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qijianwei.tarena.groupon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouHuiFragment extends Fragment {


    public YouHuiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_you_hui, container, false);
    }

}
