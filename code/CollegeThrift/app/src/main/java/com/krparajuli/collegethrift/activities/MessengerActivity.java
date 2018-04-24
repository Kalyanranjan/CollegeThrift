package com.krparajuli.collegethrift.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Listing;
import com.krparajuli.collegethrift.models.Message;
import com.krparajuli.collegethrift.models.User;
import com.krparajuli.collegethrift.venmo.VenmoLibrary;

import java.util.HashMap;
import java.util.Iterator;


public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    public static final int REQUEST_CODE_VENMO_APP_SWITCH = 10000;

    public static String EXTRA_ARRIVED_FROM_LISTING_DETAIL = "false";
    public static String EXTRA_OTHER_USER_UID_KEY = "OTHER USER KEY";
    public static String EXTRA_OTHER_USER_EMAIL_KEY = "OTHER USER EMAIL KEY";
    public static String EXTRA_OTHER_USER_NAME_KEY = "OTHER USER NAME KEY";
    public static String EXTRA_LISTING_UID_KEY = "LISTING KEY";
    public static String EXTRA_LISTING_TITLE_KEY = "LISTING TITLE KEY";
    public static String EXTRA_LISTING_PRICE_KEY = "LISTING_PRICE_KEY";
    public static String EXTRA_CONVERSATION_ID = "";

    private boolean mNewConversation = true;
    private String mOtherUserUid = "RandomUserUid";
    private String mListingUid = "ListingUid";
    private String mConversationId = "";

    private String mThisUserName = "User";
    private String mThisUserEmail = "generic.user@trincoll.edu";
    private String mOtherUserName = "User";
    private String mOtherUserEmail = "generic.user@trincoll.edu";
    private String mListingTitle = "Listing";
    private int mListingPrice = 0;
    private String mListingSellerUid;

    private ChatView mChatView;

    private int mNumDisplayedMessages = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Toolbar toolbar = (Toolbar) findViewById(R.id.messenger_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find If this is a new conversation, put details and messages in place
        processDetails();

        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account_circle);
        //User name
        String myName = "You";

        final ChatUser me = new ChatUser(myId, myName, myIcon);
        mChatView = (ChatView) findViewById(R.id.chat_view);

//        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("New Message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = mChatView.getInputText().toString();
                if (mNewConversation) {
                    createConversationAndSendToServer(messageText);
                } else {
                    updateConversation(messageText);
                }

                //new message
                com.github.bassaer.chatmessageview.model.Message message =
                        new com.github.bassaer.chatmessageview.model.Message.Builder()
                                .setUser(me)
                                .setRight(true)
                                .setText(mChatView.getInputText())
                                .hideIcon(true)
                                .build();

                mChatView.send(message);
                mNumDisplayedMessages++;

//                //Reset edit text
                mChatView.setInputText("");
            }
        });

        ((TextView) findViewById(R.id.messenger_toolbar_user_name_view)).setText(formatUserNameAndEmail(false));
        ((TextView) findViewById(R.id.messenger_toolbar_listing_name_view)).setText(mListingTitle + " - $" + mListingPrice);

        ((Button) findViewById(R.id.messenger_pay_using_venmo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTakingToVenmoDialogue();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void processDetails() {
        Intent intent = getIntent();
        mListingUid = intent.getStringExtra(EXTRA_LISTING_UID_KEY);
        mOtherUserUid = intent.getStringExtra(EXTRA_OTHER_USER_UID_KEY);
        mListingPrice = intent.getIntExtra(EXTRA_LISTING_PRICE_KEY, 0);
        mListingTitle = intent.getStringExtra(EXTRA_LISTING_TITLE_KEY);
        mOtherUserName = intent.getStringExtra(EXTRA_OTHER_USER_NAME_KEY);
        mOtherUserEmail = intent.getStringExtra(EXTRA_OTHER_USER_EMAIL_KEY);


        getAdditionalDetails();

        if (getIntent().getBooleanExtra(EXTRA_ARRIVED_FROM_LISTING_DETAIL, true)) { // If arrived from Listing Detail Check if previous conversation exists
            DatabaseReference convRef = FirebaseDatabase.getInstance().getReference()
                    .child("conversationsByListing")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("buying").child(mListingUid).child("convUid");
            convRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        mNewConversation = true;
                        Log.d(TAG, "onDataChange: No Previous Conversation Found" + dataSnapshot.toString());
                    } else {
                        mNewConversation = false;
                        mConversationId = dataSnapshot.getValue().toString();
                        Log.d(TAG, "onDataChange: Previous Conversation Found" + mConversationId);
                        displayPreviousMessages(mConversationId);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            mNewConversation = false;
            mConversationId = getIntent().getStringExtra(EXTRA_CONVERSATION_ID);
            displayPreviousMessages(mConversationId);
        }
    }

    private void getAdditionalDetails() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("users").child(mOtherUserUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User otherUser = dataSnapshot.getValue(User.class);
                        mOtherUserName = otherUser.getFullname();
                        mOtherUserEmail = otherUser.getEmail();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        dbRef.child("listings").child(mListingUid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Listing listing = dataSnapshot.getValue(Listing.class);
                        mListingTitle = listing.getTitle();
                        mListingPrice = listing.getPrice();
                        mListingSellerUid = listing.getListerUid();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        dbRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User otherUser = dataSnapshot.getValue(User.class);
                        mThisUserName = otherUser.getFullname();
                        mThisUserEmail = otherUser.getEmail();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void createConversationAndSendToServer(String messageText) {
        DatabaseReference convUserRef = FirebaseDatabase.getInstance().getReference().child("conversations");
        long messageTimestamp = System.currentTimeMillis();
        String thisUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a new Conversation for the buyer
        DatabaseReference buyerConvNodeReference = convUserRef.child(thisUserUid)
                .child("buying");
        String convKey = buyerConvNodeReference.push().getKey();

        HashMap<String, Object> buyerConvMap = new HashMap<>();
        buyerConvMap.put("convUid", convKey);
        buyerConvMap.put("otherUserUid", mOtherUserUid);
        buyerConvMap.put("otherUserName", mOtherUserName);
        buyerConvMap.put("otherUserEmail", mOtherUserEmail);
        buyerConvMap.put("listingUid", mListingUid);
        buyerConvMap.put("listingTitle", mListingTitle);
        buyerConvMap.put("listingPrice", mListingPrice);
        buyerConvMap.put("lastMessage", messageText);
        buyerConvMap.put("lastMessageTime", messageTimestamp);
        buyerConvNodeReference.child(convKey).setValue(buyerConvMap);

        //Create a New Conversation for the lister
        DatabaseReference listerConvNodeReference = convUserRef.child(mOtherUserUid)
                .child("selling");
        HashMap<String, Object> listerConvMap = new HashMap<>();
        listerConvMap.put("convUid", convKey);
        listerConvMap.put("otherUserUid", thisUserUid);
        listerConvMap.put("otherUserName", mThisUserName);
        listerConvMap.put("otherUserEmail", mThisUserEmail);
        listerConvMap.put("listingUid", mListingUid);
        listerConvMap.put("listingTitle", mListingTitle);
        listerConvMap.put("listingPrice", mListingPrice);
        listerConvMap.put("lastMessage", messageText);
        listerConvMap.put("lastMessageTime", messageTimestamp);
        listerConvNodeReference.child(convKey).setValue(listerConvMap);

        DatabaseReference convByListingRef = FirebaseDatabase.getInstance().getReference().child("conversationsByListing");
        convByListingRef.child(thisUserUid).child("buying").child(mListingUid).setValue(buyerConvMap);
        convByListingRef.child(mOtherUserUid).child("selling").child(mListingUid).child(convKey).setValue(listerConvMap);

        mConversationId = convKey;
        mNewConversation = false;
        insertMessage(mConversationId, thisUserUid, mOtherUserUid, messageText, messageTimestamp);
    }

    private void updateConversation(String messageText) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("conversations");
        long messageTimestamp = System.currentTimeMillis();
        String thisUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        boolean selling = mListingSellerUid.equals(thisUserUid);

        DatabaseReference buyerConvNodeReference = reference.child(thisUserUid)
                .child(selling ? "selling" : "buying").child(mConversationId);
        buyerConvNodeReference.child("lastMessage").setValue(messageText);
        buyerConvNodeReference.child("lastMessageTime").setValue(messageTimestamp);


        DatabaseReference listerConvNodeReference = reference.child(mOtherUserUid)
                .child(!selling ? "selling" : "buying").child(mConversationId);
        listerConvNodeReference.child("lastMessage").setValue(messageText);
        listerConvNodeReference.child("lastMessageTime").setValue(messageTimestamp);

        insertMessage(mConversationId, thisUserUid, mOtherUserUid, messageText, messageTimestamp);
        //Need to update conversation by Listings too??
    }

    private void insertMessage(String convUid, String senderUid, String recieverUid, String messageText, long messageTime) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("messages").child(convUid);
        HashMap<String, Object> messageMap = new HashMap<>();
        messageMap.put("messageSenderUid", senderUid);
        messageMap.put("messageReceiverUid", recieverUid);
        messageMap.put("messageText", messageText);
        messageMap.put("messageTime", messageTime);
        reference.push().setValue(messageMap);
    }

    private void displayPreviousMessages(String convUid) {

        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        final String myName = formatUserNameAndEmail(true);

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = formatUserNameAndEmail(false);

        final ChatUser me = new ChatUser(myId, myName, myIcon);
        final ChatUser you = new ChatUser(yourId, yourName, yourIcon);


        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference()
                .child("messages").child(convUid);
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange:MESSAGE SNAPSHOT " + dataSnapshot.getValue().toString());
                Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();

                int numMessages = 0;

                Message message;
                DataSnapshot ds;
                while (iter.hasNext()) {
                    ds = iter.next();
                    numMessages++;
                    if (numMessages > mNumDisplayedMessages) {
                        message = ds.getValue(Message.class);
                        Log.d(TAG, "onDataChange: " + dataSnapshot.toString());
                        Log.d(TAG, "onDataChange:MESSAGE" + message.getMessageText());
                        com.github.bassaer.chatmessageview.model.Message dispMessage =
                                new com.github.bassaer.chatmessageview.model.Message.Builder()
                                        .setUser(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderUid()) ? me : you)
                                        .setRight(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderUid()))
                                        .setText(message.getMessageText())
                                        .hideIcon(true)
                                        .build();
                        mChatView.send(dispMessage);
                        mNumDisplayedMessages++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String formatUserNameAndEmail(boolean me) {
        if (me) {
            return "You";
        }
        return mOtherUserName + " <" + mOtherUserEmail + ">";
    }

    private void displayVenmoNotInstalledDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("Venmo Not installed")
                .setMessage("Cannot Proceed with the payment since Venmo is not installed. Please pay using other methods.")
                .setNegativeButton("OK", null).show();
    }

    private void displayListerVenmoNotConnectedDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("Lister's Venmo Not Connected")
                .setMessage("Cannot Proceed with the payment since the lister's venmo is not connected to the account")
                .setNegativeButton("OK", null).show();
    }

    private void displayTakingToVenmoDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm to exit to Venmo")
                .setMessage("Are you sure you want to continue with this payment through Venmo App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToVenmo();
                    }
                })
                .setNegativeButton("No", null).show();
    }

    private void goToVenmo() {
        if (VenmoLibrary.isVenmoInstalled(this)) {
            Intent venmoIntent = VenmoLibrary.openVenmoPayment("SD", "CollegeThrift", mOtherUserEmail, String.valueOf(mListingPrice), "CollegeThrift: Payment for <"+ mListingTitle+">", "Pay");
            startActivityForResult(venmoIntent, REQUEST_CODE_VENMO_APP_SWITCH);
        } else {
            displayVenmoNotInstalledDialogue();
        }
    }

}
