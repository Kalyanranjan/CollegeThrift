package com.krparajuli.collegethrift.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.fragments.MyBuyingConversationsFragment;
import com.krparajuli.collegethrift.fragments.MySellingConversationsFragment;

public class MyMessagesActivity extends AppCompatActivity {

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);


        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            final Fragment[] mFragments = new Fragment[]{
                    new MyBuyingConversationsFragment(),
                    new MySellingConversationsFragment()
            };

            private final String[] mFragmentNames = new String[]{
                    "Buying",
                    "Selling"
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
        mViewPager = (ViewPager) findViewById(R.id.mm_container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.mm_tabs);
        tabLayout.setupWithViewPager(mViewPager, true);
    }
}
