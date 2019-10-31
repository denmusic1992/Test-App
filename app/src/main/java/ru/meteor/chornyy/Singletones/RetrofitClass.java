package ru.meteor.chornyy.Singletones;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.meteor.chornyy.Config;
import ru.meteor.chornyy.Interfaces.IMeteorBlogsApi;
import ru.meteor.chornyy.Interfaces.IMeteorContactsApi;
import ru.meteor.chornyy.Interfaces.IMeteorReviewsApi;
import ru.meteor.chornyy.Interfaces.IMeteorServicesApi;

// Синглтон ретрофит
public class RetrofitClass {

    // инстанс сингтона
    private static RetrofitClass mInstance;
    // экземпляр в ед. числе
    private Retrofit mRetrofit;

    //Конструктор
    private RetrofitClass() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // получение экземпляра
    public static RetrofitClass getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClass();
        }
        return mInstance;
    }

    // получить контакты
    public IMeteorContactsApi getContactsApi() {
        return mRetrofit.create(IMeteorContactsApi.class);
    }

    public IMeteorServicesApi getServicesApi() {
        return mRetrofit.create(IMeteorServicesApi.class);
    }

    public IMeteorReviewsApi getReviewsApi() {
        return mRetrofit.create(IMeteorReviewsApi.class);
    }

    public IMeteorBlogsApi getBlogsApi() {
        return mRetrofit.create(IMeteorBlogsApi.class);
    }
}
