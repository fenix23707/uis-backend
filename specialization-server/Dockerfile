FROM openjdk:17-alpine
ARG JAR_FILE=specialization-server-rest/target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]