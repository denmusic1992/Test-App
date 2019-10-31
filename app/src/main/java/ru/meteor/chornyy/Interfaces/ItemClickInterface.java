package ru.meteor.chornyy.Interfaces;

// Интерфейс взаимодействия
    public interface ItemClickInterface {
        //
        void onClick();
        //
        void onClick(int position);
        // метод нажатия на итем
        void onClick(int position, String title);
    }