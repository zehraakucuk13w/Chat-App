package model;

import android.content.Context;
import android.database.Cursor;

import java.util.Arrays;
import java.util.List;

import database.DatabaseHelper;

public class IndividualChat extends AbstractChat {
    private AbstractUser user1;
    private AbstractUser user2;
    private Context context;

    public IndividualChat(Context context, int chatId, long createdAt, AbstractUser user1, AbstractUser user2) {
        super(chatId, "individual", createdAt, Arrays.asList(user1, user2));
        this.user1 = user1;
        this.user2 = user2;
        this.context = context;
    }

    public AbstractUser getUser1() {
        return user1;
    }

    public AbstractUser getUser2() {
        return user2;
    }

    public AbstractUser getReceiver(AbstractUser sender) {
        if (sender.getUserId() == user1.getUserId()) return user2;
        else return user1;
    }

    @Override
    public void sendMessage(AbstractUser sender, String content, long timestamp) {
        AbstractUser receiver = getReceiver(sender);
        try (DatabaseHelper db = new DatabaseHelper(context)) {
            db.insertMessage(getChatId(), sender.getUserId(), receiver.getUserId(), content, timestamp);
        }
    }

    @Override
    public List<Message> getMessages() {
        try (DatabaseHelper db = new DatabaseHelper(context)) {
            Cursor cursor = db.getMessages(getChatId());
            return loadMessagesFromCursor(cursor);
        }
    }

    @Override
    public boolean editMessage(int messageId, String newContent) {
        long newTimestamp = System.currentTimeMillis();
        try (DatabaseHelper db = new DatabaseHelper(context)) {
            return db.updateMessage(messageId, newContent, newTimestamp);
        }
    }

    @Override
    public boolean deleteMessage(int messageId) {
        try (DatabaseHelper db = new DatabaseHelper(context)) {
            return db.deleteMessage(messageId);
        }
    }

}
