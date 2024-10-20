FROM maven:3.9.9-eclipse-temurin-21

RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/*

RUN wget https://github.com/protocolbuffers/protobuf/releases/download/v3.11.4/protoc-3.11.4-linux-x86_64.zip && \
    ls -la && \
    unzip protoc-3.11.4-linux-x86_64.zip -d /usr/local && \
    chmod +x /usr/local/bin/protoc && \
    rm protoc-3.11.4-linux-x86_64.zip

ENV PATH="/usr/local/bin:${PATH}"
RUN protoc --version && which protoc

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.30.0/opentelemetry-javaagent.jar /opt/opentelemetry-agent.jar

ENTRYPOINT ["java", "-javaagent:/opt/opentelemetry-agent.jar", "-Dotel.resource.attributes=service.instance.id=$HOSTNAME", "-jar", "/app/target/rules-0.0.1.jar"]

EXPOSE 8083
EXPOSE 8090


