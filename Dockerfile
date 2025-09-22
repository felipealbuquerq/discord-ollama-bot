# Use official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV TZ=UTC
ENV JAVA_OPTS=""

# Set work directory
WORKDIR /app

# Copy the built jar file
COPY target/discord-ollama-bot.jar /app/discord-ollama-bot.jar

# Expose port (if needed for local testing)
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/discord-ollama-bot.jar"]