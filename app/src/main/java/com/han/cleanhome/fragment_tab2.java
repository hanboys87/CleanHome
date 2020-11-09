package com.han.cleanhome;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;


public class fragment_tab2 extends Fragment {

    Context mContext;
    @BindView(R.id.timePicker) TimePicker picker;
    public fragment_tab2() {
        // Required empty public constructor
    }

    public fragment_tab2(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alam, container, false);
        ButterKnife.bind(this, view);
        InitSetting();
        return view;
    }


    @OnClick(R.id.bt_alarm_register) void RegisterAlarm(){
        int hour, hour_24, minute;
        String am_pm;
        if (Build.VERSION.SDK_INT >= 23 ){
            hour_24 = picker.getHour();
            minute = picker.getMinute();
        }
        else{
            hour_24 = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        }
        if(hour_24 > 12) {
            am_pm = "PM";
            hour = hour_24 - 12;
        }
        else
        {
            hour = hour_24;
            am_pm="AM";
        }

        // 현재 지정된 시간으로 알람 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour_24);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        Date currentDateTime = calendar.getTime();
        //  Preference에 설정한 값 저장
        SharedPreferences.Editor editor = mContext.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
        editor.apply();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
        Toast.makeText(mContext,date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
        diaryNotification(calendar,0);

    }

    void diaryNotification(Calendar calendar,int index)
    {
        Boolean dailyNotify = true; // 무조건 알람을 사용
        PackageManager pm = mContext.getPackageManager();
        //ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(mContext, AlarmReceive.class);
        alarmIntent.putExtra("type",index);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, alarmIntent, index);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        }
    }



    void InitSetting(){
        picker.setIs24HourView(true);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);
        Date nextDate = nextNotifyTime.getTime();
        // 이전 설정값으로 TimePicker 초기화
        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

        if (Build.VERSION.SDK_INT >= 23 ){
            picker.setHour(pre_hour);
            picker.setMinute(pre_minute);
        }
        else{
            picker.setCurrentHour(pre_hour);
            picker.setCurrentMinute(pre_minute);
        }
    }

}