FROM amazoncorretto:11-alpine-jdk
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9081
COPY target/*.jar statserver.jar
ENTRYPOINT ["java","-jar","/statserver.jar"]
