package com.myreliablegames.troutscout.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.myreliablegames.troutscout.Fragments.AllLakesFragment;
import com.myreliablegames.troutscout.Fragments.CountyLakesFragment;
import com.myreliablegames.troutscout.Fragments.FavoritesFragment;
import com.myreliablegames.troutscout.Fragments.RecentStockingFragment;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private StockingDatabaseUtil stockingDatabaseUtil;
    private FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private ActivityMainBinding binding;

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
        stockingDatabaseUtil = new StockingDatabaseUtil();

        createAndAttachPagerAdapter();
    }

    private void createAndAttachPagerAdapter() {
        fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case TAB_RECENT:
                        return RecentStockingFragment.newInstance(stockingDatabaseUtil);
                    case TAB_FAVORITES:
                        return FavoritesFragment.newInstance(stockingDatabaseUtil);
                    case TAB_COUNTY:
                        return CountyLakesFragment.newInstance(stockingDatabaseUtil);
                    case TAB_ALL:
                        return AllLakesFragment.newInstance(stockingDatabaseUtil);
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        pager = (ViewPager) findViewById(R.id.pager);
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
}
