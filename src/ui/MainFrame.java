package ui;

import model.Stock;
import service.StockService;
import service.GeminiAIService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main application window for stock management
 */
public class MainFrame extends JFrame {
    private String currentUser;
    private StockService stockService;
    private GeminiAIService aiService;
    
    // UI Components
    private JTable stockTable;
    private DefaultTableModel tableModel;
    private JTextField stockNameField;
    private JTextField stockSymbolField;
    private JTextField buyThresholdField;
    private JTextField sellThresholdField;
    
    public MainFrame(String username) {
        this.currentUser = username;
        this.stockService = new StockService();
        this.aiService = new GeminiAIService();
        
        initializeComponents();
        setupLayout();
        loadStockData();
    }
    
    private void initializeComponents() {
        setTitle("Stock Portfolio Manager - Welcome " + currentUser);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Initialize table
        String[] columnNames = {"Stock Name", "Symbol", "Current NAV", "Buy Threshold", 
                               "Sell Threshold", "Recommendation", "AI Prediction"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        stockTable = new JTable(tableModel);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockTable.setRowHeight(25);
        
        // Initialize input fields
        stockNameField = new JTextField(15);
        stockSymbolField = new JTextField(10);
        buyThresholdField = new JTextField(10);
        sellThresholdField = new JTextField(10);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Stock Portfolio Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
        
        // Input panel
        JPanel inputPanel = createInputPanel();
        
        // Table panel
        JScrollPane tableScrollPane = new JScrollPane(stockTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Your Stocks"));
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        
        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(inputPanel, BorderLayout.NORTH);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add New Stock"));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Add New Stock"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Stock Name:"), gbc);
        gbc.gridx = 1;
        panel.add(stockNameField, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Symbol:"), gbc);
        gbc.gridx = 3;
        panel.add(stockSymbolField, gbc);
        
        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Buy Threshold (₹):"), gbc);
        gbc.gridx = 1;
        panel.add(buyThresholdField, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Sell Threshold (₹):"), gbc);
        gbc.gridx = 3;
        panel.add(sellThresholdField, gbc);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("Add Stock");
        JButton removeButton = new JButton("Remove Selected");
        JButton viewGraphButton = new JButton("View NAV Graph");
        JButton refreshButton = new JButton("Refresh NAV");
        JButton predictButton = new JButton("AI Prediction");
        
        addButton.addActionListener(e -> handleAddStock());
        removeButton.addActionListener(e -> handleRemoveStock());
        viewGraphButton.addActionListener(e -> handleViewGraph());
        refreshButton.addActionListener(e -> handleRefreshNAV());
        predictButton.addActionListener(e -> handleAIPrediction());
        
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(viewGraphButton);
        panel.add(refreshButton);
        panel.add(predictButton);
        
        return panel;
    }
    
    private void loadStockData() {
        // Add some sample stocks
        stockService.addStock("Tata Motors", "TATAMOTORS");
        stockService.addStock("Reliance Industries", "RELIANCE");
        stockService.addStock("Infosys", "INFY");
        
        updateTable();
    }
    
    private void updateTable() {
        tableModel.setRowCount(0);
        List<Stock> stocks = stockService.getAllStocks();
        
        for (Stock stock : stocks) {
            Object[] row = {
                stock.getName(),
                stock.getSymbol(),
                String.format("₹%.2f", stock.getCurrentNAV()),
                stock.getBuyThreshold() > 0 ? String.format("₹%.2f", stock.getBuyThreshold()) : "Not Set",
                stock.getSellThreshold() > 0 ? String.format("₹%.2f", stock.getSellThreshold()) : "Not Set",
                stock.getRecommendation(),
                "Click Predict"
            };
            tableModel.addRow(row);
        }
    }
    
    private void handleAddStock() {
        String name = stockNameField.getText().trim();
        String symbol = stockSymbolField.getText().trim().toUpperCase();
        
        if (name.isEmpty() || symbol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter stock name and symbol.", 
                                        "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Stock stock = stockService.addStock(name, symbol);
            
            // Set thresholds if provided
            if (!buyThresholdField.getText().trim().isEmpty()) {
                double buyThreshold = Double.parseDouble(buyThresholdField.getText().trim());
                stock.setBuyThreshold(buyThreshold);
            }
            
            if (!sellThresholdField.getText().trim().isEmpty()) {
                double sellThreshold = Double.parseDouble(sellThresholdField.getText().trim());
                stock.setSellThreshold(sellThreshold);
            }
            
            // Clear fields
            stockNameField.setText("");
            stockSymbolField.setText("");
            buyThresholdField.setText("");
            sellThresholdField.setText("");
            
            updateTable();
            JOptionPane.showMessageDialog(this, "Stock added successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for thresholds.", 
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding stock: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleRemoveStock() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a stock to remove.", 
                                        "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String stockName = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
                                                  "Are you sure you want to remove " + stockName + "?", 
                                                  "Confirm Removal", 
                                                  JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            stockService.removeStock(stockName);
            updateTable();
            JOptionPane.showMessageDialog(this, "Stock removed successfully!", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void handleViewGraph() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a stock to view graph.", 
                                        "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String stockName = (String) tableModel.getValueAt(selectedRow, 0);
        Stock stock = stockService.getStock(stockName);
        
        if (stock != null) {
            new GraphFrame(stock).setVisible(true);
        }
    }
    
    private void handleRefreshNAV() {
        // Simulate NAV refresh with random values
        List<Stock> stocks = stockService.getAllStocks();
        for (Stock stock : stocks) {
            double newNAV = stockService.generateRandomNAV();
            stock.setCurrentNAV(newNAV);
        }
        
        updateTable();
        JOptionPane.showMessageDialog(this, "NAV values refreshed!", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handleAIPrediction() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a stock for AI prediction.", 
                                        "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String stockName = (String) tableModel.getValueAt(selectedRow, 0);
        Stock stock = stockService.getStock(stockName);
        
        if (stock != null) {
            String prediction = aiService.predictNAV(stock);
            JOptionPane.showMessageDialog(this, 
                                        "AI Prediction for " + stockName + ":\n\n" + prediction, 
                                        "AI Prediction", 
                                        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                                                  "Are you sure you want to logout?", 
                                                  "Confirm Logout", 
                                                  JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }
}
