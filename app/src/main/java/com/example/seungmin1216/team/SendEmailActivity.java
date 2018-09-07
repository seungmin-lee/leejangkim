package com.example.seungmin1216.team;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.seungmin1216.team.GMailSender.GMailSender;
import com.example.seungmin1216.team.data.SaveMember;

import javax.mail.SendFailedException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendEmailActivity extends AppCompatActivity {

    @BindView(R.id.btn_close_send_email) Button btn_close_send_email;
    @BindView(R.id.et_send_email_title) EditText et_send_email_title;
    @BindView(R.id.et_send_email_contents) EditText et_send_email_contents;
    @BindView(R.id.btn_send_email) Button btn_send_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_send_email)
    public void onClickBtnSendEmail(View view) {

        String email = SaveMember.getInstance().getMember().getMem_email();
        String title = et_send_email_title.getText().toString();
        String contents = et_send_email_contents.getText().toString();

        Log.d("lsm",email + " " + title + " "+ contents);


        if(!title.equals("") && !contents.equals("")) {

            try {
                GMailSender sender = new GMailSender("lee.jang.kim3@gmail.com", "ljk1234-");
                sender.sendMail(title, "\n' " + email + " '님이 문의를 남겼습니다\n" + "******** 문의내용 ******** \n" + contents + "\n **************************", "lee.jang.kim3@gmail.com");
                Toast.makeText(SendEmailActivity.this, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } catch (SendFailedException e) {
                Toast.makeText(SendEmailActivity.this, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("lsm", "오잉?");
            }
        }else if(title.equals("")){
            Toast.makeText(SendEmailActivity.this,"제목을 입력해주세요.",Toast.LENGTH_LONG).show();
        }else if(contents.equals("")){
            Toast.makeText(SendEmailActivity.this,"내용을 입력해주세요.",Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R.id.btn_close_send_email)
    public void onClickBtnCloseSendEmail(View view){
        finish();
    }
}
