package ru.meteor.chornyy.Ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ru.meteor.chornyy.R;

public class NavigationActivity extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    public void setContentView(int layoutResID) {
        // Честно, пока не понял как именно здесь происходит отрисовка внутреннего контента
        mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation, null);
        FrameLayout frameLayout = mDrawerLayout.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(mDrawerLayout);

        initialize(false, getString(R.string.app_name));
    }

//    // Метод инициализации Toolbar
//    private void initToolBar() {
//        // инициализация ToolBar
//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//
//        // Включаем стандартное отображение mToggle иконки
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//            // отключаем отображение заголовка приложения
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
//    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void initControls() {
        //mDrawerLayout = findViewById(R.id.drawerMain);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.toggle_open, R.string.toggle_close);
        mDrawerLayout.addDrawerListener(mToggle);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    // При нажатии на элементы навигационной панели
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            // В случае нажатия на "Контакты"
            case R.id.contacts:
                startActivity(ContactsActivity.class);
                break;
            case R.id.services:
                startActivity(ServicesActivity.class);
                break;
            case R.id.reviews:
                startActivity(ReviewsActivity.class);
                break;
            case R.id.blogs:
                startActivity(BlogsActivity.class);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
