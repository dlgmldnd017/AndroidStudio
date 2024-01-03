package com.example.samplepush;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    // 새로운 토큰을 확인했을 때 호출되는 메서드
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }

    // 클라우드 서버로부터 새로운 메시지를 받았을 때 호출되는 메서드
    // 또한 푸시 메시지를 받았을 때 그 내용 확인한 후 액티비티 쪽으로 보내는 메서드 호출
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String contents = data.get("contents");

        sendToActivity(getApplicationContext(), from, contents);
    }

    // 액티비티 쪽으로 데이터를 보내기 위해 인텐트 객체를 만들고
    // startActvity()를 수행한다.
    private void sendToActivity(Context context, String from, String contents) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("contents", contents);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }
}