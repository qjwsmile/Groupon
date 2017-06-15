package com.qijianwei.tarena.groupon.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGuidanceTwo extends BaseFragment {

    @BindView(R.id.button_guide_two_id)
     Button button_skip;

    public FragmentGuidanceTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_fragment_guidance_two, container, false);
        ButterKnife.bind(this,view);
        skip(button_skip);
        return view;
    }




}
