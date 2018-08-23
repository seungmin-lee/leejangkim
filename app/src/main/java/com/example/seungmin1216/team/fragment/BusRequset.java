package com.example.seungmin1216.team.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seungmin1216.team.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BusRequset extends Fragment {

    private static BusRequset curr = null;
    public static BusRequset getInstance() {
        if (curr == null) {
            curr = new BusRequset();
        }

        return curr;
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.busrequest, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

