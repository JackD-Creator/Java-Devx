# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml /build/pom.xml
COPY backend-libs/quarkus-theme-api/pom.xml /build/backend-libs/quarkus-theme-api/pom.xml
RUN mvn -f /build/backend-libs/quarkus-theme-api/pom.xml dependency:go-offline -q
COPY backend-libs/quarkus-theme-api/src /build/backend-libs/quarkus-theme-api/src
RUN mvn -f /build/backend-libs/quarkus-theme-api/pom.xml package -DskipTests -q -Dquarkus.container-image.build=false

# Stage 2: Runtime (JVM)
FROM eclipse-temurin:21-jre-alpine AS jvm
WORKDIR /app
COPY --from=build /build/backend-libs/quarkus-theme-api/target/quarkus-app ./
EXPOSE 8080
ENV JAVA_OPTS="-Xms64m -Xmx256m"
CMD ["sh", "-c", "java $JAVA_OPTS -jar quarkus-run.jar"]
