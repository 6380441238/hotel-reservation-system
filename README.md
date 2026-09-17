# 🏨 Hotel Reservation System

A RESTful Hotel Reservation System developed using **Java 21, Spring Boot, Spring Data JPA, Hibernate, MySQL and REST APIs**.

## 📌 Project Overview

The Hotel Reservation System is a backend REST API application designed to manage hotel room reservations.

The system allows customers to reserve hotel rooms based on room availability for a selected date range. It manages customers, room types, rooms, reservations, payments and room amenities.

The application follows a layered architecture using **Controller, Service and Repository** layers.

---

## 🛠️ Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- REST API
- Maven
- Lombok
- Jakarta Bean Validation
- Swagger / OpenAPI
- IntelliJ IDEA
- Postman

---

## 🏗️ Project Architecture

The application follows a layered architecture:

```text
Client / Postman / Swagger
          |
          v
     Controller
          |
          v
       Service
          |
          v
     Repository
          |
          v
       MySQL

DTOs and Mappers are used to separate API request/response objects from database entities.

📂 Project Structure
hotel-reservation-system
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.hotel.reservation
│   │   │       │
│   │   │       ├── config
│   │   │       │   └── OpenApiConfig.java
│   │   │       │
│   │   │       ├── controller
│   │   │       │   ├── CustomerController.java
│   │   │       │   ├── RoomTypeController.java
│   │   │       │   ├── RoomController.java
│   │   │       │   ├── ReservationController.java
│   │   │       │   ├── PaymentController.java
│   │   │       │   └── AmenityController.java
│   │   │       │
│   │   │       ├── service
│   │   │       │   ├── CustomerService.java
│   │   │       │   ├── RoomTypeService.java
│   │   │       │   ├── RoomService.java
│   │   │       │   ├── ReservationService.java
│   │   │       │   ├── PaymentService.java
│   │   │       │   └── AmenityService.java
│   │   │       │
│   │   │       ├── repository
│   │   │       │   ├── CustomerRepository.java
│   │   │       │   ├── RoomTypeRepository.java
│   │   │       │   ├── RoomRepository.java
│   │   │       │   ├── ReservationRepository.java
│   │   │       │   ├── PaymentRepository.java
│   │   │       │   └── AmenityRepository.java
│   │   │       │
│   │   │       ├── entity
│   │   │       │   ├── Customer.java
│   │   │       │   ├── RoomType.java
│   │   │       │   ├── Room.java
│   │   │       │   ├── Reservation.java
│   │   │       │   ├── Payment.java
│   │   │       │   └── Amenity.java
│   │   │       │
│   │   │       ├── dto
│   │   │       │   ├── CustomerRequestDTO.java
│   │   │       │   ├── CustomerResponseDTO.java
│   │   │       │   ├── RoomTypeRequestDTO.java
│   │   │       │   ├── RoomTypeResponseDTO.java
│   │   │       │   ├── RoomRequestDTO.java
│   │   │       │   ├── RoomResponseDTO.java
│   │   │       │   ├── ReservationRequestDTO.java
│   │   │       │   ├── ReservationResponseDTO.java
│   │   │       │   ├── PaymentRequestDTO.java
│   │   │       │   ├── PaymentResponseDTO.java
│   │   │       │   ├── AmenityRequestDTO.java
│   │   │       │   └── AmenityResponseDTO.java
│   │   │       │
│   │   │       ├── mapper
│   │   │       │   ├── CustomerMapper.java
│   │   │       │   ├── RoomTypeMapper.java
│   │   │       │   ├── RoomMapper.java
│   │   │       │   ├── ReservationMapper.java
│   │   │       │   ├── PaymentMapper.java
│   │   │       │   └── AmenityMapper.java
│   │   │       │
│   │   │       └── exception
│   │   │           ├── ResourceNotFoundException.java
│   │   │           ├── BusinessException.java
│   │   │           └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│
├── pom.xml
├── README.md
└── .gitignore
📦 Modules
1. Customer Management

The customer module manages hotel customers.

Features:

Create customer
Get all customers
Get customer by ID
Update customer
Delete customer
2. Room Type Management

The room type module manages different categories of hotel rooms.

Features:

Create room type
Get all room types
Get room type by ID
Update room type
Delete room type

Example room types:

Standard
Deluxe
Suite
3. Room Management

The room module manages individual hotel rooms.

Features:

Create room
Get all rooms
Get room by ID
Update room
Delete room
Assign amenities to rooms

Each room belongs to a room type.

4. Reservation Management

The reservation module manages hotel bookings.

Features:

Check room availability
Create reservation
Update reservation
Cancel reservation
View customer reservation history

The system prevents overlapping confirmed reservations for the same room.

5. Payment Management

The payment module manages payments related to reservations.

Features:

Create payment
Get payment by ID
Validate payment amount
Prevent duplicate payments
Allow payment only for confirmed reservations
6. Amenity Management

The amenity module manages facilities available in hotel rooms.

Examples:

WiFi
Swimming Pool
Air Conditioning
Television

Features:

Create amenity
Get all amenities
Get amenity by ID
Update amenity
Delete amenity
Assign amenity to a room
🔗 Entity Relationships

The application uses JPA entity relationships.

RoomType
   |
   | One-to-Many
   v
Room
   |
   | Many-to-Many
   v
Amenity


Customer
   |
   | One-to-Many
   v
Reservation
   |
   | Many-to-One
   v
Room


Reservation
   |
   | One-to-One
   v
Payment
Relationships Used
RoomType → Room : One-to-Many
Room → RoomType : Many-to-One
Room → Amenity : Many-to-Many
Customer → Reservation : One-to-Many concept
Reservation → Customer : Many-to-One
Reservation → Room : Many-to-One
Reservation → Payment : One-to-One
🗄️ Database Tables

The application uses MySQL with the following tables:

room_types
rooms
customers
reservations
payments
amenities
room_amenities
Database Relationships
room_types
     |
     | room_type_id
     v
   rooms
     |
     | room_id
     v
reservations
     |
     | reservation_id
     v
 payments

The room_amenities table is used as the join table for the Room ↔ Amenity many-to-many relationship.

⭐ Important Features
Room Availability

Before creating a reservation, the system checks whether the selected room is already reserved for overlapping dates.

Date Range Validation

The system validates that:

Check-in date < Check-out date

Invalid date ranges are rejected with a validation error.

Double Booking Prevention

The system prevents two confirmed reservations from overlapping for the same room.

The overlap logic checks:

Existing Check-in < New Check-out
AND
Existing Check-out > New Check-in
Automatic Reservation Amount

The total reservation amount is calculated automatically:

Total Amount =
Number of Nights × Room Type Base Price

Example:

Room price = ₹3000 per night
Number of nights = 4

Total = ₹3000 × 4
      = ₹12000
Payment Validation

Payment is allowed only when:

Reservation exists
Reservation status is CONFIRMED
Payment does not already exist
Payment amount matches the reservation total
DTO Mapping

The application uses DTO classes for API requests and responses.

This prevents direct exposure of entity objects through REST APIs.

Transaction Management

Spring @Transactional is used for important database operations such as:

Reservation creation
Reservation update
Reservation cancellation
Payment creation
Room amenity assignment
Exception Handling

Global exception handling is implemented using @RestControllerAdvice.

The application handles:

Resource not found
Business validation errors
Request validation errors
🔐 Validation

Jakarta Bean Validation is used for validating API requests.

Examples:

Required customer name
Valid email address
Required phone number
Required room number
Required room type
Valid room type price
Required reservation dates
Positive payment amount
🌐 API Base URL
http://localhost:8081
📚 Swagger / OpenAPI

Swagger UI is available at:

http://localhost:8081/swagger-ui/index.html

Swagger provides interactive documentation and testing for all REST APIs.

🔌 API Endpoints
Customer APIs
Method	Endpoint	Description
POST	/api/customers	Create customer
GET	/api/customers	Get all customers
GET	/api/customers/{id}	Get customer by ID
PUT	/api/customers/{id}	Update customer
DELETE	/api/customers/{id}	Delete customer
Room Type APIs
Method	Endpoint	Description
POST	/api/room-types	Create room type
GET	/api/room-types	Get all room types
GET	/api/room-types/{id}	Get room type by ID
PUT	/api/room-types/{id}	Update room type
DELETE	/api/room-types/{id}	Delete room type
Room APIs
Method	Endpoint	Description
POST	/api/rooms	Create room
GET	/api/rooms	Get all rooms
GET	/api/rooms/{id}	Get room by ID
PUT	/api/rooms/{id}	Update room
DELETE	/api/rooms/{id}	Delete room
PUT	/api/rooms/{roomId}/amenities/{amenityId}	Add amenity to room
Reservation APIs
Method	Endpoint	Description
POST	/api/reservations	Create reservation
GET	/api/reservations/availability	Check room availability
PUT	/api/reservations/{id}	Update reservation
PUT	/api/reservations/{id}/cancel	Cancel reservation
GET	/api/reservations/customer/{customerId}	Get customer reservation history
Payment APIs
Method	Endpoint	Description
POST	/api/payments	Create payment
GET	/api/payments/{id}	Get payment by ID
Amenity APIs
Method	Endpoint	Description
POST	/api/amenities	Create amenity
GET	/api/amenities	Get all amenities
GET	/api/amenities/{id}	Get amenity by ID
PUT	/api/amenities/{id}	Update amenity
DELETE	/api/amenities/{id}	Delete amenity
📝 Sample API Requests
Create Customer
POST /api/customers
{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210"
}
Create Room Type
POST /api/room-types
{
    "typeName": "Deluxe",
    "description": "Deluxe room with premium facilities",
    "basePrice": 3000
}
Create Room
POST /api/rooms
{
    "roomNumber": "101",
    "floor": 1,
    "status": "AVAILABLE",
    "roomTypeId": 1
}
Create Amenity
POST /api/amenities
{
    "name": "WiFi",
    "description": "High-speed wireless internet"
}
Add Amenity to Room
PUT /api/rooms/1/amenities/1

No request body is required.

Check Room Availability
GET /api/reservations/availability?roomId=1&checkInDate=2026-10-01&checkOutDate=2026-10-05
Create Reservation
POST /api/reservations
{
    "customerId": 1,
    "roomId": 1,
    "checkInDate": "2026-10-01",
    "checkOutDate": "2026-10-05"
}
Create Payment
POST /api/payments
{
    "reservationId": 1,
    "amount": 12000,
    "paymentMethod": "UPI"
}
🚀 Running the Project
Prerequisites

Make sure the following are installed:

Java 21
MySQL
IntelliJ IDEA
Maven
Postman
Step 1: Clone the Repository
git clone YOUR_GITHUB_REPOSITORY_URL
Step 2: Open the Project

Open the project in IntelliJ IDEA.

Step 3: Create the Database

Open MySQL Workbench and run:

CREATE DATABASE hotel_reservation_db;
Step 4: Configure Database

Open:

src/main/resources/application.properties

Configure your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/hotel_reservation_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

Do not commit your actual database password to GitHub.

Step 5: Run the Application

Run:

HotelReservationSystemApplication.java

The application starts on:

http://localhost:8081
Step 6: Open Swagger

Open:

http://localhost:8081/swagger-ui/index.html
🧪 Testing

The APIs can be tested using:

Swagger UI
Postman
MySQL Workbench

The following functionality has been tested:

Customer CRUD operations
Room Type CRUD operations
Room CRUD operations
Amenity CRUD operations
Room and Amenity relationship
Room availability
Reservation creation
Reservation update
Reservation cancellation
Customer reservation history
Payment creation
Payment retrieval
Date validation
Double booking prevention
Payment validation
Global exception handling
📊 Example Reservation Flow
1. Create Room Type
        ↓
2. Create Room
        ↓
3. Create Customer
        ↓
4. Add Amenities to Room
        ↓
5. Check Room Availability
        ↓
6. Create Reservation
        ↓
7. Calculate Total Amount
        ↓
8. Create Payment
        ↓
9. View Customer Reservation History
🔮 Future Enhancements

The following features can be added in future versions:

User authentication and authorization
Admin and customer roles
Hotel frontend application
Online payment gateway integration
Email booking confirmation
Pagination and sorting
Advanced room search
Cancellation and refund management
Booking notifications
Docker deployment
Cloud deployment