FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.30.0/opentelemetry-javaagent.jar /opt/opentelemetry-agent.jar

ENTRYPOINT ["java", "-javaagent:/opt/opentelemetry-agent.jar", "-Dotel.resource.attributes=service.instance.id=$HOSTNAME", "-jar", "/app/target/simulator-0.0.1.jar"]

EXPOSE 8082