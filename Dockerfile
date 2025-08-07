FROM maven:3.9.6-eclipse-temurin-21

# Add cache-busting ARG before instruction you want to rebuild
ARG CACHEBUST=1

# This instruction will NOT use cache (rebuilds every time)
# Install Chrome and Allure
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    curl \
    unzip \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install Allure CLI
RUN wget -q https://github.com/allure-framework/allure2/releases/download/2.25.0/allure-2.25.0.tgz \
    && tar -zxf allure-2.25.0.tgz \
    && mv allure-2.25.0 /opt/allure \
    && ln -s /opt/allure/bin/allure /usr/local/bin/allure \
    && rm allure-2.25.0.tgz

WORKDIR /app

# Copy Maven files first (for better caching)
COPY pom.xml .

# Download dependencies (this will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy rest of the project
COPY . .

# Expose port for Allure report
EXPOSE 8080

# Create simple entrypoint script
COPY docker-entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

CMD ["/entrypoint.sh"]