package com.krparajuli.collegethrift.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.activities.ListingDetailActivity;
import com.krparajuli.collegethrift.activities.MessengerActivity;
import com.krparajuli.collegethrift.models.Conversation;

public class ConversationViewHolder extends RecyclerView.ViewHolder {

    private TextView mOtherUserNameView, mListingTitleView, mLastMessageView;

    public ConversationViewHolder(View itemView) {
        super(itemView);

        mOtherUserNameView = (TextView) itemView.findViewById(R.id.lc_other_user);
        mListingTitleView = (TextView) itemView.findViewById(R.id.lc_item_title);
        mLastMessageView = (TextView) itemView.findViewById(R.id.lc_last_message);
    }

    public void bindToConversation(final Conversation conversation, final Context context) {
        mOtherUserNameView.setText(conversation.getOtherUserName() + " <"+conversation.getOtherUserEmail()+">");
        mListingTitleView.setText(conversation.getListingTitle() + " - $" + conversation.getListingPrice());
        mLastMessageView.setText(conversation.getLastMessage());

        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch ListingDetailsActivity
                Intent messengerIntent = new Intent(context, MessengerActivity.class);
                messengerIntent.putExtra(MessengerActivity.EXTRA_ARRIVED_FROM_LISTING_DETAIL, false);
                messengerIntent.putExtra(MessengerActivity.EXTRA_OTHER_USER_UID_KEY, conversation.getOtherUserUid());
                messengerIntent.putExtra(MessengerActivity.EXTRA_LISTING_UID_KEY, conversation.getListingUid());
                messengerIntent.putExtra(MessengerActivity.EXTRA_CONVERSATION_ID, conversation.getConvUid());
                context.startActivity(messengerIntent);
            }
        });
    }
}
