package service;

import model.Stock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for managing stocks and NAV data
 */
public class StockService {
    private List<Stock> stocks;
    private Random random;
    
    public StockService() {
        stocks = new ArrayList<>();
        random = new Random();
    }
    
    /**
     * Add a new stock
     */
    public Stock addStock(String name, String symbol) {
        // Check if stock already exists
        for (Stock stock : stocks) {
            if (stock.getName().equalsIgnoreCase(name) || stock.getSymbol().equalsIgnoreCase(symbol)) {
                throw new IllegalArgumentException("Stock with this name or symbol already exists");
            }
        }
        
        Stock stock = new Stock(name, symbol);
        // Set initial random NAV
        stock.setCurrentNAV(generateRandomNAV());
        
        // Generate some historical data
        generateHistoricalData(stock);
        
        stocks.add(stock);
        return stock;
    }
    
    /**
     * Remove a stock by name
     */
    public boolean removeStock(String name) {
        return stocks.removeIf(stock -> stock.getName().equalsIgnoreCase(name));
    }
    
    /**
     * Get a stock by name
     */
    public Stock getStock(String name) {
        return stocks.stream()
                .filter(stock -> stock.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get all stocks
     */
    public List<Stock> getAllStocks() {
        return new ArrayList<>(stocks);
    }
    
    /**
     * Generate random NAV value (simulating real market data)
     */
    public double generateRandomNAV() {
        // Generate NAV between 50 and 5000
        return 50 + (random.nextDouble() * 4950);
    }
    
    /**
     * Generate historical NAV data for a stock
     */
    private void generateHistoricalData(Stock stock) {
        double baseNAV = stock.getCurrentNAV();
        
        // Generate 10-20 historical records
        int recordCount = 10 + random.nextInt(11);
        
        for (int i = recordCount; i > 0; i--) {
            // Simulate price fluctuation (±5% from base)
            double fluctuation = 0.9 + (random.nextDouble() * 0.2); // 0.9 to 1.1
            double historicalNAV = baseNAV * fluctuation;
            
            stock.addNAVRecord(historicalNAV);
            baseNAV = historicalNAV; // Use this as base for next record
        }
    }
    
    /**
     * Update NAV for all stocks (simulating real-time data)
     */
    public void updateAllNAVs() {
        for (Stock stock : stocks) {
            // Simulate small fluctuations in NAV
            double currentNAV = stock.getCurrentNAV();
            double fluctuation = 0.95 + (random.nextDouble() * 0.1); // ±5% change
            double newNAV = currentNAV * fluctuation;
            
            stock.setCurrentNAV(newNAV);
        }
    }
    
    /**
     * Get market summary
     */
    public String getMarketSummary() {
        if (stocks.isEmpty()) {
            return "No stocks in portfolio";
        }
        
        long buyRecommendations = stocks.stream()
                .mapToLong(stock -> "BUY".equals(stock.getRecommendation()) ? 1 : 0)
                .sum();
        
        long sellRecommendations = stocks.stream()
                .mapToLong(stock -> "SELL".equals(stock.getRecommendation()) ? 1 : 0)
                .sum();
        
        long holdRecommendations = stocks.size() - buyRecommendations - sellRecommendations;
        
        return String.format("Portfolio Summary: %d stocks | BUY: %d | SELL: %d | HOLD: %d", 
                           stocks.size(), buyRecommendations, sellRecommendations, holdRecommendations);
    }
}
