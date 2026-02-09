package model;

import java.util.Date;

public class Message {
    private int messageId;
    private int chatId; // Chat ID that the message belongs to
    private int senderId; // Sender user ID
    private Integer receiverId; // Receiver user ID (for individual messages, null for groups)
    private String content; // Message content
    private Date timestamp; // Date and time when the message was sent
    private boolean isRead; // Message read status
    private MessageType type; // Message type (TEXT, IMAGE, VIDEO etc.)

    public enum MessageType {
        TEXT,
        IMAGE,
        VIDEO
        // Other types can be added if needed
    }

    public Message(int messageId, int chatId, int senderId, Integer receiverId, String content, Date timestamp,
            boolean isRead) {
        this.messageId = messageId;
        this.chatId = chatId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.type = MessageType.TEXT; // Default type, can be added as parameter if needed
    }

    // Getter and Setter methods

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
