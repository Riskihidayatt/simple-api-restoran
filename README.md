# Simple Restaurant API

API sederhana untuk mengelola menu, pelanggan, dan transaksi restoran.

## Menjalankan API (Contoh)

_Bagian ini memerlukan informasi spesifik tentang bagaimana API ini dijalankan. Silakan ganti dengan instruksi yang relevan._

```bash
# Contoh (ganti dengan perintah sebenarnya):
# ./gradlew bootRun
# atau
# npm start
```

## Endpoints API

Basis URL: `http://localhost:8080`

### Menu

Endpoints untuk mengelola menu restoran.

*   **POST Menu**
    *   Metode: `POST`
    *   Path: `/menus` atau `/menus/{id}` (Catatan: Path `/menus/{id}` untuk POST tidak umum, biasanya `/menus`)
    *   Deskripsi: Menambahkan menu baru.
    *   Contoh Body:
        ```json
        {
          "name": "Mie Goreng Spesial",
          "description": "Mie goreng dengan telur, ayam, dan sayuran",
          "price": 35000.0,
          "category": "FOOD"
        }
        ```

*   **GET Menu by ID**
    *   Metode: `GET`
    *   Path: `/menus/{menuId}`
    *   Deskripsi: Mendapatkan detail menu berdasarkan ID.

*   **GET All Menus**
    *   Metode: `GET`
    *   Path: `/menus`
    *   Deskripsi: Mendapatkan daftar semua menu. (Catatan: Koleksi Postman menunjukkan path `/menus/{id}` untuk ini, yang mungkin tidak benar).

*   **PUT Menu**
    *   Metode: `PUT`
    *   Path: `/menu/update/{menuId}`
    *   Deskripsi: Memperbarui menu yang sudah ada.
    *   Contoh Body:
        ```json
        {
          "name": "Mie Goreng Spesial update",
          "description": "Mie goreng dengan telur, ayam, dan sayuran update",
          "price": 38000.0,
          "category": "FOOD",
          "isAvailable": false
        }
        ```

*   **DELETE Menu**
    *   Metode: `DELETE`
    *   Path: `/menu/delete/{menuId}`
    *   Deskripsi: Menghapus menu berdasarkan ID.

### Customer

Endpoints untuk mengelola data pelanggan.

*   **POST Customer**
    *   Metode: `POST`
    *   Path: `/customer`
    *   Deskripsi: Menambahkan pelanggan baru.
    *   Contoh Body:
        ```json
        {
          "name": "Budi Santoso",
          "email": "budi.santoso@email.com",
          "phone": "08123456789",
          "address": "Jl. Merdeka No. 123, Jakarta"
        }
        ```

*   **GET Customer by ID**
    *   Metode: `GET`
    *   Path: `/api/customer/search/{customerId}`
    *   Deskripsi: Mendapatkan detail pelanggan berdasarkan ID.

*   **GET All Customers**
    *   Metode: `GET`
    *   Path: `/customer` atau `/api/customer` (Catatan: Koleksi Postman menunjukkan path `/menus/{id}` untuk ini, yang jelas salah. Path yang lebih umum adalah `/customer` atau `/api/customer`).
    *   Deskripsi: Mendapatkan daftar semua pelanggan.

*   **PUT Customer**
    *   Metode: `PUT`
    *   Path: `/customer/update/{customerId}`
    *   Deskripsi: Memperbarui data pelanggan yang sudah ada.
    *   Contoh Body:
        ```json
        {
          "name": "Budi Santoso Updated",
          "email": "budi.santoso.updated@email.com",
          "phone": "08123456780",
          "address": "Jl. Merdeka No. 12A, Jakarta"
        }
        ```

*   **DELETE Customer**
    *   Metode: `DELETE`
    *   Path: `/customer/delete/{customerId}`
    *   Deskripsi: Menghapus pelanggan berdasarkan ID.

### Transactions

Endpoints untuk mengelola transaksi.

*   **POST Transaction**
    *   Metode: `POST`
    *   Path: `/api/transactions`
    *   Deskripsi: Membuat transaksi baru.
    *   Contoh Body:
        ```json
        {
          "customerId": "b1488d9c-8772-422d-a2c9-29b885776fe2",
          "items": [
            {
              "menuId": "f169ba64-894f-4676-9055-151206b83473",
              "quantity": 2
            },
            {
              "menuId": "98422b0e-5370-4404-a643-7ce70b36bcaa",
              "quantity": 1
            }
          ],
          "notes": "Nasi goreng tidak pedas."
        }
        ```

*   **GET Transaction by ID**
    *   Metode: `GET`
    *   Path: `/api/transactions/search/{transactionId}`
    *   Deskripsi: Mendapatkan detail transaksi berdasarkan ID.

*   **GET All Transactions**
    *   Metode: `GET`
    *   Path: `/api/transactions`
    *   Deskripsi: Mendapatkan daftar semua transaksi, dapat difilter berdasarkan parameter.
    *   Parameter Query:
        *   `customerId` (string): ID pelanggan
        *   `startDate` (string, format: `YYYY-MM-DDTHH:MM:SS`): Tanggal mulai filter
        *   `endDate` (string, format: `YYYY-MM-DDTHH:MM:SS`): Tanggal akhir filter
        *   `page` (integer): Nomor halaman (default 0)
        *   `size` (integer): Jumlah item per halaman (default 10)

## Catatan Tambahan

*   Beberapa path endpoint dalam koleksi Postman yang asli (misalnya untuk "GET All Menus" dan "GET All Customers") tampak tidak konsisten atau mungkin salah. README ini mencoba menyajikan path yang lebih standar dan umum digunakan untuk operasi RESTful. Selalu verifikasi dengan implementasi backend yang sebenarnya.
*   ID yang digunakan dalam contoh path (seperti `37d67030-683f-420f-938d-f49e7f1a4972`) adalah contoh UUID dan harus diganti dengan ID yang relevan saat menggunakan API.
