package com.krparajuli.collegethrift.Activity;

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
import com.krparajuli.collegethrift.FBDatabase;
import com.krparajuli.collegethrift.Firebase.FBUserAuthentication;
import com.krparajuli.collegethrift.R;

import java.util.HashMap;

public class CreateListingsActivity extends Activity {

    private EditText clTitle, clDesc, clPrice;
    private RadioButton clSale, clTrade, clGiveaway;
    private Button clSubmit;

    private String TAG = "---------LOG:";

    private ImageButton clImageCapture;
    private CameraManager camManager;

    public static final int REQUEST_IMAGE_CAPTURE = 1;


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
                try {
                    DatabaseReference listingsRef = FBDatabase.getListingsDbRef();

                    HashMap<String, Object> inputObject = getCreateListingObject();
                    Log.v(TAG, inputObject.toString());
                    listingsRef.push().setValue(inputObject);
                } catch (Exception e) {
                    Log.v("Error: ", "Database Connection");
                }
                finish();
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

    private HashMap<String, Object> getCreateListingObject() {
        HashMap<String, Object> listing = new HashMap<>();
        listing.put("title", clTitle.getText().toString().trim());
        listing.put("desc", clDesc.getText().toString().trim());
        listing.put("sale", clSale.isChecked());
        listing.put("trade", clTrade.isChecked());
        listing.put("giveaway", clTrade.isChecked());
        listing.put("price", clPrice.getText().toString().trim());
        listing.put("lister", FBUserAuthentication.getUser().getUid());

        return listing;
    }

    private void dispatchTakePictureIntent() {
        Log.v(TAG, "ASD");
    }
}
