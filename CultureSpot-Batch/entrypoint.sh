#!/bin/sh

echo "SPRING_PROFILE: ${SPRING_PROFILE}"
exec java -Dspring.profiles.active=${SPRING_PROFILE} -jar /app/batch.jar