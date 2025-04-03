#!/bin/sh

echo "SPRING_PROFILE: ${SPRING_PROFILE}"
exec java spring.profiles.active=${SPRING_PROFILE} -jar /app/api.jar