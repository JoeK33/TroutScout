package com.myreliablegames.troutscout.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Adapters.LakesAdapter;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.FragmentAllLakesBinding;

/**
 * Created by Joe on 10/15/2017.
 */

public class AllLakesFragment extends Fragment {

    private FragmentAllLakesBinding binding;
    private LakesAdapter adapter;

    public static AllLakesFragment newInstance(StockingDatabaseUtil stockingDatabaseUtil) {
        AllLakesFragment fragment = new AllLakesFragment();

        Bundle args = new Bundle();
        args.putSerializable(StockingDatabaseUtil.KEY, stockingDatabaseUtil);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StockingDatabaseUtil stockingDatabaseUtil = (StockingDatabaseUtil)getArguments().getSerializable(StockingDatabaseUtil.KEY);
        adapter = new LakesAdapter(stockingDatabaseUtil.getLakeHistories());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_lakes, container, false);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }
}
