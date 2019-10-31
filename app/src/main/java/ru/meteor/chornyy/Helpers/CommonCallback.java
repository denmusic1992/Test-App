package ru.meteor.chornyy.Helpers;

import android.content.Context;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.meteor.chornyy.R;
import ru.meteor.chornyy.Ui.CommonActivity;

public abstract class CommonCallback<T> implements Callback<T> {

    private Context context;

    protected CommonCallback(Context context) {
        this.context = context;
    }

    public abstract void onSuccess(T content);

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (context instanceof CommonActivity) {
            ((CommonActivity) context).dismissDialog();
            T content = response.body();
            if(content != null)
                onSuccess(content);
            else
                ((CommonActivity) context).showErrorDialog(context.getString(R.string.error_connection));
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (context instanceof CommonActivity) {
            ((CommonActivity) context).dismissDialog();
            ((CommonActivity) context).showErrorDialog(context.getString(R.string.error_connection));
        }
    }
}
