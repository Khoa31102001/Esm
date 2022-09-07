FROM openjdk:11
EXPOSE 8009
WORKDIR /app
COPY ./target/ESM-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar" ,"/app.jar"]
