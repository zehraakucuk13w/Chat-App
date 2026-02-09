package model;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import database.DatabaseHelper;

public class GroupChat extends AbstractChat {
    private String groupName;
    private Context context;

    public GroupChat(Context context, int chatId, long createdAt, String groupName, List<AbstractUser> members) {
        super(chatId, "group", createdAt, members);
        this.groupName = groupName;
        this.context = context;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String newName) {
        this.groupName = newName;
        // Can be updated in database here if needed
    }

    public void addMember(AbstractUser user) {
        if (!getMembers().contains(user)) {
            getMembers().add(user);
            // If you want to add to DB as well:
            try (DatabaseHelper db = new DatabaseHelper(context)) {
                db.addToGroup(getChatId(), user.getUserId());
            }

        }
    }

    public void removeMember(AbstractUser user) {
        getMembers().remove(user);
        try {
            DatabaseHelper db = new DatabaseHelper(context);
            db.removeFromGroup(getChatId(), user.getUserId());
        } catch (Exception e) {
            e.printStackTrace(); // For logging errors
        }
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void sendMessage(AbstractUser sender, String content, long timestamp) {
        DatabaseHelper db = new DatabaseHelper(context);
        int senderId = sender.getUserId();
        db.insertMessage(getChatId(), senderId, null, content, timestamp);
    }

    @Override
    public List<Message> getMessages() {
        DatabaseHelper db = new DatabaseHelper(context);
        Cursor cursor = db.getMessages(getChatId());
        return loadMessagesFromCursor(cursor);
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
