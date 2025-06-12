# 🛡️ GuardLog

**GuardLog** adalah aplikasi pencatatan keamanan harian berbasis **JavaFX**, didesain untuk **petugas keamanan** yang menggunakan **akun bersama**, namun tetap memisahkan identitas berdasarkan **nama petugas aktif** saat login.

> Semua aktivitas terekam dengan nama petugas yang sedang bertugas.

---

## 🚀 Fitur Unggulan

- 🔐 Login Aman dengan Validasi Username, Password, dan Nama Petugas
- 🖥️ Dashboard Ringkas dengan Profil Petugas Aktif
- 🧾 Form Pencatatan Kunjungan & Checkout Pengunjung
- 🚨 Laporan Insiden (Kategori, Lokasi, Deskripsi)
- 🚶‍♂️ Patroli Area dengan Checklist & Notes
- 📆 Laporan Harian Otomatis berdasarkan Aktivitas
- 📝 Catatan Pribadi & Handover Antar Shift
- ☎️ Kontak Darurat (Data statis + log opsional)
- 📦 Data Pencatatan Terstruktur per Hari

---

## 🧭 Alur Aplikasi

```plaintext
Login ➝ Dashboard ➝ (Visitors / Incident / Patrol / Notes / Reports)
           ↓
Nama Petugas Terekam Otomatis di Semua Modul
```

---

## 🛠 Cara Menjalankan Aplikasi

1. **Clone Repository**
   ```bash
   git clone https://github.com/DewiDevX/GuardLog.git
   ```
2. **Buka di IDE**: Gunakan IntelliJ IDEA atau NetBeans.
3. **Run File**: `Main.java`
4. **Login:**
   - Username: `admin`
   - Password: `secure`
   - Nama Petugas: (isi bebas sesuai nama yang bertugas)

---

## 🗂 Struktur Folder

```plaintext
GuardLog/
├── src/
│   ├── auth/         # Login & Session Handling
│   ├── dashboard/    # Tampilan utama pasca login
│   ├── visitor/      # Form & Table kunjungan
│   ├── incident/     # Modul laporan insiden
│   ├── patrol/       # Modul catatan patroli
│   ├── notes/        # Modul catatan pribadi
│   ├── utils/        # Helper, Session, Manager
│   └── Main.java     # Entry point
├── resources/
│   ├── fxml/
│   └── css/
└── README.md
```

---

## 💻 Teknologi yang Digunakan

| Teknologi | Fungsi |
|-----------|--------|
| JavaFX | UI/UX Aplikasi |
| FXML | Struktur layout layar |
| Java (OOP) | Logika aplikasi & controller |
| Singleton Pattern | Menyimpan data sesi petugas |
| SQLite / JSON (opsional) | Penyimpanan data lokal |
| Scene Builder | Desain visual FXML |

---

## 👨‍💻 Developer

- **Nama**
- Aditya Hisbulah Bahri
- Dewi Astuti Muchtar
- **Kampus**: Universitas Hasanuddin
- **Program Studi**: Sistem Informasi

---

---

## ⚖️ Lisensi

Proyek ini berlisensi [MIT License](LICENSE), bebas digunakan dan dimodifikasi untuk kepentingan pendidikan atau internal organisasi.

---

## 📬 Kontak

Untuk kolaborasi, masukan, atau pertanyaan teknis:

📧 Email: dewiastutimuchtar9@email.com  
🐙 GitHub: [DewiDevX](https://github.com/DewiDevX)
