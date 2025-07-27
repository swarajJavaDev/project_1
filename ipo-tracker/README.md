# IPO Tracker Backend

A Spring Boot 3.x backend application for tracking IPO applications submitted under multiple PAN cards and Demat IDs. This is a private system for recording and monitoring only - no real IPO applications are submitted.

## Features

- **User Management**: Registration and Basic Authentication
- **Role-based Access**: USER and ADMIN roles with different permissions
- **IPO Application Tracking**: Record IPO applications with personal details
- **Search Functionality**: Search across applications by various criteria
- **External API Integration**: Fetch GMP (Grey Market Premium) and listing dates
- **Security**: Spring Security with Basic Authentication
- **Database**: MySQL with JPA/Hibernate

## Tech Stack

- Java 17+
- Spring Boot 3.2.0
- Spring Security 6.x
- Spring Data JPA
- MySQL 8.0
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ipo-tracker
```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE ipo_tracker_db;
```

Update `src/main/resources/application.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ipo_tracker_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 3. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Default Users

The application creates default users on startup:

- **Admin**: `admin` / `admin123`
- **Test User**: `testuser` / `test123`

## API Documentation

Base URL: `http://localhost:8080/api`

### Authentication Endpoints

#### Register User
```http
POST /api/auth/signup
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "confirmPassword": "password123"
}
```

#### Get Current User Info
```http
GET /api/auth/me
Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
```

### User Endpoints (ROLE_USER, ROLE_ADMIN)

#### Create IPO Application
```http
POST /api/user/ipo
Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw==
Content-Type: application/json

{
  "name": "John Doe",
  "pan": "ABCDE1234F",        // Optional field
  "dematId": "DP12345678",    // Optional field  
  "ipoName": "Reliance Industries",
  "applicationDate": "2024-01-15",
  "allotmentStatus": "PENDING"
}
```

**Note**: The `pan` and `dematId` fields are optional and can be omitted or set to `null`.

**Example without PAN/Demat ID:**
```json
{
  "name": "Jane Smith",
  "ipoName": "TCS IPO",
  "applicationDate": "2024-01-20",
  "allotmentStatus": "PENDING"
}
```

#### Get User's IPO Applications
```http
GET /api/user/ipo
Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw==
```

### Admin Endpoints (ROLE_ADMIN only)

#### Get All IPO Applications
```http
GET /api/admin/ipo
Authorization: Basic YWRtaW46YWRtaW4xMjM=
```

### Search Endpoints

#### Search IPO Applications
```http
GET /api/ipo/search?keyword=reliance
Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw==
```

### IPO Information Endpoints

#### Get IPO GMP and Info
```http
GET /api/ipo/gmp/Reliance%20Industries
Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw==
```

```http
GET /api/ipo/gmp?name=Reliance%20Industries
Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw==
```

## Data Models

### User
```json
{
  "id": 1,
  "username": "testuser",
  "role": "USER",
  "createdAt": "2024-01-01T10:00:00"
}
```

### IPO Application
```json
{
  "id": 1,
  "name": "John Doe",
  "pan": "ABCDE1234F",
  "dematId": "DP12345678",
  "ipoName": "Reliance Industries",
  "applicationDate": "2024-01-15",
  "listingDate": "2024-02-15",
  "allotmentStatus": "PENDING",
  "gmp": "₹150",
  "username": "testuser"
}
```

### Allotment Status Values
- `PENDING`
- `ALLOTTED`
- `NOT_ALLOTTED`
- `REFUND_INITIATED`
- `CANCELLED`

## Testing with cURL

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "password123",
    "confirmPassword": "password123"
  }'
```

### Create IPO Application
```bash
curl -X POST http://localhost:8080/api/user/ipo \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw==" \
  -d '{
    "name": "John Doe",
    "pan": "ABCDE1234F",
    "dematId": "DP12345678",
    "ipoName": "Reliance Industries",
    "applicationDate": "2024-01-15"
  }'
```

### Get User IPO Applications
```bash
curl -X GET http://localhost:8080/api/user/ipo \
  -H "Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw=="
```

### Search IPO Applications
```bash
curl -X GET "http://localhost:8080/api/ipo/search?keyword=reliance" \
  -H "Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw=="
```

### Get IPO GMP Information
```bash
curl -X GET http://localhost:8080/api/ipo/gmp/Reliance%20Industries \
  -H "Authorization: Basic dGVzdHVzZXI6dGVzdDEyMw=="
```

## Mock IPO Data

The application includes mock data for testing:

- Reliance Industries (GMP: ₹150, 12.5%)
- Tata Technologies (GMP: ₹200, 15.2%)
- IREDA (GMP: ₹35, 11.7%)
- Bharti Hexacom (GMP: ₹80, 14.5%)

## Security

- **Authentication**: Basic Authentication
- **Authorization**: Role-based (USER, ADMIN)
- **Password Encoding**: BCrypt
- **CORS**: Enabled for all origins (configure as needed)

## Error Handling

The application includes global exception handling with standardized error responses:

```json
{
  "success": false,
  "message": "Error description",
  "errors": {}
}
```

## Development

### Running with H2 Database (Testing)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

### Building for Production
```bash
mvn clean package
java -jar target/ipo-tracker-1.0.0.jar
```

## Future Enhancements

- JWT Token-based authentication
- Real external API integration
- Email notifications for allotment updates
- Export functionality (CSV, PDF)
- Advanced filtering and sorting
- Audit logging
- API rate limiting

## License

This project is for private use only.