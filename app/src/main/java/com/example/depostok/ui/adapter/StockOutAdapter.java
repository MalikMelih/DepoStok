package com.example.depostok.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.depostok.R;
import com.example.depostok.obj.StockOutData;

import java.util.ArrayList;

public class StockOutAdapter extends RecyclerView.Adapter<StockOutAdapter.StockOutViewHolder> {
    private ArrayList<StockOutData> mlistStockOut;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class StockOutViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public StockOutViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ivImage);
            mTextView1 = itemView.findViewById(R.id.tvTitle);
            mTextView2 = itemView.findViewById(R.id.tvDesc1);

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

    public StockOutAdapter(ArrayList<StockOutData> listStockOut) { mlistStockOut = listStockOut; }

    @NonNull
    @Override
    public StockOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_item, parent, false);
        StockOutViewHolder evh = new StockOutViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull StockOutViewHolder holder, int position) {
        StockOutData currentItem = mlistStockOut.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getLabel());
        holder.mTextView2.setText(currentItem.getDesc1());
    }

    @Override
    public int getItemCount() {
        return mlistStockOut.size();
    }
}
