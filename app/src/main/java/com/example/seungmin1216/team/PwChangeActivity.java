package com.example.seungmin1216.team;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.retrofit.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PwChangeActivity extends AppCompatActivity {

    @BindView(R.id.et_change_pw1) EditText et_change_pw1;
    @BindView(R.id.et_change_pw2) EditText et_change_pw2;
    @BindView(R.id.txt_error_pw1) TextView txt_error_pw1;
    @BindView(R.id.txt_error_pw2) TextView txt_error_pw2;
    @BindView(R.id.btn_close_pw_change) Button btn_close_pw_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);
        ButterKnife.bind(PwChangeActivity.this);

    }

    @OnClick(R.id.btn_close_pw_change)
    public void onClickBtnClosePwChange(View view){
        String pw1 = et_change_pw1.getText().toString();
        String pw2 = et_change_pw2.getText().toString();
        String id = SaveMember.getInstance().getMember().getId().toString();

        if(pw1.equals("")||pw1.equals(null)){
            txt_error_pw1.setVisibility(View.VISIBLE);
        }else if(!pw1.equals(pw2)){
            txt_error_pw1.setVisibility(View.GONE);
            txt_error_pw2.setVisibility(View.VISIBLE);
        }else{
            Call<Void> observ = RetrofitService.getInstance().getRetrofitRequest().updatePw(pw1,id);
            observ.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("lsm", "전송성공");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            finish();
        }

    }

}
