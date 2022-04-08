# spring-reactive-demo
spring-reactive-demo

## Build
```
./mvnw clean package -DskipTests=true
```

## Run
```
./mvnw spring-boot:run
```

## Test

### Get Users
```
curl -v -L -X GET http://localhost:8080/api/users
```

### Get User
```
curl -v -L -X GET http://localhost:8080/api/users/1
```


### Add User
```
curl -v -L -X POST http://localhost:8080/api/users \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName": "scott", 
    "email": "scott@spring.io"
}'
```

### Update User
```
curl -v -L -X PUT http://localhost:8080/api/users/1 \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName": "melon", 
    "email": "melon@fruit.io"
}'
```

### Delete User
```
curl -v -L -X DELETE http://localhost:8080/api/users/10 \
--header 'Content-Type: application/json' 
```