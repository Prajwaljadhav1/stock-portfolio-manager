package main;

import ui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main application class for Stock Manager
 * Simple Java Swing application for stock portfolio management
 */
public class StockManagerApp {
    
    public static void main(String[] args) {
        // Start the application on EDT
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
