FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y maven protobuf-compiler

WORKDIR /app

COPY pom.xml work-order/
COPY src work-order/src

ARG CACHEBUST=1
RUN cd work-order && mvn clean package -DskipTests

COPY ./target/wo-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "wo-0.0.1.jar"]
