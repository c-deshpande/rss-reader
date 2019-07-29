package com.c4l1ber.rssreader.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.c4l1ber.rssreader.DataModel.RSSItem;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContent = itemView.findViewById(R.id.txtContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext, getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Toast.makeText(mContext, "Long pressed: "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.txtTitle.setText(rssItems.get(i).getTitle());
        viewHolder.txtDate.setText(rssItems.get(i).getPubDate());
        viewHolder.txtContent.setText(rssItems.get(i).getContent());
    }

    @Override
    public int getItemCount() {

        return rssItems.size();
    }
}
