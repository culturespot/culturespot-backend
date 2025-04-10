#!/bin/sh

JVM_OPTIONS=${JVM_OPTIONS:-""}

echo "Executing: java ${JVM_OPTIONS} -Dspring.profiles.active=${SPRING_PROFILE} -Dperformance.migration.job.cron.schedule=\"${CRON_SCHEDULE}\" -jar /app/batch.jar"
exec java ${JVM_OPTIONS} -Dspring.profiles.active=${SPRING_PROFILE} -Dperformance.migration.job.cron.schedule="${CRON_SCHEDULE}" -jar /app/batch.jar