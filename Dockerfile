FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

COPY target/cat-data-miner-1.0.0.jar /app/spring-app.jar

ENV PROFILE local
ENV DB_URL url
ENV DB_USER user
ENV DB_PASSWORD password

EXPOSE 8010
ENTRYPOINT [ "java", "-jar", "spring-app.jar", "--spring.profiles.active=${PROFILE}", "--spring.datasource.url=${DB_URL}", "--spring.datasource.username=${DB_USER}", "--spring.datasource.password=${DB_PASSWORD}" ]