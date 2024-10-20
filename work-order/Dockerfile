FROM eclipse-temurin:21-jdk

RUN apt-get update && \
    apt-get install -y maven wget unzip && \
    wget https://github.com/protocolbuffers/protobuf/releases/download/v3.11.4/protoc-3.11.4-linux-x86_64.zip && \
    unzip protoc-3.11.4-linux-x86_64.zip -d /usr/local && \
    chmod +x /usr/local/bin/protoc && \
    rm protoc-3.11.4-linux-x86_64.zip

WORKDIR /app
COPY . .

RUN protoc --version && \
    which protoc && \
    mvn clean package -DskipTests

ENTRYPOINT ["java", "-jar", "wo-0.0.1.jar"]
