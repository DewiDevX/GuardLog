# 🛡️ GuardLog - Aplikasi Keamanan (JavaFX - Single User)

**GuardLog** adalah aplikasi pencatatan keamanan harian berbasis JavaFX yang didesain khusus untuk penggunaan satu akun bersama. Setiap sesi login akan mencatat nama petugas yang aktif dan menghubungkan semua aktivitas dalam aplikasi dengan petugas tersebut.

---

## 🧭 Alur Program & Modul Fungsional

### 1️⃣ Guard Login - (Modul Utama & Input Nama Petugas)
- **Tampilan Awal:** Langsung menampilkan **Form Login**.
- **Input:** 
  - `Username` dan `Password`
  - `Nama Lengkap Petugas` (Field tambahan)
- **Validasi:**
  - Username & password harus sesuai (misalnya: `admin` / `secure`)
  - Nama petugas **tidak boleh kosong**
- **Jika Berhasil:**
  - Nama petugas disimpan secara global (misalnya melalui `UserSession` singleton)
  - Navigasi menuju **Security Dashboard**
- **Jika Gagal:**
  - Muncul pesan error: `"Username, password, atau nama petugas tidak valid"`

---

### 2️⃣ Security Dashboard - (Pusat Informasi)
- **Akses:** Hanya setelah login berhasil
- **Tampilan:**
  - `Profil Petugas`: Menampilkan nama lengkap petugas yang sedang aktif
  - `Statistik & Ringkasan Hari Ini`:
    - Jumlah pengunjung
    - Laporan insiden yang masih pending
    - Jadwal patroli
    - Panel statistik cepat

---

### 3️⃣ Visitor Management - (Manajemen Pengunjung)
- **Registrasi Masuk:**
  - Form input: `Nama Pengunjung`, `Tujuan`
  - Nama Petugas akan otomatis tercatat
  - Data: `Jam Masuk`, dan nanti `Jam Keluar` saat checkout
- **Riwayat Kunjungan:**
  - Tabel histori + kolom “Petugas Pencatat”
  - Bisa ditambahkan fitur pencarian berdasarkan petugas

---

### 4️⃣ Incident Reporting - (Laporan Insiden)
- **Laporan Baru:**
  - Input: `Kategori`, `Waktu`, `Lokasi`, `Deskripsi`
  - Nama petugas tercatat otomatis sebagai `Pelapor`
  - Status awal: `Pending`
- **Edit/Lihat Laporan:**
  - Tabel dengan filter berdasarkan: `Status`, `Kategori`, atau `Petugas`

---

### 5️⃣ Patrol Log - (Catatan Patroli)
- **Pencatatan Patroli:**
  - Petugas dapat mengisi checklist area dan catatan patroli
  - Nama petugas otomatis terlampir
- **Penyelesaian Shift:**
  - Nama petugas disimpan sebagai penutup shift patroli

---

### 6️⃣ Emergency Contacts - (Kontak Darurat)
- **Data Statis:**
  - Tidak berubah
- **Log Panggilan (Opsional):**
  - Catat juga nama petugas yang melakukan panggilan, jika fitur ini diaktifkan

---

### 7️⃣ Daily Reports - (Laporan Harian)
- **Generate Otomatis:**
  - Kumpulan data harian dari semua modul
  - Header laporan mencantumkan nama petugas yang membuat laporan
  - Setiap entri log sudah menyertakan nama petugasnya

---

### 8️⃣ Personal Notes - (Catatan Pribadi)
- **Catatan Baru:**
  - Input: `Judul`, `Isi`, `Kategori`
  - Nama petugas tercatat otomatis
- **Daftar Catatan:**
  - Bisa difilter
  - Menampilkan kolom petugas pembuat catatan
- **Catatan Handover (Opsional):**
  - Digunakan untuk transisi shift atau pesan ke diri sendiri

---

## 🧱 Struktur Program (Saran Folder JavaFX)

```plaintext
GuardLog/
│
├── src/
│   ├── auth/              # LoginController, AuthValidator
│   ├── dashboard/         # DashboardController, DashboardView
│   ├── visitor/           # VisitorController, VisitorModel
│   ├── incident/          # IncidentController, IncidentModel
│   ├── patrol/            # PatrolController, PatrolModel
│   ├── notes/             # NotesController, NotesModel
│   ├── utils/             # Session, Alerts, DatabaseHandler
│   └── Main.java          # Entry point
│
├── resources/
│   ├── fxml/              # Semua file tampilan .fxml
│   └── css/               # Styling aplikasi
│
└── README.md
