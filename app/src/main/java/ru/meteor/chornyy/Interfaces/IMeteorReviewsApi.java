package ru.meteor.chornyy.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Review;

public interface IMeteorReviewsApi {

    @GET("reviews")
    Call<ResultApi<List<Review>>> getReviews();

    @GET("reviews/{Review}")
    Call<ResultApi<Review>> getCurrentReview(@Path("Review") int reviewID);
}
