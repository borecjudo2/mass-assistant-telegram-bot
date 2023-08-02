# Use the official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package

# Use the official OpenJDK slim image as the base image
FROM openjdk:17.0.1-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR from the previous stage to the container
COPY --from=build /app/target/*.jar ./app.jar

# Set the environment variables for the Java application
ARG SPRING_PROFILES_ACTIVE
ARG TELEGRAM_BOT_APIKEY
ARG TELEGRAM_BOT_NAME

ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE \
    TELEGRAM_BOT_APIKEY=$TELEGRAM_BOT_APIKEY \
    TELEGRAM_BOT_NAME=$TELEGRAM_BOT_NAME

# Expose the application's port (if your app listens on a different port, change it here)
EXPOSE 8080

# Command to run the Java application
CMD ["java", "-jar", "app.jar"]
