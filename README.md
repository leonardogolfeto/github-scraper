# Github Scraper

This project aims to enumerate and return information on public projects on github

### Stack

Java 11

Quarkus

### Postman Collection

To understand the api's signature, import the postman collection found at
https://www.getpostman.com/collections/05b054f79e08ea2176ed

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** The application ships with a Dev UI and Swagger UI, which is available in dev mode only at http://localhost:8080/q/dev/ and http://localhost:8080/q/swagger-ui/

## Packaging and running the standalone application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Packaging and running the application with Docker

Go to the root project directory there will be the Dockerfile. 

This Dockerfile is used in order to build a container that runs the Quarkus application in JVM mode

Before building the container image run:

```shell script
./mvnw package
```

Then, build the image with:

```shell script
docker build -f Dockerfile -t tricklook/githubscraper .
```

Then run the container using:

```shell script
docker run -i --rm -p 8080:8080 tricklook/githubscraper
```

If you want to include the debug port into your docker image
you will have to expose the debug port (default 5005) like this :  EXPOSE 8080 5005

Then run the container using :

```shell script
docker run -i --rm -p 8080:8080 -p 5005:5005 -e JAVA_ENABLE_DEBUG="true" tricklook/githubscraper
```
### Or use the image directly from the docker hub

Before run the container image execute:

```shell script
docker pull tricklook/githubscraper
```

Then run the container

```shell script
docker run -i --rm -p 8080:8080 tricklook/githubscraper
```
