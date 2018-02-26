package com.krparajuli.collegethrift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.krparajuli.collegethrift.fragment.ViewListingsRecentFragment;
import com.krparajuli.collegethrift.R;

public class ViewListingsActivity extends AppCompatActivity {

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listings);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

             final Fragment[] mFragments = new Fragment[] {
                    new ViewListingsRecentFragment(),
                     new ViewListingsRecentFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_recent),
                    "Favourites"
            };

            @Override
            public Fragment getItem(int position) { return mFragments[position]; }

            @Override
            public int getCount() { return mFragments.length; }

            @Override
            public CharSequence getPageTitle(int position) { return mFragmentNames[position]; }
        };

        // Set up the ViewPager with the selections adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager, true);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createListingsIntent = new Intent(ViewListingsActivity.this, CreateListingsActivity.class);
                startActivity(createListingsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_listings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_search:
                Intent searchIntent = new Intent(ViewListingsActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
