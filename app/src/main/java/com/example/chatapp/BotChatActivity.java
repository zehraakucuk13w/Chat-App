package com.example.chatapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import model.ChatBot;

/**
 * Activity for chatting with the AI bot.
 */
public class BotChatActivity extends AppCompatActivity {

    // TODO: Replace with your Gemini API key
    private static final String GEMINI_API_KEY = "YOUR_GEMINI_API_KEY_HERE";

    private EditText etBotMessage;
    private Button btnSendToBot;
    private Button btnClearChat;
    private RecyclerView rvBotMessages;
    private ProgressBar progressBar;

    private ChatBot chatBot;
    private BotMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_chat);

        // Initialize views
        etBotMessage = findViewById(R.id.etBotMessage);
        btnSendToBot = findViewById(R.id.btnSendToBot);
        btnClearChat = findViewById(R.id.btnClearChat);
        rvBotMessages = findViewById(R.id.rvBotMessages);
        progressBar = findViewById(R.id.progressBar);

        // Initialize ChatBot
        chatBot = new ChatBot(GEMINI_API_KEY);

        // Setup RecyclerView
        adapter = new BotMessageAdapter(chatBot.getConversationHistory());
        rvBotMessages.setLayoutManager(new LinearLayoutManager(this));
        rvBotMessages.setAdapter(adapter);

        // Send button click
        btnSendToBot.setOnClickListener(v -> sendMessage());

        // Clear button click
        btnClearChat.setOnClickListener(v -> {
            chatBot.clearHistory();
            adapter.notifyDataSetChanged();
        });
    }

    private void sendMessage() {
        String message = etBotMessage.getText().toString().trim();
        if (message.isEmpty()) {
            return;
        }

        // Clear input
        etBotMessage.setText("");

        // Show loading
        progressBar.setVisibility(View.VISIBLE);
        btnSendToBot.setEnabled(false);

        // Add user message to UI immediately
        adapter.notifyItemInserted(chatBot.getConversationHistory().size());

        // Send to AI
        chatBot.sendMessage(message, new ChatBot.ChatCallback() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                btnSendToBot.setEnabled(true);

                // Update UI with bot response
                adapter.notifyItemInserted(chatBot.getConversationHistory().size() - 1);
                rvBotMessages.scrollToPosition(chatBot.getConversationHistory().size() - 1);
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                btnSendToBot.setEnabled(true);
                Toast.makeText(BotChatActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });

        // Scroll to latest message
        if (chatBot.getConversationHistory().size() > 0) {
            rvBotMessages.scrollToPosition(chatBot.getConversationHistory().size() - 1);
        }
    }
}
