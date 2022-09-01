package com.setianjay.myallstorage.presentations.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.setianjay.myallstorage.databinding.ItemDailyBinding;
import com.setianjay.myallstorage.model.Daily;

import java.util.ArrayList;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private final ArrayList<Daily> dailyList = new ArrayList<>();
    private final OnDailyAdapterListener listener;
    public interface OnDailyAdapterListener{
        void onClick(Daily daily);
        void onLongClick(Daily daily, int position);
    }

    public DailyAdapter(OnDailyAdapterListener onDailyAdapterListener){
        this.listener = onDailyAdapterListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDailyBinding view = ItemDailyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Daily daily = dailyList.get(position);
        holder.bind(daily);
    }

    @Override
    public int getItemCount() {
        return this.dailyList.size();
    }

    public void deleteData(Daily daily, int position){
        this.dailyList.remove(daily);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, this.dailyList.size());
    }

    public void setData(List<Daily> dailyList) {
        this.dailyList.clear();
        this.dailyList.addAll(dailyList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDailyBinding binding;

        public ViewHolder(ItemDailyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Daily daily) {
            binding.tvDailyTitle.setText(daily.getTitle());
            binding.tvDailyContent.setText(daily.getContent());
            binding.tvDailyDate.setText(daily.getDate());
            binding.getRoot().setOnClickListener((view) -> listener.onClick(daily));
            binding.getRoot().setOnLongClickListener((view) -> {
                listener.onLongClick(daily, getAdapterPosition());
                return true;
            });
        }
    }
}
