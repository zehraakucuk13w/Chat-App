package service;

import model.User;
import model.UserRepository;
import util.ValidationUtils;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(User user) throws IllegalArgumentException {
        // Server-side validation
        if (!ValidationUtils.isValidUsername(user.getUsername())) {
            throw new IllegalArgumentException(
                    "Invalid username. Must contain only letters, numbers, _ and . (6-15 characters)");
        }
        if (!ValidationUtils.isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException(
                    "Invalid password. Must be 8-20 characters and contain special characters");
        }
        if (userRepository.isUsernameTaken(user.getUsername())) {
            throw new IllegalArgumentException("This username is already taken.");
        }
        return userRepository.saveUser(user);
    }

    public User login(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user == null)
            return null;

        // In the future, comparison should be made with hashed password
        if (!user.getPassword().equals(password))
            return null;

        return user;
    }
}
