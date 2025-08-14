package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stock with NAV history and trading thresholds
 */
public class Stock {
    private String name;
    private String symbol;
    private double currentNAV;
    private List<NAVRecord> navHistory;
    private double buyThreshold;
    private double sellThreshold;
    
    public Stock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.navHistory = new ArrayList<>();
        this.buyThreshold = 0.0;
        this.sellThreshold = 0.0;
        this.currentNAV = 0.0;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public double getCurrentNAV() { return currentNAV; }
    public void setCurrentNAV(double currentNAV) { 
        this.currentNAV = currentNAV; 
        addNAVRecord(currentNAV);
    }
    
    public List<NAVRecord> getNavHistory() { return new ArrayList<>(navHistory); }
    
    public double getBuyThreshold() { return buyThreshold; }
    public void setBuyThreshold(double buyThreshold) { this.buyThreshold = buyThreshold; }
    
    public double getSellThreshold() { return sellThreshold; }
    public void setSellThreshold(double sellThreshold) { this.sellThreshold = sellThreshold; }
    
    /**
     * Add a new NAV record with current timestamp
     */
    public void addNAVRecord(double nav) {
        navHistory.add(new NAVRecord(nav, LocalDateTime.now()));
    }
    
    /**
     * Get recommendation based on current NAV and thresholds
     */
    public String getRecommendation() {
        if (currentNAV <= buyThreshold && buyThreshold > 0) {
            return "BUY";
        } else if (currentNAV >= sellThreshold && sellThreshold > 0) {
            return "SELL";
        } else {
            return "HOLD";
        }
    }
    
    @Override
    public String toString() {
        return name + " (" + symbol + ") - ₹" + String.format("%.2f", currentNAV);
    }
}
