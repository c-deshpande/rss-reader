package com.c4l1ber.rssreader.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c4l1ber.rssreader.activities.WebViewActivity;
import com.c4l1ber.rssreader.data.RSSItem;
import com.c4l1ber.rssreader.R;

import java.util.List;

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.ViewHolder> {

    private static final String TAG = RSSAdapter.class.getSimpleName();

    private Context mContext;
    private List<RSSItem.Item> rssItems;

    public RSSAdapter(Context mContext, List<RSSItem.Item> rssItems) {
        this.mContext = mContext;
        this.rssItems = rssItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDate, txtContent;
        ImageView imgNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgNews = itemView.findViewById(R.id.imgNews);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        String date = rssItems.get(i).getPubDate().split(" ")[0];
        String time = rssItems.get(i).getPubDate().split(" ")[1];
        String author = rssItems.get(i).getAuthor();

        viewHolder.txtTitle.setText(rssItems.get(i).getTitle());
        viewHolder.txtDate.setText(date + " at " + time + " by " + author);
        viewHolder.txtContent.setText(rssItems.get(i).getContent());
        Glide.with(mContext).load(rssItems.get(i).getEnclosure().getLink()).into(viewHolder.imgNews);

        viewHolder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("Title", rssItems.get(i).getTitle());
            intent.putExtra("URL", rssItems.get(i).getGuid());
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {

        return rssItems.size();
    }
}
