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
    private String phone = "15884388";  // 서울시 장애인 콜택시 번호
    private boolean ischack = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
        unbinder = ButterKnife.bind(this, view);
        bus.register(this);

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
        }


        getActivity().registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(getActivity(), "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(getActivity(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(getActivity(), "비행기 모드 입니다", Toast.LENGTH_SHORT).show();
//                        sendSmsIntent(콜);

                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(getActivity(), "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_OK:
                        Toast.makeText(getActivity(), "신청이 완료되었습니다. \n문자로 승인여부 확인바랍니다.\n( 신청 후 10분 이내 )", Toast.LENGTH_SHORT).show();
                        break;
                }

                txt_taxi_origin.setText(null);
                txt_taxi_destination.setText(null);
                et_taxi_memo.setText(null);
            }
        }, new IntentFilter("SMS_SENT_ACTION"));





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

        Log.d("문자", message2);
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
            Toast.makeText(getActivity(), "권한을 허용하지않으면 서비스 이용이 불가능합니다.", Toast.LENGTH_SHORT).show();
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            String name = SaveMember.getInstance().getMember().getMem_name();
            String start_place = txt_taxi_origin.getText().toString();
            String end_place = txt_taxi_destination.getText().toString();
            String memo = et_taxi_memo.getText().toString();

            String ori_memo = SaveMember.getInstance().getMember().getMem_etc();
            String post="";
            if(memo.equals("")) {
                post =ori_memo;
            }else if(ori_memo.equals("미입력")) {
                post = memo;
            }else{
                post="없음";
            }

            if (!start_place.equals("") && !end_place.equals("")){
                message2 = "- 이름: " + name + "\n-출발: " + start_place + "\n-도착: " + end_place + "\n-전달사항: " + post;

                String sms_phone = phone.toString();

                sendSMS2(sms_phone,message2);
                ArrayList<String> partMessage = smsManager.divideMessage(message2);
            }else {
                Toast.makeText(getActivity(),"정보를 입력해주세요",Toast.LENGTH_SHORT).show();
            }



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

    public void sendSMS2(String smsNumber, String smsText) {
        SmsManager smsManager = SmsManager.getDefault();

        String sendTo = smsNumber;

        ArrayList<String> partMessage = smsManager.divideMessage(smsText);
        ArrayList<PendingIntent> sendIntentArray = new ArrayList<>();
        ArrayList<PendingIntent> deliveredIntentArray = new ArrayList<>();

        for(int i = 0; i < partMessage.size(); i++) {
            sendIntentArray.add(PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_SENT_ACTION"), 0));
            deliveredIntentArray.add(PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_DELIVERED_ACTION"), 0));
        }

        smsManager.sendMultipartTextMessage(sendTo, null, partMessage, sendIntentArray, deliveredIntentArray);
    }


}