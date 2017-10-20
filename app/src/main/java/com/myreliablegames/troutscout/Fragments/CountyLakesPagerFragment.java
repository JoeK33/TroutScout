package com.myreliablegames.troutscout.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.CountyWrapper;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.FragmentCountyPagerBinding;

/**
 * Created by Joe on 10/19/2017.
 */

public class CountyLakesPagerFragment extends Fragment {

    private static final String COUNTIES_FRAGMENT_TAG = "counties";
    private static final String LAKES_FRAGMENT_TAG = "lakes";

    private FragmentCountyPagerBinding binding;
    StockingDatabaseUtil stockingDatabaseUtil;

    public static CountyLakesPagerFragment newInstance(StockingDatabaseUtil stockingDatabaseUtil) {
        CountyLakesPagerFragment fragment = new CountyLakesPagerFragment();

        Bundle args = new Bundle();
        args.putSerializable(StockingDatabaseUtil.KEY, stockingDatabaseUtil);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        stockingDatabaseUtil = (StockingDatabaseUtil) getArguments().getSerializable(StockingDatabaseUtil.KEY);

        CountyLakesFragment fragment = CountyLakesFragment.newInstance(stockingDatabaseUtil);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.county_lakes_frame, fragment, COUNTIES_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_county_pager, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }

    public void openLakesInCounty(CountyWrapper countyWrapper) {
        final FragmentManager childManager = getChildFragmentManager();

        Fragment fragment = childManager.findFragmentByTag(LAKES_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = AllLakesFragment.newInstance(countyWrapper.getLakes());
        }

        childManager.beginTransaction()
                .replace(R.id.county_lakes_frame, fragment, LAKES_FRAGMENT_TAG)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();

    }
}
