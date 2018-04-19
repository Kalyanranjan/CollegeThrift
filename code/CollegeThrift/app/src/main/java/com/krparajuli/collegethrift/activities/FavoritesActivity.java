package com.krparajuli.collegethrift.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.fragments.FavoritesFragment;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = "Favorites";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ESPasswordGetter.retrieveElasticSearchPaswordFromDb();
        setContentView(R.layout.activity_favorites);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            final Fragment[] mFragments = new Fragment[]{
                    new FavoritesFragment()
            };
            private final String[] mFragmentNames = new String[]{
                    "Favorites"
            };

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set up the ViewPager with the selections adapter
        mViewPager = (ViewPager) findViewById(R.id.favorites_container);
        mViewPager.setAdapter(mPagerAdapter);
    }
}

