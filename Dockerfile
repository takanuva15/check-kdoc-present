# Container image that runs your code
FROM openjdk:11-jdk-slim

COPY entrypoint.sh /entrypoint.sh

# Copy jar file that contains verification code
COPY build/libs/check-kdoc-present-1.0-SNAPSHOT.jar /app/

# Code file to execute when the docker container starts up (`entrypoint.sh`)
ENTRYPOINT ["/entrypoint.sh"]
