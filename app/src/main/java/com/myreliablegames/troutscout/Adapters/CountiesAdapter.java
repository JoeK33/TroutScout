package com.myreliablegames.troutscout.Adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.CountyItemBinding;

import java.util.List;

/**
 * Created by Joe on 10/16/2017.
 */

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.ViewHolder> {

    private List<LakeStockingHistory> lakes;

    public CountiesAdapter (List<LakeStockingHistory> lakes) {
        this.lakes = lakes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CountyItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.county_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LakeStockingHistory lakeStockingHistory = lakes.get(position);
        holder.binding.lakeCounty.setText(lakeStockingHistory.getCounty());
        holder.binding.numberLakes.setText(Integer.toString(lakeStockingHistory.getStockings().size()));
    }

    @Override
    public int getItemCount() {
        return lakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CountyItemBinding binding;

        public ViewHolder(CountyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LakeStockingHistory lakeStockingHistory) {
            binding.setLakeStockingHistory(lakeStockingHistory);
            binding.executePendingBindings();
        }
    }
}
