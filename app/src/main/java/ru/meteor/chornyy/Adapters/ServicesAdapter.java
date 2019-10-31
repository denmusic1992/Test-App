package ru.meteor.chornyy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.meteor.chornyy.Config;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.Service;
import ru.meteor.chornyy.R;

// Адаптер для отображения сервисов
public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    // Список услуг
    private List<Service> mServices;

    // Обработчик нажатий
    private ItemClickInterface mListener;

    // getter
    public List<Service> getmServices() {
        return mServices;
    }

    // Конструктор
    public ServicesAdapter(List<Service> services, ItemClickInterface listener) {
        mServices = services;
        mListener = listener;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_cardview, parent, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, final int position) {
        // Засовываем url картинку в holder
        if (!mServices.get(position).getServiceImageURL().isEmpty()) {
            Picasso
                    .get()
                    //load(mServices.get(position).getServiceImageURL())
                    .load(Config.TEMP_IMG_URL)
                    .centerCrop()
                    .noFade()
                    .placeholder(R.drawable.loading_animation)
                    .fit()
                    .error(R.drawable.ic_photo_black_24dp)
                    .into(holder.servicesImage);
        }

        holder.servicesTitle.setText(mServices.get(position).getTitle());
        holder.servicesDescription.setText(mServices.get(position).getDescription());

        // инициализируем обработку нажатий
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(mServices.get(position).getServiceID(), mServices.get(position).getTitle());
                }
            }
        });
    }

    // Метод вернет количество элементов, присутствующих в данных
    @Override
    public int getItemCount() {
        return mServices.size();
    }

    static class ServicesViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView servicesImage;
        TextView servicesTitle;
        TextView servicesDescription;

        ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.servicesCardView);
            servicesImage = itemView.findViewById(R.id.imageServices);
            servicesTitle = itemView.findViewById(R.id.servicesTitle);
            servicesDescription = itemView.findViewById(R.id.servicesDescription);
        }
    }
}
