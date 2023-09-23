FROM ghcr.io/graalvm/jdk-community:21.0.0 as builder

# Set the working directory in the container
WORKDIR /app

# Install necessary utilities
RUN yum install -y findutils

# Copy the local project files to the container
COPY . .

# Install native-image tool for GraalVM
#RUN gu install native-image

# Grant execute permissions for the Gradle wrapper
RUN chmod +x ./gradlew

# Compile the Spring Boot application to a native image
RUN ./gradlew nativeCompile

# Use a lightweight distroless base image for running the application
FROM gcr.io/distroless/cc-debian12

# Copy the native binary from the GraalVM image
COPY --from=builder ./build/native/nativeCompile/demo /app/demoj21

# Set the entry point to run the application
ENTRYPOINT ["/app/demoj21"]
