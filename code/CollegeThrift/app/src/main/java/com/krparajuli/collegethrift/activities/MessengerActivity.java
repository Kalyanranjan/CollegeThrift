package com.krparajuli.collegethrift.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.bassaer.chatmessageview.view.ChatView;
import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.User;


public class MessengerActivity extends AppCompatActivity {

    private ChatView mChatView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        //User id
//        int myId = 0;
//        //User icon
//        Bitmap myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
//        //User name
//        String myName = "Michael";
//
//        int yourId = 1;
//        Bitmap yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
//        String yourName = "Emily";

//        final User me = new User(myId, myName, myIcon);
//        final User you = new User(yourId, yourName, yourIcon);
//
//        mChatView = (ChatView)findViewById(R.id.chat_view);
//
//        //Set UI parameters if you need
//        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
//        mChatView.setLeftBubbleColor(Color.WHITE);
//        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
//        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan500));
//        mChatView.setSendIcon(R.drawable.ic_action_send);
//        mChatView.setRightMessageTextColor(Color.WHITE);
//        mChatView.setLeftMessageTextColor(Color.BLACK);
//        mChatView.setUsernameTextColor(Color.WHITE);
//        mChatView.setSendTimeTextColor(Color.WHITE);
//        mChatView.setDateSeparatorColor(Color.WHITE);
//        mChatView.setInputTextHint("new message...");
//        mChatView.setMessageMarginTop(5);
//        mChatView.setMessageMarginBottom(5);
//
//        //Click Send Button
//        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //new message
//                Message message = new Message.Builder()
//                        .setUser(me)
//                        .setRightMessage(true)
//                        .setMessageText(mChatView.getInputText())
//                        .hideIcon(true)
//                        .build();
//                //Set to chat view
//                mChatView.send(message);
//                //Reset edit text
//                mChatView.setInputText("");
//
//                //Receive message
//                final Message receivedMessage = new Message.Builder()
//                        .setUser(you)
//                        .setRightMessage(false)
//                        .setMessageText(ChatBot.talk(me.getName(), message.getMessageText()))
//                        .build();
//
//                // This is a demo bot
//                // Return within 3 seconds
//                int sendDelay = (new Random().nextInt(4) + 1) * 1000;
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mChatView.receive(receivedMessage);
//                    }
//                }, sendDelay);
//            }
//
//        });

    }
}
