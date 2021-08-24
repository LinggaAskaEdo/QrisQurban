FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/QrisQurban-1.1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]