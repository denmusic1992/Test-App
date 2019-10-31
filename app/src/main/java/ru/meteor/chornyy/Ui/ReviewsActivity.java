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
import ru.meteor.chornyy.Adapters.ReviewsAdapter;
import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Review;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class ReviewsActivity extends CommonActivity {

    private RecyclerView mRecyclerView;
    private ItemClickInterface mItemClickInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        
        initialize(true, getString(R.string.reviews));
        
        if(isLaunchSuccessfull()) {
            getReviews();
        }
    }

    // Получаем отзывы
    private void getReviews() {
        // Показываем диалоговое окно
        showDialog();

        CommonCallback<ResultApi<List<Review>>> callback = new CommonCallback<ResultApi<List<Review>>>(this) {

            @Override
            public void onSuccess(ResultApi<List<Review>> content) {
                mRecyclerView.setAdapter(new ReviewsAdapter(content.getContent(), mItemClickInterface));
            }
        };

        RetrofitClass.getInstance().getReviewsApi().getReviews().enqueue(callback);
    }

    @Override
    void initControls() {
        mRecyclerView = findViewById(R.id.reviewsRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(layoutManager);

        // Интерфейс для обработки кликов
        mItemClickInterface = new ItemClickInterface() {
            @Override
            public void onClick() { }

            @Override
            public void onClick(int position) { }

            @Override
            public void onClick(int position, String title) {
                Intent intent = new Intent(getApplicationContext(), CurrentReviewActivity.class);
                intent.putExtra(CurrentReviewActivity.INTENT_REVIEWER_ID, position);
                intent.putExtra(CurrentReviewActivity.INTENT_REVIEWER_TITLE, title);
                startActivity(intent);
            }
        };
    }
}
