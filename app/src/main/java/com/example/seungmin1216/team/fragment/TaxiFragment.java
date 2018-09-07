package com.example.seungmin1216.team.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seungmin1216.team.Map.MapMainActivity;
import com.example.seungmin1216.team.R;
import com.example.seungmin1216.team.bus.BusProvider;
import com.example.seungmin1216.team.data.SaveMember;
import com.example.seungmin1216.team.event.Taxiplace2Event;
import com.example.seungmin1216.team.event.TaxiplaceEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class TaxiFragment extends Fragment {


    private static TaxiFragment curr = null;

    public static TaxiFragment getInstance() {
        if (curr == null) {
            curr = new TaxiFragment();
        }

        return curr;

    }

    Bus bus = BusProvider.getInstance().getBus();

    Spinner spinner;
    @BindView(R.id.txt_taxi_origin)
    TextView txt_taxi_origin;
    @BindView(R.id.txt_taxi_destination)
    TextView txt_taxi_destination;
    @BindView(R.id.et_taxi_memo)
    EditText et_taxi_memo;
    @BindView(R.id.btn_taxi_request)
    Button btn_taxi_request;
    private Unbinder unbinder;
    Integer star_set = 0;

    InputMethodManager imm;
    PendingIntent sentPI;

    private String message2 = " "; // 문자 보낼 메시지
    private String phone = "01071927746";  // 문자 보낼 휴대폰 번호
    private boolean ischack = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus.register(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.txt_taxi_origin)
    public void onClickTxtTaxiOrigin(View view) {

        Intent intent = new Intent(getActivity(), MapMainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.txt_taxi_destination)
    public void onClickTxtTaxiDestination(View view) {
        Intent intent = new Intent(getActivity(), MapMainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.et_taxi_memo)
    public void onClickEtTaxiMemo(View view) {

    }



    @OnClick(R.id.btn_taxi_request)
    public void onClickBtnTaxiRequest(View view) {

        String name = "";
        String start_place = txt_taxi_origin.getText().toString();
        String end_place = txt_taxi_destination.getText().toString();
        String ori_memo = SaveMember.getInstance().getMember().getMem_etc();
        String post;
        if(et_taxi_memo.getText().toString().equals("")) {
            post =ori_memo;
        }else if(ori_memo.equals("")) {
            post = et_taxi_memo.getText().toString();
        }else{
            post = et_taxi_memo.getText().toString() + " / " + ori_memo;
        }
        message2 = "- 이름: " + name + "\n-출발: " + start_place + "\n-도착: " + end_place + "\n-전달사항: " + post;
        Log.d("문자", message2);
        sendSMS(phone.toString(), message2);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
            Toast.makeText(getActivity(), "권한을 허용하지않으면 서비스 이용이 불가능합니다.", Toast.LENGTH_SHORT).show();
        } else {


            SmsManager smsManager = SmsManager.getDefault();

            String sms_text = "sklfjskdljfklajfklwㄴㅇ라ㅣㅓㄴㅇ라ㅣㅓㄴㅇ라ㅣㅓㄴ아ㅣㅓㄴ아ㅣ러ㅏㅣ러쟈ㅐ덜나이ㅓㄹㄴ어량러더라ㅣㄴ어랴ㅐㅈ더라러나이ㅓ랴ㅐ러ㅏㅓ나이러제ㅑㅐㅂ덜나이ㅓㄹㅈ나이ㅓㄹ쟈ㅐ덜나ㅣㅓㄹ";
            String sms_phone = phone.toString();

            message2 = sms_text;
            sendSMS(sms_phone,sms_text);
            ArrayList<String> partMessage = smsManager.divideMessage(message2);



            Log.d("jjj", sms_text + sms_phone);
        }


    }

    @Subscribe
    public void getTaxi (TaxiplaceEvent taxiplaceEvent){
        Log.d("lsm","2 : "+ taxiplaceEvent.getPlace_name() +" " +taxiplaceEvent.getAddr());
        txt_taxi_origin.setText(taxiplaceEvent.getPlace_name() + "\n" + taxiplaceEvent.getAddr());

    }

    @Subscribe
    public void getTaxi2 (Taxiplace2Event taxiplace2Event){

        txt_taxi_destination.setText(taxiplace2Event.getPlace_name2() + "\n" + taxiplace2Event.getAddr2());

    }

    public void sendSMS(String smsNumber, String smsText) {
        if (ischack) {
            SmsManager smsManager = SmsManager.getDefault();

            String sendTo = smsNumber;

            ArrayList<String> partMessage = smsManager.divideMessage(smsText);

            smsManager.sendMultipartTextMessage(sendTo, null, partMessage, null, null);

            Toast.makeText(getActivity(), "전송되었습니다.", Toast.LENGTH_SHORT).show();
        }else if (!ischack) {
            Toast.makeText(getActivity(), "전송실패", Toast.LENGTH_LONG).show();

        }
        PendingIntent sentIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_DELIVERED_ACTION"), 0);

//        getActivity().registerReceiver(new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                switch (getResultCode()) {
//                    case RESULT_OK:
//                        // 전송 성공
//                        Toast.makeText(getActivity(), "전송 완료", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        // 전송 실패
//                        Toast.makeText(getActivity(), "전송 실패", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        // 서비스 지역 아님
//                        Toast.makeText(getActivity(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        // 무선 꺼짐
//                        Toast.makeText(getActivity(), "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
////                        sendSmsIntent(phone);
//
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        // PDU 실패
//                        Toast.makeText(getActivity(), "PDU Null", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter("SMS_SENT_ACTION"));
//
//        getActivity().registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        // 도착 완료
//                        Toast.makeText(getActivity(), "SMS 도착 완료", Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        // 도착 안됨
//                        Toast.makeText(getActivity(), "SMS 도착 실패", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }

//    public void sendSmsIntent(String number) {
//        try {
//            Uri smsUri = Uri.parse("sms:" + number);
//            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, smsUri);
//            sendIntent.putExtra("sms_body", et.getText());
//            startActivity(sendIntent);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
