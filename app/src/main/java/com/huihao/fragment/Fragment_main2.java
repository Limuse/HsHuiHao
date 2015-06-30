package com.huihao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huihao.R;
import com.leo.base.activity.fragment.LFragment;

/**
 * Created by admin on 2015/6/26.
 */

public class Fragment_main2 extends LFragment {
    private View parentView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_main2,
                container, false);
        return parentView;
    }
}
