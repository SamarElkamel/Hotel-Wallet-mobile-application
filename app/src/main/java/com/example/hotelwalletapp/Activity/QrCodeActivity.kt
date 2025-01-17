package com.example.hotelwalletapp.Activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.hotelwalletapp.Fragment.LoginFragment
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.databinding.ScanqrcodeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity

class QrCodeActivity : AppCompatActivity()  {
    private lateinit var binding: ScanqrcodeBinding
    private var cardView2: CardView? = null
    private var hide: Animation? = null
    private var reveal: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScanqrcodeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        cardView2 = binding.cardView2
        hide = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        reveal = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        cardView2?.startAnimation(reveal)
        cardView2?.visibility = View.VISIBLE
        cardView2?.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }




    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startQRCodeScanning()
        } else {
            Toast.makeText(
                applicationContext,
                "Permission to access your camera is required to use this app",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun startQRCodeScanning() {
        val qrScanner = IntentIntegrator(this)
        qrScanner.setPrompt("Scan a QR code")
        qrScanner.setCameraId(0)
        qrScanner.setOrientationLocked(true)
        qrScanner.setBeepEnabled(true)
        qrScanner.captureActivity = CaptureActivity::class.java
        qrScanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val qrData = result.contents.split(",")
                if (qrData.size == 3 && qrData[2] in listOf("Room", "SPA", "Restaurant")) {

                    val id = qrData[0]
                    val num = qrData[1]
                    val type = qrData[2]
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("type", type)
                    intent.putExtra("num", num)
                    intent.putExtra("id", id)
                    startActivity(intent)
                } else {
                    val snackbar = Snackbar.make(binding.root, "Invalid Qr Code", Snackbar.LENGTH_SHORT)
                    val view = snackbar.view
                    val params = view.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.CENTER
                    view.layoutParams = params
                    view.setBackgroundColor(ContextCompat.getColor(this , androidx.appcompat.R.color.error_color_material_dark))
                    snackbar.show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
