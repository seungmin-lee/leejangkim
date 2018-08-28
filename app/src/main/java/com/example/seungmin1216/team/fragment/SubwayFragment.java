package com.example.seungmin1216.team.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.SearchActivity;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.event.DestinationStationName;
import com.example.seungmin1216.team.event.NameEvent;
import com.example.seungmin1216.team.retrofit.RetrofitService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubwayFragment extends Fragment {

    private static SubwayFragment curr = null;

    public static SubwayFragment getInstance() {
        if (curr == null) {
            curr = new SubwayFragment();
        }

        return curr;
    }

    Bus bus = BusProvider.getInstance().getBus();

    Spinner spinner;
    @BindView(R.id.btn_bookmark_nonclick) Button btn_bookmark_nonclick;
    @BindView(R.id.txt_subway_origin) TextView txt_subway_origin;
    @BindView(R.id.txt_subway_destination) TextView txt_subway_destination;
    @BindView(R.id.et_subway_memo) EditText et_subway_memo;
    @BindView(R.id.myScrollView) ScrollView myScrollView;
    @BindView(R.id.datepicker) DatePicker datepicker;


    private Unbinder unbinder;
    Integer star_set =0;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subway, container, false);
        unbinder=ButterKnife.bind(this,view);
        btn_bookmark_nonclick = view.findViewById(R.id.btn_bookmark_nonclick);
        bus.register(this);

        Log.d("kkkk", "onCreateView: "+ SaveMember.getInstance().getMember().getMem_email());

        spinner = view.findViewById(R.id.sp1);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        et_subway_memo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == true){
                    myScrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myScrollView.smoothScrollBy(0,800);
                        }
                    },100);
                }
            }
        });

        return view;
    }


    @OnClick(R.id.txt_subway_origin)
    public void onClickEtSubwayOrigin(View view){

        Intent intent = new Intent(getActivity(),SearchActivity.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }

    @OnClick(R.id.txt_subway_destination)
    public void onClickEtSubwayDestination(View view){
        Intent intent = new Intent(getActivity(),SearchActivity.class);
        intent.putExtra("type",2);
        startActivity(intent);
    }


    @OnClick(R.id.btn_bookmark_nonclick)
    public void onClickBtnBookmarkNonclick(View view){
        String start_st = txt_subway_origin.getText().toString();
        String end_st = txt_subway_destination.getText().toString();
        Long mem_id = SaveMember.getInstance().getMember().getId();
        if(star_set==0){
            star_set =1;
            btn_bookmark_nonclick.setBackgroundResource(R.drawable.star_gold);
            Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().insertBookmark("0",start_st,end_st,mem_id.toString());
            observ.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("ksj","전송성공");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }else {
            star_set=0;
            btn_bookmark_nonclick.setBackgroundResource(R.drawable.star2);

        }
    }

    @OnClick(R.id.request_btn)
    public void onClickrequest(View view){
        String start_st = txt_subway_origin.getText().toString();
        String end_st = txt_subway_destination.getText().toString();
        Integer year = datepicker.getYear();
        Integer month = datepicker.getMonth()+1;
        Integer day = datepicker.getDayOfMonth();
        String time = spinner.getSelectedItem().toString();
        String hour = time.substring(0,2);
        String ho2 = time.substring(0,1);
        if(ho2.equals("0")){
            hour = time.substring(1,2);
        }
        String minute = time.substring(5,7);
        String post = et_subway_memo.getText().toString();
        String mem_id = SaveMember.getInstance().getMember().getId().toString();

        Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().inputlist(start_st,end_st,year.toString(),month.toString()
                ,day.toString(),hour,minute,post,mem_id);


        if(start_st.equals(null) || start_st.equals("")){
            Toast.makeText(getActivity(), "출발역을 선택하세요.", Toast.LENGTH_LONG).show();
        }else if(end_st.equals(null) || end_st.equals("")){
            Toast.makeText(getActivity(), "도착역을 선택하세요.", Toast.LENGTH_LONG).show();
        }else {
            observ.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("ksj","전송성공");
                        Toast.makeText(getActivity(), "신청이 완료되었습니다.", Toast.LENGTH_LONG).show();
                        txt_subway_origin.setText(null);
                        txt_subway_destination.setText(null);
                        et_subway_memo.setText(null);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        bus.unregister(this);
    }

    @Subscribe
    public void setOriginStationName(NameEvent event){
        txt_subway_origin.setText(event.getName());
    }

    @Subscribe
    public void setDestinationStationName(DestinationStationName event){
        txt_subway_destination.setText(event.getName());

    }

    @Override
    public void onResume() {
        et_subway_memo.requestFocus();
        InputMethodManager mgr = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(et_subway_memo, InputMethodManager.SHOW_IMPLICIT);
        super.onResume();
    }





}

