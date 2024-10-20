FROM eclipse-temurin:21-jdk
ENV PROTOBUF_VERSION=3.8.0
RUN apt-get update && apt-get install -y maven
RUN curl -LO https://github.com/protocolbuffers/protobuf/releases/download/v${PROTOBUF_VERSION}/protoc-${PROTOBUF_VERSION}-linux-x86_64.zip && \
        unzip protoc-${PROTOBUF_VERSION}-linux-x86_64.zip -d /usr
WORKDIR /app
COPY pom.xml rules/
COPY src rules/src
ARG CACHEBUST=1
RUN cd rules && mvn clean package -DskipTests
COPY target/rules-0.0.1.jar .
ENTRYPOINT ["java", "-jar", "rules-0.0.1.jar"]


