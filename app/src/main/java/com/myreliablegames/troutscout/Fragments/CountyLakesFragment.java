package com.myreliablegames.troutscout.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Adapters.CountiesAdapter;
import com.myreliablegames.troutscout.CountyWrapper;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.FragmentCountyBinding;

import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Joe on 10/15/2017.
 */

public class CountyLakesFragment extends Fragment {

    private FragmentCountyBinding binding;
    private CountiesAdapter adapter;
    private DisposableObserver<List<CountyWrapper>> observer;

    public static CountyLakesFragment newInstance(StockingDatabaseUtil stockingDatabaseUtil) {
        CountyLakesFragment fragment = new CountyLakesFragment();

        Bundle args = new Bundle();
        args.putSerializable(StockingDatabaseUtil.KEY, stockingDatabaseUtil);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StockingDatabaseUtil stockingDatabaseUtil = (StockingDatabaseUtil) getArguments().getSerializable(StockingDatabaseUtil.KEY);
        adapter = new CountiesAdapter((CountyLakesPagerFragment) getParentFragment());

        observer = new DisposableObserver<List<CountyWrapper>>() {
            @Override
            public void onNext(@NonNull List<CountyWrapper> countyWrappers) {
                Collections.sort(countyWrappers);
                adapter.replaceItems(countyWrappers);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        stockingDatabaseUtil.getCounties().subscribeWith(observer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_county, container, false);
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
    public void onDestroy() {
        super.onDestroy();
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
    }
}
