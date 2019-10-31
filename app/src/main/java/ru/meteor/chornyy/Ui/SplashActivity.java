package ru.meteor.chornyy.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import ru.meteor.chornyy.Config;
import ru.meteor.chornyy.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        // Здесь мы рассматриваем, было ли запущено приложение с помощью уведомления или нет
        Bundle extras = getIntent().getExtras();
        // Если есть данные, то рассмотрим их
        if(extras != null) {
            Object extra = extras.get(CurrentBlogActivity.INTENT_CURRENT_BLOG_ID);
            // Если есть данные для блога, то открываем блог
            if(extra != null) {

                Intent intent = new Intent(getApplicationContext(), CurrentBlogActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(CurrentBlogActivity.INTENT_CURRENT_BLOG_ID, (String) extra);
                intent.putExtras(bundle);

                startActivity(intent);

                finish();
            }

        }
        // Иначе мы просто идем дальше
        else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, Config.delayMS);
        }

    }
}
