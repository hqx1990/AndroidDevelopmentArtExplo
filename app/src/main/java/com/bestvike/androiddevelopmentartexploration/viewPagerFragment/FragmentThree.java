package com.bestvike.androiddevelopmentartexploration.viewPagerFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseFragment;

public class FragmentThree extends BaseFragment {
    @Override
    protected void destroyPresenter() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        TextView tvFragmentViwePager = view.findViewById(R.id.tvFragmentViwePager);
        tvFragmentViwePager.setText("FragmentThree");
        return view;
    }
}
