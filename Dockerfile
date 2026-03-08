# Use Maven image to build the application
FROM maven:3.9-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use JDK 21 for running the application (JDK needed for runtime compilation)
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file from build stage
COPY --from=build /app/target/java-compiler-api-1.0.0.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
