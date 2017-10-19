package com.myreliablegames.troutscout.Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.FragmentFavoritesBinding;

/**
 * Created by Joe on 10/15/2017.
 */

public class FavoritesFragment extends Fragment {

    FragmentFavoritesBinding binding;

    public static FavoritesFragment newInstance(StockingDatabaseUtil stockingDatabaseUtil) {
        FavoritesFragment fragment = new FavoritesFragment();

        Bundle args = new Bundle();
        args.putSerializable(StockingDatabaseUtil.KEY, stockingDatabaseUtil);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }
}
