package ru.meteor.chornyy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.Post;
import ru.meteor.chornyy.R;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.BlogsViewHolder> {

    private List<Post> mPosts;

    private ItemClickInterface mListener;

    public BlogsAdapter(List<Post> mPosts, ItemClickInterface mListener) {
        this.mPosts = mPosts;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public BlogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blogs_cardview, parent, false);
        return new BlogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogsViewHolder holder, final int position) {
        holder.blogTitleTextView.setText(mPosts.get(position).getTitle());
        holder.blogDateTextView.setText(mPosts.get(position).getCreated());
        holder.blogAuthorTextView.setText(mPosts.get(position).getAuthor());
        Picasso.get()
                .load(mPosts.get(position).getPostImageURL())
                .centerCrop()
                .noFade()
                //.placeholder(R.drawable.loading_animation)
                .fit()
                .error(R.drawable.ic_photo_black_24dp)
                .into(holder.blogImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

        holder.blogDescriptionTextView.setText(mPosts.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(mPosts.get(position).getPostID(), mPosts.get(position).getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    static class BlogsViewHolder extends RecyclerView.ViewHolder {

        TextView blogTitleTextView;
        TextView blogDateTextView;
        TextView blogAuthorTextView;
        CircleImageView blogImageView;
        TextView blogDescriptionTextView;

        BlogsViewHolder(@NonNull View itemView) {
            super(itemView);

            blogTitleTextView = itemView.findViewById(R.id.blogTitleTextView);
            blogDateTextView = itemView.findViewById(R.id.blogDateTextView);
            blogAuthorTextView = itemView.findViewById(R.id.blogAuthorTextView);
            blogImageView = itemView.findViewById(R.id.blogImageView);
            blogDescriptionTextView = itemView.findViewById(R.id.blogDescriptionTextView);
        }
    }
}
