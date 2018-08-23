package com.example.seungmin1216.team.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seungmin1216.team.fragment.BusRequset;
import com.example.seungmin1216.team.fragment.SubwayRequest;
import com.example.seungmin1216.team.fragment.TaxiRequest;

public class RequestAdapter2 extends FragmentStatePagerAdapter {
    public RequestAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SubwayRequest.getInstance();
        } else if (position == 1){
            return BusRequset.getInstance();
        } else {
            return TaxiRequest.getInstance();
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}
