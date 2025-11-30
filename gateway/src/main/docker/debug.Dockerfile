FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
WORKDIR /app
COPY --from=build /workspace/target/quarkus-app/ /app/
EXPOSE 8080 5005
CMD ["java", "-jar", "/app/quarkus-run.jar"]
