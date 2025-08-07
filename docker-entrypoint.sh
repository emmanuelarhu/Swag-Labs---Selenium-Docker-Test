#!/bin/bash

echo "🚀 Running tests..."

# Run tests (continue even if they fail)
mvn test -Dbrowser=chrome -Dheadless=true || echo "⚠️ Tests completed with failures"

echo "📊 Starting Allure report server..."

# Check if results exist, if not create dummy results
if [ ! -d "target/allure-results" ] || [ ! "$(ls -A target/allure-results)" ]; then
    echo "No test results found, creating empty results..."
    mkdir -p target/allure-results
    echo '{}' > target/allure-results/dummy.json
fi

# Serve the report
allure serve target/allure-results -h 0.0.0.0 -p 8080