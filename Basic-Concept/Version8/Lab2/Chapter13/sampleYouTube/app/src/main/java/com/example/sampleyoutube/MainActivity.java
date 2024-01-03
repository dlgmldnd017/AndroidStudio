package com.example.sampleyoutube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {
    YouTubePlayerView playerView;
    YouTubePlayer player;

    Boolean isFirst = true;

    private static String API_KEY = "AIzaSyANeSqhvXFCUmS37PUccHXXLK_UGC1oR7M";
    private static String videoId = "QsuhIGOXxfs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPlayer();
        // YouTube의 PlayerView와 Player를 각각 초기화 한다.

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo();
            }
        });

    }

    public void initPlayer() {
        playerView = findViewById(R.id.playerView);

        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;

                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                    }

                    @Override
                    public void onLoaded(String id) {
                        player.play();
                    }

                    @Override
                    public void onAdStarted() {}

                    @Override
                    public void onVideoStarted() {}

                    @Override
                    public void onVideoEnded() {}

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {}
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        });

    }

    public void playVideo() {
        if (player != null) {
            if(isFirst){
                player.cueVideo(videoId);
                isFirst = false;
            }else{
                if (player.isPlaying()) {
                    player.pause();
                }else{
                    player.play();
                }
            }
        }
    }
}