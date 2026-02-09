package com.example.chatapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.MessageAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import constants.DBConstants;
import database.DatabaseHelper;
import model.Message;

public class ChatActivity extends AppCompatActivity {

    private EditText etMessage;
    private Button btnSend;
    private RecyclerView rvMessages;

    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();

    private DatabaseHelper dbHelper;

    private int currentUserId = 1; // Temporary: Logged-in user ID
    private int currentChatId = 101; // Temporary: Chat ID (should be received via intent in production)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        rvMessages = findViewById(R.id.rvMessages);

        dbHelper = new DatabaseHelper(this);

        loadMessagesFromDatabase();

        messageAdapter = new MessageAdapter(messageList);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(messageAdapter);

        btnSend.setOnClickListener(v -> {
            String content = etMessage.getText().toString().trim();
            if (!content.isEmpty()) {
                long timestamp = new Date().getTime();

                boolean inserted = dbHelper.insertMessage(currentChatId, currentUserId, null, content, timestamp);
                if (inserted) {
                    // Add new message to list
                    Message newMsg = new Message(0, currentChatId, currentUserId, -1, content, timestamp, false);
                    messageList.add(newMsg);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    rvMessages.scrollToPosition(messageList.size() - 1);
                    etMessage.setText("");
                } else {
                    Toast.makeText(this, "Failed to save message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadMessagesFromDatabase() {
        messageList.clear();
        Cursor cursor = dbHelper.getMessages(currentChatId);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_MESSAGE_ID));
            int senderId = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_SENDER_ID));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COL_CONTENT));
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(DBConstants.COL_TIMESTAMP));
            boolean isRead = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COL_IS_READ)) == 1;

            messageList.add(new Message(id, currentChatId, senderId, -1, content, timestamp, isRead));
        }
        cursor.close();
    }
}
