#!/bin/bash
echo "Building TicketTango application..."

# Clean previous build
rm -rf build/classes/*

# Create build directory if it doesn't exist
mkdir -p build/classes

# Compile the application
javac -d build/classes -cp "lib/*" src/main/java/tickettango/*.java

# Copy resources
cp -r src/main/resources/* build/classes/

echo "Build complete! You can run the application with: ./scripts/run.sh"
