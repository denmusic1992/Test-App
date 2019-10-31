package ru.meteor.chornyy.Ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.meteor.chornyy.Adapters.ServicesAdapter;
import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Service;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class ServicesActivity extends CommonActivity {

    // Список
    private RecyclerView recyclerView;

    private ItemClickInterface clickInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        initialize(true, getString(R.string.services));

        // Если все ништяк с подключением
        if (isLaunchSuccessfull()) {
            getServices();
        }
    }

    @Override
    void initControls() {
        recyclerView = findViewById(R.id.servicesRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        // Интерфейс для обработки кликов
        clickInterface = new ItemClickInterface() {
            @Override
            public void onClick() { }

            @Override
            public void onClick(int position) { }

            @Override
            public void onClick(int position, String title) {
                Intent intent = new Intent(getApplicationContext(), CurrentServiceActivity.class);
                intent.putExtra(CurrentServiceActivity.INTENT_CURRENT_SERVICE_ID, position);
                intent.putExtra(CurrentServiceActivity.INTENT_CURRENT_SERVICE_TITLE, title);
                startActivity(intent);
            }
        };
    }

    // Получение услуг
    private void getServices() {
        // Загрузка диалогового окна
        showDialog();

        CommonCallback<ResultApi<List<Service>>> callback = new CommonCallback<ResultApi<List<Service>>>(this) {
            @Override
            public void onSuccess(ResultApi<List<Service>> content) {
                recyclerView.setAdapter(new ServicesAdapter(content.getContent(), clickInterface));
            }
        };

        RetrofitClass.getInstance().getServicesApi().getServices().enqueue(callback);

    }
}

