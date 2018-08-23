package com.example.seungmin1216.team.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.example.seungmin1216.team.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class BusFragment extends Fragment {


    private static BusFragment curr = null;

    public static BusFragment getInstance() {
        if (curr == null) {
            curr = new BusFragment();
        }

        return curr;
    }

    InputMethodManager imm;
    private Unbinder unbinder;

    @BindView(R.id.et_bus_num) EditText et_bus_num;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bus, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(et_bus_num.getWindowToken(), 0);
    }





}
