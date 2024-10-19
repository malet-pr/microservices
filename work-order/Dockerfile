FROM eclipse-temurin:21-jdk

WORKDIR /app

<<<<<<< HEAD
<<<<<<< HEAD
COPY work-order/pom.xml work-order/
COPY work-order/src work-order/src

RUN cd work-order && mvn clean package -DskipTests

COPY ./target/wo-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "wo-0.0.1.jar"]

=======
COPY ./work-order/pom.xml ./work-order/
COPY ./work-order/src ./work-order/src
=======
COPY work-order/pom.xml work-order/
COPY work-order/src work-order/src
>>>>>>> 78517d7 (more changes to fix the workflow)

RUN cd work-order && mvn clean package -DskipTests

COPY ./target/wo-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "wo-0.0.1.jar"]


<<<<<<< HEAD
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
>>>>>>> 91304b2 (changes to try to get the workflow to deploy image to dockerhub)
=======
>>>>>>> 78517d7 (more changes to fix the workflow)
