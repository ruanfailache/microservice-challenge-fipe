FROM maven:3.9-eclipse-temurin-21 AS build

COPY pom.xml mvnw mvnw.cmd ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw package -DskipTests

FROM registry.access.redhat.com/ubi9/openjdk-21

ENV LANGUAGE='en_US:en'

WORKDIR /deployments

COPY --from=build --chown=185 target/quarkus-app/lib/ ./lib/
COPY --from=build --chown=185 target/quarkus-app/quarkus/ ./quarkus/
COPY --from=build --chown=185 target/quarkus-app/app/ ./app/
COPY --from=build --chown=185 target/quarkus-app/*.jar ./

EXPOSE 8081
USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT ["/opt/jboss/container/java/run/run-java.sh"]