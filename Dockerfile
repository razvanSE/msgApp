FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
ADD build/libs/messagingapp-0.0.1-SNAPSHOT.jar messagingapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/messagingapp.jar"]