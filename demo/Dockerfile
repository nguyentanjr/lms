# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
ENV DATASOURCE_URL="mysql://root:EEIWDUEfrLCNenUZxswKfOAMWYOislEd@junction.proxy.rlwy.net:21036/railway"
ENV DATASOURCE_USER="root"
ENV DATASOURCE_PASSWORD="EEIWDUEfrLCNenUZxswKfOAMWYOislEd"
RUN mkdir -p /root/.m2 && \
    echo '<mirrors>' > /root/.m2/settings.xml && \
    echo '<mirror>' >> /root/.m2/settings.xml && \
    echo '    <id>maven-central</id>' >> /root/.m2/settings.xml && \
    echo '    <mirrorOf>*</mirrorOf>' >> /root/.m2/settings.xml && \
    echo '    <url>https://repo.maven.apache.org/maven2</url>' >> /root/.m2/settings.xml && \
    echo '</mirror>' >> /root/.m2/settings.xml && \
    echo '</mirrors>' >> /root/.m2/settings.xml

# Download all dependencies first (for better caching)
RUN mvn dependency:go-offline

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
RUN apt-get update && apt-get install -y tzdata

# Copy the built artifact from build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar .

# Expose the port your application runs on
EXPOSE 8080

# Set environment variables if needed
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]