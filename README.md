# 🔧 Car Service Manager

> A web application for managing a car service shop, developed as a project for the **Software Systems Engineering** course at the Faculty of Mathematics and Computer Science, UBB Cluj (2025-2026).

---

## 📋 Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Features](#features)
- [Actors](#actors)
- [Installation & Setup](#installation--setup)
- [Project Structure](#project-structure)
- [Application Flow](#application-flow)
- [Database](#database)

---

## 📖 Description

Car Service Manager is a web application with a layered architecture that enables complete management of a car service shop:

- **Clients** can make online appointments for repairs
- **Receptionists** manage clients, vehicles, appointments and invoices
- **Mechanics** view their assigned repairs and update their status
- **Managers** manage employees, spare parts and view statistics

---

## 🛠️ Technologies

| Component | Technology |
|-----------|------------|
| Backend | Java 17 + Spring Boot 4.0 |
| Frontend | Thymeleaf + Bootstrap 5 |
| Database | PostgreSQL |
| ORM | Hibernate / Spring Data JPA |
| Security | Spring Security (BCrypt) |
| Build tool | Gradle |
| Diagram tool | StarUML |

---

## 🏗️ Architecture

The application follows a **Layered Architecture**:

```
┌─────────────────────────────────┐
│     Presentation Layer          │
│  Controller + View (Thymeleaf)  │
├─────────────────────────────────┤
│     Business Logic Layer        │
│           Service               │
├─────────────────────────────────┤
│      Data Access Layer          │
│    Repository (Spring JPA)      │
├─────────────────────────────────┤
│          Database               │
│          PostgreSQL             │
└─────────────────────────────────┘
```

---

## ⚙️ Features

### Iteration 1 — Core Features
| UC | Name | Actor |
|----|------|-------|
| UC-0 | Authentication | All users |
| UC-1 | Schedule a repair | Client |
| UC-2 | Client management (CRUD) | Receptionist |
| UC-3 | Vehicle management (CRUD) | Receptionist |
| UC-8 | Employee management | Manager |

### Iteration 2 — Main Features
| UC | Name | Actor |
|----|------|-------|
| UC-4 | Repair management | Mechanic |
| UC-5 | Spare parts management | Manager |
| UC-6 | Invoice generation | Receptionist |

### Iteration 3 — Advanced Features
| UC | Name | Actor |
|----|------|-------|
| UC-7 | View statistics | Manager |
| UC-9 | View repair history | Client |
| UC-10 | Client notification | System |

---

## 👥 Actors

| Actor | Description | Access |
|-------|-------------|--------|
| **Manager** | System administrator | `/manager/**` |
| **Receptionist** | Front-office staff | `/receptionist/**` |
| **Mechanic** | Technical staff | `/mechanic/**` |
| **Client** | External user | `/client/**` |

---

## 🚀 Installation & Setup

### Prerequisites
- Java 17+
- PostgreSQL 14+
- Gradle

### Steps

**1. Clone the project**
```bash
git clone <repository-url>
cd CarServiceManager
```

**2. Create the database in PostgreSQL**
```sql
CREATE DATABASE carservicedb;
```

**3. Configure the database connection**

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/carservicedb
spring.datasource.username=postgres
spring.datasource.password=your_password
```

**4. Run the application**
```bash
./gradlew bootRun
```

**5. Access the application**
```
http://localhost:8080
```

### Default account (created automatically on first start)
```
Username: admin
Password: admin123
Role:     MANAGER
```

---

## 📁 Project Structure

```
src/main/java/com/carservice/carservicemanager/
├── config/
│   └── DataInitializer.java          # Creates admin user on startup
├── controller/
│   ├── AuthController.java           # Login + role-based redirect
│   ├── ClientController.java         # Client CRUD
│   ├── VehicleController.java        # Vehicle CRUD
│   ├── EmployeeController.java       # Employee CRUD
│   ├── AppointmentController.java    # Client appointments
│   ├── ReceptionistAppointmentController.java  # Receptionist appointments
│   ├── RepairController.java         # Mechanic repair management
│   ├── SparePartController.java      # Spare parts CRUD
│   ├── InvoiceController.java        # Invoice generation
│   └── DashboardController.java      # Role-based dashboards
├── service/
│   ├── ClientService.java
│   ├── VehicleService.java
│   ├── EmployeeService.java
│   ├── AppointmentService.java
│   ├── RepairService.java
│   ├── SparePartService.java
│   ├── InvoiceService.java
│   ├── NotificationService.java
│   └── impl/                         # Service implementations
├── repository/
│   ├── ClientRepository.java
│   ├── VehicleRepository.java
│   ├── EmployeeRepository.java
│   ├── AppointmentRepository.java
│   ├── RepairRepository.java
│   ├── SparePartRepository.java
│   ├── InvoiceRepository.java
│   └── NotificationRepository.java
├── model/entity/
│   ├── Client.java
│   ├── Vehicle.java
│   ├── Employee.java
│   ├── Appointment.java
│   ├── Repair.java
│   ├── SparePart.java
│   ├── Invoice.java
│   └── Notification.java
└── security/
    ├── SecurityConfig.java
    └── CustomUserDetailsService.java

src/main/resources/
├── templates/
│   ├── login.html
│   ├── manager/
│   ├── receptionist/
│   ├── mechanic/
│   └── client/
└── application.properties
```

---

## 🔄 Application Flow

```
1. CLIENT makes an online appointment
        ↓
2. RECEPTIONIST sees the appointment
   → Creates a repair
   → Assigns a mechanic
        ↓
3. MECHANIC receives the repair
   → Updates status (Pending → In Progress → Completed)
   → Adds labor cost
        ↓
4. RECEPTIONIST generates the invoice
   (available only after status is Completed)
        ↓
5. CLIENT receives a notification
   → Can view repair history
```

---

## 🗄️ Database

### Main Entities

| Table | Description |
|-------|-------------|
| `clients` | Client data + login credentials |
| `vehicles` | Vehicles associated with clients |
| `employees` | Staff (Manager, Receptionist, Mechanic) |
| `appointments` | Appointments made by clients |
| `repairs` | Repairs created by receptionists |
| `spare_parts` | Spare parts in stock |
| `repair_spareparts` | Many-to-many join table for repairs and parts |
| `invoices` | Generated invoices |
| `notifications` | Notifications sent to clients |

### ORM (Hibernate / Spring Data JPA)
Tables are created automatically by Hibernate on application startup (`ddl-auto=update`). Relationships are defined using JPA annotations:
- `@OneToMany` / `@ManyToOne` — Client → Vehicle, Repair → Employee
- `@ManyToMany` — Repair ↔ SparePart
- `@OneToOne` — Repair → Invoice, Repair → Notification

---

## 📊 UML Diagrams

The project includes the following diagrams created in **StarUML**:

- ✅ Use Case Diagram (10 features)
- ✅ Conceptual Class Diagram (Lab 3)
- ✅ Refined Class Diagram with boundary/control/service/repository (Lab 4)
- ✅ Sequence Diagrams for Iteration 1 (UC-0, UC-1, UC-2, UC-3, UC-8)
- ✅ Communication Diagrams (UC-0, UC-2, UC-4, UC-8)

---

*Project developed for the Software Systems Engineering course | UBB Cluj | 2025-2026*
