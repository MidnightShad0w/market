FROM openjdk:17
ADD target/market-0.0.1-SNAPSHOT.jar market-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "market-0.0.1-SNAPSHOT.jar"]