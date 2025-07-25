# Complete Blog Application Setup Instructions

This guide will help you set up and run the complete blog application with both the Spring Boot backend and React frontend.

## 📋 Prerequisites

- **Java 11+** (for Spring Boot backend)
- **Maven** (for building Spring Boot application)
- **Node.js 14+** (for React frontend)
- **npm** (comes with Node.js)

## 🚀 Quick Start

### Step 1: Set up the Spring Boot Backend

First, you need to create the Spring Boot backend with the Java files you provided:

1. **Create a new Spring Boot project** using Spring Initializr or your IDE
2. **Add the following dependencies** to your `pom.xml`:
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-data-jpa</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-validation</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-security</artifactId>
       </dependency>
       <dependency>
           <groupId>com.h2database</groupId>
           <artifactId>h2</artifactId>
           <scope>runtime</scope>
       </dependency>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <optional>true</optional>
       </dependency>
   </dependencies>
   ```

3. **Create the package structure**:
   ```
   src/main/java/com/myblogapi/
   ├── controller/
   ├── payload/
   ├── exception/
   ├── service/
   └── entity/
   ```

4. **Add your Java files** to the appropriate packages
5. **Create missing service implementations** (PostService, CommentService)
6. **Create entity classes** (Post, Comment)
7. **Add CORS configuration** to allow React frontend access

### Step 2: Configure CORS in Spring Boot

Add this configuration to your Spring Boot application:

```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

### Step 3: Start the Spring Boot Backend

```bash
# Navigate to your Spring Boot project directory
cd your-spring-boot-project

# Run the application
./mvnw spring-boot:run
# OR
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Step 4: Start the React Frontend

```bash
# Navigate to the React frontend directory
cd blog-frontend

# Install dependencies (if not already done)
npm install

# Start the development server
npm start
```

The frontend will start on `http://localhost:3000`

## 🔧 Configuration Options

### Backend Configuration (application.properties)
```properties
# Database configuration (H2 for development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console (for development)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Server configuration
server.port=8080
```

### Frontend Configuration
The frontend is configured to connect to `http://localhost:8080/api` by default. If you need to change this:

1. Open `blog-frontend/src/services/api.js`
2. Modify the `API_BASE_URL` constant

## 📱 Application Features

### Frontend Features
- ✅ **Post Management**: Create, read, update, delete blog posts
- ✅ **Pagination**: Navigate through posts with page controls
- ✅ **Sorting**: Sort posts by date or title
- ✅ **Comments System**: Add, edit, delete comments on posts
- ✅ **Responsive Design**: Works on mobile, tablet, and desktop
- ✅ **Form Validation**: Client-side and server-side validation
- ✅ **Error Handling**: User-friendly error messages
- ✅ **Loading States**: Spinners and loading indicators

### Backend API Endpoints
- `GET /api/post` - Get all posts with pagination
- `POST /api/post` - Create new post
- `GET /api/post/{id}` - Get post by ID
- `PUT /api/post/{id}` - Update post
- `DELETE /api/post/{id}` - Delete post
- `GET /api/posts/{postId}/comments` - Get comments for post
- `POST /api/posts/{postId}/comments` - Create comment
- `PUT /api/posts/{postId}/comments/{commentId}` - Update comment
- `DELETE /api/posts/{postId}/comments/{commentId}` - Delete comment

## 🐛 Troubleshooting

### Common Issues

1. **CORS Errors**
   - Ensure CORS is properly configured in Spring Boot
   - Check that the frontend URL (http://localhost:3000) is allowed

2. **Backend Not Starting**
   - Check Java version (needs Java 11+)
   - Verify all dependencies are in pom.xml
   - Check for compilation errors

3. **Frontend API Errors**
   - Ensure backend is running on port 8080
   - Check browser network tab for failed requests
   - Verify API endpoints are responding

4. **Database Issues**
   - H2 database is in-memory, data will be lost on restart
   - Access H2 console at http://localhost:8080/h2-console

### Testing the Setup

1. **Backend Test**: Visit `http://localhost:8080/api/post` in your browser
2. **Frontend Test**: Visit `http://localhost:3000` and try creating a post
3. **Full Integration**: Create a post, view it, add comments, edit, delete

## 🎯 Next Steps

Once you have the basic setup working:

1. **Add Authentication**: Implement user login/registration
2. **Database**: Switch from H2 to PostgreSQL/MySQL for production
3. **Deployment**: Deploy to cloud platforms
4. **Testing**: Add unit and integration tests
5. **Features**: Add categories, tags, search functionality

## 📞 Support

If you encounter issues:
1. Check the browser console for JavaScript errors
2. Check the Spring Boot console for backend errors
3. Verify all dependencies are correctly installed
4. Ensure both servers are running on the correct ports

Happy coding! 🚀