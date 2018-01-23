package rsam.utn2017.dam.agenda;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by npadula on 22/1/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String _TAG = "::MyFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try{
            Log.d(_TAG,"getCollapseKey"+remoteMessage.getCollapseKey());
            Log.d(_TAG,"getFrom"+remoteMessage.getFrom());
            Log.d(_TAG,"getMessageId"+remoteMessage.getMessageId());
            Log.d(_TAG,"getMessageType"+remoteMessage.getMessageType());
            Log.d(_TAG,"getTo"+remoteMessage.getTo());
            Log.d(_TAG,"getData"+remoteMessage.getData());
            Log.d(_TAG,"getSentTime"+remoteMessage.getSentTime());
            Log.d(_TAG,"getTtl"+remoteMessage.getTtl());
            if(remoteMessage.getNotification()!=null){
                Log.d(_TAG,"getBody"+remoteMessage.getNotification().getBody());
                Log.d(_TAG,"getTitle"+remoteMessage.getNotification().getTitle());

            }
            sendNotification(remoteMessage.getNotification().getBody());
        }catch (Exception e ){
            e.printStackTrace();
        }

    }

    public MyFirebaseMessagingService() {
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_missed_call)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
