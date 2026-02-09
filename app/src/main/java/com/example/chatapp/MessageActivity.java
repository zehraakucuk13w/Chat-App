package com.example.chatapp;

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

import model.AbstractChat;
import model.AbstractUser;
import model.GroupChat;
import model.Message;
import model.User;
import service.ChatService;

public class MessageActivity extends AppCompatActivity {

    private EditText etMessage;
    private Button btnSend;
    private RecyclerView rvMessages;

    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();

    private AbstractUser currentUser;
    private AbstractChat currentChat;
    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Bind UI components
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        rvMessages = findViewById(R.id.rvMessages);

        // Initialize services, create temporary user/chat
        chatService = new ChatService(this);
        currentUser = new User(1, "user1", "mail", "123456"); // Test user
        currentChat = createMockGroupChat();

        // Get existing messages
        messageList = currentChat.getMessages();

        // RecyclerView settings
        messageAdapter = new MessageAdapter(messageList);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(messageAdapter);

        // Send message
        btnSend.setOnClickListener(v -> {
            String content = etMessage.getText().toString().trim();
            if (!content.isEmpty()) {
                long timestamp = new Date().getTime();
                boolean sent = chatService.sendMessage(currentChat, currentUser, content, timestamp);

                if (sent) {
                    Message newMessage = new Message(0, currentChat.getChatId(), currentUser.getUserId(), -1, content,
                            timestamp, false);
                    messageList.add(newMessage);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    rvMessages.scrollToPosition(messageList.size() - 1);
                    etMessage.setText("");
                } else {
                    Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Mock group chat for testing
    private AbstractChat createMockGroupChat() {
        return new GroupChat(this, 101, System.currentTimeMillis(), "Test Group", List.of(currentUser));
    }
}
