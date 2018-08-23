package com.example.seungmin1216.team.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.seungmin1216.team.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TaxiFragment extends Fragment {

    private static TaxiFragment curr = null;
    public static TaxiFragment getInstance() {
        if (curr == null) {
            curr = new TaxiFragment();
        }

        return curr;
    }

    Spinner spinner;
    @BindView(R.id.btn_bookmark_nonclick)
    Button btn_bookmark_nonclick;
    private Unbinder unbinder;
    Integer star_set =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
        unbinder = ButterKnife.bind(this,view);
        btn_bookmark_nonclick = view.findViewById(R.id.btn_bookmark_nonclick);

        spinner = view.findViewById(R.id.sp3);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        return view;
    }

    @OnClick(R.id.btn_bookmark_nonclick)
    public void onClickBtnBookmarkNonclick(View view){
        if(star_set==0){
            star_set =1;
            btn_bookmark_nonclick.setBackgroundResource(R.drawable.star_gold);
        }else {
            star_set=0;
            btn_bookmark_nonclick.setBackgroundResource(R.drawable.star2);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
