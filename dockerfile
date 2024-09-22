# Step 1: Use a base image with JDK 17 or 11 (depends on your app)
FROM openjdk:22-jdk

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the built Spring Boot JAR file to the container (adjust the path as needed)
COPY target/Mend-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port the Spring Boot app will run on (default is 8080)
EXPOSE 8080

# Step 5: Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
