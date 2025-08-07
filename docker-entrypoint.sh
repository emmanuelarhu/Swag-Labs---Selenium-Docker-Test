#!/bin/bash

echo "=== SauceDemo Docker Test Runner ==="
echo "Browser: $BROWSER"
echo "Headless: $HEADLESS"
echo "Command: $1"
echo "=================================="

# Function to run tests
run_tests() {
    echo "🚀 Starting test execution..."

    # Start virtual display for headless Chrome
    Xvfb :99 -ac -screen 0 1920x1080x16 &
    export DISPLAY=:99

    # Run the tests
    case "$1" in
        "validation")
            echo "Running validation tests..."
            mvn clean test -Dtest="CheckoutValidationTest" -Dbrowser=$BROWSER -Dheadless=$HEADLESS
            ;;
        "e2e")
            echo "Running E2E tests..."
            mvn clean test -Dtest="SauceDemoTest" -Dbrowser=$BROWSER -Dheadless=$HEADLESS
            ;;
        "all"|"test"|*)
            echo "Running all tests..."
            mvn clean test -Dbrowser=$BROWSER -Dheadless=$HEADLESS
            ;;
    esac

    TEST_EXIT_CODE=$?
    echo "Tests completed with exit code: $TEST_EXIT_CODE"

    # Generate Allure report regardless of test results
    echo "📊 Generating Allure report..."
    if [ -d "target/allure-results" ] && [ "$(ls -A target/allure-results)" ]; then
        allure generate target/allure-results -o target/allure-report --clean
        echo "✅ Allure report generated in target/allure-report"

        # List the contents of allure-report
        echo "Report contents:"
        ls -la target/allure-report/
    else
        echo "❌ No test results found for Allure report"
    fi

    return $TEST_EXIT_CODE
}

# Function to serve Allure report
serve_report() {
    echo "🌐 Starting Allure report server..."
    if [ -d "target/allure-report" ]; then
        cd target/allure-report
        echo "Serving Allure report on http://localhost:8080"
        python3 -m http.server 8080
    else
        echo "❌ No Allure report found. Run tests first."
        exit 1
    fi
}

# Function to run tests and then serve report
test_and_serve() {
    run_tests "$1"
    TEST_RESULT=$?

    echo "🎯 Test execution completed. Starting report server..."
    serve_report

    return $TEST_RESULT
}

# Main logic
case "$1" in
    "test"|"validation"|"e2e"|"all")
        run_tests "$1"
        ;;
    "report")
        serve_report
        ;;
    "test-and-serve")
        test_and_serve "$2"
        ;;
    "bash")
        /bin/bash
        ;;
    *)
        echo "Usage:"
        echo "  docker run <image> test           - Run all tests"
        echo "  docker run <image> validation     - Run validation tests only"
        echo "  docker run <image> e2e           - Run E2E tests only"
        echo "  docker run <image> report         - Serve existing Allure report"
        echo "  docker run <image> test-and-serve - Run tests and serve report"
        echo "  docker run <image> bash           - Interactive bash shell"
        exit 1
        ;;
esac