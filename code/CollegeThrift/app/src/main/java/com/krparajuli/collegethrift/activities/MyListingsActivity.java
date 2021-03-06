package com.krparajuli.collegethrift.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.fragments.MyListingsCurrentFragment;
import com.krparajuli.collegethrift.fragments.MyListingsRemovedFragment;
import com.krparajuli.collegethrift.fragments.MyListingsSoldFragment;

public class MyListingsActivity extends AppCompatActivity {
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            final Fragment[] mFragments = new Fragment[]{
                    new MyListingsCurrentFragment(),
                    new MyListingsSoldFragment(),
                    new MyListingsRemovedFragment()
            };
            private final String[] mFragmentNames = new String[]{
                    "Current",
                    "Sold",
                    "Removed"
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

        // Set up the ViewPager with the selections adapter
        mViewPager = (ViewPager) findViewById(R.id.dash_container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.dash_tabs);
        tabLayout.setupWithViewPager(mViewPager, true);
    }
}
