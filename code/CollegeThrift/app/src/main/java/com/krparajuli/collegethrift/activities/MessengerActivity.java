package com.krparajuli.collegethrift.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.bassaer.chatmessageview.model.ChatUser;
import com.github.bassaer.chatmessageview.model.Message;
import com.github.bassaer.chatmessageview.util.ChatBot;
import com.github.bassaer.chatmessageview.model.IChatUser;
import com.github.bassaer.chatmessageview.view.ChatView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.krparajuli.collegethrift.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;


public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    public static String EXTRA_NEW_CONVERSATION_BOOLEAN_KEY = "false";
    public static String EXTRA_OTHER_USER_UID_KEY = "";
    // private static String mListerName = "User";
    // private static String mListerEmail = "generic.user@trincoll.edu";
    public static String EXTRA_LISTING_UID_KEY = "";
    //private static String mListingTitle = "Listing";
    //Also add listing price, type, category

    private boolean mNewConversation = false;
    private String mOtherUserUid = "RandomUserUid";
    private String mListingUid = "ListingUid";
    private String mConversationId = "";

    // add other fields also


    private ChatView mChatView;

    private String yourName = "You";
    private String otherName = "Recipient";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        processDetails();


        //User id
        int myId = 0;
        //User icon
        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        String myName = "Michael";

        int yourId = 1;
        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        String yourName = "Emily";

        final ChatUser me = new ChatUser(myId, myName, myIcon);
        final ChatUser you = new ChatUser(yourId, yourName, yourIcon);

        mChatView = (ChatView)findViewById(R.id.chat_view);

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
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);

        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("conversations");
                String messageText = mChatView.getInputText().toString();
                String messageTimestamp = String.valueOf(System.currentTimeMillis());
                String thisUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (mNewConversation) {
                    // Create a new Conversation for the buyer
                    DatabaseReference buyerConvNodeReference = reference.child(thisUserUid)
                            .child("buying");
                    String buyerConvKey = buyerConvNodeReference.push().getKey();
                    HashMap<String, String> buyerConvMap = new HashMap<>();
                    buyerConvMap.put("otherUserUid", mOtherUserUid);
                    buyerConvMap.put("listingUid", mListingUid);
                    buyerConvMap.put("lastMessage", messageText);
                    buyerConvMap.put("lastMessageTime", messageTimestamp);
                    buyerConvNodeReference.child(buyerConvKey).setValue(buyerConvMap);

                    //Create a New Conversation for the lister
                    DatabaseReference listerConvNodeReference = reference.child(mOtherUserUid)
                            .child("selling");
                    String listerConvKey = listerConvNodeReference.push().getKey();
                    HashMap<String, String> listerConvMap = new HashMap<>();
                    listerConvMap.put("otherUserUid", thisUserUid);
                    listerConvMap.put("listingUid", mListingUid);
                    listerConvMap.put("lastMessage", messageText);
                    listerConvMap.put("lastMessageTime", messageTimestamp);
                    listerConvNodeReference.child(listerConvKey).setValue(listerConvMap);
                }

                //new message
                Message message = new Message.Builder()
                        .setUser(me)
                        .setRight(true)
                        .setText(mChatView.getInputText())
                        .hideIcon(true)
                        .build();
                //Set to chat view
                mChatView.send(message);
//                //Reset edit text
                mChatView.setInputText("");

                //Receive message
                final Message receivedMessage = new Message.Builder()
                        .setUser(you)
                        .setRight(false)
                        .setText(ChatBot.INSTANCE.talk(me.getName(), message.getText()))
                        .build();

//                // This is a demo bot
//                // Return within 3 seconds
                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.receive(receivedMessage);
                    }
                }, sendDelay);
            }

        });
    }

    private void processDetails() {
        if (mNewConversation = getIntent().getBooleanExtra(EXTRA_NEW_CONVERSATION_BOOLEAN_KEY, false)) {
            mListingUid = getIntent().getStringExtra(EXTRA_LISTING_UID_KEY);
            mOtherUserUid = getIntent().getStringExtra(EXTRA_OTHER_USER_UID_KEY);
        } else {
            // Non-new conversation action filler
        }
    }
}
