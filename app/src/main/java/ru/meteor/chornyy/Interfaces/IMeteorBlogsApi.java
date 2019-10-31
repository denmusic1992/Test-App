package ru.meteor.chornyy.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.meteor.chornyy.Models.Post;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Tag;

public interface IMeteorBlogsApi {
    @GET("tags")
    Call<ResultApi<List<Tag>>> getTags();

    @GET("posts")
    Call<ResultApi<List<Post>>> getPosts();

    @GET("posts/{PostID}")
    Call<ResultApi<Post>> getCurrentPost(@Path("PostID") int postID);

    @GET("posts")
    Call<ResultApi<List<Post>>> getFilteredPosts(@Query("tags[]") String[] tags);
}
