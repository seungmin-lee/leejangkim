package com.example.seungmin1216.team.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.seungmin1216.team.fragment.BusFragment;
import com.example.seungmin1216.team.fragment.SubwayFragment;
import com.example.seungmin1216.team.fragment.TaxiFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("ksj","pos : " + position);
        if (position == 0) {
            return new SubwayFragment();
        } else if (position == 1){
            return new BusFragment();
        } else {
            return new TaxiFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
