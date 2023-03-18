FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar statserver.jar
ENTRYPOINT ["java","-jar","/statserver.jar"]