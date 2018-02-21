package com.krparajuli.collegethrift.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.krparajuli.collegethrift.Firebase.FBUserAuthentication;
import com.krparajuli.collegethrift.R;

public class ViewListingsActivity extends AppCompatActivity {


    @Override
    protected  void onStart() {
        super.onStart();
        if (!FBUserAuthentication.userSignedIn()) {
            Intent loginActivity = new Intent(ViewListingsActivity.this, LoginActivity.class);
            startActivity(loginActivity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_listings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton createListingsIcon = (FloatingActionButton) findViewById(R.id.fab);
        createListingsIcon.setOnClickListener(new View.OnClickListener() {
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
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                FBUserAuthentication.signOut(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
