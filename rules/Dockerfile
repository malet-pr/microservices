FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml rules/
COPY src rules/src

RUN cd rules && mvn clean package -DskipTests

COPY ./target/rules-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "rules-0.0.1.jar"]

