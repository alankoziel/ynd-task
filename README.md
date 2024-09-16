# Tor Exit Node Checker

## Service Description
This project is a REST API service that checks whether a given IP address is a Tor exit node. It provides the following endpoints:
- **HEAD /A.B.C.D**:
    - Responds with HTTP 200 OK and an empty body if the provided IP is a Tor exit node.
    - Responds with HTTP 404 Not Found and an empty body if the provided IP is not a Tor exit node.
- **GET /A.B.C.D** (Optional):
    - Responds with HTTP 200 OK and a JSON body if the provided IP is a Tor exit node.
    - Responds with HTTP 404 Not Found and an empty body if the provided IP is not a Tor exit node.

### Assumptions
- The service processes IP addresses in the format A.B.C.D and checks their status as Tor exit nodes using a preloaded list.

## Tech Stack
- **Language**: Java 21
- **Framework**: Spring Boot
- **Runtime Environment**: Java 21
- **Build Tool**: Maven
- **Key Libraries**:
    - Spring Web
    - Caffeine
    - WireMock, JUnit
    - Docker and Docker Compose

## How to Build the Service
To build the service, use the following command:
```bash
mvn clean install
```

## How to Run Automatic Tests
To execute the tests, use the following command:
```bash
mvn test
```

## How to Run the Service Locally
1. Ensure that Docker is installed and running.
2. Use `docker-compose` to build and start the service:
```bash
docker-compose up --build
```
The service will be available at `http://localhost:8080`.

## What improvements would you make if you had more time?
I decided to parse the response from the API into objects instead of just checking if the string from the response has IP data :)

I spent only 2 hours on this task, so please don't judge me too harshly :D

- Implement a more efficient way to check if an IP address is a Tor exit node.
- Better parsing of the response from the API.
- Validate the IP address format.
- Perform more extensive testing.
- Performances improvements.
- Implement a more detailed logging system.
- Implement a more detailed error handling system.
- and more...# ynd-task

