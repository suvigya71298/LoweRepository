FROM amazoncorretto:11
VOLUME /tmp
ADD target/url-shortener-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongoapp:27017/url-shortener", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]