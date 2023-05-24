package com.example.ani_lore.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ani_lore.DetailActivity;
import com.example.ani_lore.R;
import com.example.ani_lore.api.jikan.response.DataItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VerticalItemAdapter extends RecyclerView.Adapter<VerticalItemAdapter.ViewHolder> {

    private List<DataItem> localDataSet;

    public VerticalItemAdapter(List<DataItem> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_vertical_item_season_anime, viewGroup, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAnimePoster;
        private final TextView tvAnimeTitle;

        public ViewHolder(View view) {
            super(view);

            ivAnimePoster = view.findViewById(R.id.iv_anime_poster);
            tvAnimeTitle = view.findViewById(R.id.tv_anime_title);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        DataItem dataItem = localDataSet.get(position);
        String title = dataItem.getTitle();
        String imageUrl = dataItem.getImages().getJpg().getLargeImageUrl();

        viewHolder.tvAnimeTitle.setText(title);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(viewHolder.ivAnimePoster);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("anime_id", String.valueOf(dataItem.getMalId()));
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
