# Use OpenJDK 17 with Maven pre-installed
FROM maven:3.9.4-openjdk-17-slim

# Install Chrome and dependencies
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    gnupg2 \
    software-properties-common \
    xvfb \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list' \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install Allure CLI
RUN wget -q https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.tgz \
    && tar -zxf allure-2.24.0.tgz \
    && mv allure-2.24.0 /opt/allure \
    && ln -s /opt/allure/bin/allure /usr/local/bin/allure \
    && rm allure-2.24.0.tgz

# Set working directory
WORKDIR /app

# Copy Maven files first (for better caching)
COPY pom.xml .
COPY src/ ./src/

# Download dependencies (cached layer)
RUN mvn dependency:resolve

# Set environment variables
ENV HEADLESS=true
ENV BROWSER=chrome

# Create directories for test results
RUN mkdir -p /app/target/allure-results
RUN mkdir -p /app/target/allure-report
RUN mkdir -p /app/logs

# Copy entrypoint script
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh

# Expose port for Allure report server
EXPOSE 8080

# Default command
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["test"]