# Use a lightweight distroless base image for running the application
FROM oraclelinux:8-slim

# Copy the native binary from the GraalVM image
COPY ./build/native/nativeCompile/demo /app/demoj21

# Set the entry point to run the application
ENTRYPOINT ["/app/demoj21"]
