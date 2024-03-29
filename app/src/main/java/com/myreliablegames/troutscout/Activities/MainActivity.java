package com.myreliablegames.troutscout.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Fragments.AllLakesFragment;
import com.myreliablegames.troutscout.Fragments.CountyLakesPagerFragment;
import com.myreliablegames.troutscout.Fragments.RecentStockingFragment;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.ActivityMainBinding;

import Fragments.FavoritesFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private ActivityMainBinding binding;
    private CountyLakesPagerFragment countyLakesPagerFragment;

    private static final int TAB_RECENT = 0;
    private static final int TAB_FAVORITES = 1;
    private static final int TAB_COUNTY = 2;
    private static final int TAB_ALL = 3;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recent:
                    pager.setCurrentItem(TAB_RECENT);
                    return true;
                case R.id.navigation_favorites:
                    pager.setCurrentItem(TAB_FAVORITES);
                    return true;
                case R.id.navigation_county:
                    pager.setCurrentItem(TAB_COUNTY);
                    return true;
                case R.id.navigation_all:
                    pager.setCurrentItem(TAB_ALL);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        createAndAttachPagerAdapter();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = countyLakesPagerFragment;
        if (fragment != null && fragment.isAdded() && fragment.getChildFragmentManager().getBackStackEntryCount() > 0){
            fragment.getChildFragmentManager().popBackStack();
        } else{
            super.onBackPressed();
        }
    }

    private void createAndAttachPagerAdapter() {
        fragmentStatePagerAdapter = new ReferenceSavingFragmentStatePagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(fragmentStatePagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case TAB_RECENT:
                        binding.navigation.setSelectedItemId(R.id.navigation_recent);
                        break;
                    case TAB_FAVORITES:
                        binding.navigation.setSelectedItemId(R.id.navigation_favorites);
                        break;
                    case TAB_COUNTY:
                        binding.navigation.setSelectedItemId(R.id.navigation_county);
                        break;
                    case TAB_ALL:
                        binding.navigation.setSelectedItemId(R.id.navigation_all);
                        break;
                    default:
                        binding.navigation.setSelectedItemId(R.id.navigation_recent);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ReferenceSavingFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public ReferenceSavingFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case TAB_RECENT:
                    return RecentStockingFragment.newInstance();
                case TAB_FAVORITES:
                    return FavoritesFragment.newInstance();
                case TAB_COUNTY:
                    return CountyLakesPagerFragment.newInstance();
                case TAB_ALL:
                    return AllLakesFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        // Here we can finally safely save a reference to the created
        // Fragment, no matter where it came from (either getItem() or
        // FragmentManger). Simply save the returned Fragment from
        // super.instantiateItem() into an appropriate reference depending
        // on the ViewPager position.
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case TAB_COUNTY:
                    countyLakesPagerFragment = (CountyLakesPagerFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }
}
