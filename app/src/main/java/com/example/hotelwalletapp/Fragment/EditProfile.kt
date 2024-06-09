package com.example.hotelwalletapp.Fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.databinding.EditProfileBinding
import com.example.hotelwalletapp.viewmodel.ClientViewModel
import com.example.hotelwalletapp.viewmodel.ClientViewModelFactory
import com.google.android.material.snackbar.Snackbar


class EditProfile : Fragment() , View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener {

    private lateinit var binding: EditProfileBinding
    private lateinit var ViewModel: ClientViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditProfileBinding.inflate(layoutInflater)
        binding.PhoneNumber.onFocusChangeListener = this
        binding.FullName.onFocusChangeListener = this
        ViewModel = ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)

        ViewModel.client.observe(viewLifecycleOwner) { client ->
            binding.FullName.setText(client.name)
            binding.PhoneNumber.setText(client.phone)

        }

        binding.button1.setOnClickListener(this)
        binding.FullName.setOnKeyListener(this)
        binding.PhoneNumber.setOnKeyListener(this)

        binding.button2.setOnClickListener()
        {
            binding.PhoneNumber.text?.clear()
            binding.FullName.text?.clear()
        }

        return binding.root
    }


    private fun validateFullName(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.FullName.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Full name is required"
        }
        if (errorMessage != null) {
            binding.edtFullName.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePhonenumber(): Boolean {

        var errorMessage: String? = null
        val value = binding.PhoneNumber.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Phone number is required"
        }
        if (errorMessage != null) {
            binding.mobile.apply {
                isErrorEnabled = true
                error = errorMessage
            }

        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.button1)
            onSubmit()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (binding.root != null && view != null) {
            when (view.id) {
                binding.FullName.id -> {
                    if (hasFocus) {
                        if (binding.edtFullName.isErrorEnabled) {
                            binding.edtFullName.isErrorEnabled = false
                        }
                    } else {
                        validateFullName()
                    }
                }

                binding.PhoneNumber.id -> {
                    if (hasFocus) {
                        if (binding.mobile.isErrorEnabled) {
                            binding.mobile.isErrorEnabled = false
                        }
                    } else {
                        validatePhonenumber()
                    }
                }

            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP) {
            onSubmit()
        }
        return false
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!validateFullName()) isValid = false
        if (!validatePhonenumber()) isValid = false
        return isValid
    }

    private fun onSubmit() {
        if (validate()) {
            ViewModel.client.value?.let { client ->
                try {
                    client.name = binding.FullName.text.toString()
                    client.phone = binding.PhoneNumber.text.toString()
                    ViewModel.updateClient(client.id, client)
                    val snackbar = Snackbar.make(binding.root, "Your information has been updated!", Snackbar.LENGTH_SHORT)
                    val view = snackbar.view
                    val params = view.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.CENTER
                    view.layoutParams = params
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.material_deep_teal_200))
                    snackbar.show()
                } catch (e: Exception) {
                    Log.e("Exception", "Error updating client", e)
                }

            }
        }
    }
}



