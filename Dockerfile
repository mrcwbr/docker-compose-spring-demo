FROM openjdk:8

RUN apt-get update && apt-get install -y maven
COPY . /project
RUN  cd /project && mvn package

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dblabla", "-jar","/project/target/docker-demo-app-0.1.0-SNAPSHOT.jar"]


