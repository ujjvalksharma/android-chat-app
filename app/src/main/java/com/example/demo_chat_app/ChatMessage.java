package com.example.demo_chat_app;

public class ChatMessage {

   String username;
   String textMessage;

    public ChatMessage(){

    }
    public ChatMessage(String username, String textMessage) {
        this.username = username;
        this.textMessage = textMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "username='" + username + '\'' +
                ", textMessage='" + textMessage + '\'' +
                '}';
    }
}
