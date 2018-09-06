package com.example.seungmin1216.team;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoChangeActivity extends AppCompatActivity {

    @BindView(R.id.txt_id) TextView txt_id;
    @BindView(R.id.et_name) EditText et_name;
    @BindView(R.id.et_user_phone) EditText et_user_phone;
    @BindView(R.id.et_helper_phone) EditText et_helper_phone;
    @BindView(R.id.et_etc) EditText et_etc;
    @BindView(R.id.et_age) EditText et_age;
    @BindView(R.id.btn_change_pw) Button btn_change_pw;
    @BindView(R.id.btn_change_info) Button btn_change_info;
    @BindView(R.id.infochange_main_view) ScrollView infochange_main_view;

    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);
        ButterKnife.bind(InfoChangeActivity.this);

        txt_id.setText(SaveMember.getInstance().getMember().getMem_mid());
        et_name.setText(SaveMember.getInstance().getMember().getMem_name());
        et_user_phone.setText(SaveMember.getInstance().getMember().getMem_myp());
        et_helper_phone.setText(SaveMember.getInstance().getMember().getMem_subp());
        et_etc.setText(SaveMember.getInstance().getMember().getMem_etc());
        et_age.setText(SaveMember.getInstance().getMember().getMem_age().toString());

        infochange_main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_name.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(et_user_phone.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_helper_phone.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_etc.getWindowToken(),0);
                imm.hideSoftInputFromWindow(et_age.getWindowToken(),0);
            }
        });


        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @OnClick(R.id.btn_change_pw)
    public void onClickBtnChangePw(View view) {
        Intent intent = new Intent(InfoChangeActivity.this,PwChangeActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.btn_change_info)
    public void onClickBtnChangeInfo(View view) {
        final String mem_name = et_name.getText().toString();
        final String mem_myp = et_user_phone.getText().toString();
        final String mem_subp = et_helper_phone.getText().toString();
        final String mem_etc = et_etc.getText().toString();
        final String mem_age = et_age.getText().toString();
        final String id = SaveMember.getInstance().getMember().getId().toString();

        //다이얼로그 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        //속성 지정
        builder.setTitle("안내");
        builder.setMessage("수정 하시겠습니까?");
        //아이콘
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().updateMember(mem_name,mem_myp,mem_subp,mem_etc,mem_age,id);
                observ.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("lsm","전송성공");
                            SaveMember.getInstance().getMember().setMem_name(mem_name);
                            SaveMember.getInstance().getMember().setMem_myp(mem_myp);
                            SaveMember.getInstance().getMember().setMem_subp(mem_subp);
                            SaveMember.getInstance().getMember().setMem_etc(mem_etc);
                            SaveMember.getInstance().getMember().setMem_age(Long.parseLong(mem_age));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                finish();
            }
        });


        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }



}
