package com.example.android.voiceassistant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.voiceassistant.pojo.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int USER_MESSAGE = 0;
    private static final int ASSISTANT_MESSAGE = 1;

    public List<Message> messageList = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        return message.getUser() ? USER_MESSAGE : ASSISTANT_MESSAGE;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layout;
        if (viewType == USER_MESSAGE) {
            layout = R.layout.user_message;
        } else {
            layout = R.layout.assistant_message;
        }

        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(layout, viewGroup, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder viewHolder, int index) {
        Message message = messageList.get(index);
        ((MessageViewHolder)viewHolder).bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView messageTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            messageTime = itemView.findViewById(R.id.messageTime);
        }

        public void bind(Message message) {
            messageText.setText(message.getText());
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            messageTime.setText(dateFormat.format(message.getDate()));
        }
    }
}
