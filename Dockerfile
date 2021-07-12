FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/QrisQurban-0.0.2.jar
COPY ${JAR_FILE} app.jar
EXPOSE 6666
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]