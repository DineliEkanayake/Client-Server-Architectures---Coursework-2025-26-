# Smart Campus REST API

## Overview

The **Smart Campus Initiative** is a robust, scalable, and highly available RESTful API designed to manage thousands of university rooms and their connected smart sensors such as:

* CO2 monitors
* Occupancy trackers
* Smart lighting controllers
* Environmental sensors

The system is implemented using **JAX-RS (Jakarta RESTful Web Services)** and follows REST architectural principles.

**Key Features**

* RESTful resource design
* Nested resources (sub-resource locators)
* Centralised exception handling
* Request/response logging
* In-memory data storage using Java collections

---

**Base Configuration**

| Property       | Value                                 |
| -------------- | ------------------------------------- |
| Base URL       | `http://localhost:8080/testCW/api/v1` |
| Protocol       | HTTP/1.1                              |
| Data Format    | JSON (UTF-8)                          |
| Authentication | None (Development Mode)               |

**API Endpoints**

**Room Resources**

| Method | Endpoint      | Description                                          |
| ------ | ------------- | ---------------------------------------------------- |
| GET    | /rooms        | Retrieves a list of all registered rooms             |
| POST   | /rooms        | Create a new room resource                           |
| GET    | /rooms/{id}   | Fetches details of the specific room                 |
| DELETE | /rooms/{id}   | Removes a room only if there are no sensors attached |

**Sensor Resources**

| Method | Endpoint            | Description                                        |
| ------ | ------------------- | -------------------------------------------------  |
| GET    | /sensors            | Retrieves all sensors                              |
| GET    | /sensors?type=CO2   | Filters sensors by their specific type (e.g., CO2) |
| POST   | /sensors            | Register a new sensor                              |

**Sensor Reading Resources**

| Method | Endpoint                 | Description                              |
| ------ | ------------------------ | ---------------------------------------- |
| GET    | /sensors/{id}/readings   | fetches the history of a specific sensor |
| POST   | /sensors/{id}/readings   | Adds new reading                         |

**Sub-Resource Locator**

Sensor readings are delegated by `SensorResource` to `SensorReadingResource`.

```java
@Path("{sensorId}/readings")
public SensorReadingResource getReading(...)
```

This improves maintainability by separating sensor logic from reading logic.

**Error Handling**

| Status Code | Meaning                                                                                                                |
| ----------- | ---------------------------------------------------------------------------------------------------------------------- |
| 404         | Returned when a room or sensor id is not found in DataStorage                                                        |
| 409         | Returned through custom made exception RoomNotEmptyException, room still contains sensors                              |
| 422         | Returned through custom made exception LinkedResourceNotFoundException. When there is invalid linked roomId for sensor |
| 403         | Returned through custom made exception SensorUnavailableException. Sensor under maintenance                            |
| 500         | Unexpected internal error        |


**Request / Response Lifecycle**

1. Client sends HTTP request
2. Logging filter records method and URI
3. JAX-RS routes request to matching resource
4. Service layer accesses `DataStore`
5. Logging filter records final response code
6. Client receives JSON response

**Steps taken to complete the project**
1. Setting up Apache Tomcat server
   * Download the Apache Tomcat server provided in the BlackBoard
   * On netbeans: service tab -> Server -> Add Server -> Apache Tomcat or TomEE -> next -> browse -> open the downloaded file
   * In the Service tab select the Tomcat server
2. Project Setup
   * File -> New Project -> Java with Maven -> Web Application
   * Name the project
   * Select Apache Tomcat as the server
   * Add the following dependencies to the pom.xml file
   <dependencies>
        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-servlet</artifactId>
            <version>2.32</version>
        </dependency>
        
        <dependency>
            <groupId>org.glassfish.jersey.core</groupId>
            <artifactId>jersey-server</artifactId>
            <version>2.32</version>
        </dependency>
        
        <dependency>
            <groupId>org.glassfish.jersey.inject</groupId>
            <artifactId>jersey-hk2</artifactId>
            <version>2.32</version>
        </dependency>

        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-json-jackson</artifactId>
            <version>2.32</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-servlet-core</artifactId>
            <version>2.32</version>
        </dependency>
    </dependencies>
    * The project package structure 
     - com.mycompany.testcw.model       (POJOs)
     - com.mycompany.testcw.storage     (DataStore)
     - com.mycompany.testcw.resource    (API endpoints)
     - com.mycompany.testcw.service     (Business logic)
     - com.mycompany.testcw.exception   (Custom exceptions)
     - com.mycompany.testcw.mapper      (Exception mappers)
     - com.mycompany.testcw.filter      (Logging filter)
   
    * Create an Application class to define the base URI path for all the REST resources within the application.
    * Create a DataStore class for the in-memory data store
    * Create resource models for Rooms, Sensors and Readings
    * Create the four resources for the API endpoints and service files with the business logic
    * For error handling, create a ErrorMessage model, exceptions classes and mapper classes for each custom exception and implement logging filters
    * Update the resources to trigger the exception
  3. Build the project
   * Right click project -> Clean and Build
   * Right click project -> Run or click the green play button

**cURL Commands**
1. Create a new room - POST /rooms
   
curl --location 'http://localhost:8080/testCW/api/v1/rooms' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data '{
        "id": "R5",
        "name": "Library Quiet Study",
        "capacity": 30
    }'

2. Delete a room - DELETE /rooms/R5
   
curl --location --request DELETE 'http://localhost:8080/testCW/api/v1/rooms/R5' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'

3. Create a new sensor - POST /sensors

curl --location 'http://localhost:8080/testCW/api/v1/sensors' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data '{
        "id": "TEMP-005",
        "type": "Temperature",
        "status": "ACTIVE",
        "currentValue": 24.5,
        "roomId": "R1"
    }'

4. Add readings to sensor - POST /sensors/TEMP-005/readings

curl --location 'http://localhost:8080/testCW/api/v1/sensors/TEMP-005/readings' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data '{
    "id": "READ-999",
    "timestamp": 1713876000000,
    "value": 24.5
}'

5. Retrieve the type of sensor - GET /sensors?type=Temperature

curl --location --request GET 'http://localhost:8080/testCW/api/v1/sensors?type=Temperature' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'

Answers to the questions of each part
1) In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.
•	By default, JAX-RS endpoints lifecycle is once-per-request, meaning a new instance is created for each HTTP request and it is destroyed after the response is sent. This avoids sharing instance state between requests. Shared data should be stored in a static or singleton-managed class because request-scoped resource instance fields are recreated for each request. For this project DataStore class is in static state to avoid losing data. To prevent race conditions, ConcurrentHashMaps or explicit synchronized blocks should be used. 

2) Why is the provision of “Hypermedia” (links and navigation within responses) considering a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?
•	HATEOAS allows the server to include links in responses so the client can find available actions dynamically and does not need to know all the URLs. This reduces dependency on static documentation.

3) When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client-side processing.
•	Returning a list of rooms would use less bandwidth though the client would need to make repeated requests to get more information. While returning the full room objects may take more bandwidth the client receives all required data in one request.  

4) Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE requests for a room multiple times.
•	Yes, it is. The server state is the same regardless of multiple requests. The room will remain deleted after the initial delete request. Repeated DELETE request will throw a 404 error, displaying that the room was not found.

5) We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?
•	If the client attempts to send data in a different format such as text/plain, JAX-RS will return a 415 Unsupported Media Type error stating that only JSON format is allowed. 

6) You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?
•	Using /api/vl/sensors/type/CO2 would mean it is looking for a new resource called CO2. As the requirement is to filter sensors by type using query parameters is a better option to filter the collection resource.

7) Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?
•	Sub-Resource locators improve organization by delegating tasks to nested functionalities. SensorResource handles the sensors while SensorReadingResource handles the readings. This keeps the code simple and avoids having one massive controller class.

8) Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?
•	The 422 is more accurate because the request syntax was correct and the JSON body was correct but the data inside is semantically incorrect. A 404 usually means the requested URI resource does not exist, rather than a validation issue inside a correct JSON body.

9) From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?
•	Exposed stacks traces allow attackers to see vulnerabilities within the code, and could gather information such as library versions used, the line of code locations. These vulnerabilities could be used to attack the system. 

10) Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?
•	It is advantageous because this keeps business logic clean and separate from the filters. Using ContainerRequestFilter and ContainerResponseFilter automatically catches requests and are not missed during development. Additionally, this method promotes reusability of the code and consistency.
