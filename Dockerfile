FROM openjdk:8
ADD target/movie-catalog-service.jar movie-catalog-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "movie-catalog-service.jar"]
