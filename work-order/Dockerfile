FROM eclipse-temurin:21-jdk

WORKDIR /app

<<<<<<< HEAD
COPY work-order/pom.xml work-order/
COPY work-order/src work-order/src

RUN cd work-order && mvn clean package -DskipTests

COPY ./target/wo-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "wo-0.0.1.jar"]

=======
COPY ./work-order/pom.xml ./work-order/
COPY ./work-order/src ./work-order/src

RUN cd work-order && mvn clean package -DskipTests

COPY ./work-order/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
>>>>>>> 91304b2 (changes to try to get the workflow to deploy image to dockerhub)
