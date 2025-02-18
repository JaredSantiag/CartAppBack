FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /cartapp

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jdk

WORKDIR /cartapp

COPY --from=build /cartapp/target/*.jar cartapp.jar

EXPOSE 8080

CMD ["java", "-jar", "cartapp.jar"]
