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
    sudo \
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

# Create a non-root user
RUN useradd -m -s /bin/bash emmanuel && \
    usermod -aG sudo emmanuel && \
    echo "emmanuel ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Set working directory and change ownership
WORKDIR /app
RUN chown -R emmanuel:emmanuel /app

# Switch to non-root user for Maven operations
USER emmanuel

# Copy Maven files first (for better caching)
COPY --chown=emmanuel:emmanuel pom.xml .

# Download dependencies (this will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy rest of the project
COPY --chown=emmanuel:emmanuel . .

# Copy and set up entrypoint script
COPY --chown=emmanuel:emmanuel docker-entrypoint.sh /entrypoint.sh
RUN sudo chmod +x /entrypoint.sh

# Expose port for Allure report
EXPOSE 8080

CMD ["/entrypoint.sh"]