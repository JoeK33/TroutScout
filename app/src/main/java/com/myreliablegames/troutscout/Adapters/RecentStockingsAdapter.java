package com.myreliablegames.troutscout.Adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingEvent;
import com.myreliablegames.troutscout.databinding.RecentStockingDetailItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/5/2017.
 */

public class RecentStockingsAdapter extends RecyclerView.Adapter<RecentStockingsAdapter.ViewHolder>{

    private List<StockingEvent> events;

    public RecentStockingsAdapter () {
        this.events = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecentStockingDetailItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.recent_stocking_detail_item, parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RecentStockingDetailItemBinding binding;

        public ViewHolder(RecentStockingDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StockingEvent stockingEvent) {
            binding.setStockingEvent(stockingEvent);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(RecentStockingsAdapter.ViewHolder holder, int position) {
        final StockingEvent lakeStockingHistory = events.get(position);
        holder.bind(lakeStockingHistory);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void replaceItems(List<StockingEvent> items) {
        events.clear();
        events.addAll(items);
    }
}
