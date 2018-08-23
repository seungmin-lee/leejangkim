package com.example.seungmin1216.team.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.event.JoinEvent;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Join2Fragment extends Fragment {
    private static Join2Fragment curr = null;
    public static Join2Fragment getInstance() {
        if (curr == null) {
            curr = new Join2Fragment();
        }

        return curr;
    }

    private Unbinder unbinder;

    @BindView(R.id.et_input_name) EditText et_input_name;
    @BindView(R.id.txt_error_name) TextView txt_error_name;
    @BindView(R.id.et_input_user_phone) EditText et_input_user_phone;
    @BindView(R.id.txt_error_user_phone) TextView txt_error_user_phone;

    String name;
    String phone;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join2, container, false);
        unbinder = ButterKnife.bind(this,view);


        et_input_user_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        name = et_input_name.getText().toString();
        phone = et_input_user_phone.getText().toString();

        txt_error_user_phone.setVisibility(GONE);



        et_input_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    txt_error_name.setVisibility(VISIBLE);
                }else{
                    txt_error_name.setVisibility(GONE);
                }

            }
        });

        et_input_user_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    txt_error_user_phone.setVisibility(VISIBLE);
                    txt_error_user_phone.setText("번호를 입력해주세요.");
                }else{
                    txt_error_user_phone.setVisibility(GONE);
                }

            }
        });




        return view;
    }

    @Override
    public void onResume() {
        et_input_name.setText("");
        et_input_user_phone.setText("");
        super.onResume();
    }

    public String input_name(){
        String name = et_input_name.getText().toString();
        return name;
    }
    public String input_phone(){
        String phone = et_input_user_phone.getText().toString();
        return phone;
    }
}