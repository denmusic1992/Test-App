package ru.meteor.chornyy.Ui;

import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.appcompat.app.ActionBar;

import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Helpers.LollipopFixedWebView;
import ru.meteor.chornyy.Models.Post;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class CurrentBlogActivity extends CommonActivity {

    private LollipopFixedWebView mWebview;
    public static final String INTENT_CURRENT_BLOG_ID = "Blog ID";
    public static final String INTENT_CURRENT_BLOG_TITLE = "Blog Title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_blog);

        initialize(true, "");


        int blogID = -1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Object id = extras.get(INTENT_CURRENT_BLOG_ID);
            if (id instanceof String) {
                blogID = Integer.parseInt((String) id);
            }
            else if(id instanceof Integer) {
                blogID = (int) id;
            }
        } else
            blogID = getIntent().getIntExtra(INTENT_CURRENT_BLOG_ID, -1);

        if (isLaunchSuccessfull() & blogID != -1) {
            getCurrentBlog(blogID);
        }
    }

    // получаем блог
    private void getCurrentBlog(int blogID) {

        showDialog();

        CommonCallback<ResultApi<Post>> callback = new CommonCallback<ResultApi<Post>>(this) {
            @Override
            public void onSuccess(ResultApi<Post> content) {

                // если переход был по диплинк, то указываем заголовок
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(content.getContent().getTitle());
                }

                String htmlContent = content.getContent().getContent();
                mWebview.loadDataWithBaseURL(null, getCssHtml(htmlContent),
                        "text/html; charset=utf-8", "UTF-8", null);
            }
        };

        RetrofitClass.getInstance().getBlogsApi().getCurrentPost(blogID).enqueue(callback);
    }

    @Override
    void initControls() {
        mWebview = findViewById(R.id.blogWebview);
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    // Формируем контент нашего webView
    private String getCssHtml(String body) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
//                "    <style>body, html{word-wrap:break-word; } " +
//                "" +
//                //"img{display: inline;height: auto;max-width: 100%;}" +
//                //"img{ max-width:100%; height:auto;} " +
////                "img{\n" +
////                "max-width: 100%;\n" +
////                "display:inline-block;\n" +
////                "height: auto;}" +
//                //"img{ max-width: 100%; height: auto; object-fit: scale-down;  border: 2px solid black;}" +
//                "</style>" +
                "<style>img{display: inline;height: auto !important ;max-width: 100% !important; object-fit: scale-down}</style>" +
                "</head>" +
                "<body>" +
                body +
                "</body>" +
                "</html>";
    }
}
