package com.example.seungmin1216.team.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seungmin1216.team.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Join4Fragment extends Fragment {
    private static Join4Fragment curr = null;
    public static Join4Fragment getInstance() {
        if (curr == null) {
            curr = new Join4Fragment();
        }

        return curr;
    }

    @BindView(R.id.et_input_etc) EditText et_input_etc;
    @BindView(R.id.et_input_age) EditText et_input_age;
    @BindView(R.id.txt_error_age) TextView txt_error_age;


    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join4, container, false);
        unbinder = ButterKnife.bind(this,view);

        et_input_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    txt_error_age.setVisibility(VISIBLE);
                }else{
                    txt_error_age.setVisibility(GONE);
                }


            }
        });

        return view;
    }

    @Override
    public void onResume() {
        et_input_etc.setText("");
        et_input_age.setText("");

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public String input_etc(){
        String etc = et_input_etc.getText().toString();
        return etc;
    }
    public Integer input_age(){
        Integer age = Integer.parseInt(et_input_age.getText().toString());
        return age;
    }
}