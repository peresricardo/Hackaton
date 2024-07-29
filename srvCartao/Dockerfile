FROM amazoncorretto:21-alpine-jdk

EXPOSE 9511

ARG JAR_FILE=target/srvCartao-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]