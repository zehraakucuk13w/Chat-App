package model;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractChat implements IMessageSender {
    private int chatId;
    private String chatType;
    private long createdAt;
    private List<AbstractUser> members;

    public AbstractChat(int chatId, String chatType, long createdAt, List<AbstractUser> members) {
        this.chatId = chatId;
        this.chatType = chatType;
        this.createdAt = createdAt;
        this.members = members;
    }

    // Getters
    public int getChatId() {
        return chatId;
    }

    public String getChatType() {
        return chatType;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public List<AbstractUser> getMembers() {
        return members;
    }

    // Abstract methods (subclasses must override)
    public abstract void sendMessage(AbstractUser sender, String content, long timestamp);

    public abstract List<Message> getMessages();

    public abstract boolean editMessage(int messageId, String newContent);

    public abstract boolean deleteMessage(int messageId);

    // Common message loading method
    protected List<Message> loadMessagesFromCursor(Cursor cursor) {
        List<Message> messages = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int messageId = cursor.getInt(cursor.getColumnIndexOrThrow("message_id"));
                int senderId = cursor.getInt(cursor.getColumnIndexOrThrow("sender_id"));

                Integer receiverId = null;
                int receiverIndex = cursor.getColumnIndex("receiver_id");
                if (receiverIndex != -1 && !cursor.isNull(receiverIndex)) {
                    receiverId = cursor.getInt(receiverIndex);
                }

                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                long timestampLong = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));
                Date timestampDate = new Date(timestampLong);
                boolean isRead = cursor.getInt(cursor.getColumnIndexOrThrow("is_read")) == 1;

                messages.add(new Message(messageId, getChatId(), senderId, receiverId, content, timestampDate, isRead));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return messages;
    }
}
