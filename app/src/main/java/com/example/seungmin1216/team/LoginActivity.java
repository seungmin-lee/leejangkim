package com.example.seungmin1216.team;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.seungmin1216.team.data.Member;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_join) Button btn_join;
    @BindView(R.id.mainLayout) RelativeLayout mainLayout;
    @BindView(R.id.et_user_id) EditText et_user_id;
    @BindView(R.id.et_user_pw) EditText et_user_pw;
    @BindView(R.id.id_ch_box) CheckBox id_ch_box;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        pref = getSharedPreferences("key", MODE_PRIVATE);

        et_user_id.setSelection(et_user_id.getText().length());
        et_user_pw.setSelection(et_user_pw.getText().length());

        Member member = new Member(-2L, "blank", "blank", "blank", "blank", "blank", "blank", "blank", -2L, "blank");
        SaveMember.getInstance().setMember(member);

        Log.d("kkkk", "onCreate: "+ SaveMember.getInstance().getMember().getMem_email());

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_user_id.getWindowToken(),0);
            }
        });

        id_ch_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id_ch_box.isChecked()){
                    String id = et_user_id.getText().toString();
                    String pw = et_user_pw.getText().toString();
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("id",id);
                    edit.putString("pw",pw);
                    edit.putBoolean("ch",true);
                    edit.putBoolean("logtxt",true);
                    edit.apply();
                }else {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.clear();
                    edit.apply();
                }
            }
        });


        String id = pref.getString("id","");
        String pw = pref.getString("pw","");
        et_user_id.setText(id);
        et_user_pw.setText(pw);
        if(pref.getBoolean("ch",false)){
            id_ch_box.setChecked(true);
            myLogin();
        }


    }

    @OnClick(R.id.btn_login)
    public void onClickBtnLogin(View view){
        myLogin();
    }

    @OnClick(R.id.btn_join)
    public void onClickBtnJoin(View view){
        Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        Member member = new Member(-2L, "blank", "blank", "blank", "blank", "blank", "blank", "blank", -2L, "blank");
        SaveMember.getInstance().setMember(member);

        Log.d("kkkk", "onResume: "+ SaveMember.getInstance().getMember().getMem_email());

        if(!pref.getBoolean("logtxt",false)){
            et_user_id.setText("");
            et_user_pw.setText("");
        }

        super.onResume();
    }


    public void myLogin(){
        final String id = et_user_id.getText().toString();
        final String pw = et_user_pw.getText().toString();

        Call<Member> observ = RetrofitService.getInstance().getRetrofitRequest().logMember(id,pw);
        observ.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful()) {
                    Log.d("lsm","전송성공");
                    Member member = response.body();
                    SaveMember.getInstance().setMember(member);
                    if(!id_ch_box.isChecked()){
                        SharedPreferences.Editor edit = pref.edit();
                        edit.remove("logtxt").apply();
                    }
                    if(id.equals(member.getMem_mid()) && pw.equals(member.getMem_pw())){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);

                    }else if(id!=member.getMem_mid() || pw != member.getMem_pw()){
                        Toast.makeText(LoginActivity.this,"아이디 및 비밀번호를 확인해주세요",Toast.LENGTH_LONG).show();
                        id_ch_box.setChecked(false);
                    }

                }
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {


            }
        });
    }


}
