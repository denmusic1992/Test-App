package ru.meteor.chornyy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.Review;
import ru.meteor.chornyy.R;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    // список отзывов
    private List<Review> mReviews;
    // обработчик нажатия на элемент recycler view
    private ItemClickInterface mListener;

    // Конструктор
    public ReviewsAdapter(List<Review> mReviews, ItemClickInterface mListener) {
        this.mReviews = mReviews;
        this.mListener = mListener;
    }

    // при создании вью холдера указываем какой лэйаут используем
    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_cardview, parent, false);
        return new ReviewsViewHolder(view);
    }

    // биндим элементы с холдером
    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, final int position) {
        // получаем текущий элемент
        Review review = mReviews.get(position);

        // Для получения картинок используем OkhttpClient
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                final Request original = chain.request();
                final Request authorized = original.newBuilder().build();
                return chain.proceed(authorized);
            }
        }).build();

//        Picasso picasso = new Picasso.Builder(holder.roundedImageView.getContext())
//                .downloader(new OkHttp3Downloader(client))
//                .listener(new Picasso.Listener() {
//                    @Override
//                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
//                        exception.printStackTrace();
//                    }
//                })
//                .build();

        Picasso picasso = new Picasso.Builder(holder.roundedImageView.getContext()).indicatorsEnabled(true).build();
        // добавляем фото
        picasso
                .load(review.getReviewImageURL())
                .centerCrop()
                .noFade()
                //.placeholder(R.drawable.loading_animation)
                .fit()
                .error(R.drawable.ic_photo_black_24dp)
                .into(holder.roundedImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
        // Добавляем имя
        holder.reviewerNameTextView.setText(review.getName());
        // Добавляем должность
        holder.reviewerPositionTextView.setText(review.getPost());
        // Добавляем описание
        holder.reviewerDescriptionTextView.setText(review.getDescription());

        // инициализируем обработку нажатий
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(mReviews.get(position).getReviewID(), mReviews.get(position).getName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    static class ReviewsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CircleImageView roundedImageView;
        TextView reviewerNameTextView;
        TextView reviewerPositionTextView;
        TextView reviewerDescriptionTextView;

        ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.reviewsCardView);
            roundedImageView = itemView.findViewById(R.id.roundedImageView);
            reviewerNameTextView = itemView.findViewById(R.id.reviewerNameTextView);
            reviewerPositionTextView = itemView.findViewById(R.id.reviewerPositionTextView);
            reviewerDescriptionTextView = itemView.findViewById(R.id.reviewerDescriptionTextView);

        }
    }
}
