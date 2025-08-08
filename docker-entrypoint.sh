#!/bin/bash

echo "🚀 Running tests..."

# Run tests (continue even if they fail)
mvn test -Dbrowser=chrome -Dheadless=true || echo "⚠️ Tests completed with failures"

echo "📊 Generating Allure report..."

# Check if results exist
if [ -d "target/allure-results" ] && [ "$(ls -A target/allure-results)" ]; then
    # Generate static HTML report (not serve)
    allure generate target/allure-results -o target/allure-report --clean
    echo "✅ Allure report generated in target/allure-report"

    # List files for debugging
    echo "Report files:"
    ls -la target/allure-report/
else
    echo "❌ No test results found"
    mkdir -p target/allure-report
    echo "<h1>No Test Results Found</h1><p>Tests may have failed to run or no test results were generated.</p>" > target/allure-report/index.html
fi

echo "🏁 Done! Exiting..."