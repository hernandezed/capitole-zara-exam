FROM maven:3.8.3-openjdk-17-slim AS build

COPY capitole-exam-business /usr/code/app/capitole-exam-business
COPY capitole-exam-infrastructure /usr/code/app/capitole-exam-infrastructure
COPY pom.xml /usr/code/app
RUN mvn -f /usr/code/app/pom.xml clean package

FROM eclipse-temurin:17-jre-centos7
COPY --from=build /usr/code/app/capitole-exam-infrastructure/target/capitole-exam-infrastructure-0.0.1-SNAPSHOT.jar /usr/local/lib/capitole-exam-infrastructure-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/capitole-exam-infrastructure-1.0.jar"]