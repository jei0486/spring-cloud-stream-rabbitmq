# spring cloud stream rabbitmq

- spring boot 2.7.12 , jdk 17, maven 
- spring cloud stream rabbit

## Running the application with Rabbit binder

* Go to the application root
* `docker-compose -f docker-compose-rabbit.yml up -d`

* `./mvnw clean package`

* `java -jar target/spring-cloud-stream-publisher-0.0.1-SNAPSHOT.jar`
* `java -jar target/spring-cloud-stream-consumer-0.0.1-SNAPSHOT.jar`

Upon starting the application on the default port 8080, if the following data are sent:

curl -X GET --location "http://localhost:8080/api/direct/p2p-topic"

curl -H "Content-Type: application/json" -X POST -d '{"id":"customerId-1","bill-pay":"100"}' http://localhost:8080

curl -H "Content-Type: application/json" -X POST -d '{"id":"customerId-2","bill-pay":"150"}' http://localhost:8080

The destinations 'customerId-1' and 'customerId-2' are created as exchanges in Rabbit and the data is published to the appropriate destinations dynamically.

Do the above HTTP post a few times and watch the output appear on the corresponding exchanges.

`http://localhost:15672`

Once you are done testing: `docker-compose -f docker-compose-rabbit.yml down`

