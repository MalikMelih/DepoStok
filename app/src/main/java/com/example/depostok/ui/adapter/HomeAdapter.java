package com.example.depostok.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depostok.obj.DataHome;
import com.example.depostok.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<DataHome> mlistDataHome;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public HomeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ivImage);
            mTextView1 = itemView.findViewById(R.id.tvTitle);
            mTextView2 = itemView.findViewById(R.id.tvDesc1);
            mTextView3 = itemView.findViewById(R.id.tvDesc2);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public HomeAdapter(ArrayList<DataHome> listDataHome) { mlistDataHome = listDataHome; }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_item, parent, false);
        HomeViewHolder evh = new HomeViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        DataHome currentItem = mlistDataHome.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getLabel());
        holder.mTextView2.setText(currentItem.getDesc1());
        holder.mTextView3.setText(currentItem.getDesc2());
    }

    @Override
    public int getItemCount() {
        return mlistDataHome.size();
    }
}
