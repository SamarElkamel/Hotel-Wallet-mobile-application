package com.example.hotelwalletapp.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.hotelwalletapp.databinding.PayementBinding
import com.example.hotelwalletapp.databinding.ThanksBinding
import com.example.hotelwalletapp.viewmodel.SharedViewModel
import java.io.File

class PaymentHereFragment: Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: ThanksBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ThanksBinding.inflate(layoutInflater)
        val pdfFilePath = arguments?.getString("pdfFilePath")
        binding.btnconf.setOnClickListener()

        {
            openPdf()
        }



        return binding.root
    }

    companion object {
        fun newInstance(pdfFilePath: String): PaymentHereFragment {
            val args = Bundle()
            args.putString("pdfFilePath", pdfFilePath)
            val fragment = PaymentHereFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun openPdf() {
        val pdfFilePath = arguments?.getString("pdfFilePath")
        if (!pdfFilePath.isNullOrEmpty()) {
            val file = File(pdfFilePath)
            if (file.exists()) {
                val uri = FileProvider.getUriForFile(requireContext(), "com.example.hotelwalletapp.fileprovider", file)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                }
            } else {

            }
        } else {
            // Handle the case where the PDF file path is null or empty
        }
    }

}

