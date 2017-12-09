package com.myreliablegames.troutscout.Fragments;

import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.myreliablegames.troutscout.Adapters.LakeDetailStockingAdapter;
import com.myreliablegames.troutscout.BuildConfig;
import com.myreliablegames.troutscout.FavoritesUtil;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.FragmentLakeDetailsBinding;

import java.util.List;
import java.util.Locale;

/**
 * Created by Joe on 10/15/2017.
 */

public class LakeDetailFragment extends Fragment implements OnMapReadyCallback {

    private static final String LAKE_HISTORY_KEY = "Lake History";
    private static final String WEATHER_FRAGMENT_TAG = "weather";

    private static final double WA_LOWER_LEFT_LAT = 45.467836;
    private static final double WA_LOWER_LEFT_LNG = -124.255371;
    private static final double WA_UPPER_RIGHT_LAT = 49.05227;
    private static final double WA_UPPER_RIGHT_LNG = -116.982422;
    private static final double WA_CENTER_LAT = 47.301585;
    private static final double WA_CENTER_LNG = -120.003662;

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

        if (savedInstanceState != null) {
            return;
        }
            WeatherFragment fragment = WeatherFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.weather_fragment, fragment, WEATHER_FRAGMENT_TAG);
            transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lake_details, container, false);

        if (FavoritesUtil.isFavorited(history)) {
            binding.favoriteButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_star));
        } else {
            binding.favoriteButton.setBackground(getResources().getDrawable(R.drawable.ic_unfavorited_star));
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BuildConfig.PAID_VERSION) {
                    if (FavoritesUtil.isFavorited(history)) {
                        FavoritesUtil.removeLakeFromFavorites(history);
                        binding.favoriteButton.setBackground(getResources().getDrawable(R.drawable.ic_unfavorited_star));
                    } else {
                        FavoritesUtil.addLakeToFavorites(history);
                        binding.favoriteButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_star));
                    }
                } else {
                    binding.favoriteButton.setBackground(getResources().getDrawable(R.drawable.ic_unfavorited_star));
                }
            }
        });

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setLakeStockingHistory(history);
        binding.recyclerView.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng WACenter = new LatLng(WA_CENTER_LAT, WA_CENTER_LNG);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(WACenter).zoom(5).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        LatLng location = getLatLngFromName(history.getCounty() + " " + history.getLakeName());

        if (location != null) {
            // For zooming automatically to the location of the marker
            cameraPosition = new CameraPosition.Builder().target(location).zoom(14).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            WeatherFragment fragment = (WeatherFragment) getChildFragmentManager().findFragmentByTag(WEATHER_FRAGMENT_TAG);
            fragment.changeLatLngAndUpdateViews(location);
        }
    }

    private LatLng getLatLngFromName(String name) {
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(name, 1, WA_LOWER_LEFT_LAT, WA_LOWER_LEFT_LNG, WA_UPPER_RIGHT_LAT, WA_UPPER_RIGHT_LNG);
            if (addresses.size() > 0) {
                LatLng location = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                // Even though the geocoder search is bounded, this only prioritizes results. It still returns results outside the bounds which we don't want.
                // Make sure the names match to not map to things that are incorrect
                if (inWAStateBounds(location) && history.getLakeName().toUpperCase().equals(addresses.get(0).getFeatureName().toUpperCase())) {
                    return location;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean inWAStateBounds(LatLng point) {
        return (point.latitude <= WA_UPPER_RIGHT_LAT && point.latitude >= WA_LOWER_LEFT_LAT &&
                point.longitude >= WA_LOWER_LEFT_LNG && point.longitude <= WA_UPPER_RIGHT_LNG);
    }
}
