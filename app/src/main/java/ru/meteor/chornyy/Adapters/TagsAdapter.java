package ru.meteor.chornyy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.meteor.chornyy.Interfaces.ItemClickInterface;
import ru.meteor.chornyy.Models.Tag;
import ru.meteor.chornyy.R;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {

    private List<Tag> mTags;
    private ItemClickInterface mItemClickInterface;


    public String[] getSelectedTags() {
        //return mTags.stream().filter( p -> p.isSelected()).map(x -> x.getTag()).;

        // т.к. мы не можем использовать предикаты и не хотим подключать доп библиотеки (пока что), то
        // воспользуемся обычным фильтром, написанным вручную
        ArrayList<String> selectedTags = new ArrayList<>();
        for(Tag tag : mTags) {
            if(tag.isSelected())
                selectedTags.add(tag.getTag());
        }
        return selectedTags.toArray(new String[selectedTags.size()]);
    }

    public TagsAdapter(List<Tag> mTags, ItemClickInterface mItemClickInterface) {
        this.mTags = mTags;
        this.mItemClickInterface = mItemClickInterface;
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_cardview, parent, false);
        return new TagsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, final int position) {
        holder.tagTextView.setText(mTags.get(position).getTag());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickInterface != null) {
                    // изменяем значение состояния тега
                    mTags.get(position).setSelected(!mTags.get(position).isSelected());
                    notifyDataSetChanged();
                    // вызываем метод клика
                    mItemClickInterface.onClick(position);
                }
            }
        });

        holder.cardView.setCardBackgroundColor(
                holder.cardView.getContext().getResources().getColor(mTags.get(position).isSelected()
                        ? R.color.colorPrimary : android.R.color.white ));

    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    static class TagsViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tagTextView;

        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.tagCardView);
            tagTextView = itemView.findViewById(R.id.tagTextView);
        }
    }
}
