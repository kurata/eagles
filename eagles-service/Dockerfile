FROM openjdk:11

ARG JAR_FILE
ARG PROFILE=standalone

WORKDIR /app

ENV spring_profiles_active=${PROFILE}

ADD ${JAR_FILE} eagles-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "eagles-service.jar", ""]
