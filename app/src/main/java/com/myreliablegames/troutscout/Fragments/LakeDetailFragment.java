package com.myreliablegames.troutscout.Fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Adapters.LakeDetailStockingAdapter;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.FragmentLakeDetailsBinding;

/**
 * Created by Joe on 10/15/2017.
 */

public class LakeDetailFragment extends Fragment {

    private static final String LAKE_HISTORY_KEY = "Lake History";

    private FragmentLakeDetailsBinding binding;
    private LakeStockingHistory history;
    private LakeDetailStockingAdapter adapter;

    public static LakeDetailFragment newInstance(LakeStockingHistory history) {
        LakeDetailFragment fragment = new LakeDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(LAKE_HISTORY_KEY, history);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        history = (LakeStockingHistory)getArguments().getSerializable(LAKE_HISTORY_KEY);
        adapter = new LakeDetailStockingAdapter(history);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lake_details, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setLakeStockingHistory(history);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }
}
