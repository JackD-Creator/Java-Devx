FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine AS jvm
WORKDIR /app
COPY --from=build /app/target/quarkus-app ./
EXPOSE 8082
ENV JAVA_OPTS="-Xms64m -Xmx256m"
CMD ["sh", "-c", "java $JAVA_OPTS -jar quarkus-run.jar"]
