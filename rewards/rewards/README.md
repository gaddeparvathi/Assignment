
# Rewards Program API

## Overview
This project is a RESTful API for a rewards program that calculates reward points for customers based on their transactions. It includes endpoints for managing customers, transactions, and retrieving reward points.

## Technologies Used
- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For data access and interaction with the database.
- **H2 Database**: In-memory database for development and testing.
- **JUnit**: For unit testing.
- **Maven**: For dependency management and build automation.

## Setup and Run
### Prerequisites
- Java 17
- Maven

### Steps to Run the Application
Clone the repository:https://github.com/gaddeparvathi/Assignment.git

### Endpoints

### Customer Endpoints

###### Reward Points Endpoints

**Get Monthly Reward Points for a Specific Customer**

URL: /reward-reports/customer/{customerId}/monthly

Method: GET

Description: Retrieves monthly reward points for a specific customer.

Example Request:

GET http://localhost:8080/reward-reports/customer/1/monthly?startDate=2024-07-01&endDate=2024-09-30
GET http://localhost:8080/reward-reports/customer/2/monthly?startDate=2024-07-01&endDate=2024-09-30


**Get All Reward Points for All Customers**

URL: /reward-reports/all/monthly"

Method: GET

Description: Retrieves reward points for all customers within a specified date range.

Example Request:

GET http://localhost:8080/reward-reports/all/monthly?startDate=2024-07-01&endDate=2024-09-30


### Reward Points Calculation Logic

Reward points are calculated based on the following criteria:

2 points for every dollar spent over $100.

1 point for every dollar spent between $50 and $100.


### Example Calculation

For a transaction of $120:

Points for the in between  $50 and $100: 1 * 50 = 50 points

Points for the remaining $20: 2 * 20 = 40 points

Total points: 40 + 50 = 90 points


###### Sample Data

When the application starts, it initializes with the following sample data:

**Customers**

Joseph (ID: 1, Email: joseph@example.com)

John (ID: 2, Email: john@example.com)

**Transactions**

Joseph's Transactions:

1. $120.0 on 2024-07-10


2. $80.0 on 2024-07-15


3. $150.0 on 2024-09-05


4. $60.0 on 2024-08-10

John's Transactions:

1. $200.0 on 2024-07-20


2. $90.0 on 2024-08-15


3. $50.0 on 2024-09-08
