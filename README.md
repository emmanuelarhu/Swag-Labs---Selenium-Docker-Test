# 🧪 Swaglabs Selenium Test Automation with Docker

[![Tests](https://github.com/emmanuelarhu/Swag-Labs---Selenium-Docker-Test/workflows/Tests/badge.svg)](https://github.com/emmanuelarhu/Swag-Labs---Selenium-Docker-Test/actions)
[![Docker](https://img.shields.io/badge/docker-enabled-blue.svg)](https://www.docker.com/)
[![Allure Report](https://img.shields.io/badge/allure-report-green.svg)](https://emmanuelarhu.github.io/Swag-Labs---Selenium-Docker-Test/allure-report)

A comprehensive test automation framework for SwagLabs using Selenium WebDriver, containerized with Docker, and integrated with CI/CD pipeline featuring automated Allure reporting.

## 🚀 Features

- ✅ **Dockerized Test Environment** - Consistent test execution across all platforms
- ✅ **Selenium WebDriver** - Automated browser testing with Chrome headless
- ✅ **Page Object Model** - Maintainable and scalable test architecture
- ✅ **Allure Reports** - Beautiful HTML test reports with detailed insights
- ✅ **CI/CD Integration** - Automated testing with GitHub Actions
- ✅ **GitHub Pages** - Live test reports published automatically
- ✅ **Maven Integration** - Dependency management and test execution
- ✅ **TestNG Framework** - Powerful test organization and execution

## 🏗️ Project Structure

```
├── 📁 src/
│   ├── 📁 main/java/com/swaglabs/
│   │   ├── 📁 base/           # Base test classes
│   │   ├── 📁 pages/          # Page Object Model classes
│   │   ├── 📁 utils/          # Utility classes and helpers
│   │   └── 📁 listeners/      # TestNG listeners
│   └── 📁 test/java/com/swaglabs/tests/  # Test classes
├── 📁 .github/workflows/      # CI/CD pipeline configuration
├── 🐳 Dockerfile             # Docker container definition
├── 🐳 docker-compose.yml     # Docker Compose configuration
├── 📄 docker-entrypoint.sh   # Container entry point script
├── 📄 pom.xml                # Maven configuration
└── 📊 target/                # Generated reports and artifacts
```

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 21** | Programming language |
| **Maven** | Dependency management & build tool |
| **Selenium WebDriver** | Browser automation |
| **TestNG** | Testing framework |
| **Allure** | Test reporting |
| **Docker** | Containerization |
| **GitHub Actions** | CI/CD pipeline |
| **Chrome Headless** | Browser for automated testing |

## 🚀 Quick Start

### Prerequisites
- Docker installed on your machine
- Git for cloning the repository

### 1. Clone the Repository
```bash
git clone https://github.com/emmanuelarhu/Swag-Labs---Selenium-Docker-Test.git
cd Swag-Labs---Selenium-Docker-Test
```

### 2. Build Docker Image
```bash
docker build -t swaglabs-tests .
```

### 3. Run Tests Locally
```bash
# Run tests and serve report on localhost:8080
docker run --rm -p 8080:8080 swaglabs-tests

# Or run tests and save results locally
docker run --rm -v $(pwd)/target:/app/target swaglabs-tests
```

### 4. View Results
- **Local**: Open http://localhost:8080 in your browser
- **CI Reports**: Check [GitHub Pages](https://emmanuelarhu.github.io/Swag-Labs---Selenium-Docker-Test/allure-report)

## 🐳 Docker Commands

### Basic Usage
```bash
# Build the image
docker build -t swaglabs-tests .

# Run all tests
docker run --rm swaglabs-tests

# Run with volume mounting (to save reports locally)
docker run --rm -v $(pwd)/target:/app/target swaglabs-tests

# Run with port binding (to access report server)
docker run --rm -p 8080:8080 swaglabs-tests
```

### Advanced Usage
```bash
# Build without cache
docker build --no-cache -t swaglabs-tests .

# Run specific test class
docker run --rm swaglabs-tests mvn test -Dtest=SwagLabsTest

# Run in interactive mode for debugging
docker run --rm -it swaglabs-tests bash
```

### Docker Compose
```bash
# Run using Docker Compose
docker-compose up

# Run in background
docker-compose up -d

# Stop and remove containers
docker-compose down
```

## 🔄 CI/CD Pipeline

The project uses GitHub Actions for automated testing:

### Workflow Features
- 🔨 **Automatic Docker Build** - Builds fresh container for each run
- 🧪 **Test Execution** - Runs complete test suite in isolated environment
- 📊 **Report Generation** - Creates beautiful Allure HTML reports
- 🌐 **GitHub Pages** - Publishes reports to live URL
- 📦 **Artifact Storage** - Saves test results for download

### Trigger Events
- ✅ Push to any branch
- ✅ Pull request creation
- ✅ Manual workflow dispatch

### Accessing CI Results

1. **Live Report**: [https://emmanuelarhu.github.io/Swag-Labs---Selenium-Docker-Test/allure-report](https://emmanuelarhu.github.io/Swag-Labs---Selenium-Docker-Test/allure-report)
2. **GitHub Actions**: Go to Actions tab → Select workflow run
3. **Download Artifacts**: Click on "allure-report" in the artifacts section

## 📊 Test Reports

### Allure Report Features
- 📈 **Test Execution Timeline** - Visual representation of test runs
- 📋 **Detailed Test Steps** - Step-by-step test execution with screenshots
- 🐛 **Failure Analysis** - Detailed error logs and stack traces
- 📊 **Statistics Dashboard** - Pass/fail rates and execution trends
- 🏷️ **Test Categorization** - Tests grouped by feature and severity
- 📸 **Screenshots** - Automatic screenshots on test failures

### Local Report Access
```bash
# Run tests and serve report
docker run --rm -p 8080:8080 swaglabs-tests

# Open in browser
open http://localhost:8080
```

## 🧪 Test Structure

### Test Categories
- **E2E Tests** - Complete user journey testing
- **Validation Tests** - Form validation and error handling
- **Checkout Tests** - Payment and order processing flows

### Page Objects
- `LoginPage` - User authentication
- `InventoryPage` - Product browsing and selection
- `CartPage` - Shopping cart management
- `CheckoutStepOnePage` - Customer information
- `CheckoutStepTwoPage` - Order review
- `CheckoutCompletePage` - Order confirmation

### Test Data
- Configurable test data in JSON format
- Environment-specific configurations
- Reusable test scenarios

## 🛠️ Development

### Running Tests Locally (without Docker)
```bash
# Install dependencies
mvn clean install

# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=SwagLabsTest

# Run with specific browser
mvn test -Dbrowser=chrome -Dheadless=true
```

### Adding New Tests
1. Create test class in `src/test/java/com/swaglabs/tests/`
2. Follow Page Object Model pattern
3. Add Allure annotations for reporting
4. Update TestNG configuration if needed

### Debugging
```bash
# Run container in interactive mode
docker run --rm -it swaglabs-tests bash

# Check logs
docker logs container-name

# Run with Maven debug
docker run --rm swaglabs-tests mvn test -X
```

## 🔧 Configuration

### Environment Variables
| Variable | Default | Description |
|----------|---------|-------------|
| `BROWSER` | `chrome` | Browser for testing |
| `HEADLESS` | `true` | Run browser in headless mode |
| `CI` | `false` | CI environment detection |

### Maven Properties
- `browser` - Target browser (chrome, firefox)
- `headless` - Headless mode (true, false)
- `test` - Specific test class to run

## 📈 Performance

### Build Optimization
- ✅ **Multi-stage Dockerfile** - Optimized image layers
- ✅ **Dependency Caching** - Maven dependencies cached separately
- ✅ **Cache Busting** - Selective cache invalidation for code changes

### Test Execution
- ⚡ **Parallel Execution** - TestNG parallel test execution
- 🚀 **Headless Browser** - Faster test execution without GUI
- 📦 **Container Isolation** - Consistent test environment

## 🤝 Contributing

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Guidelines
- Follow Page Object Model pattern
- Add comprehensive test documentation
- Include Allure annotations for reporting
- Ensure Docker compatibility
- Add appropriate logging

## 📋 Troubleshooting

### Common Issues

**Docker Build Fails**
```bash
# Clear Docker cache and rebuild
docker system prune -a
docker build --no-cache -t swaglabs-tests .
```

**Tests Fail in CI but Pass Locally**
- Check Chrome version compatibility
- Verify headless mode configuration
- Review GitHub Actions logs

**No Test Reports Generated**
```bash
# Check if allure-results directory exists
ls -la target/allure-results/

# Verify Maven Allure plugin configuration
mvn allure:report
```

**Port 8080 Already in Use**
```bash
# Use different port
docker run --rm -p 8081:8080 swaglabs-tests

# Or kill process using port 8080
lsof -ti:8080 | xargs kill -9
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏆 Acknowledgments

- **SwagLabs** - Test application provider
- **Selenium WebDriver** - Browser automation framework
- **Allure Framework** - Test reporting solution
- **Docker** - Containerization platform
- **GitHub Actions** - CI/CD platform

---

## 📱 Quick Links

- 🌐 **Live Reports**: [GitHub Pages](https://emmanuelarhu.github.io/Swag-Labs---Selenium-Docker-Test/allure-report)
- 🔄 **CI Pipeline**: [GitHub Actions](https://github.com/emmanuelarhu/Swag-Labs---Selenium-Docker-Test/actions)
- 🐳 **Docker Hub**: [Docker Image](https://hub.docker.com/repository/docker/manuelarhu/swaglabs-selenium-tests/general)
- 📊 **Test Results**: [Latest Artifacts](https://github.com/emmanuelarhu/Swag-Labs---Selenium-Docker-Test/actions)

---

**Happy Testing! 🧪✨**

*Built with ❤️ by [Emmanuel Arhu](https://github.com/emmanuelarhu)*