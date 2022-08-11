package com.example.demo_chat_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChatMessageRecycleViewAdaptor.NameClickListener{

    RecyclerView recyclerView;
    ChatMessageRecycleViewAdaptor adapter;
    String username;
    int leagueId;
    Button button;
    List<ChatMessage> chatMessages=new ArrayList<ChatMessage>();
    /*
    To do:
    1) Get token of all members of this league and notify about the chat room
    2) Change Ui and display messages coming from realtime database

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        // this.username = intent.getStringExtra("username"); //user object/ usernmae from intent
        // this.leagueId = Integer.parseInt(intent.getStringExtra("leagueId"));
        this.username="dummy-username";
        this.leagueId=1;
         this.button= findViewById(R.id.button);
         getAllChats();
    }

    private void getAllChats() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-example-e11f0-default-rtdb.firebaseio.com/");
        DatabaseReference ref = database.getReference("final_project");
        DatabaseReference stickerUserRelationRef = ref.child("chat_messages_for_league_"+leagueId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ChatMessage value=ds.getValue(ChatMessage.class);
                    System.out.println(" new chat message: "+value);
                    chatMessages.add(value);
                }
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new ChatMessageRecycleViewAdaptor(MainActivity.this, chatMessages, username);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        stickerUserRelationRef.addListenerForSingleValueEvent(valueEventListener);

    }

    public void sendMessage(View v) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-example-e11f0-default-rtdb.firebaseio.com/");
        DatabaseReference ref = database.getReference("final_project");
        DatabaseReference usersRef = ref.child("chat_messages_for_league_"+leagueId);
        EditText messageToBeSent= (EditText) findViewById(R.id.editTextMessageToBeSent);
        System.out.println("message to be sent: "+messageToBeSent.getText().toString());
        if(TextUtils.isEmpty(messageToBeSent.getText().toString())){
            Toast.makeText(this, "message is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        ChatMessage chatMessage=new ChatMessage(this.username,messageToBeSent.getText().toString());
        System.out.println("chatMessage: "+chatMessage);
        usersRef.push().setValue(chatMessage);
    }

    @Override
    public void onClickName(View view, int position) {

    }
}