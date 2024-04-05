# Orders assigment

## Project structure

- The project is a simple REST API that allows to create orders and get the order details.
- The project is built using Spring Boot and uses MySQL as the database.
- The project uses JPA for data access and Lombok for generating boilerplate code.
- The project uses Gradle as the build tool.
- The project include unit-test and integration-test
- unit-test: test the service layer and repository layer (src/test)
- integration-test: test the controller layer (src/api-test) [Required database running]

## How to build the project
### Requirements
- Java 17
- Gradle 8.7
- Clone the repository, setup the JDK and Gradle.
- Run `gradle build` to build the project.
## Test project locally
- Requires docker 
- Run ``./gradlew bootJar docker`` to build the project and create the docker image
- Run `docker-compose up -d` to start the database and server

## Request payload
Only support list of following user-id

```
3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1
3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2
3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2
```

### Create order
```
curl -X POST 'http://localhost:8080/v1/order' \
-H 'Content-Type: application/json' \
-H 'user-id: 3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1' \
-d '{
    "items": [
        {
            "id": "3810b1a3-f690-4dd2-acb6-f61ecfb17ed5",
            "name": "product-name",
            "price": 111,
            "amount": 2
        },
         {
            "id": "341e65b6-42d8-4a28-a6ac-bcae21caa503",
            "name": "product-name1",
            "price": 10,
            "amount": 20
        }
    ]
}'
```

### Get order value based on user-id and time range
```
curl -X GET 'http://localhost:8080/v1/order/total?from=2024-01-01 00:00:00&to=2024-04-07 00:00:00' \  
-H 'user-id: 3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1' \    
```

## Get order details
```
curl -X GET 'http://localhost:8080/v1/order' \
-H 'user-id: 3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1' \    

```
    
  


