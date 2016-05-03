package com.nyu.cs9033.eta.http;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.controllers.ViewTripActivity;

/**
 * Created by payamrastogi on 4/9/16.
 */
public class PollTripService extends IntentService {
    private static final String TAG = "PollTripService";
    private static final int POLL_INTERVAL = 1000 * 15; // 15 seconds


    public PollTripService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        // new trip status information
        Resources r = getResources();
        PendingIntent pi = PendingIntent
                .getActivity(this,0,new Intent(this, ViewTripActivity.class),0);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.trip_update))
                .setContentTitle(r.getString(R.string.trip_update_title))
                        .setContentText(r.getString(R.string.trip_update_text))
                        .setContentIntent(pi)
                        .setAutoCancel(true) // removes after user click
                        .build();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }


    public static void setServiceAlarm(Context context, boolean isOn)
    {
        Intent i = new Intent(context, PollTripService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if(isOn) {
            alarmManager.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(), POLL_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }
}

