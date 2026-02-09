package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import model.User;
import model.UserRepository;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private Button btnChatWithAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Main layout in XML is adjusted according to system bar spacing
        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Bind XML components
        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        btnChatWithAI = findViewById(R.id.btnChatWithAI);

        // Get username from LoginActivity
        String username = getIntent().getStringExtra("username");

        // Get user from database
        UserRepository userRepository = new UserRepository(this);
        User user = userRepository.getUserByUsername(username);

        if (user != null) {
            tvWelcome.setText("Welcome, " + user.getUsername());
        } else {
            tvWelcome.setText("Welcome!");
        }

        // Open AI Chat when button is clicked
        btnChatWithAI.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BotChatActivity.class);
            startActivity(intent);
        });

        // Return to LoginActivity when logout button is pressed
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
