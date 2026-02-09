# task-management-app 🚀

**task-management-app** is a Trello-inspired project management system developed as a robust REST API using Java and Spring Boot. The project's goal is to provide a solid backend structure for managing workspaces, tasks, and users following industry standards.

---

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 4.x
* **Database:** PostgreSQL
* **Security:** Spring Security + JWT (JSON Web Tokens)
* **Persistence:** Spring Data JPA
* **Documentation:** Swagger / OpenAPI (Coming soon)

---

## ✨ Key Features (MVP)

* ✅ **Authentication:** User registration and Login with encrypted passwords (BCrypt).
* ✅ **Task Management:** Full CRUD for tasks with statuses (`BACKLOG`, `TODO`, `IN_PROGRESS`, `DONE`) and priorities.
* ✅ **Workspaces:** Task organization grouped by workspaces.
* ✅ **Security:** Endpoint protection using JWT tokens.
* ✅ **Validation:** Input data validation on all endpoints.

---

## 📂 Project Architecture

The project follows a standard **Layered Architecture** to ensure scalability and ease of maintenance:

1.  **Controller:** Entry layer that handles HTTP requests.
2.  **Service:** Business logic and complex validations.
3.  **Repository:** Interaction with the PostgreSQL database.
4.  **Model/DTO:** Database entities and Data Transfer Objects.



---

## 🚀 Installation & Setup

### Prerequisites
* JDK 21 or higher
* Gradle
* PostgreSQL running locally or via Docker

### Steps to run
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/task-management-app.git](https://github.com/your-username/task-management-app.git)