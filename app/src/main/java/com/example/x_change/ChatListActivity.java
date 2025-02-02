package com.example.x_change;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.x_change.adapters.ChatListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ChatListAdapter adapter;
    String uId = FirebaseAuth.getInstance().getUid();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("people").child(uId).child("chatsIds");
    ArrayList<String> chatsIds = new ArrayList<>();
    CardView menuBtn, profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        recyclerView = findViewById(R.id.chatList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatListAdapter(chatsIds, this);
        recyclerView.setAdapter(adapter);
        menuBtn = findViewById(R.id.chatList_menuBtn);
        profileBtn = findViewById(R.id.chatList_profileBtn);

        menuBtn.setOnClickListener(view -> { // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        profileBtn.setOnClickListener(view -> { // go to profile page
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() { // gather list of chats
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String currId = snap.getValue(String.class);
                    if (uId.compareTo(currId) < 0) { // smaller id comes first
                        currId = uId + currId;
                    } else {
                        currId = currId + uId;
                    }
                    chatsIds.add(currId);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}