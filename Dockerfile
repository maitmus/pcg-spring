FROM openjdk:23-slim

RUN apt-get update && apt-get install -y curl

WORKDIR /app

COPY target/pcg-back.jar /app

ENV TZ=Asia/Seoul

EXPOSE 8080

HEALTHCHECK --interval=20s --timeout=10s --retries=3 \
  CMD curl -f http://localhost:8080/api/health && curl -f http://localhost:8080/api/health/db || exit 1

ENTRYPOINT ["java", "-jar", "/app/pcg-back.jar"]