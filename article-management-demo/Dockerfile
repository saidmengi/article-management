FROM openjdk:8-jdk-alpine
MAINTAINER saidmengi.com
VOLUME /tmp
EXPOSE 8080
ADD target/article-management-demo-0.0.1-SNAPSHOT.jar article-management-demo.jar
ENTRYPOINT ["java","-jar","/article-management-demo.jar"]
