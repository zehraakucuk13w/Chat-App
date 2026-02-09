package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import constants.DBConstants;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ChatApp.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + DBConstants.TABLE_USERS + "(" +
                DBConstants.COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBConstants.COL_USERNAME + " TEXT," +
                DBConstants.COL_EMAIL + " TEXT," +
                DBConstants.COL_BIO + " TEXT," +
                DBConstants.COL_IS_ACTIVE + " INTEGER DEFAULT 1," +
                DBConstants.COL_IS_BLOCKED + " INTEGER DEFAULT 0," +
                DBConstants.COL_PASSWORD + " TEXT)";

        String createChatsTable = "CREATE TABLE " + DBConstants.TABLE_CHATS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT NOT NULL," + // 'individual' or 'group'
                "created_at INTEGER DEFAULT (strftime('%s','now') * 1000)," +
                "group_name TEXT," + // for group chat
                "user1_id INTEGER," + // for individual chat
                "user2_id INTEGER)"; // for individual chat

        String createChatMembersTable = "CREATE TABLE " + DBConstants.TABLE_CHAT_MEMBERS + "(" +
                "chat_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "PRIMARY KEY(chat_id, user_id))";

        String createMessagesTable = "CREATE TABLE " + DBConstants.TABLE_MESSAGES + "(" +
                DBConstants.COL_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBConstants.COL_CHAT_ID + " INTEGER," +
                DBConstants.COL_SENDER_ID + " INTEGER," +
                DBConstants.COL_RECEIVER_ID + " INTEGER," +
                DBConstants.COL_CONTENT + " TEXT," +
                DBConstants.COL_TIMESTAMP + " INTEGER," +
                DBConstants.COL_IS_READ + " INTEGER DEFAULT 0)";

        String createBlockedUsersTable = "CREATE TABLE " + DBConstants.TABLE_BLOCKED + "(" +
                DBConstants.COL_BLOCKED_ID + " INTEGER," +
                DBConstants.COL_BLOCKER_ID + " INTEGER," +
                "PRIMARY KEY (" + DBConstants.COL_BLOCKER_ID + ", " + DBConstants.COL_BLOCKED_ID + "))";

        db.execSQL(createUsersTable);
        db.execSQL(createChatsTable);
        db.execSQL(createChatMembersTable);
        db.execSQL(createMessagesTable);
        db.execSQL(createBlockedUsersTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_CHATS);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_CHAT_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_BLOCKED);
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String password, String bio, boolean isActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBConstants.COL_USERNAME, username);
        values.put(DBConstants.COL_EMAIL, email);
        values.put(DBConstants.COL_PASSWORD, password);
        values.put(DBConstants.COL_BIO, bio);
        values.put(DBConstants.COL_IS_ACTIVE, isActive ? 1 : 0);
        values.put(DBConstants.COL_IS_BLOCKED, 0);

        long rowId = db.insert(DBConstants.TABLE_USERS, null, values);
        return rowId != -1; // true if successful
    }

    public Cursor getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DBConstants.TABLE_USERS + " WHERE " + DBConstants.COL_USERNAME + " = ?",
                new String[] { username });
    }

    public int createIndividualChat(int user1Id, int user2Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", "individual");
        values.put("user1_id", user1Id);
        values.put("user2_id", user2Id);
        return (int) db.insert(DBConstants.TABLE_CHATS, null, values);
    }

    public int createGroupChat(String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", "group");
        values.put("group_name", groupName);
        return (int) db.insert(DBConstants.TABLE_CHATS, null, values);
    }

    public Cursor getChats() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DBConstants.TABLE_CHATS, null);
    }

    public boolean addToGroup(int chatId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chat_id", chatId);
        values.put("user_id", userId);
        return db.insert(DBConstants.TABLE_CHAT_MEMBERS, null, values) != -1;
    }

    public boolean removeFromGroup(int chatId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DBConstants.TABLE_CHAT_MEMBERS,
                "chat_id = ? AND user_id = ?",
                new String[] { String.valueOf(chatId), String.valueOf(userId) }) > 0;
    }

    public Cursor getMembers(int chatId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT user_id FROM " + DBConstants.TABLE_CHAT_MEMBERS + " WHERE chat_id = ?",
                new String[] { String.valueOf(chatId) });
    }

    public boolean insertMessage(int chatId, int senderId, Integer receiverId, String content, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_CHAT_ID, chatId);
        values.put(DBConstants.COL_SENDER_ID, senderId);
        if (receiverId != null) {
            values.put(DBConstants.COL_RECEIVER_ID, receiverId);
        }
        values.put(DBConstants.COL_CONTENT, content);
        values.put(DBConstants.COL_TIMESTAMP, timestamp);
        values.put(DBConstants.COL_IS_READ, 0);
        return db.insert(DBConstants.TABLE_MESSAGES, null, values) != -1;
    }

    public Cursor getMessages(int chatId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + DBConstants.TABLE_MESSAGES +
                        " WHERE " + DBConstants.COL_CHAT_ID + " = ? ORDER BY " +
                        DBConstants.COL_TIMESTAMP + " ASC",
                new String[] { String.valueOf(chatId) });
    }

    public boolean updateMessage(int messageId, String updatedContent, long newTimestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_CONTENT, updatedContent);
        values.put(DBConstants.COL_TIMESTAMP, newTimestamp);
        return db.update(DBConstants.TABLE_MESSAGES, values,
                DBConstants.COL_MESSAGE_ID + " = ?", new String[] { String.valueOf(messageId) }) > 0;
    }

    public boolean deleteMessage(int messageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DBConstants.TABLE_MESSAGES,
                DBConstants.COL_MESSAGE_ID + " = ?", new String[] { String.valueOf(messageId) }) > 0;
    }

    public boolean blockUser(int blockerId, int blockedId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.COL_BLOCKER_ID, blockerId);
        values.put(DBConstants.COL_BLOCKED_ID, blockedId);
        return db.insert(DBConstants.TABLE_BLOCKED, null, values) != -1;
    }

    public boolean unblockUser(int blockerId, int blockedId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DBConstants.TABLE_BLOCKED,
                DBConstants.COL_BLOCKER_ID + " = ? AND " + DBConstants.COL_BLOCKED_ID + " = ?",
                new String[] { String.valueOf(blockerId), String.valueOf(blockedId) }) > 0;
    }

    public Cursor getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DBConstants.TABLE_USERS + " WHERE " + DBConstants.COL_USER_ID + " = ?",
                new String[] { String.valueOf(userId) });
    }

    public boolean checkUserLogin(String username, String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DBConstants.TABLE_USERS +
                        " WHERE " + DBConstants.COL_USERNAME + " = ? AND " +
                        DBConstants.COL_PASSWORD + " = ?",
                new String[] { username, hashedPassword });

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean isUserExists(String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_USERS +
                " WHERE " + DBConstants.COL_USERNAME + " = ? OR " + DBConstants.COL_EMAIL + " = ?",
                new String[] { username, email });
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}