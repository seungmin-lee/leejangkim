package com.example.seungmin1216.team.fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.seungmin1216.team.Map.MapMainActivity;
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
    @BindView(R.id.btn_bookmark_nonclick) Button btn_bookmark_nonclick;
    @BindView(R.id.txt_subway_origin) TextView txt_subway_origin;
    @BindView(R.id.txt_subway_destination) TextView txt_subway_destination;
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

    @OnClick(R.id.txt_subway_origin)
    public void onClickTxtSubOrigin(View view){
        Intent intent = new Intent(getActivity(),MapMainActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @OnClick(R.id.txt_subway_destination)
    public void onClickEtSubwayOrigin(View view){

        Intent intent = new Intent(getActivity(),MapMainActivity.class);
        intent.putExtra("type",2);
        startActivity(intent);
    }
}
