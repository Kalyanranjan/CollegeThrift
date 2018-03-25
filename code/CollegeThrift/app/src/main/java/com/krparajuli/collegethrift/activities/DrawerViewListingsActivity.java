package com.krparajuli.collegethrift.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.fragments.ViewListingsRecentFragment;

public class DrawerViewListingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_view_listings);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            final Fragment[] mFragments = new Fragment[] {
                    new ViewListingsRecentFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_recent),
            };

            @Override
            public Fragment getItem(int position) { return mFragments[position]; }

            @Override
            public int getCount() { return mFragments.length; }

            @Override
            public CharSequence getPageTitle(int position) { return mFragmentNames[position]; }
        };

        // Set up the ViewPager with the selections adapter
        mViewPager = (ViewPager) findViewById(R.id.dvl_container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.dvl_tabs);
        tabLayout.setupWithViewPager(mViewPager, true);


        Toolbar toolbar = (Toolbar) findViewById(R.id.dash_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_listings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent createListingsIntent = new Intent(DrawerViewListingsActivity.this, CreateListingsActivity.class);
               createListingsIntent.putExtra(CreateListingsActivity.EXTRA_EDIT_MODE_BOOLEAN_KEY, false);
               startActivity(createListingsIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_view_listings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.dvlm_action_search:
                Intent searchIntent = new Intent(DrawerViewListingsActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.dlvd_nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(DrawerViewListingsActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
            case R.id.dlvd_nav_dashboard:
                Intent dashboardIntent = new Intent(DrawerViewListingsActivity.this, MyListingsActivity.class);
                startActivity(dashboardIntent);
                closeDrawer();
                break;
            default:
                closeDrawer();
                break;
        }
        return true;
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
