package com.krparajuli.collegethrift.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.krparajuli.collegethrift.R;

public class MyMessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages);

//        Intent messageWindowIntent = new Intent(MyMessagesActivity.this, MessageWindowActivity.class);
//        startActivity(messageWindowIntent);
//        finish();
//
//        RecyclerView mcRecyclerView = (RecyclerView) findViewById(R.id.mc_recycler_view);
//        mcRecyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("MyMessagesActivity", "onClick: asdsa");
//                Intent messageWindowIntent = new Intent(MyMessagesActivity.this, MessageWindowActivity.class);
//                startActivity(messageWindowIntent);
//            }
//        });
    }
}
