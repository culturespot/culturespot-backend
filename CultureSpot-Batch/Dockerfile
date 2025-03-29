FROM rockylinux:8

# Install OpenJDK 17 and dumb-init
RUN yum install -y epel-release && \
    yum install -y java-17-openjdk dumb-init

WORKDIR /app

COPY build/libs/batch.jar /app/batch.jar
COPY entrypoint.sh /app/entrypoint.sh

EXPOSE 8080

RUN chmod +x /app/entrypoint.sh

# Use dumb-init as the entrypoint
ENTRYPOINT ["/usr/bin/dumb-init", "--", "/app/entrypoint.sh"]