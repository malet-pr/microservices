FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY work-order/pom.xml work-order/
COPY work-order/src work-order/src

RUN cd work-order && mvn clean package -DskipTests

COPY ./target/wo-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "wo-0.0.1.jar"]

