FROM maven:3.9-eclipse-temurin-21 AS build

COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /deployments

COPY --from=build target/quarkus-app/lib/ ./lib/
COPY --from=build target/quarkus-app/quarkus/ ./quarkus/
COPY --from=build target/quarkus-app/app/ ./app/
COPY --from=build target/quarkus-app/*.jar ./

EXPOSE 8081 5005
USER 185

ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["java", "-jar", "quarkus-run.jar"]