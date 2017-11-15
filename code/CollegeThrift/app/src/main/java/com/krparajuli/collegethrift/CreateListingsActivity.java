package com.krparajuli.collegethrift;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateListingsActivity extends Activity {

    private EditText clTitle, clDesc, clPrice;
    private RadioButton clSale, clTrade, clGiveaway;
    private Button clSubmit;

    private String inputValues;

    private String TAG = "---------LOG:";

    private ImageButton clImageCapture;
    private CameraManager camManager;

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference collegeThriftDbRef = database.getReference("collegethrift-base");
    final DatabaseReference listingsRef = collegeThriftDbRef.child("listings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listings);

        clTitle = (EditText) findViewById(R.id.cl_edit_listing_title);
        clDesc = (EditText) findViewById(R.id.cl_edit_listing_desc);
        clPrice = (EditText) findViewById(R.id.cl_edit_price);
        clSale = (RadioButton) findViewById(R.id.cl_radio_sale);
        clTrade = (RadioButton) findViewById(R.id.cl_radio_trade);
        clGiveaway = (RadioButton) findViewById(R.id.cl_radio_giveaway);
        clSubmit = (Button) findViewById(R.id.cl_submit);

        clImageCapture = (ImageButton) findViewById(R.id.cl_thumb_image_main);

        clSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValues = getCreateListingValues();
                Log.v(TAG, inputValues);
                listingsRef.push().setValue(inputValues);
                }
        });

        clImageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent cameraIntent = new Intent(CreateListingsActivity.this, CameraActivity.class);
//                startActivity(cameraIntent);
            }
        });
    }

    private String getCreateListingValues() {
        return "{"
                    +"title: "+ clTitle.getText()
                    + ", desc: "+ clDesc.getText()
                    + ", type: " +  clSale.isChecked()
                    + ", price: " + clPrice.getText()
                    + ", status: 1"
                    + ", listerId: 91203123192312"
                +"}";
    }

    private void dispatchTakePictureIntent() {
        Log.v(TAG, "ASD");
    }
}
