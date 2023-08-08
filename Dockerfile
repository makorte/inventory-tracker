FROM openjdk:17-jdk-slim AS builder
WORKDIR .
COPY build/libs/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17-jdk-slim
WORKDIR .
COPY --from=builder dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder application/ ./
EXPOSE 8081
VOLUME ["/application/data"]
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]