# Messaging RestApi 

## Implementation
The solution is a Dockerized Java Spring Boot using Gradle.

Implemented following endpoints :
- POST /users
- GET /messages/sent
- GET messages/received
- GET messages/received/senderId=?

## Running
The following command will create a build and spin up the containers: `./gradlew build && ./gradlew composeUp`

## Testing
In order to run the unit tests use the command : `./gradlew test`.

## Improvements 
- Improve HTTP error messages and use a standard format
- Implement Kafka messaging
- Improve Testing using Stubs