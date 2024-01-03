package com.example.mission16;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button btn1;
    WebView webView;
    LinearLayout slidingLayout;

    Animation goUp, goDown;

    boolean isOpen;
    boolean requestedUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.ed1);
        slidingLayout = findViewById(R.id.SearchLayout);

        SlidingPageListener listener = new SlidingPageListener();
        goUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_go_up);
        goUp.setAnimationListener(listener);
        goUp.setAnimationListener(listener);

        goDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_go_down);
        goDown.setAnimationListener(listener);

        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if(requestedUrl){
                    slidingLayout.startAnimation(goUp);
                    requestedUrl = false;
                }
            }
        });

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUri = ed1.getText().toString();

                if(inputUri.equals("")){
                    Toast.makeText(MainActivity.this, "비어있습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    if(!(inputUri.startsWith("http://"))){
                        inputUri = "http://" + inputUri;
                    }
                    webView.loadUrl(inputUri);
                    requestedUrl = true;
                }
            }
        });

    }

    class SlidingPageListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            slidingLayout.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}