package com.example.seungmin1216.team;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MypageActivity extends AppCompatActivity {

    @BindView(R.id.txt_change_info) TextView txt_change_info;
    @BindView(R.id.txt_logout) TextView txt_logout;
    @BindView(R.id.txt_notice2) TextView txt_notice2;
    @BindView(R.id.txt_direct_ques) TextView txt_direct_ques;
    @BindView(R.id.txt_email_ques) TextView txt_email_ques;
    @BindView(R.id.txt_info_us) TextView txt_info_us;
    @BindView(R.id.btn_close) Button btn_close;

    private PopupWindow mPopupWindow ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ButterKnife.bind(this);



    }

    @OnClick(R.id.txt_change_info)
    public void onClickTxtName(View view) {
        Intent intent = new Intent(MypageActivity.this,InfoChangeActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.txt_logout)
    public void onClickTxtLogout(View view) {
        Log.d("lsm","로그아웃");
        View popupView = getLayoutInflater().inflate(R.layout.activity_popup,null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,330);

        mPopupWindow.showAsDropDown(txt_logout, 50, 50);



        mPopupWindow.setOutsideTouchable(false);
        Button btn_close = (Button) popupView.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        Button btn_logout = (Button)popupView.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MypageActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }



    @OnClick(R.id.txt_notice2)
    public void onClickTxtNotice2(View view) {
        Intent intent = new Intent(MypageActivity.this,NoticeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.txt_direct_ques)
    public void onClickTxtDirectQues(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-3099-8902"));
        startActivity(intent);
    }

    @OnClick(R.id.txt_email_ques)
    public void onClickTxtReference(View view) {
        Intent intent = new Intent(MypageActivity.this,SendEmailActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.txt_info_us)
    public void onClickTxtInfoUs(View view) {
        Intent intent = new Intent(MypageActivity.this,AboutUsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.btn_close)
    public void onClickBtnClose(View view){
        finish();
    }

}
