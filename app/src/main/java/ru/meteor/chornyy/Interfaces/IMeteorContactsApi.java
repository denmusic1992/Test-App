package ru.meteor.chornyy.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.meteor.chornyy.Models.Contact;
import ru.meteor.chornyy.Models.ResultApi;

public interface IMeteorContactsApi {
    @GET("contacts")
    Call<ResultApi<Contact>> getContacts();

}
