# SDAT Sprint API (Spring Boot + MySQL)

REST API built with Spring Boot + Spring Data JPA and a MySQL relational database.

This API models:
- City (has many Airports)
- Airport (belongs to one City)
- Passenger (lives in one City, flies on many Aircraft)
- Aircraft (carries many Passengers, uses many Airports for takeoff/landing)

## Tech
- Java 17
- MySQL
- Spring Web + Spring Data JPA

## How to Run
### 1 Start MySQL
Make sure MySQL is running locally.

Create database (optional if using createDatabaseIfNotExist):
```sql
CREATE DATABASE IF NOT EXISTS sdat_sprint;
