#FROM openjdk:17
#WORKDIR /app
#
## 프로젝트 빌드 후 생성된 JAR 파일 복사
#COPY ./CultureSpot-ServiceApi/build/libs/*.jar app.jar
#
## 실행
#ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]

FROM rockylinux:8

# Install OpenJDK 17 and dumb-init
RUN yum install -y epel-release && \
    yum install -y java-17-openjdk dumb-init

WORKDIR /app

COPY ./CultureSpot-Batch/build/libs/batch.jar /app/batch.jar
COPY ./CultureSpot-Batch/entrypoint.sh /app/entrypoint.sh

EXPOSE 8080

RUN chmod +x /app/entrypoint.sh

# Use dumb-init as the entrypoint
ENTRYPOINT ["/usr/bin/dumb-init", "--", "/app/entrypoint.sh"]