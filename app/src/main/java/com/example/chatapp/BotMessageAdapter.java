package com.example.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import model.ChatBot;

/**
 * Adapter for displaying bot chat messages in RecyclerView.
 */
public class BotMessageAdapter extends RecyclerView.Adapter<BotMessageAdapter.ViewHolder> {

    private final List<ChatBot.BotMessage> messages;

    public BotMessageAdapter(List<ChatBot.BotMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bot_message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatBot.BotMessage message = messages.get(position);

        if (message.isFromUser) {
            holder.tvUserMessage.setText(message.content);
            holder.tvUserMessage.setVisibility(View.VISIBLE);
            holder.tvBotMessage.setVisibility(View.GONE);
        } else {
            holder.tvBotMessage.setText(message.content);
            holder.tvBotMessage.setVisibility(View.VISIBLE);
            holder.tvUserMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserMessage;
        TextView tvBotMessage;

        ViewHolder(View itemView) {
            super(itemView);
            tvUserMessage = itemView.findViewById(R.id.tvUserMessage);
            tvBotMessage = itemView.findViewById(R.id.tvBotMessage);
        }
    }
}
