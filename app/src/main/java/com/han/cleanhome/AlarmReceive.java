package com.han.cleanhome;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceive extends BroadcastReceiver {
    // BroadcastReceiver 가 broadcast 휴대폰에 어떠한 이벤트 일어나면 받는 클래스
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Received!", Toast.LENGTH_LONG).show();
        Log.i("##### Alarm Received!", "####");
        NotificationManager notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // 위에 뜨는거 카톡처럼 위에뜨는거
        //  Notification notify = new Notification(R.drawable.icon, "text", System.currentTimeMillis());

        Intent intent2 = new Intent(context, MainActivity.class); // 이페이지가 실행됫으니깐 다시 메인으로 보여줘라고 이페이즈는 뷰가없어 그러니깐
        // 원래그상태를 보여줘라
        PendingIntent pender = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        /*
        PendingIntent pender = PendingIntent.getActivity(context, 0, intent2, 0);
        notify.setLatestEventInfo(context, "alimtitle", "hackjang", pender);
        */


        Notification n1 = new Notification();
        n1.flags |= Notification.FLAG_SHOW_LIGHTS;
        n1.flags |= Notification.FLAG_AUTO_CANCEL;
        n1.defaults |= Notification.DEFAULT_ALL;
        n1.when |= System.currentTimeMillis();


        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pender)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("알람!!")
                .setContentText("약속 시간을 지키세요!!!")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE); // 사운드랑 진동 일어나게


        // mp = MediaPlayer.create(context.getApplicationContext(), R.raw.Kalimba);
        //-mp라는 객체에 bgm파일이 위치한 주소값을 전해줌(쉽게 말해 음악 읽어옴)
        //mp.setLooping(true);//-읽어들어온 음악을 계속 반복시켜줌
//      mp.start();//-노래재생!!!

        n1 = builder.build();
        notifier.notify(0,n1); //시행~

/*

        NotificationManager notiManager;
        Vibrator vibrator;
        final static int MyNoti = 0;

        @Override
        public void onCreate(Bundle savedlnstanceState) {
            super.onCreate(savedlnstanceState);
        }
*/


      /*  notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notify.vibrate = new long[] { 200, 200, 500, 300 };
        // notify.sound=Uri.parse("file:/");
        notify.number++;
        notifier.notify(1, notify);*/
    }
}