FROM eclipse-temurin:17
WORKDIR /root
EXPOSE 8761
COPY target/Tick-It-service-registry.jar service-registry.jar
ENTRYPOINT [ "java", "-jar", "service-registry.jar" ]