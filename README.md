# School_Management_System
Designed and developed a scalable school management system leveraging Spring Boot, implementing MVC architecture and best practices for backend development.
## ⚙️ Getting Started

### Prerequisites

Make sure you have installed:

- Java 17+
- Maven
- MySQL

---

### 🔧 Installation
1. Clone the repository:
```
https://github.com/AbdallahElgendy/School_Management_System.git
```
Navigate to the project folder:
```
cd easy-school
```
Configure database in application.properties:
```
spring.datasource.url=jdbc:mysql://localhost:3306/easyschool
spring.datasource.username=root
spring.datasource.password=your_password
```
Run the application:
```
mvn spring-boot:run
```
🌐 Application URLs
```
Home Page: http://localhost:8080/
```
🔐 Authentication
Spring Security is used for login and role-based access
Default roles: USER / ADMIN
📂 Project Structure
Controller → Handles HTTP requests
Service → Business logic
Repository → Database access
Entity → Database models

📌 Future Improvements
Convert to REST APIs with Angular frontend
Add JWT authentication
Dockerize the application

👨‍💻 Author
Abdallah Elgendy
