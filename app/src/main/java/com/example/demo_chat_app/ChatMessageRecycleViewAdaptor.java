package com.example.demo_chat_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.List;

public class ChatMessageRecycleViewAdaptor extends RecyclerView.Adapter<ChatMessageRecycleViewAdaptor.ViewHolder>{

        private LayoutInflater layoutInflater;
        private NameClickListener nameClickListener;
        private List<ChatMessage> chatMessages;
        Context context;
        String username;
   public ChatMessageRecycleViewAdaptor(Context context,List<ChatMessage> chatMessages, String username ) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context=context;
        this.chatMessages=chatMessages;
        this.username=username;
    }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.chat_message, parent, false));
    }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,  int position) {
            ChatMessage tempChatMessage=chatMessages.get(position);
            if(tempChatMessage.getUsername().equals(username)){
                viewHolder.senderChatTextView.setContentDescription(tempChatMessage.getUsername()+ " : "+tempChatMessage.getTextMessage());
            }else {
                viewHolder.reciverChatTextView.setContentDescription(tempChatMessage.getUsername()+ " : "+tempChatMessage.getTextMessage());
            }

    }

        @Override
        public int getItemCount() {
        return chatMessages.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
          TextView senderChatTextView;
          TextView reciverChatTextView;
            ViewHolder(View itemView) {
                super(itemView);
                senderChatTextView = itemView.findViewById(R.id.SenderChatTextView);
                reciverChatTextView = itemView.findViewById(R.id.ReciverChatTextView);
            }

            @Override
            public void onClick(View view) {
                if (nameClickListener != null){
                    nameClickListener.onClickName(view, getAdapterPosition());
                }
            }
        }

    void setClickListener(NameClickListener itemClickListener) {
        this.nameClickListener = itemClickListener;
    }

    public interface NameClickListener {
        void onClickName(View view, int position);
    }


}