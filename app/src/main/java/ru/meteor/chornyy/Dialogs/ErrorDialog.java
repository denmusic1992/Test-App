package ru.meteor.chornyy.Dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.R;

public class ErrorDialog extends CommonDialog implements View.OnClickListener {

    // Какая ошибка произошла
    private String mError;


    // Конструктор с ошибкой
    public ErrorDialog(String error) {
        mError = error;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_fragment, container, false);

        initDialogContent(v);
        return v;
    }

    @Override
    public void onClick(View v) {
        // Можно заменить на простой вызов dismiss(), но мало ли, если ещё добавится функционал обработки нажатия
        if (v.getId() == R.id.btnOK) {
            if (getDialog() != null)
                getDialog().dismiss();
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        // Здесь получаем последнее активити из стека
        Activity activity = getActivity();
        if (activity instanceof ItemClickInterface)
            ((ItemClickInterface) activity).onClick();
    }

    private void initDialogContent(View v) {
        // обработчик события нажатия
        Button btnOk = v.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(this);
        // Здесь указываем причину ошибки, если есть
        ((TextView) v.findViewById(R.id.textNoConnection)).setText(mError);
    }
}
