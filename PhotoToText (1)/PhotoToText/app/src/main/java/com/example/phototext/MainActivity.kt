package com.example.phototext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.example.phototext.databinding.ActivityMainBinding

/**
 * Aplikasi sederhana: pilih foto dari galeri -> ekstrak teks dengan ML Kit (on-device OCR).
 * Tidak butuh koneksi internet dan tidak butuh API key.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var hasilTeks: String = ""

    // Photo Picker bawaan Android (tidak butuh izin storage di Android 13+,
    // dan otomatis fallback ke picker sistem di versi lebih lama).
    private val pickImage = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            prosesGambar(uri)
        } else {
            Toast.makeText(this, "Tidak ada foto dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPilihFoto.setOnClickListener {
            pickImage.launch(
                androidx.activity.result.PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        binding.btnSalinTeks.setOnClickListener {
            salinKeClipboard()
        }
    }

    private fun prosesGambar(uri: Uri) {
        tampilkanLoading(true)
        try {
            val bitmap = decodeBitmapDariUri(uri)
            binding.ivPreview.setImageBitmap(bitmap)

            val gambarInput = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            recognizer.process(gambarInput)
                .addOnSuccessListener(OnSuccessListener { hasil ->
                    tampilkanLoading(false)
                    hasilTeks = hasil.text
                    if (hasilTeks.isBlank()) {
                        binding.tvHasil.text = "Tidak ada teks yang terdeteksi pada foto ini."
                        binding.btnSalinTeks.isEnabled = false
                    } else {
                        binding.tvHasil.text = hasilTeks
                        binding.btnSalinTeks.isEnabled = true
                    }
                })
                .addOnFailureListener(OnFailureListener { e ->
                    tampilkanLoading(false)
                    binding.tvHasil.text = "Gagal membaca teks: ${e.message}"
                    Toast.makeText(this, "Gagal memproses gambar", Toast.LENGTH_SHORT).show()
                })
        } catch (e: Exception) {
            tampilkanLoading(false)
            Toast.makeText(this, "Gagal memuat gambar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun decodeBitmapDariUri(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = android.graphics.ImageDecoder.createSource(contentResolver, uri)
            android.graphics.ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                decoder.isMutableRequired = true
            }
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }
    }

    private fun tampilkanLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) android.view.View.VISIBLE else android.view.View.GONE
        binding.btnPilihFoto.isEnabled = !loading
    }

    private fun salinKeClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Hasil OCR", hasilTeks)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Teks disalin ke clipboard", Toast.LENGTH_SHORT).show()
    }
}
