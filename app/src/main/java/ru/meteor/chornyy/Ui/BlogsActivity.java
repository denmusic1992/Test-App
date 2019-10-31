package ru.meteor.chornyy.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.meteor.chornyy.Adapters.BlogsAdapter;
import ru.meteor.chornyy.Adapters.TagsAdapter;
import ru.meteor.chornyy.Helpers.CommonCallback;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.Post;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Tag;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Singletones.RetrofitClass;

public class BlogsActivity extends CommonActivity {

    // вью для тэгов
    private RecyclerView recyclerViewTags;
    // вью для постов
    private RecyclerView recyclerViewBlogs;

    private ItemClickInterface mListenerTags;
    private ItemClickInterface mListenerBlogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        initialize(true, getString(R.string.blogs));

        if (isLaunchSuccessfull()) {
            getTags();
            getBlogs();
        }

    }

    // Получение тэгов для отображения в recyclerview
    private void getTags() {

        CommonCallback<ResultApi<List<Tag>>> callback = new CommonCallback<ResultApi<List<Tag>>>(this) {
            @Override
            public void onSuccess(ResultApi<List<Tag>> content) {
                recyclerViewTags.setAdapter(new TagsAdapter(content.getContent(), mListenerTags));
            }
        };
        RetrofitClass.getInstance().getBlogsApi().getTags().enqueue(callback);
    }

    // Получить посты
    private void getBlogs(String... tags) {

        showDialog();

        if(tags.length > 0)
            RetrofitClass.getInstance().getBlogsApi().getFilteredPosts(tags).enqueue(getBlogsCallback());
        else
            RetrofitClass.getInstance().getBlogsApi().getPosts().enqueue(getBlogsCallback());
    }

    // здесь общий метод создания callback
    private CommonCallback<ResultApi<List<Post>>> getBlogsCallback() {
        return new CommonCallback<ResultApi<List<Post>>>(this) {
            @Override
            public void onSuccess(ResultApi<List<Post>> content) {
                recyclerViewBlogs.setAdapter(new BlogsAdapter(content.getContent(), mListenerBlogs));

            }
        };
    }

    @Override
    void initControls() {
        recyclerViewTags = findViewById(R.id.recyclerViewTags);
        recyclerViewBlogs = findViewById(R.id.recyclerViewBlogs);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewTags.setHasFixedSize(true);
        recyclerViewBlogs.setHasFixedSize(true);

        // use a linear layout manager
        //RecyclerView.LayoutManager layoutManagerTags = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //RecyclerView.LayoutManager layoutManagerBlogs = new LinearLayoutManager(this);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerViewTags.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBlogs.setLayoutManager(new LinearLayoutManager(this));

        mListenerTags = new ItemClickInterface() {
            @Override
            public void onClick() {
            }

            @Override
            public void onClick(int position) {
                //Toast.makeText(getApplicationContext(), "tag touched!", Toast.LENGTH_SHORT).show();
                TagsAdapter adapter = (TagsAdapter) recyclerViewTags.getAdapter();
                if(adapter != null) {
                    getBlogs(adapter.getSelectedTags());
                }

            }

            @Override
            public void onClick(int position, String title) {
            }
        };

        mListenerBlogs = new ItemClickInterface() {
            @Override
            public void onClick() {}

            @Override
            public void onClick(int position) {}


            @Override
            public void onClick(int position, String title) {
                //Toast.makeText(getApplicationContext(), "Blog touched!", Toast.LENGTH_SHORT).show();
                // Открываем новое активити
                Intent intent = new Intent(getApplicationContext(), CurrentBlogActivity.class);
                intent.putExtra(CurrentBlogActivity.INTENT_CURRENT_BLOG_ID, position);
                intent.putExtra(CurrentBlogActivity.INTENT_CURRENT_BLOG_TITLE, title);
                startActivity(intent);
            }
        };
    }
}
