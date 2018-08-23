package com.example.seungmin1216.team.adapter;


import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.seungmin1216.team.fragment.Join1Fragment;
import com.example.seungmin1216.team.fragment.Join2Fragment;
import com.example.seungmin1216.team.fragment.Join3Fragment;
import com.example.seungmin1216.team.fragment.Join4Fragment;
import com.example.seungmin1216.team.fragment.Join5Fragment;

public class JoinAdapter extends FragmentStatePagerAdapter {

    public JoinAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
    if (position == 0) {
        return Join1Fragment.getInstance();
    } else if (position == 1){
        return Join2Fragment.getInstance();
    } else if (position == 2){
        return Join3Fragment.getInstance();
    }else if(position == 3) {
        return Join4Fragment.getInstance();
    }else {
        return Join5Fragment.getInstance();
    }
}

    @Override
    public int getCount() {
    return 5;
}
}
