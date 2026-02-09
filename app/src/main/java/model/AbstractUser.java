package model;

public abstract class AbstractUser {

    protected int userId;
    protected String username;
    protected String email;
    protected String password;
    protected String bio;
    protected UserStatus status;
    protected long createdAt;
    protected long lastLogin;

    public AbstractUser(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = UserStatus.INACTIVE;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getBio() { return bio; }
    public UserStatus getStatus() { return status; }
    public long getCreatedAt() { return createdAt; }
    public long getLastLogin() { return lastLogin; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBio(String bio) { this.bio = bio; }
    public void setStatus(UserStatus status) { this.status = status; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
    public void setLastLogin(long lastLogin) { this.lastLogin = lastLogin; }

    public abstract boolean login(String username, String password);
    public abstract void logout();
    public abstract void updateProfile(String email, String password);
}
