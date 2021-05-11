FROM openjdk:8
COPY / /

WORKDIR /


EXPOSE 8080


CMD ["java", "-jar", "target/checkpoint-system-0.0.1-SNAPSHOT.jar"]
