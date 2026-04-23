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


**How to Run**

```bash
git clone <repo-url>
cd testCW
mvn clean install
```

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
--header 'Content-Type: application/json' \
--data '{
        "id": "R5",
        "name": "Library Quiet Study",
        "capacity": 30
    }'

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

5. Retreive the type of sensor - GET /sensors?type=Temperature

curl --location --request GET 'http://localhost:8080/testCW/api/v1/sensors?type=Temperature' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json'
