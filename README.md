# task-management-app 🚀

**task-management-app** is a Trello-inspired project management system developed as a robust REST API using Java and Spring Boot. The project's goal is to provide a solid backend structure for managing workspaces, tasks, and users following industry standards.

---

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 4.x
* **Database:** PostgreSQL 15 (Dockerized)
* **Security:** Spring Security + JWT (Stateless)
* **Infrastructure:** Docker & Docker Compose
* **Persistence:** Spring Data JPA + Hibernate
* **Documentation:** Swagger / OpenAPI (Coming soon)

---

## ✨ Key Features (MVP)

* ✅ **Authentication:** User registration and Login with encrypted passwords (BCrypt).
* ✅ **Task Management:** Full CRUD for tasks with statuses (`BACKLOG`, `TODO`, `IN_PROGRESS`, `DONE`) and priorities.
* ✅ **Workspaces:** Task organization grouped by workspaces.
* ✅ **Security:** Endpoint protection using JWT tokens.
* ✅ **Validation:** Input data validation on all endpoints.
* ✅ **Enviroment isolation:** Fully dockerized database and enviroment variable management via ```.env```.

---

## 📂 Project Architecture

The project follows a **Hybrid Layered Architecture** following standard Layered architecture package structure with sub-packaging by feature to maintain high cohesion:

1.  **Controller:** Entry layer that handles HTTP requests.
2.  **Service:** Business logic and complex validations.
3.  **Repository:** Interaction with the PostgreSQL database.
4.  **Model/DTO:** Database entities and Data Transfer Objects.



---

## 🚀 Installation & Setup

### Prerequisites
* JDK 21 or higher
* Gradle
* PostgreSQL running locally or via Docker (recommended)

### Steps to run
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/task-management-app.git](https://github.com/your-username/task-management-app.git)
   ```
2. **Environment Configuration:**<br>
   Create a ```.env``` file in the root directory following ```.env.example```.
3. **Infrastructure Setup:**<br>
   ```docker-compose up -d```
4. **Run the application:**<br>
   ```./gradlew bootRun```