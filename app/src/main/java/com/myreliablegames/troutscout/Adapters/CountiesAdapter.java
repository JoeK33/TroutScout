package com.myreliablegames.troutscout.Adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.CountyWrapper;
import com.myreliablegames.troutscout.databinding.CountyItemBinding;

import java.util.List;

/**
 * Created by Joe on 10/16/2017.
 */

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.ViewHolder> {

    private List<CountyWrapper> counties;

    public CountiesAdapter (List<CountyWrapper> counties) {
        this.counties = counties;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CountyItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.county_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CountyWrapper countyWrapper = counties.get(position);
        holder.bind(countyWrapper);
    }

    @Override
    public int getItemCount() {
        return counties.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CountyItemBinding binding;

        public ViewHolder(CountyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CountyWrapper countyWrapper) {
            binding.setCountyWrapper(countyWrapper);
            binding.executePendingBindings();
        }
    }
}
