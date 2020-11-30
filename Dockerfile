FROM openjdk:15
EXPOSE 5000

COPY target/rms-api-*.jar /rms-api.jar

ENTRYPOINT ["java", "-jar", "/rms-api.jar"]
