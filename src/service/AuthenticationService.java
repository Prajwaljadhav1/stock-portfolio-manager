package service;

import model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple authentication service for user login/registration
 */
public class AuthenticationService {
    private List<User> users;
    
    public AuthenticationService() {
        users = new ArrayList<>();
        // Add default admin user
        users.add(new User("admin", "admin"));
    }
    
    /**
     * Authenticate user with username and password
     */
    public boolean authenticate(String username, String password) {
        User loginUser = new User(username, password);
        return users.contains(loginUser);
    }
    
    /**
     * Register a new user
     */
    public boolean register(String username, String password) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        
        users.add(new User(username, password));
        return true;
    }
    
    /**
     * Get all registered users (for testing purposes)
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
