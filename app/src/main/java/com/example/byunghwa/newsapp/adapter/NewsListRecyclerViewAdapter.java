package com.example.byunghwa.newsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.byunghwa.newsapp.R;
import com.example.byunghwa.newsapp.model.News;

import java.util.ArrayList;

public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<NewsListRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<News> mList;
    private NewsListRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public NewsListRecyclerViewAdapter() {

    }

    public void setOnItemClickListener(NewsListRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getWebTitle());
        holder.pubDate.setText(mList.get(position).getWebPublicationDate());
        holder.nameSection.setText(mList.get(position).getSectionName());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            Log.i("adapter", "item count: " + mList.size());
            return mList.size();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, (Integer) v.getTag());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int clickedItemPosition);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        TextView nameSection;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            pubDate = itemView.findViewById(R.id.tv_pubDate);
            nameSection = itemView.findViewById(R.id.tv_section);
        }
    }

    public void swapData(ArrayList<News> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
