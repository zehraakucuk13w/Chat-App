package model;

import java.util.ArrayList;
import java.util.List;

import service.GeminiService;

/**
 * ChatBot model that manages conversations with Gemini AI.
 */
public class ChatBot {

    private final GeminiService geminiService;
    private final List<BotMessage> conversationHistory;

    public static class BotMessage {
        public final String content;
        public final boolean isFromUser;
        public final long timestamp;

        public BotMessage(String content, boolean isFromUser) {
            this.content = content;
            this.isFromUser = isFromUser;
            this.timestamp = System.currentTimeMillis();
        }
    }

    public interface ChatCallback {
        void onResponse(String response);

        void onError(String error);
    }

    public ChatBot(String apiKey) {
        this.geminiService = new GeminiService(apiKey);
        this.conversationHistory = new ArrayList<>();
    }

    /**
     * Sends a message to the AI and receives a response.
     */
    public void sendMessage(String message, ChatCallback callback) {
        // Add user message to history
        conversationHistory.add(new BotMessage(message, true));

        // Send to Gemini API
        geminiService.sendMessage(message, new GeminiService.GeminiCallback() {
            @Override
            public void onSuccess(String response) {
                // Add bot response to history
                conversationHistory.add(new BotMessage(response, false));
                callback.onResponse(response);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    /**
     * Returns the conversation history.
     */
    public List<BotMessage> getConversationHistory() {
        return conversationHistory;
    }

    /**
     * Clears the conversation history.
     */
    public void clearHistory() {
        conversationHistory.clear();
    }
}
