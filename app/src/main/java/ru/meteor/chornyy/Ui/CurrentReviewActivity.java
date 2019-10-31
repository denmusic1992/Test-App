package ru.meteor.chornyy.Ui;

import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Helpers.LollipopFixedWebView;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Review;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class CurrentReviewActivity extends CommonActivity {

    public static final String INTENT_REVIEWER_TITLE = "Review Title";
    public static final String INTENT_REVIEWER_ID = "Review ID";

    private CircleImageView mImageView;
    private TextView reviewerNameTextView;
    private TextView reviewerPositionTextView;
    private LollipopFixedWebView contentWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_review);

        int currentReviewer = getIntent().getIntExtra(INTENT_REVIEWER_ID, -1);
        initialize(true, String.format("%s. %s", getString(R.string.reviews),
                getIntent().getStringExtra(INTENT_REVIEWER_TITLE)));

        if (isLaunchSuccessfull() & currentReviewer != -1) {
            getCurrentReviewer(currentReviewer);
        }
    }

    private void getCurrentReviewer(int currentReviewer) {

        showDialog();

        CommonCallback<ResultApi<Review>> callback = new CommonCallback<ResultApi<Review>>(this) {
            @Override
            public void onSuccess(ResultApi<Review> content) {
                Review reviewer = content.getContent();

                // Устанавливаем картинку
                Picasso.get()
                        .load(reviewer.getReviewImageURL())
                        .centerCrop()
                        .noFade()
                        //.placeholder(R.drawable.loading_animation)
                        .fit()
                        .error(R.drawable.ic_photo_black_24dp)
                        .into(mImageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
                // Добавляем имя
                reviewerNameTextView.setText(reviewer.getName());
                // Добавляем должность
                reviewerPositionTextView.setText(reviewer.getPost());
                // Добавляем описание
                contentWebview.loadData(prepareHtml(reviewer.getContent()),
                        "text/html; charset=utf-8", "UTF-8");
            }
        };

        RetrofitClass.getInstance().getReviewsApi().getCurrentReview(currentReviewer).enqueue(callback);
    }

    @Override
    void initControls() {
        mImageView = findViewById(R.id.reviewerImageView);
        reviewerNameTextView = findViewById(R.id.reviewerNameTextView);
        reviewerPositionTextView = findViewById(R.id.reviewerPositionTextView);
        contentWebview = findViewById(R.id.reviewerContentWebView);
    }

    // Взял метод, чтобы отобразить контент
    public String prepareHtml(String body) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <style>body, html{word-wrap:break-word; } body {padding-top: 20px; padding-left: 10px; padding-right: 10px; padding-bottom: 10px;}</style>" +
                "</head>" +
                "<body>" +
                body +
                "</body>" +
                "</html>";
    }
}
