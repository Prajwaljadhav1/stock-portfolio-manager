package service;

import model.Stock;
import model.NAVRecord;
import java.util.List;
import java.util.Random;

/**
 * Mock Gemini AI service for NAV prediction
 * In a real application, this would integrate with Google's Gemini API
 */
public class GeminiAIService {
    private Random random;
    private static final String[] MARKET_FACTORS = {
        "market volatility", "economic indicators", "sector performance", 
        "company fundamentals", "technical analysis", "global market trends"
    };
    
    private static final String[] PREDICTIONS = {
        "upward trend", "downward trend", "sideways movement", "strong bullish signal",
        "bearish indication", "consolidation phase", "breakout pattern", "reversal signal"
    };
    
    public GeminiAIService() {
        random = new Random();
    }
    
    /**
     * Generate AI prediction for stock NAV
     * This is a mock implementation - in real scenario, you would call Gemini API
     */
    public String predictNAV(Stock stock) {
        if (stock == null) {
            return "Unable to generate prediction: Invalid stock data";
        }
        
        List<NAVRecord> history = stock.getNavHistory();
        double currentNAV = stock.getCurrentNAV();
        
        // Mock AI analysis
        StringBuilder prediction = new StringBuilder();
        
        // Analyze trend
        String trend = analyzeTrend(history);
        prediction.append("📈 Trend Analysis: ").append(trend).append("\n\n");
        
        // Generate prediction
        double predictedNAV = generatePredictedNAV(currentNAV, history);
        prediction.append("🔮 Predicted NAV (Next Period): ₹").append(String.format("%.2f", predictedNAV)).append("\n\n");
        
        // Calculate confidence
        int confidence = 60 + random.nextInt(35); // 60-95% confidence
        prediction.append("📊 Confidence Level: ").append(confidence).append("%\n\n");
        
        // Market factors
        String factor = MARKET_FACTORS[random.nextInt(MARKET_FACTORS.length)];
        prediction.append("🌍 Key Factor: Based on ").append(factor).append("\n\n");
        
        // Recommendation
        String recommendation = generateRecommendation(currentNAV, predictedNAV, stock);
        prediction.append("💡 AI Recommendation: ").append(recommendation).append("\n\n");
        
        // Risk assessment
        String risk = assessRisk(history);
        prediction.append("⚠️ Risk Assessment: ").append(risk);
        
        return prediction.toString();
    }
    
    private String analyzeTrend(List<NAVRecord> history) {
        if (history.size() < 2) {
            return "Insufficient data for trend analysis";
        }
        
        // Simple trend analysis
        double firstNAV = history.get(0).getNav();
        double lastNAV = history.get(history.size() - 1).getNav();
        double change = ((lastNAV - firstNAV) / firstNAV) * 100;
        
        if (change > 5) {
            return "Strong upward trend (+⬆️)";
        } else if (change > 1) {
            return "Mild upward trend (+⬆️)";
        } else if (change < -5) {
            return "Strong downward trend (-⬇️)";
        } else if (change < -1) {
            return "Mild downward trend (-⬇️)";
        } else {
            return "Sideways/consolidation (↔️)";
        }
    }
    
    private double generatePredictedNAV(double currentNAV, List<NAVRecord> history) {
        // Mock prediction logic
        double volatility = calculateVolatility(history);
        double trendFactor = 0.95 + (random.nextDouble() * 0.1); // ±5% change
        
        // Add some randomness based on volatility
        double prediction = currentNAV * trendFactor;
        
        // Ensure prediction is within reasonable bounds
        double maxChange = currentNAV * 0.15; // Max 15% change
        double change = prediction - currentNAV;
        
        if (Math.abs(change) > maxChange) {
            change = change > 0 ? maxChange : -maxChange;
            prediction = currentNAV + change;
        }
        
        return Math.max(1.0, prediction); // Minimum NAV of ₹1
    }
    
    private double calculateVolatility(List<NAVRecord> history) {
        if (history.size() < 2) return 0.1;
        
        double totalVariation = 0;
        for (int i = 1; i < history.size(); i++) {
            double change = Math.abs(history.get(i).getNav() - history.get(i-1).getNav());
            totalVariation += change;
        }
        
        return totalVariation / (history.size() - 1);
    }
    
    private String generateRecommendation(double currentNAV, double predictedNAV, Stock stock) {
        double change = ((predictedNAV - currentNAV) / currentNAV) * 100;
        
        StringBuilder rec = new StringBuilder();
        
        if (change > 3) {
            rec.append("🟢 STRONG BUY - Expected growth of ").append(String.format("%.1f%%", change));
        } else if (change > 1) {
            rec.append("🟢 BUY - Moderate growth expected (").append(String.format("%.1f%%", change)).append(")");
        } else if (change < -3) {
            rec.append("🔴 SELL - Expected decline of ").append(String.format("%.1f%%", Math.abs(change)));
        } else if (change < -1) {
            rec.append("🟡 WEAK SELL - Minor decline expected (").append(String.format("%.1f%%", Math.abs(change))).append(")");
        } else {
            rec.append("🟡 HOLD - Sideways movement expected");
        }
        
        // Consider user thresholds
        if (stock.getBuyThreshold() > 0 && predictedNAV <= stock.getBuyThreshold()) {
            rec.append("\n   📉 Note: Predicted NAV may reach your buy threshold!");
        }
        if (stock.getSellThreshold() > 0 && predictedNAV >= stock.getSellThreshold()) {
            rec.append("\n   📈 Note: Predicted NAV may reach your sell threshold!");
        }
        
        return rec.toString();
    }
    
    private String assessRisk(List<NAVRecord> history) {
        double volatility = calculateVolatility(history);
        
        if (volatility < 10) {
            return "🟢 LOW RISK - Stable price movement";
        } else if (volatility < 25) {
            return "🟡 MODERATE RISK - Normal market fluctuation";
        } else if (volatility < 50) {
            return "🟠 HIGH RISK - Significant volatility";
        } else {
            return "🔴 VERY HIGH RISK - Extreme volatility";
        }
    }
    
    /**
     * Mock method to simulate API key validation
     */
    public boolean validateAPIKey(String apiKey) {
        // In real implementation, this would validate against Gemini API
        return apiKey != null && !apiKey.trim().isEmpty();
    }
    
    /**
     * Get AI service status
     */
    public String getServiceStatus() {
        return "🤖 Gemini AI Service: Mock Mode\n" +
               "📡 Status: Online (Simulated)\n" +
               "🔑 API: Demo Mode - Replace with real Gemini API key\n" +
               "📊 Predictions: Based on technical analysis simulation";
    }
}
