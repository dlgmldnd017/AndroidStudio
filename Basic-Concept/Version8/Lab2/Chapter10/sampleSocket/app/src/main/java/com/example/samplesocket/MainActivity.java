package com.example.samplesocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2;
    EditText ed1;
    TextView tv1,tv2;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ed1 = findViewById(R.id.ed1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = ed1.getText().toString();
                        send(data);
                    }
                }).start();
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startServer();
                    }
                }).start();
            }
        });
    }

    private void send(String data) {
        try{
            int portNumber = 5001;
            Socket socket = new Socket("localHost", portNumber);
            printClientLog("소켓 설정 완료");
            sleepThread();

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(data);
            outputStream.flush();
            printClientLog("클라이언트 메시지 전송 완료");
            sleepThread();

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            sleepThread();
            printClientLog("서버 메시지가 도착...\n" + inputStream.readObject());
            socket.close();
        }catch (Exception e){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "아직 서버가 활성화 되지 않음.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void startServer() {
        try{
            int portNumber = 5001;
            ServerSocket server = new ServerSocket(portNumber);
            printServerLog("서버 시작함.\n포트번호: " + portNumber);

            while(true){
                Socket socket = server.accept(); // 소켓이 accept() 될 때까지 기다림.
                printServerLog("클라이언트가 접속함.");

                InetAddress inetAddress = socket.getInetAddress();
                InetAddress localAddress = socket.getLocalAddress();
                int clientPortNumber = socket.getPort();
                String clientInfo = "InentAddress: " + inetAddress + "\nLocalAddress: " + localAddress
                        + "\n ClientPortNumber: " + clientPortNumber;
                printServerLog(clientInfo);
                sleepThread();

                sleepThread();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                printServerLog("클라이언트에게 데이터를 전달 받음.\n데이터: " + inputStream.readObject());
                sleepThread();

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("Hello, Client");
                outputStream.flush();
                printServerLog("클라이언트에게 데이터를 전달함.");
                sleepThread();
                socket.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printClientLog(String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv1.append(msg + "\n\n");
            }
        });
    }

    private void printServerLog(String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv2.append(msg + "\n\n");
            }
        });
    }

    private void sleepThread(){
        try{
            Thread.sleep(1500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}