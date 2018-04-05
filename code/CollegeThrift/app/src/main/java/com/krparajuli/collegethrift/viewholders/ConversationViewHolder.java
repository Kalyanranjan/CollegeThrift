package com.krparajuli.collegethrift.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.krparajuli.collegethrift.R;
import com.krparajuli.collegethrift.models.Conversation;

public class ConversationViewHolder extends RecyclerView.ViewHolder {
    private TextView mOtherUserNameView, mListingTitleView, mLastMessageView;

    public ConversationViewHolder(View itemView) {
        super(itemView);

        mOtherUserNameView = (TextView) itemView.findViewById(R.id.lc_other_user);
        mListingTitleView = (TextView) itemView.findViewById(R.id.lc_item_title);
        mLastMessageView = (TextView) itemView.findViewById(R.id.lc_last_message);
    }

    public void bindToConversation(Conversation conversation) {
        mOtherUserNameView.setText(conversation.getOtherUserUid());
        mListingTitleView.setText(conversation.getListingUid());
        mLastMessageView.setText(conversation.getLastMessage());
    }
}
