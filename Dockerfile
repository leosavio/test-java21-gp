FROM ghcr.io/graalvm/jdk-community:21.0.0 as builder

# Set the working directory in the container
WORKDIR /app

# Copy the local project files to the container
COPY . .

# For SDKMAN to work we need unzip & zip
RUN microdnf install -y unzip zip

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

RUN sdk version

RUN sdk install gradle 8.3

RUN gu install native-image









RUN gradle --version

RUN native-image --version

RUN ./gradlew nativeCompile

# Use a lightweight distroless base image for running the application
FROM gcr.io/distroless/cc-debian12

# Copy the native binary from the GraalVM image
COPY --from=builder ./build/native/nativeCompile/demo /app/demoj21

# Set the entry point to run the application
ENTRYPOINT ["/app/demoj21"]
