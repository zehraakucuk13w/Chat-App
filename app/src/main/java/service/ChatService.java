package service;

import android.content.Context;
import model.AbstractUser;
import model.AbstractChat;

public class ChatService {

    private final Context context;

    public ChatService(Context context) {
        this.context = context;
    }

    public boolean sendMessage(AbstractChat chat, AbstractUser sender, String content, long timestamp) {
        try {
            chat.sendMessage(sender, content, timestamp);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Errors can be logged
            return false;
        }
    }

    // If needed, methods like getMessages, editMessage, deleteMessage can be added
    // here in the future
}
