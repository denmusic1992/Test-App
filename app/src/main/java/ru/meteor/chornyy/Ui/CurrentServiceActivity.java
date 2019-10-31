package ru.meteor.chornyy.Ui;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Service;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class CurrentServiceActivity extends CommonActivity {

    public static final String INTENT_CURRENT_SERVICE_ID = "Service ID";
    public static final String INTENT_CURRENT_SERVICE_TITLE = "Service Title";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_service);

        initialize(true, String.format("%s. %s", getString(R.string.services),
                getIntent().getStringExtra(INTENT_CURRENT_SERVICE_TITLE)));

        // ID сервиса
        int serviceID = getIntent().getIntExtra(INTENT_CURRENT_SERVICE_ID, -1);

        // Получаем ID сервиса
        if(isLaunchSuccessfull() & serviceID != -1) {
            getCurrentService(serviceID);
        }
    }

    @Override
    void initControls() {
        mWebView = findViewById(R.id.webview);
    }

    private void getCurrentService(int serviceID) {

        showDialog();

        CommonCallback<ResultApi<Service>> callback = new CommonCallback<ResultApi<Service>>(this) {
            @Override
            public void onSuccess(ResultApi<Service> content) {
                if(!content.getContent().getContent().isEmpty()) {
                    mWebView.loadData(prepareHtml(content.getContent().getContent()),
                            "text/html; charset=utf-8", "UTF-8");
                }
            }
        };

        RetrofitClass.getInstance().getServicesApi().getCurrentService(serviceID).enqueue(callback);
    }

    // Взял метод, чтобы отобразить контент
    public String prepareHtml(String body) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <style>body, html{word-wrap:break-word; } body {padding: 0 0.6em;}</style>" +
                "</head>" +
                "<body>" +
                body +
                "</body>" +
                "</html>";
    }
}
