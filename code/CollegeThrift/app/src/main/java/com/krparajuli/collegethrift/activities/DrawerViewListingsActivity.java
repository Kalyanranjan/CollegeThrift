package com.krparajuli.collegethrift.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.fragments.ViewListingsRecentFragment;
import com.krparajuli.collegethrift.utils.ESPasswordGetter;

import java.util.Iterator;

public class DrawerViewListingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DrawerViewListings";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    private String mUserEmail;
    private String mUserFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ESPasswordGetter.retrieveElasticSearchPaswordFromDb();
        setContentView(R.layout.activity_drawer_view_listings);

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

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                mUserEmail = iter.next().getValue().toString();
                mUserFullName = iter.next().getValue().toString();

                ((TextView) findViewById(R.id.drawer_user_name_text)).setText(mUserFullName);
                ((TextView) findViewById(R.id.drawer_user_email_text)).setText(mUserEmail);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "DatabaseError: " + databaseError.getMessage());
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            final Fragment[] mFragments = new Fragment[]{
                    new ViewListingsRecentFragment()
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.heading_recent),
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
        mViewPager = (ViewPager) findViewById(R.id.dvl_container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.dvl_tabs);
        tabLayout.setupWithViewPager(mViewPager, true);
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

        switch (id) {
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
        switch (id) {
            case R.id.dlvd_nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(DrawerViewListingsActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
                break;
            case R.id.dlvd_my_listings:
                Intent dashboardIntent = new Intent(DrawerViewListingsActivity.this, MyListingsActivity.class);
                startActivity(dashboardIntent);
                closeDrawer();
                break;
            case R.id.dlvd_nav_messages:
                Intent conversationIntent = new Intent(DrawerViewListingsActivity.this, MyConversationsActivity.class);
                startActivity(conversationIntent);
                closeDrawer();
                break;
            case R.id.dlvd_nav_my_favorites:
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


    private void initFCM(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "initFCM: token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "sendRegistrationToServer: sending token to server: " + token);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("userFCMToken")
                .setValue(token);
    }
}
