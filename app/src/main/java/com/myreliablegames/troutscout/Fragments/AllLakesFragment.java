package com.myreliablegames.troutscout.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Adapters.LakesAdapter;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.FragmentAllLakesBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Joe on 10/15/2017.
 */

public class AllLakesFragment extends Fragment {

    private static final String LAKE_LIST_KEY = "Key";
    private FragmentAllLakesBinding binding;
    private LakesAdapter adapter;
    private DisposableObserver observer;

    public static AllLakesFragment newInstance() {
        AllLakesFragment fragment = new AllLakesFragment();
        return fragment;
    }

    public static AllLakesFragment newInstance(List<LakeStockingHistory> lakes) {
        AllLakesFragment fragment = new AllLakesFragment();

        Bundle args = new Bundle();
        args.putSerializable(LAKE_LIST_KEY, (Serializable) lakes);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LakesAdapter();

        if (getArguments() != null && getArguments().containsKey(LAKE_LIST_KEY)) {
            List<LakeStockingHistory> lakes = (List<LakeStockingHistory>) getArguments().getSerializable(LAKE_LIST_KEY);
            adapter.replaceItems(lakes);
            adapter.notifyDataSetChanged();
        } else {
            StockingDatabaseUtil util = StockingDatabaseUtil.getInstance();
            observer = new DisposableObserver<List<LakeStockingHistory>>() {
                @Override
                public void onNext(final @NonNull List<LakeStockingHistory> lakeStockingHistories) {
                    ArrayList<LakeStockingHistory> lakeStockingHistorySort = new ArrayList<>(lakeStockingHistories);
                    Collections.sort(lakeStockingHistorySort);
                    adapter.replaceItems(lakeStockingHistorySort);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                }
            };

            util.getLakeHistories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_lakes, container, false);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
    }
}
