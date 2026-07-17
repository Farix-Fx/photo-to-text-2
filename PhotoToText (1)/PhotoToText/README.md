# Foto ke Teks (Android)

Aplikasi Android sederhana untuk mengubah foto dari galeri menjadi teks (OCR),
menggunakan **ML Kit Text Recognition** dari Google (berjalan langsung di
perangkat / on-device, gratis, tanpa API key, tanpa internet).

## Fitur
- Pilih foto dari galeri (pakai Photo Picker bawaan Android, aman untuk semua versi Android).
- Ekstrak teks dari foto secara otomatis.
- Tampilkan hasil teks yang bisa di-scroll dan dipilih (select/copy manual).
- Tombol "Salin Teks" untuk menyalin seluruh hasil ke clipboard.

## Cara membuka project
1. Download & extract folder `PhotoToText` ini.
2. Buka **Android Studio** (versi terbaru direkomendasikan, minimal Hedgehog 2023.1+).
3. Pilih **File > Open**, arahkan ke folder `PhotoToText`.
4. Tunggu proses **Gradle Sync** selesai (Android Studio otomatis mengunduh
   dependency yang dibutuhkan, pastikan koneksi internet aktif saat sync pertama kali).
5. Sambungkan HP Android (aktifkan USB Debugging) atau gunakan emulator.
6. Klik tombol **Run ▶** di Android Studio.

## Struktur project
```
PhotoToText/
├── app/
│   ├── build.gradle.kts          # dependency (ML Kit, dsb)
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/phototext/
│       │   └── MainActivity.kt   # logika utama
│       └── res/
│           ├── layout/activity_main.xml
│           └── values/           # warna, tema, string
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Catatan penting
- Minimum Android: **API 24 (Android 7.0)**.
- Tidak perlu meminta izin akses galeri secara manual — aplikasi memakai
  **Photo Picker** sistem Android, yang otomatis aman & tidak butuh
  permission `READ_EXTERNAL_STORAGE` di Android 13 ke atas.
- ML Kit Text Recognition versi *default* paling akurat untuk teks berbasis
  huruf Latin (termasuk Bahasa Indonesia dan Inggris). Kalau nanti butuh
  dukungan aksara Cina/Jepang/Korea/Devanagari, tinggal tambahkan library
  ML Kit tambahan (`text-recognition-chinese`, dst) — beri tahu saya kalau
  perlu dibantu.
- Ukuran APK akan bertambah sedikit karena model ML Kit di-bundle di dalam
  aplikasi (supaya bisa jalan offline).

## Kalau ingin saya kembangkan lebih lanjut
Beberapa ide pengembangan yang bisa ditambahkan:
- Ambil foto langsung dari kamera (bukan cuma dari galeri).
- Simpan riwayat hasil OCR.
- Ekspor hasil teks ke file .txt atau bagikan (share) ke aplikasi lain.
- Dukungan bahasa non-Latin (Cina, Jepang, Korea, dll).

Tinggal beri tahu, nanti saya update project-nya.
