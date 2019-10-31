package ru.meteor.chornyy.Dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.meteor.chornyy.R;

abstract class CommonDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // делаем фон прозрачным, чтобы были видны скругленные углы
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void showDialog(FragmentManager fragmentManager, String tag, boolean cancelable) {
        // Проверяем, нет ли в стеке фрагментов уже нашего dialog
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);

        // Если уже есть такой фрагмент, удаляем его
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        } else {
            fragmentTransaction.addToBackStack(null);
        }
        super.show(fragmentTransaction, tag);
        setCancelable(cancelable);
    }
}
