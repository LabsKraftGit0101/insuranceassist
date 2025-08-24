# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/insuranceassist-0.0.1-SNAPSHOT.jar app.jar

# USE port 9898
EXPOSE 9898

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]