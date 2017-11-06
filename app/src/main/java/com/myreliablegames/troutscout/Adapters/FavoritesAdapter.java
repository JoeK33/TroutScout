package com.myreliablegames.troutscout.Adapters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Activities.LakeDetailActivity;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.LakeItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/5/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    public static final String TAG = "LakesAdapter";

    private List<LakeStockingHistory> lakes;

    public FavoritesAdapter(List<LakeStockingHistory> lakes) {
        this.lakes = lakes;
    }

    public FavoritesAdapter() {
        this.lakes = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LakeItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.lake_item, parent, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LakeItemBinding binding;

        public ViewHolder(LakeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LakeStockingHistory lakeStockingHistory) {
            binding.setLakeStockingHistory(lakeStockingHistory);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        final LakeStockingHistory lakeStockingHistory = lakes.get(position);
        holder.bind(lakeStockingHistory);
        holder.binding.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LakeDetailActivity.class);
                intent.putExtra(TAG, lakeStockingHistory);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lakes.size();
    }

    public void replaceItems(List<LakeStockingHistory> items) {
        lakes.clear();
        lakes.addAll(items);
    }
}
