# Use a lightweight distroless base image for running the application
FROM gcr.io/distroless/cc-debian12

# Copy the native binary from the GraalVM image
COPY ./build/native/nativeCompile/demo /app/demoj21

# Set the entry point to run the application
ENTRYPOINT ["/app/demoj21"]
