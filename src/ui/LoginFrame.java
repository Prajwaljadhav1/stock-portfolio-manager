package ui;

import service.AuthenticationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login window for the application
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthenticationService authService;
    
    public LoginFrame() {
        authService = new AuthenticationService();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setTitle("Stock Manager - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 152, 219));
        JLabel titleLabel = new JLabel("Stock Portfolio Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        
        loginButton.setPreferredSize(new Dimension(100, 35));
        registerButton.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);
        
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        
        // Default credentials info
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Default: admin/admin"));
        add(infoPanel, BorderLayout.SOUTH);
        
        // Setup event handlers
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        
        // Enter key login
        getRootPane().setDefaultButton(loginButton);
    }
    
    private void setupEventHandlers() {
        // Already handled in setupLayout for simplicity
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", 
                                        "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (authService.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Open main application window
            SwingUtilities.invokeLater(() -> {
                new MainFrame(username).setVisible(true);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", 
                                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
    
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", 
                                        "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (authService.register(username, password)) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now login.", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists.", 
                                        "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
