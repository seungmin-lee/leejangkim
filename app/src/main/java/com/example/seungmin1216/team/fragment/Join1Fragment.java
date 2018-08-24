package com.example.seungmin1216.team.fragment;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.event.JoinEvent;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Join1Fragment extends Fragment {
    private static Join1Fragment curr = null;
    public static Join1Fragment getInstance() {
        if (curr == null) {
            curr = new Join1Fragment();
        }

        return curr;
    }
    private Unbinder unbinder;

    @BindView(R.id.et_input_id) EditText et_input_id;
    @BindView(R.id.btn_check_id) Button btn_check_id;
    @BindView(R.id.txt_error_id) TextView txt_error_id;
    @BindView(R.id.et_input_pw1) EditText et_input_pw1;
    @BindView(R.id.et_input_pw2) EditText et_input_pw2;
    @BindView(R.id.txt_error_pw1) TextView txt_error_pw1;
    @BindView(R.id.txt_error_pw2) TextView txt_error_pw2;


    Integer id_check_num=1 ;
    Integer pw_check_num=1 ;

    String pw1;
    String pw2;

    String lower_id;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);
        unbinder = ButterKnife.bind(this, view);



        et_input_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_error_id.setText("중복확인을 해주세요.");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    txt_error_id.setText("아이디를 입력해주세요.");
                }


            }
        });

        et_input_pw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pw1 = et_input_pw1.getText().toString();
                pw2 = et_input_pw2.getText().toString();
                if(pw1.equals(pw2)){
                    if(id_check_num == 0) {
                        txt_error_id.setVisibility(GONE);
                    }
                    txt_error_pw2.setVisibility(GONE);
                }else{
                    txt_error_pw2.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_input_pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pw1 = et_input_pw1.getText().toString();
                pw2 = et_input_pw2.getText().toString();
                if(pw1.equals(pw2)){

                }else{
                    txt_error_pw2.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(pw1.equals(pw2)){
                   txt_error_pw2.setVisibility(GONE);
                   pw_check_num = 0;
               }else{
                   pw_check_num =1;
               }
            }
        });


        return view;



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.et_input_pw1)
    public void onClickEtPw1(View view){
        if (id_check_num ==0){
            txt_error_id.setVisibility(GONE);
        }
    }

    @OnClick(R.id.btn_check_id)
    public void onClickBtnCheckId(View view){
        Log.d("ksj","onClickBtnCheckId");
        String id = et_input_id.getText().toString();
        lower_id = id.toLowerCase();



        Call<Integer> observ2 = RetrofitService.getInstance().getRetrofitRequest().calList(lower_id);
        observ2.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int num = response.body();

                    if(num == 0){
                        txt_error_id.setVisibility(View.VISIBLE);
                        txt_error_id.setText("사용 가능한 아이디입니다.");
                        id_check_num = 0;
                    }else if(num ==1){
                        txt_error_id.setVisibility(View.VISIBLE);
                        txt_error_id.setText("중복된 아이디 입니다.");
                        id_check_num = 1;
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        et_input_id.setText("");
        et_input_pw1.setText("");
        et_input_pw2.setText("");

        id_check_num = 1;
        pw_check_num = 1;

        super.onResume();
    }

    public String input_id(){
        String id = et_input_id.getText().toString();

        return id;
    }

    public String input_pw(){
        String pw = et_input_pw1.getText().toString();
        return  pw;
    }

    public Integer id_check(){
        int id_check = id_check_num;

        return id_check;
    }

    public Integer pw_check(){
        int pw_check = pw_check_num;

        return pw_check;
    }

}
