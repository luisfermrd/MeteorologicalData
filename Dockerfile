FROM openjdk:17-jdk

COPY target/meteorologicalData.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "meteorologicalData.jar"]