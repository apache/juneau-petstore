FROM maven:3.5-jdk-8 as build

COPY . .

RUN mvn clean package

FROM java:8

COPY --from=build ./juneau-petstore-server/target/juneau-petstore-server-8.1.2-SNAPSHOT.war ./run.war

EXPOSE 5000

ENTRYPOINT ["java","-jar","run.war"]