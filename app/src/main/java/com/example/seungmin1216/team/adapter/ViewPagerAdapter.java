package com.example.seungmin1216.team.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seungmin1216.team.fragment.BusFragment;
import com.example.seungmin1216.team.fragment.SubwayFragment;
import com.example.seungmin1216.team.fragment.TaxiFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SubwayFragment.getInstance();
        } else if (position == 1){
            return BusFragment.getInstance();
        } else {
            return TaxiFragment.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
