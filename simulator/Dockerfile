FROM eclipse-temurin:21
WORKDIR /simulator
COPY ./target/sim-0.0.1.jar .
CMD apt-get update -y
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "sim-0.0.1.jar"]
EXPOSE 8092