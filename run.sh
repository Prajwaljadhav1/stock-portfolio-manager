#!/bin/bash
# Run script for Stock Manager
# Runs the compiled Java application

if [ ! -d "classes" ]; then
    echo "❌ Classes directory not found!"
    echo "Please run compile.sh first to compile the application."
    exit 1
fi

if [ ! -f "classes/main/StockManagerApp.class" ]; then
    echo "❌ Main class not found!"
    echo "Please run compile.sh first to compile the application."
    exit 1
fi

echo "🚀 Starting Stock Portfolio Manager..."
echo ""
echo "Default login credentials:"
echo "Username: admin"
echo "Password: admin"
echo ""

cd classes
java main.StockManagerApp
