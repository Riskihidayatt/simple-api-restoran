# Simple Restaurant API

A simple RESTful API for managing restaurant menus, customers, and transactions. Built using Java 21, Spring Boot 3.4.5, and PostgreSQL.

---

## ?? System Requirements

Before running this project, make sure your system has the following installed:
- **Java Development Kit (JDK) 21**
- **Maven** (Optional, as this project already includes Maven Wrapper `mvnw`)
- **PostgreSQL** (Default port 5432)
- **Git**

---

## ??? How to Clone and Run the Project

### 1. Clone the Repository
Run the following command in your terminal to clone the repository:
```bash
git clone <YOUR_REPOSITORY_URL>
cd simple-api-restoran
```

### 2. Database Configuration (Environment Variables)
This project uses environment variables to secure database credentials.
1. Duplicate the `.env.example` file and rename it to `.env`.
   ```bash
   cp .env.example .env
   ```
2. Open the `.env` file and set your PostgreSQL password:
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/name_db
   DB_USERNAME=postgres
   DB_PASSWORD=your_postgres_password
   ```
3. **Make sure** you have already created an empty database named `name_db` in your PostgreSQL before running the application. Tables will be created automatically by Hibernate (Spring Data JPA).

### 3. Running the Application
You can run the application using the project's built-in Maven Wrapper.

**Windows Users (PowerShell):**
If you are not using an `.env` runner plugin in your IDE, load the environment variables into your PowerShell session first:
```powershell
Get-Content .env | Foreach-Object {
    $var = $_.Split('=')
    Set-Item "Env:\$($var[0])" $var[1]
}
.\mvnw spring-boot:run
```

**Or If Using IntelliJ IDEA:**
1. Install the **EnvFile** plugin.
2. Click on `Edit Configurations...` in the application's Run configuration.
3. Open the **EnvFile** tab, check `Enable EnvFile`, then add the newly created `.env` file.
4. Click the *Run* / *Debug* button.

---

## ?? API Endpoints Structure

Base URL: `http://localhost:8081/api`

### ?? Menu (`/api/menus`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/menus` | Add a new menu item |
| `GET` | `/api/menus` | Retrieve all menu items (Supports Pagination & Filtering) |
| `GET` | `/api/menus/{id}` | Retrieve menu item details by ID |
| `PUT` | `/api/menus/update/{id}` | Update a menu item |
| `DELETE` | `/api/menus/delete/{id}` | Delete a menu item |

### ?? Customer (`/api/customers`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/customers` | Add a new customer |
| `GET` | `/api/customers` | Retrieve all customers (Supports Pagination & Filtering) |
| `GET` | `/api/customers/search/{id}`| Retrieve customer details by ID |
| `PUT` | `/api/customers/update/{id}`| Update customer data |
| `DELETE` | `/api/customers/delete/{id}`| Delete a customer |

### ?? Transaction (`/api/transactions`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/transactions` | Create a new purchase transaction |
| `GET` | `/api/transactions` | Retrieve transaction list (Supports Pagination & Date/Customer Filtering) |
| `GET` | `/api/transactions/search/{id}`| Retrieve transaction details by ID |

---

## ?? Pagination and Sorting Notes
For endpoints that return a list (GET All), you can send standard *Spring Pageable* query params, for example:
- `?page=0&size=5` (Get the first page with a maximum of 5 items per page)
- `?sort=name,desc` (Sort by name in *descending* order)
