FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./work-order/pom.xml ./work-order/
COPY ./work-order/src ./work-order/src

RUN cd work-order && mvn clean package -DskipTests

COPY ./work-order/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
