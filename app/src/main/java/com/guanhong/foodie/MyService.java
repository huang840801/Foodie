package com.guanhong.foodie;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.guanhong.foodie.recommend.RecommendFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

//    private Runnable mRunnable;
//    private Handler mHandler;
//    private int mTime = 1000*1;
//    private int mHour = 1*1000;
    private Timer mTimer ;
    private int mCount =  0;
    private RecommendFragment mRecommendFragment;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("MyService", "run!!!");
//            }
//        }).start();
//        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        long triggerAtTime = SystemClock.elapsedRealtime() +mHour;
//        Intent intent1 = new Intent(this, MyReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent1, 0);
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        startTimer();

        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                showNotification();
                mCount++;
//                if (mCount >= 5) {
//                    mTimer.cancel();
//                }
            }
        }, 1000, 1000 * 5);
    }

    private void showNotification() {
        Log.d("MyService",   " mCount = " + mCount);

        if(mRecommendFragment==null){
            mRecommendFragment = RecommendFragment.newInstance();
        }
        mRecommendFragment.createRandomRestaurant();
    }
}