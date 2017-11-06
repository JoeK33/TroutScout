package com.myreliablegames.troutscout.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Adapters.RecentStockingsAdapter;
import com.myreliablegames.troutscout.Logger;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.StockingEvent;
import com.myreliablegames.troutscout.databinding.FragmentRecentStockingsBinding;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Joe on 10/15/2017.
 */

public class RecentStockingFragment extends Fragment {

    private FragmentRecentStockingsBinding binding;
    private RecentStockingsAdapter adapter;
    private DisposableObserver<List<StockingEvent>> observer;

    public static RecentStockingFragment newInstance() {
        RecentStockingFragment fragment = new RecentStockingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecentStockingsAdapter();
        StockingDatabaseUtil stockingDatabaseUtil = StockingDatabaseUtil.getInstance();

        observer = new DisposableObserver<List<StockingEvent>>() {
            @Override
            public void onNext(@NonNull List<StockingEvent> stockingEvents) {
                adapter.replaceItems(stockingEvents);
                adapter.notifyDataSetChanged();
                Logger.e("Recent stocking events size: " + String.valueOf(stockingEvents.size()));
                Logger.e("Recent stocking events dataset changed");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        stockingDatabaseUtil.getRecentLakeStockings().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_stockings, container, false);
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
}
