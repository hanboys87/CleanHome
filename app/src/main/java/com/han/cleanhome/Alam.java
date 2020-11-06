package com.han.cleanhome;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Alam extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */
    AlarmManager alarm;
    Button am_btn,pm_btn;
    Integer hour = new Integer(-1);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alam);
        Button btn1 = (Button) this.findViewById(R.id.button1);
        am_btn = (Button) findViewById(R.id.amBtn);
        pm_btn = (Button) findViewById(R.id.pmBtn);
        btn1.setOnClickListener(this);
        am_btn.setOnClickListener(this);
        pm_btn.setOnClickListener(this);
        alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE); // 알람 메니저 쓴다 내 system 정보를 가지고 알람을 쓸꺼다.
    }

    @Override
    public void onClick(View arg0) {
        EditText edit4;
        switch (arg0.getId()) {
            case R.id.amBtn:
                edit4 = (EditText) this.findViewById(R.id.edit4);
                hour = new Integer(Integer.valueOf(edit4.getText().toString()));
                Log.i("#### hour " , " " + hour);
                if(hour < 0) {
                    edit4.setText("0");
                    Toast.makeText(Alam.this, "값을 입력하세요", Toast.LENGTH_SHORT).show();
                }else if(hour > 12){ // am on애는 12시 초과가없다.
                    edit4.setText("0");
                    Toast.makeText(Alam.this, "값을 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pmBtn:
                edit4 = (EditText) this.findViewById(R.id.edit4);
                hour = new Integer(Integer.valueOf(edit4.getText().toString()));
                Log.i("#### hour " , " " + hour);
                if(hour < 0) {
                    edit4.setText("0");
                    Toast.makeText(Alam.this, "값을 입력하세요", Toast.LENGTH_SHORT).show();
                }else if(hour > 12){
                    edit4.setText("0");
                    Toast.makeText(Alam.this, "값을 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                }else if(hour == 12) { // pm12시는 am 0
                    hour=0;
                }else
                { // pm 2시면 12 +2 = 14시
                    hour = hour + 12;
                }
                Log.i("#### hour " , " " + hour);
                break;
            case R.id.button1:
                Intent intent = new Intent(Alam.this, AlarmReceive.class); //

                PendingIntent pender = PendingIntent.getBroadcast(Alam.this, 0, intent, 0); // pending 은 보류한다,
                // 기다리는 이벤트.
                // 정해진 조건까지 기다리다가 조건이 일치하면 이 화면에  intent AlarmReceive 를 실행한다. broadcast
                EditText edit1 = (EditText) this.findViewById(R.id.edit1);
                int year = Integer.valueOf(edit1.getText().toString());
                EditText edit2 = (EditText) this.findViewById(R.id.edit2);
                int month = Integer.valueOf(edit2.getText().toString());
                EditText edit3 = (EditText) this.findViewById(R.id.edit3);
                int day = Integer.valueOf(edit3.getText().toString());
                if(hour == -1) { // 시간 설정안했다. 그러니깐
                    edit4 = (EditText) this.findViewById(R.id.edit4);
                    hour = new Integer(Integer.valueOf(edit4.getText().toString()));
                }
                // edit4 = (EditText) this.findViewById(R.id.edit4);
                //  hour = Integer.valueOf(edit4.getText().toString());
                EditText edit5 = (EditText) this.findViewById(R.id.edit5);
                int minute = Integer.valueOf(edit5.getText().toString());
                Calendar calendar = Calendar.getInstance(); // 시간받아오는  api
                calendar.set(year, month-1, day, hour, minute,0); // 우리가 원하는 date 시간을 set 할수잇다.

                Calendar current = Calendar.getInstance();

                Log.i("##### 시간 " + calendar.getTime(), "#### 시간"  + current.getTime() + "#### hour "+hour);
                //Log.i("##### 시간 "+calendar.getTime(), "####" + current.get(Calendar.YEAR) +  " | " + current.get(Calendar.MONTH) +
                // " | " +current.get(Calendar.DAY_OF_MONTH) + " | " + current.get(Calendar.HOUR_OF_DAY) + current.get(Calendar.MINUTE) +
                // current.get(Calendar.SECOND));


                alarm.set(AlarmManager.RTC, calendar.getTimeInMillis(), pender);
                // alarm 정해진 울린다. 그걸 쓸거다 . set 팅 시간에 pendingintent(intent)
             /*   Toast.makeText(this, calendar.get(Calendar.YEAR)+
                        "/"+calendar.get(Calendar.MONTH)+
                        "/"+calendar.get(Calendar.DAY_OF_MONTH)+
                        "/"+calendar.get(Calendar.HOUR)+
                        "/"+calendar.get(Calendar.MINUTE) +
                        "|||||"+current.get(Calendar.YEAR) +
                        "/"+current.get(Calendar.MONTH) +
                        "/"+current.get(Calendar.DAY_OF_MONTH) +
                        "/"+current.get(Calendar.HOUR) +
                        "/"+current.get(Calendar.MINUTE) , Toast.LENGTH_SHORT).show();
                        */
                break;
        }
    }
}
