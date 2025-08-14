package ui;

import model.Stock;
import model.NAVRecord;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 * Graph window to display NAV history and trends
 */
public class GraphFrame extends JFrame {
    private Stock stock;
    
    public GraphFrame(Stock stock) {
        this.stock = stock;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("NAV Graph - " + stock.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create graph panel
        GraphPanel graphPanel = new GraphPanel(stock);
        
        // Create info panel
        JPanel infoPanel = createInfoPanel();
        
        add(graphPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Current NAV: ₹" + String.format("%.2f", stock.getCurrentNAV())));
        panel.add(new JLabel("Buy Threshold: ₹" + String.format("%.2f", stock.getBuyThreshold())));
        panel.add(new JLabel("Sell Threshold: ₹" + String.format("%.2f", stock.getSellThreshold())));
        panel.add(new JLabel("Recommendation: " + stock.getRecommendation()));
        panel.add(new JLabel("Total Records: " + stock.getNavHistory().size()));
        panel.add(new JLabel("Stock: " + stock.getSymbol()));
        
        return panel;
    }
    
    /**
     * Custom panel for drawing the NAV graph
     */
    private static class GraphPanel extends JPanel {
        private Stock stock;
        private static final int MARGIN = 50;
        
        public GraphPanel(Stock stock) {
            this.stock = stock;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder("NAV Trend Graph"));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            List<NAVRecord> history = stock.getNavHistory();
            
            if (history.isEmpty()) {
                // Draw "No Data" message
                g2d.setColor(Color.GRAY);
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                String message = "No NAV data available. Click 'Refresh NAV' to generate data.";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(message)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(message, x, y);
                return;
            }
            
            int width = getWidth();
            int height = getHeight();
            
            // Calculate graph dimensions
            int graphWidth = width - 2 * MARGIN;
            int graphHeight = height - 2 * MARGIN;
            
            // Find min and max NAV values
            double minNAV = history.stream().mapToDouble(NAVRecord::getNav).min().orElse(0);
            double maxNAV = history.stream().mapToDouble(NAVRecord::getNav).max().orElse(100);
            
            // Add some padding to min/max
            double range = maxNAV - minNAV;
            if (range == 0) range = 1; // Avoid division by zero
            minNAV -= range * 0.1;
            maxNAV += range * 0.1;
            
            // Draw axes
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            
            // Y-axis
            g2d.draw(new Line2D.Double(MARGIN, MARGIN, MARGIN, height - MARGIN));
            // X-axis
            g2d.draw(new Line2D.Double(MARGIN, height - MARGIN, width - MARGIN, height - MARGIN));
            
            // Draw grid lines and labels
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.setStroke(new BasicStroke(1));
            
            // Y-axis grid lines and labels
            for (int i = 0; i <= 5; i++) {
                double value = minNAV + (maxNAV - minNAV) * i / 5.0;
                int y = height - MARGIN - (int) ((value - minNAV) / (maxNAV - minNAV) * graphHeight);
                
                g2d.drawLine(MARGIN, y, width - MARGIN, y);
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.format("₹%.1f", value), 5, y + 5);
                g2d.setColor(Color.LIGHT_GRAY);
            }
            
            // Draw threshold lines
            if (stock.getBuyThreshold() > 0) {
                int buyY = height - MARGIN - (int) ((stock.getBuyThreshold() - minNAV) / (maxNAV - minNAV) * graphHeight);
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                g2d.drawLine(MARGIN, buyY, width - MARGIN, buyY);
                g2d.drawString("Buy: ₹" + String.format("%.2f", stock.getBuyThreshold()), width - MARGIN - 100, buyY - 5);
            }
            
            if (stock.getSellThreshold() > 0) {
                int sellY = height - MARGIN - (int) ((stock.getSellThreshold() - minNAV) / (maxNAV - minNAV) * graphHeight);
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                g2d.drawLine(MARGIN, sellY, width - MARGIN, sellY);
                g2d.drawString("Sell: ₹" + String.format("%.2f", stock.getSellThreshold()), width - MARGIN - 100, sellY - 5);
            }
            
            // Draw NAV line graph
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3));
            
            for (int i = 1; i < history.size(); i++) {
                double nav1 = history.get(i - 1).getNav();
                double nav2 = history.get(i).getNav();
                
                int x1 = MARGIN + (i - 1) * graphWidth / Math.max(1, history.size() - 1);
                int y1 = height - MARGIN - (int) ((nav1 - minNAV) / (maxNAV - minNAV) * graphHeight);
                int x2 = MARGIN + i * graphWidth / Math.max(1, history.size() - 1);
                int y2 = height - MARGIN - (int) ((nav2 - minNAV) / (maxNAV - minNAV) * graphHeight);
                
                g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            }
            
            // Draw data points
            g2d.setColor(new Color(0, 0, 139)); // Dark blue color
            for (int i = 0; i < history.size(); i++) {
                double nav = history.get(i).getNav();
                int x = MARGIN + i * graphWidth / Math.max(1, history.size() - 1);
                int y = height - MARGIN - (int) ((nav - minNAV) / (maxNAV - minNAV) * graphHeight);
                
                g2d.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
            }
            
            // Draw title
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            String title = stock.getName() + " (" + stock.getSymbol() + ") - NAV History";
            FontMetrics fm = g2d.getFontMetrics();
            int titleX = (width - fm.stringWidth(title)) / 2;
            g2d.drawString(title, titleX, 25);
            
            // Draw axis labels
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("Time →", width / 2 - 20, height - 10);
            
            // Rotate and draw Y-axis label
            Graphics2D g2dRotated = (Graphics2D) g2d.create();
            g2dRotated.rotate(-Math.PI / 2);
            g2dRotated.drawString("NAV (₹) →", -height / 2 - 20, 20);
            g2dRotated.dispose();
        }
    }
}
