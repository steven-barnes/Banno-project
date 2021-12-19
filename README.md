# Weather Service

### Description
This service returns simple JSON data describing the weather conditions for a given latitude and longitude coordinate.

### Endpoints
GET /weather

| Attribute                | Type     | Required               | Description           |
|:-------------------------|:---------|:-----------------------|:----------------------|
| `lat`                    | number |  Yes | Latitude |
| `lon`                    | number |  Yes | Longitude |

### Starting Service
Clone the project. From the project root, run ```sbt run```. The service will be available at address localhost:9000.

### Usage Example
curl -v 'http://localhost:9000/weather?lat=49&lon=-127.1'

