package ru.meteor.chornyy.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.meteor.chornyy.Models.ResultApi;
import ru.meteor.chornyy.Models.Service;

public interface IMeteorServicesApi {

    @GET("services")
    Call<ResultApi<List<Service>>> getServices();

    @GET("services/{ServiceID}")
    Call<ResultApi<Service>> getCurrentService(@Path("ServiceID") int serviceID);

}
