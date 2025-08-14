# Compilation script for Stock Manager
# Compiles all Java files and creates the necessary class structure

echo "Compiling Stock Portfolio Manager..."

# Create output directory for compiled classes
mkdir -p classes

# Compile all Java files
javac -d classes -cp classes src/main/*.java src/model/*.java src/ui/*.java src/service/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "📁 Class files created in 'classes' directory"
    echo ""
    echo "To run the application:"
    echo "cd classes && java main.StockManagerApp"
    echo ""
    echo "Default login credentials:"
    echo "Username: admin"
    echo "Password: admin"
else
    echo "❌ Compilation failed!"
    echo "Please check for syntax errors in the Java files."
fi
