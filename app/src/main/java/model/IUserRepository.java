package model;

public interface IUserRepository {
    boolean addUser(User user);
    User getUserByUsername(String username);
}
