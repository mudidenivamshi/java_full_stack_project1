<<<<<<< HEAD
# Employee_leave_management_system_vamshi
=======
# Employee Leave Management System
### College Project | Java · Spring Boot · Thymeleaf · MySQL

---

## Project Structure

```
Employee_leave_management_system/
│
├── frontend/
│   └── src/main/resources/
│       ├── templates/
│       │   ├── index.html          ← Dashboard page
│       │   ├── apply-leave.html    ← Apply Leave form
│       │   └── leave-list.html     ← View all requests (approve/reject/delete)
│       └── static/css/
│           └── style.css           ← All styling
│
└── backend/
    ├── src/main/
    │   ├── java/com/example/leave/
    │   │   ├── LeaveManagementApplication.java   ← Main class
    │   │   ├── controller/LeaveController.java   ← HTTP request handling
    │   │   ├── model/LeaveRequest.java           ← Database entity
    │   │   ├── repository/LeaveRepository.java   ← Data access
    │   │   └── service/LeaveService.java         ← Business logic
    │   └── resources/
    │       └── application.properties            ← DB config
    ├── .mvn/wrapper/
    │   └── maven-wrapper.properties
    ├── mvnw.cmd                                  ← Run without installing Maven
    └── pom.xml                                   ← Dependencies
```

---

## Step-by-Step Setup Guide

### Step 1 — Install Java JDK 21 (or higher)

1. Go to: https://www.oracle.com/java/technologies/downloads/
2. Download **JDK 21** for Windows.
3. Run the installer.
4. Add Java to your system PATH:
   - Search "Environment Variables" in the Start menu.
   - Under "System Variables", edit **Path**.
   - Add: `C:\Program Files\Java\jdk-21\bin`
5. Verify by opening Command Prompt and typing:
   ```
   java -version
   ```
   You should see something like: `java version "21.x.x"`

> **Note:** Java 24 also works if you already have it installed.

---

### Step 2 — Install MySQL

1. Go to: https://dev.mysql.com/downloads/installer/
2. Download **MySQL Installer** (Community Edition — it's free).
3. Run the installer and choose **"Developer Default"** setup.
4. During setup, you will be asked to set a **root password**. Remember it!
5. Complete the installation.
6. Verify MySQL is running by opening MySQL Workbench or MySQL Command Line Client.

---

### Step 3 — Create the Database

Open MySQL Command Line Client (or MySQL Workbench) and run:

```sql
CREATE DATABASE leave_management;
```

That's it! Spring Boot (Hibernate) will automatically create the `leave_requests` table when you first run the application.

---

### Step 4 — Configure Database Credentials

Open the file:
```
backend/src/main/resources/application.properties
```

Find these two lines and replace with your actual MySQL credentials:

```properties
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

**Example** (if you use root with password "admin123"):
```properties
spring.datasource.username=root
spring.datasource.password=admin123
```

---

### Step 5 — Install Maven (Choose ONE option)

#### Option A — Install Maven (Recommended)
1. Go to: https://maven.apache.org/download.cgi
2. Download the **Binary zip archive** (e.g., `apache-maven-3.9.6-bin.zip`).
3. Extract it to `C:\Program Files\apache-maven-3.9.6`.
4. Add `C:\Program Files\apache-maven-3.9.6\bin` to your system PATH.
5. Verify: Open Command Prompt and type `mvn -version`.

#### Option B — Use an IDE with Maven Built-In
- **IntelliJ IDEA**: Open the `backend` folder as a project. IntelliJ has Maven built-in.
- **Eclipse**: Install the "Spring Tools Suite" (STS) plugin.

---

### Step 6 — Run the Application

Open **Command Prompt** or **PowerShell**, navigate to the `backend` folder, and run:

```bash
cd C:\Users\vinut\Desktop\Employee_leave_management_system\backend
mvn spring-boot:run
```

You will see output like:
```
...
Started LeaveManagementApplication in 3.5 seconds
Tomcat started on port 8080
```

---

### Step 7 — Open in Browser

Open your web browser and go to:

```
http://localhost:8080
```

---

## Features & How to Test

### Dashboard (`/`)
- Shows total, pending, approved, and rejected leave counts.
- Click "Apply for Leave" to go to the form.
- Click "View All Requests" to see the table.

### Apply for Leave (`/apply-leave`)
1. Fill in all fields (Employee Name, Employee ID, Leave Type, Start Date, End Date, Reason).
2. Click **Apply Leave**.
3. Validation checks: all fields required, end date ≥ start date.
4. On success → redirected to Leave Requests with a green success message.

### Leave Requests (`/leave-list`)
- Displays all requests in a table.
- **PENDING** requests show yellow badge + **Approve** and **Reject** buttons.
- **APPROVED** requests show green badge (no action buttons).
- **REJECTED** requests show red badge (no action buttons).
- **Delete** button is always visible.

### Approve
- Click ✓ Approve on a PENDING request → status changes to APPROVED in MySQL.

### Reject
- Click ✗ Reject on a PENDING request → status changes to REJECTED in MySQL.

### Delete
- Click 🗑 Delete → record is permanently removed from MySQL.

---

## Technology Summary (For Viva)

| Layer | Technology | Purpose |
|---|---|---|
| Frontend | Thymeleaf | Generates HTML from Java objects |
| Styling | Plain CSS | Colours, layout, responsive design |
| Backend | Spring Boot (MVC) | Routes HTTP requests to controller |
| Business Logic | Java (Service class) | Validates and processes data |
| Database Access | Spring Data JPA | Saves/reads data from MySQL |
| Database | MySQL | Stores leave_requests table |
| Build Tool | Maven | Downloads dependencies, compiles, runs |

---

## Common Errors & Fixes

| Error | Fix |
|---|---|
| `Access denied for user 'root'@'localhost'` | Wrong password in `application.properties` |
| `Unknown database 'leave_management'` | Run `CREATE DATABASE leave_management;` in MySQL |
| `Port 8080 already in use` | Change `server.port=8081` in `application.properties` |
| `mvn not recognized` | Install Maven and add it to PATH |
| `java not recognized` | Install JDK and add it to PATH |
| Templates not found | Make sure you run from the `backend/` folder |

---

## MySQL Table Structure (Auto-created by Hibernate)

```sql
CREATE TABLE leave_requests (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL,
    employee_id   VARCHAR(255) NOT NULL,
    leave_type    VARCHAR(255) NOT NULL,
    start_date    DATE NOT NULL,
    end_date      DATE NOT NULL,
    reason        TEXT NOT NULL,
    status        VARCHAR(255) NOT NULL DEFAULT 'PENDING'
);
```

> **Note:** You do NOT need to run this SQL manually. Hibernate creates this table automatically when you start the app with `spring.jpa.hibernate.ddl-auto=update`.
>>>>>>> a066646 (Initial commit)
