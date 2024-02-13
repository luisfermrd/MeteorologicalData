FROM openjdk:17-jdk
COPY src /home/app/src
COPY target /home/app/target
COPY pom.xml /home/app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/api.jar"]