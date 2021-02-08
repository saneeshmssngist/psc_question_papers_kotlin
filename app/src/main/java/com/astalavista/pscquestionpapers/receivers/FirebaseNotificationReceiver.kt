package com.astalavista.pscquestionpapers.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.activities.SplashScreenActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.*

class FirebaseNotificationReceiver : FirebaseMessagingService()
{
    var notification: NotificationCompat.Builder? = null
    private var notifManager: NotificationManager? = null

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val title: String? = p0.data["title"]

        Log.d("TAG_NOTI","dsadsadsadsadsa")
        //       String image_url = remoteMessage.getNotification().getTitle();
        val intent1 = Intent(this, SplashScreenActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_ONE_SHOT)

        if (notifManager == null) {
            notifManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel: NotificationChannel = notifManager!!.getNotificationChannel("123")
            if (mChannel == null) {
                mChannel = NotificationChannel("123", "title", importance)
                //                mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager!!.createNotificationChannel(mChannel)
            }
        }
        notification = NotificationCompat.Builder(this, "123")


//Building notifcation
        //Building notifcation
        notification!!.setSmallIcon(R.mipmap.ic_psc_icon)
        val bm = BitmapFactory.decodeResource(this.resources, R.mipmap.ic_psc_icon)
        notification!!.setLargeIcon(bm)
        notification!!.setWhen(System.currentTimeMillis())
//        notification.setContentTitle(title);
//        notification.setContentText(message);
//        notification.setTicker("Notifiication from Quizrr");
        //        notification.setContentTitle(title);
//        notification.setContentText(message);
//        notification.setTicker("Notifiication from Quizrr");
        notification!!.setAutoCancel(true)

        val time =
            SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(Calendar.getInstance().time)

        val contentView = RemoteViews(packageName, R.layout.custom_notification)
        contentView.setTextViewText(R.id.txtViewContent, title)
        contentView.setTextViewText(R.id.txtViewTime, time)
        notification!!.setCustomContentView(contentView)

//        notification.setStyle(new NotificationCompat.BigTextStyle()
//                .bigText(message));

        //        notification.setStyle(new NotificationCompat.BigTextStyle()
//                .bigText(message));
        notification!!.setLights(Color.BLUE, 500, 500)
//            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
//            notification.setVibrate(pattern);

        //            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
//            notification.setVibrate(pattern);
        notification!!.setContentIntent(pendingIntent)

        notifManager!!.notify(0, notification!!.build())

    }
}