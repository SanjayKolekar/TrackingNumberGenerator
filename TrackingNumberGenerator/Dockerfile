#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build 

COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim

COPY --from=build /target/TrackingNumberGenerator-0.0.1-SNAPSHOT.jar demo.jar

EXPOSE 8182
ENTRYPOINT ["java", "-jar", "demo.jar"]
