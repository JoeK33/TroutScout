package com.myreliablegames.troutscout.Activities;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.myreliablegames.troutscout.Adapters.LakesAdapter;
import com.myreliablegames.troutscout.Fragments.LakeDetailFragment;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.R;

/**
 * Created by Joe on 10/15/2017.
 */

public class LakeDetailActivity extends AppCompatActivity {

    private LakeStockingHistory history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_lake_details);

        history = (LakeStockingHistory) getIntent().getSerializableExtra(LakesAdapter.TAG);

        Fragment fragment = LakeDetailFragment.newInstance(history);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }
}
