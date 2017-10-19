package com.myreliablegames.troutscout.Adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.LakeStockingEvent;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.StockingDetailItemBinding;

import java.util.List;

/**
 * Created by Joe on 10/15/2017.
 */

public class LakeDetailStockingAdapter extends RecyclerView.Adapter<LakeDetailStockingAdapter.ViewHolder>{

    private List<LakeStockingEvent> events;

    public LakeDetailStockingAdapter (LakeStockingHistory lake) {
        this.events = lake.getStockings();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        StockingDetailItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.stocking_detail_item, parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final StockingDetailItemBinding binding;

        public ViewHolder(StockingDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LakeStockingEvent lakeStockingEvent) {
            binding.setLakeStockingEvent(lakeStockingEvent);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(LakeDetailStockingAdapter.ViewHolder holder, int position) {
        final LakeStockingEvent lakeStockingEvent = events.get(position);
        holder.bind(lakeStockingEvent);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
