FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/QrisQurban-0.0.7.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]