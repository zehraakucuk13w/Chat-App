package model;

public interface IMessageSender {
    void sendMessage(AbstractUser sender, String content, long timestamp);
}
