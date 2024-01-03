package com.example.samplenoti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    private static String CHANNEL_ID2 = "channel2";
    private static String CHANNEL_NAME2 = "Channel2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti1();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti2();
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoti3();
            }
        });

    }

    private void showNoti3() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;

        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel("channel3", "Channel4", NotificationManager.IMPORTANCE_HIGH));

            builder = new NotificationCompat.Builder(this, "channel3");
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_IMMUTABLE);

        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        builder.setContentTitle("SampleNoti");
        builder.setContentText("벨리디티 매도 되었습니다.");
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        manager.notify(3, notification);
    }

    public void showNoti1() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            ));

            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        builder.setContentTitle("간단 알림");
        builder.setContentText("알림 메시지입니다.");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        Notification noti = builder.build();

        manager.notify(1, noti);
    }

    public void showNoti2() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel(
                    CHANNEL_ID2, CHANNEL_NAME2, NotificationManager.IMPORTANCE_DEFAULT
            ));

            builder = new NotificationCompat.Builder(this, CHANNEL_ID2);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_IMMUTABLE);

        // 자세하게 스크롤을 내리면 제목과 내용문이 나온다.
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("많은 글자들입니다");
        style.setBigContentTitle("제목입니다");
        style.setSummaryText("요약 글입니다");

        NotificationCompat.InboxStyle style1 = new NotificationCompat.InboxStyle();
        style1.setSummaryText("안녕");
        style1.addLine("1");
        style1.addLine("2");
        style1.addLine("3");
        style1.addLine("4");

        // 처음 스크롤 내렸을때 나오는 메시지들이다.
        builder.setContentTitle("간단 알림");
        builder.setContentText("알림 메시지입니다.");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setStyle(style1);
        builder.setSmallIcon(android.R.drawable.ic_menu_send);

        Notification noti = builder.build();
        manager.notify(2, noti);
    }
}