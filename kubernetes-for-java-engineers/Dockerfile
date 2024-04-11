FROM amazoncorretto:17-alpine
WORKDIR /app
ARG baseDir
COPY ${baseDir}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]