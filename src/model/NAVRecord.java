package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a NAV record with value and timestamp
 */
public class NAVRecord {
    private double nav;
    private LocalDateTime timestamp;
    
    public NAVRecord(double nav, LocalDateTime timestamp) {
        this.nav = nav;
        this.timestamp = timestamp;
    }
    
    public double getNav() { return nav; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    public String getFormattedDate() {
        return timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
    
    @Override
    public String toString() {
        return String.format("₹%.2f at %s", nav, getFormattedDate());
    }
}
