package com.krparajuli.collegethrift.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.krparajuli.collegethrift.R;

public class MyConversationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_conversations);

//        Intent messageWindowIntent = new Intent(MyConversationsActivity.this, MessageWindowActivity.class);
//        startActivity(messageWindowIntent);
//        finish();
//
//        RecyclerView mcRecyclerView = (RecyclerView) findViewById(R.id.mc_recycler_view);
//        mcRecyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("MyConversationsActivity", "onClick: asdsa");
//                Intent messageWindowIntent = new Intent(MyConversationsActivity.this, MessageWindowActivity.class);
//                startActivity(messageWindowIntent);
//            }
//        });
    }
}
