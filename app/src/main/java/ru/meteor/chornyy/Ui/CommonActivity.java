package ru.meteor.chornyy.Ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.meteor.chornyy.Config;
import ru.meteor.chornyy.Dialogs.ErrorDialog;
import ru.meteor.chornyy.Dialogs.LoadingDialog;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.R;

// Абстрактный класс, отвечающий за основные функции приложения
public abstract class CommonActivity extends AppCompatActivity implements ItemClickInterface {

    private LoadingDialog mDialog;
    private boolean isLaunchSuccessfull = true;
    Toolbar mToolbar;

    //region override

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Проверяем подключение к интернету, если нет доступа, то показываем всплывающее окно
        if (!isNetworkConnected()) {
            showErrorDialog(getString(R.string.no_internet));
            isLaunchSuccessfull = false;
            return;
        }

        // Инциализация контролов
        mDialog = new LoadingDialog();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // click back
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initToolbar(Boolean back, String title) {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                if (back) {
                    actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
                    actionBar.setDisplayShowTitleEnabled(true);
                    actionBar.setTitle(title);
                } else {
                    actionBar.setDisplayShowTitleEnabled(false);
                }

            }
        }
    }

    // Метод интерфейса, закрывающий текущее Activity
    @Override
    public void onClick() {
        finish();
    }

    @Override
    public void onClick(int position) {
    }

    @Override
    public void onClick(int position, String title) {
    }

    //endregion

    //region init staff

    // Метод инициализации контролов и что ещё добавится
    abstract void initControls();


    // Общая для всех инициализация тулбара и контролов
    public void initialize(boolean back, String title) {
        initToolbar(back, title);
        initControls();
    }

    public boolean isLaunchSuccessfull() {
        return isLaunchSuccessfull;
    }


    // метод показывающий диалоговое окно
    public void showErrorDialog(String error) {
        // Создаем экземпляр диалога
        ErrorDialog dialog = new ErrorDialog(error);
        // Показываем диалог
        dialog.showDialog(getSupportFragmentManager(), Config.tagNoConnectDialog, false);
    }

    // Метод для получения доступа к Интернету
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //endregion


    public void showDialog() {
        mDialog.showDialog(getSupportFragmentManager(), Config.tagLoadingDialog, true);
    }

    public void dismissDialog() {
        mDialog.dismiss();
    }

    // Вызов нового Activity
    void startActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
    }
}
