FROM openjdk:11-jdk-slim AS build-env
MAINTAINER Mossaddeque Mahmood
WORKDIR /app
ADD target/virtual-device-simulator.jar app.jar

FROM gcr.io/distroless/java:11
COPY --from=build-env /app /app
WORKDIR /app
CMD ["app.jar"]