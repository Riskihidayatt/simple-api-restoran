# Simple Restaurant API

RESTful API sederhana untuk mengelola menu, pelanggan, dan transaksi restoran. Dibangun menggunakan Java 21, Spring Boot 3.4.5, dan PostgreSQL.

---

## ?? Persyaratan Sistem

Sebelum menjalankan proyek ini, pastikan sistem Anda telah terinstal:
- **Java Development Kit (JDK) 21**
- **Maven** (Opsional, karena proyek ini sudah menyertakan Maven Wrapper `mvnw`)
- **PostgreSQL** (Port default 5432)
- **Git**

---

## ??? Cara Clone dan Menjalankan Proyek

### 1. Clone Repositori
Jalankan perintah berikut di terminal Anda untuk mengkloning repositori:
```bash
git clone <URL_REPOSITORI_ANDA>
cd simple-api-restoran
```

### 2. Konfigurasi Database (Environment Variables)
Proyek ini menggunakan variabel *environment* untuk mengamankan kredensial database.
1. Gandakan file `.env.example` dan ubah namanya menjadi `.env`.
   ```bash
   cp .env.example .env
   ```
2. Buka file `.env` dan atur kata sandi PostgreSQL Anda:
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/restoenigma_db
   DB_USERNAME=postgres
   DB_PASSWORD=password_postgres_anda
   ```
3. **Pastikan** Anda sudah membuat database kosong bernama `restoenigma_db` di PostgreSQL Anda sebelum menjalankan aplikasi. Tabel akan dibuat secara otomatis oleh Hibernate (Spring Data JPA).

### 3. Menjalankan Aplikasi
Anda dapat menjalankan aplikasi menggunakan Maven Wrapper bawaan proyek. 

**Pengguna Windows (PowerShell):**
Jika Anda tidak menggunakan plugin `.env` runner di IDE, muat environment variables ke session PowerShell Anda terlebih dahulu:
```powershell
Get-Content .env | Foreach-Object {
    $var = $_.Split('=')
    Set-Item "Env:\$($var[0])" $var[1]
}
.\mvnw spring-boot:run
```

**Atau Jika Menggunakan IntelliJ IDEA:**
1. Instal plugin **EnvFile**.
2. Klik opsi `Edit Configurations...` pada konfigurasi jalannya (Run) aplikasi.
3. Buka tab **EnvFile**, centang `Enable EnvFile`, lalu tambahkan file `.env` yang baru dibuat.
4. Klik tombol *Run* / *Debug*.

---

## ?? Struktur Endpoints API

Basis URL: `http://localhost:8081/api`

### ?? Menu (`/api/menus`)
| Metode | Endpoint | Keterangan |
| :--- | :--- | :--- |
| `POST` | `/api/menus` | Menambahkan menu baru |
| `GET` | `/api/menus` | Mengambil daftar semua menu (Mendukung Paginasi & Filter) |
| `GET` | `/api/menus/{id}` | Mengambil detail menu berdasarkan ID |
| `PUT` | `/api/menus/update/{id}` | Memperbarui menu |
| `DELETE` | `/api/menus/delete/{id}` | Menghapus menu |

### ?? Customer (`/api/customers`)
| Metode | Endpoint | Keterangan |
| :--- | :--- | :--- |
| `POST` | `/api/customers` | Menambahkan customer baru |
| `GET` | `/api/customers` | Mengambil daftar semua customer (Mendukung Paginasi & Filter) |
| `GET` | `/api/customers/search/{id}`| Mengambil detail customer berdasarkan ID |
| `PUT` | `/api/customers/update/{id}`| Memperbarui data customer |
| `DELETE` | `/api/customers/delete/{id}`| Menghapus customer |

### ?? Transaction (`/api/transactions`)
| Metode | Endpoint | Keterangan |
| :--- | :--- | :--- |
| `POST` | `/api/transactions` | Membuat transaksi pembelian baru |
| `GET` | `/api/transactions` | Mengambil daftar transaksi (Mendukung Paginasi & Filter Tanggal/Customer) |
| `GET` | `/api/transactions/search/{id}`| Mengambil detail transaksi berdasarkan ID |

---

## ?? Catatan Paginasi dan Sorting
Untuk endpoint yang mengembalikan daftar (GET All), Anda bisa mengirimkan query params standar *Spring Pageable*, contohnya:
- `?page=0&size=5` (Mendapatkan halaman pertama dengan maksimal 5 data per halaman)
- `?sort=name,desc` (Mengurutkan berdasarkan nama secara *descending*)

