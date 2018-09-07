package com.example.seungmin1216.team.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.GMailSender.GMailSender;
import com.example.seungmin1216.team.R;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Join3Fragment extends Fragment {
    private static Join3Fragment curr = null;
    public static Join3Fragment getInstance() {
        if (curr == null) {
            curr = new Join3Fragment();
        }

        return curr;
    }

    @BindView(R.id.et_input_email) EditText et_input_email;
    @BindView(R.id.btn_email_verti) Button btn_email_verti;
    @BindView(R.id.et_input_code) EditText et_input_code;
    @BindView(R.id.btn_ok) Button btn_ok;
    @BindView(R.id.ll_input_code) LinearLayout ll_input_code;
    @BindView(R.id.et_input_helper_phone) EditText et_input_helper_phone;
    @BindView(R.id.txt_error_email) TextView txt_error_email;
    @BindView(R.id.txt_error_helper_phone) TextView txt_error_helper_phone;



    String email;
    String Certification_Number;
    Integer num;
    Integer num_verity;





    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join3, container, false);
        unbinder = ButterKnife.bind(this,view);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        et_input_helper_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        et_input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    txt_error_email.setVisibility(VISIBLE);
                }else{
                    txt_error_email.setVisibility(GONE);
                }

            }
        });

        et_input_helper_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    txt_error_helper_phone.setVisibility(VISIBLE);
                }else{
                    txt_error_helper_phone.setVisibility(GONE);
                }


            }
        });

        btn_email_verti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GMailSender gMailSender = new GMailSender("lee.jang.kim3@gmail.com", "ljk1234-");
                    Certification_Number = gMailSender.getEmailCode();
                    email= et_input_email.getText().toString();
                    gMailSender.sendMail("00 앱 회원가입 이메일 인증번호 ","안녕하세요.\n\n" +
                            "인증번호 : ["+Certification_Number +"]  입니다.\n\n" + "인증번호를 빈칸에 입력해주세요",email);
                    Toast.makeText(getActivity(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();

                    ll_input_code.setVisibility(View.VISIBLE);

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String input_code = et_input_code.getText().toString();

                            if(input_code.equals(Certification_Number)){
                                num =1;
                                et_input_code.setText("인증되었습니다.");
                                num_verity =1;
                            }else{
                                Toast.makeText(getActivity(), "인증번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } catch (SendFailedException e) {
                    Toast.makeText(getActivity(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (MessagingException e) {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("lsm","code:"+Certification_Number + email  );





            }
        });


        return view;
    }

    @Override
    public void onResume() {
        et_input_email.setText("");
        et_input_code.setText("");
        et_input_helper_phone.setText("");

        Certification_Number = null;
        num = 0;
        num_verity = 0;

        super.onResume();
    }

    @OnClick(R.id.btn_ok)
    public void onClickBtnOk(View view){
        String input_code = et_input_code.getText().toString();

        if (input_code.equals(Certification_Number)) {
            num = 1;
            et_input_code.setText("인증되었습니다.");
            num_verity = 1;
        } else {
            Toast.makeText(getActivity(), "인증번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public String input_email(){
        String email = et_input_email.getText().toString();
        return email;
    }

    public  String input_helper_phone(){
        String phone = et_input_helper_phone.getText().toString();
        return  phone;
    }

    public Integer num_verity() {

        return num_verity;
    }


}