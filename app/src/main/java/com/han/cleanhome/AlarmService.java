package com.han.cleanhome;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmService extends Service{

    static MediaPlayer mPlayer;
    public static boolean plaing = false;
    public Context mContext;
    private Handler handlerUpdateLocation;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "default";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            //
            Intent notificationIntent = new Intent(mContext, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingI = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

            //
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("알람시작")
                    .setContentText("알람음이 재생됩니다.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingI)
                    .build();

            startForeground(1, notification);

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String getState = intent.getExtras().getString("state");

        assert getState != null;
        switch (getState) {
            case "play":
                startId = 1;
                Log.e("HAN","play start");
                //Play(mContext);
                if (handlerUpdateLocation == null) {
                    handlerUpdateLocation = new Handler();
                    handlerUpdateLocation.post(runnableUpdateLocation);
                }
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }


        return START_NOT_STICKY;
    }

    void Play(Context context){
        AlarmPlay(context);
        Log.e("HAN","Play");
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                Log.e("HAN","delay");
                //SendSms(mContext);
                AlamrStrop();
                stopForeground(true);
            }
        }, 30000); // 0.5초후


    }



    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    // 알람 플레이 하는 부분
    void AlarmPlay(Context mContext){
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mContext.getResources().openRawResourceFd(R.raw.music));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.start();
        plaing = true;
    }

    public static void AlamrStrop(){
        if(plaing) {
            mPlayer.stop();         // 이 방식은 미디어를 멈추는것이고
            mPlayer.release();        // 이 방식은 메모리에서 해체시키는 방법이다.
            plaing = false;
        }
    }


    private Runnable runnableUpdateLocation = new Runnable() {
        @Override
        public void run() {
            // TODO: You can send location here
            Log.e("HAN","runnableUpdateLocation");
            Play(mContext);
            Log.e("HAN","runnableUpdateLocation end");
        }
    };




}
