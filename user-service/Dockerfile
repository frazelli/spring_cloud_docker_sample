FROM java:8-jre
MAINTAINER CityAds <sqshq@sqshq.com>

ADD ./target/user-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/user-service.jar"]

EXPOSE 6000