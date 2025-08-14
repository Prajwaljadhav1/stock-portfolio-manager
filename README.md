# 📈 Stock Portfolio Manager

> A professional Java Swing desktop application for stock portfolio management with real-time NAV tracking, AI-powered predictions, and customizable trading thresholds.

![Java](https://img.shields.io/badge/Java-8%2B-orange)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![Status](https://img.shields.io/badge/Status-Production%20Ready-green)
![License](https://img.shields.io/badge/License-Educational-lightgrey)

## 🌟 Features

- **User Authentication**: Secure login and registration system
- **Stock Management**: Add and manage multiple stocks (Tata Motors, Reliance, etc.)
- **NAV Tracking**: Real-time NAV updates with historical data visualization
- **Interactive Graphs**: Visual representation of stock price trends
- **AI Predictions**: Mock Gemini AI integration for price forecasting
- **Buy/Sell Thresholds**: Set custom price alerts for trading decisions
- **Multi-stock Support**: Manage entire portfolio in one application

## 🚀 Quick Start

### Prerequisites
- ☕ Java 8 or higher
- 🛠️ JDK (Java Development Kit)

### 📦 Installation & Setup
```bash
# Clone the repository
git clone https://github.com/Prajwaljadhav1/stock-portfolio-manager.git
cd stock-portfolio-manager

# Make scripts executable (Linux/Mac)
chmod +x compile.sh run.sh

# Compile the application
./compile.sh

# Run the application
./run.sh
```

### 🖥️ For Windows Users
```cmd
# Compile
compile.bat

# Run
run.bat
```

### 🔐 Default Login Credentials
- **Username**: `admin`
- **Password**: `admin`

## Project Structure

```
src/
├── main/
│   └── StockManagerApp.java     # Main application entry point
├── model/
│   ├── Stock.java               # Stock data model
│   ├── NAVRecord.java           # NAV history record
│   └── User.java                # User authentication model
├── ui/
│   ├── LoginFrame.java          # Login window
│   ├── MainFrame.java           # Main dashboard
│   └── GraphFrame.java          # NAV graph visualization
└── service/
    ├── AuthenticationService.java # User management
    ├── StockService.java         # Stock operations
    └── GeminiAIService.java      # AI prediction service
```

## Usage

1. **Login**: Use default credentials or register a new account
2. **Add Stocks**: Enter stock name, symbol, and optional buy/sell thresholds
3. **View Portfolio**: Monitor all stocks in the main dashboard table
4. **Set Thresholds**: Define price points for buy/sell recommendations
5. **View Graphs**: Click "View NAV Graph" to see price trends and thresholds
6. **Refresh Data**: Update NAV values to simulate market changes
7. **AI Predictions**: Get mock AI analysis and recommendations

## Key Components

### Stock Model
- Stores stock information, current NAV, and historical data
- Manages buy/sell thresholds and recommendations
- Tracks price history with timestamps

### Authentication Service
- Simple user registration and login
- In-memory user storage (demo purposes)
- Default admin account provided

### Stock Service
- Add/remove stocks from portfolio
- Generate random NAV data for simulation
- Manage historical price records

### AI Service (Mock)
- Simulates Gemini AI predictions
- Provides trend analysis and recommendations
- Includes risk assessment and confidence levels

### Graph Visualization
- Real-time NAV trend charts
- Visual buy/sell threshold indicators
- Interactive price history display

## Mock Data Generation

The application generates realistic mock data for demonstration:
- Random NAV values between ₹50-₹5000
- Historical price fluctuations (±20%)
- Simulated market trends and volatility

## Customization



### Database Integration
For persistent storage:
1. Add JDBC dependencies
2. Replace in-memory storage with database operations
3. Implement data persistence for users and stocks

## Interview Talking Points

- **Object-Oriented Design**: Proper separation of concerns with model-view-service architecture
- **Swing GUI Development**: Custom components, event handling, and responsive design
- **Mock Service Implementation**: Demonstrates understanding of service layers and API integration concepts
- **Data Visualization**: Custom graph drawing with Java 2D graphics
- **User Experience**: Intuitive interface with error handling and user feedback

## Future Enhancements

- Real-time stock data integration
- Portfolio performance analytics
- Export functionality (PDF reports, CSV data)
- Advanced charting (candlestick, volume indicators)
- Mobile app companion
- Real Gemini AI integration
- Database persistence
- Multi-user support with proper authentication

## Technical Notes

- Built with Java Swing for cross-platform compatibility
- No external dependencies for easy setup and demonstration
- Modular architecture allows easy extension and modification
- Comprehensive error handling and user input validation
- Thread-safe operations for GUI updates

## License

This project is created for educational and interview demonstration purposes.
