package ru.meteor.chornyy.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ru.meteor.chornyy.Config;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Ui.SplashActivity;

public class FirebaseService extends FirebaseMessagingService {

    public static final String FIREBASE_TAG = "Firebase Tag";



    public static void getPushToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(FirebaseService.FIREBASE_TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.w(FirebaseService.FIREBASE_TAG, token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(FIREBASE_TAG, "From: " + remoteMessage.getFrom());

        // Получаем
        Map<String, String> data = remoteMessage.getData();

        // Создаем уведомление под наши нужды
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if (notification != null) {

            // Получаем данные по сообщению
            String title = notification.getTitle();
            String bodymessage = notification.getBody();


            Log.d(Config.DEBUG_TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(title, bodymessage, data);
        }
    }

    private void sendNotification(String title, String bodymessage, Map<String, String> data) {
        Intent intent;


        // Вытаскиваем диплинк
        String deeplink = data.get("deeplink");
        if (deeplink != null && deeplink.length() > 0) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
        } else {
            intent = new Intent(this, SplashActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // добавляем передаваемые данные
        Bundle extras = new Bundle();
        // убираем диплинк из данных
        data.remove("deeplink");
        for (String key : data.keySet()) {
            extras.putString(key, data.get(key));
        }

        intent.putExtras(extras);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.meteor_channel_id),
                        getString(R.string.meteor_channel_name), NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("Позволяет показывать push - сообщения и открывать определенный пост");
                notificationManager.createNotificationChannel(notificationChannel);
            }

            // даем звуковой сигнал оповещения
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            // Создаем наше уведомление
            NotificationCompat.Builder notificationBuilder = new NotificationCompat
                    .Builder(this, getString(R.string.meteor_channel_id))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setSmallIcon(R.mipmap.app_icon_round)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    //.setDefaults(android.app.Notification.DEFAULT_ALL)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));

            // Создаем интент для отображения deeplink
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_ONE_SHOT);

            // добавляем заголовок, текст и само уведомление
            notificationBuilder.setContentTitle(title)
                    .setContentText(bodymessage)
                    .setContentIntent(pendingIntent);

            notificationManager.notify(0, notificationBuilder.build());
        }

    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
