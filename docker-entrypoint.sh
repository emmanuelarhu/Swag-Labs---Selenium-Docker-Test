#!/bin/bash

echo "🚀 Running tests..."

# Run tests (continue even if they fail)
mvn test -Dbrowser=chrome -Dheadless=true || echo "⚠️ Tests completed with failures"

echo "📊 Generating Allure report..."

# Check if results exist
if [ -d "target/allure-results" ] && [ "$(ls -A target/allure-results)" ]; then
    # Generate static HTML report
    allure generate target/allure-results -o target/allure-report --clean
    echo "✅ Allure report generated in target/allure-report"
else
    echo "❌ No test results found"
    mkdir -p target/allure-report
    echo "<h1>No test results found</h1>" > target/allure-report/index.html
fi

# In CI: just generate report and exit
# In local: serve the report
if [ "$CI" = "true" ] || [ "$GITHUB_ACTIONS" = "true" ]; then
    echo "🏁 Running in CI - report generated, exiting"
    exit 0
else
    echo "🌐 Starting Allure server on port 8080..."
    allure serve target/allure-results -h 0.0.0.0 -p 8080
fi