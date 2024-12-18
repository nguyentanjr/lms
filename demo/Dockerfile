# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY target/*.jar app.jar

# Download all dependencies first (for better caching)
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
RUN apt-get update && apt-get install -y tzdata

# Copy the built artifact from build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar .

# Expose the port your application runs on
EXPOSE 8080

# Run the application with explicit database configuration
ENTRYPOINT ["java", \
    "-Dspring.datasource.url=jdbc:mysql://autorack.proxy.rlwy.net:36706/railway", \
    "-Dspring.datasource.username=root", \
    "-Dspring.datasource.password=XWNSiwlfNZmVzgQbvUgEFPPMtwOumopu", \
    "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]