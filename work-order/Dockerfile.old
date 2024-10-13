FROM eclipse-temurin:21
WORKDIR /workorder
COPY ./target/wo-0.0.1.jar .
CMD apt-get update -y
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "wo-0.0.1.jar"]
EXPOSE 8091

