package constants;

public class DBConstants {

    // Table names
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_MESSAGES = "Messages";
    public static final String TABLE_CHATS = "Chats";
    public static final String TABLE_CHAT_MEMBERS = "ChatMembers";
    public static final String TABLE_BLOCKED = "BlockedUsers";

    // Users table columns
    public static final String COL_USER_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_BIO = "bio";
    public static final String COL_IS_ACTIVE = "isActive";
    public static final String COL_IS_BLOCKED = "isBlocked";

    // Messages table columns
    public static final String COL_MESSAGE_ID = "id";
    public static final String COL_CHAT_ID = "chat_id";
    public static final String COL_SENDER_ID = "sender_id";
    public static final String COL_RECEIVER_ID = "receiver_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_TIMESTAMP = "timestamp";
    public static final String COL_IS_READ = "isRead";

    // Blocked users table columns
    public static final String COL_BLOCKER_ID = "blocker_id";
    public static final String COL_BLOCKED_ID = "blocked_id";

    // ChatMembers columns (optional - not yet used as constants)
    public static final String COL_MEMBER_CHAT_ID = "chat_id";
    public static final String COL_MEMBER_USER_ID = "user_id";

    // Chats table columns (optional)
    public static final String COL_CHAT_TYPE = "type";
    public static final String COL_CHAT_USER1_ID = "user1_id";
    public static final String COL_CHAT_USER2_ID = "user2_id";
    public static final String COL_GROUP_NAME = "group_name";
}
