package model;

public class User extends AbstractUser {

    private String bio;
    private boolean isActive;

    public User(int userId, String username, String email, String password) {
        super(userId, username, email, password);
        this.bio = ""; // optional
        this.isActive = true;
        this.status = UserStatus.ACTIVE;
    }

    public User(int userId, String username, String email, String password, String bio, boolean isActive) {
        super(userId, username, email, password);
        this.bio = bio;
        this.isActive = isActive;
        this.status = isActive ? UserStatus.ACTIVE : UserStatus.INACTIVE;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isBlocked() {
        return this.status == UserStatus.BLOCKED;
    }

    public void activate() {
        this.status = UserStatus.ACTIVE;
        this.isActive = true;
    }

    public void block() {
        this.status = UserStatus.BLOCKED;
        this.isActive = false;
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void logout() {
        System.out.println("User " + username + " logged out.");
    }

    @Override
    public void updateProfile(String newEmail, String newPassword) {
        this.email = newEmail;
        this.password = newPassword;
        System.out.println("Profile updated for user: " + username);
    }
}
