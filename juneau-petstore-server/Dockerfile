FROM java:8
VOLUME /tmp
ADD target/juneau-petstore-server-8.1.2-SNAPSHOT.war run.war
EXPOSE 5000
ENTRYPOINT ["java","-jar","run.war"]