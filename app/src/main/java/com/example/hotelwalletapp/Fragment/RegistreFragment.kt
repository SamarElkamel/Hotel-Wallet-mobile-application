package com.example.hotelwalletapp.Fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.data.RegisterBody
import com.example.hotelwalletapp.databinding.SignupBinding
import com.example.hotelwalletapp.viewmodel.RegisterActivityViewModel
import androidx.lifecycle.Observer
import com.example.hotelwalletapp.model.Client
import com.example.hotelwalletapp.viewmodel.RegisterActivityViewModelFactory

class RegistreFragment : Fragment(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener {
    private lateinit var binding: SignupBinding
    private lateinit var ViewModel: RegisterActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignupBinding.inflate(layoutInflater)
        binding.edtSignUpFullName.onFocusChangeListener = this
        binding.edtSignUpEmail.onFocusChangeListener = this
        binding.edtSignUpPassword.onFocusChangeListener = this
        binding.edtSignUpMobile.onFocusChangeListener = this
        binding.btnSignUp.setOnClickListener(this)
        binding.edtSignUpConfirmPassword.onFocusChangeListener = this
        val myTextView=binding.txtSignIn
        ViewModel = ViewModelProvider(
            this,
            RegisterActivityViewModelFactory(
                ClientRepository(APIService.getService()),
                requireActivity().application
            )
        ).get(RegisterActivityViewModel::class.java)
        setup0bservers()
        myTextView.setOnClickListener {
            val secondFragment =LoginFragment()
            val transaction=requireActivity().supportFragmentManager
                .beginTransaction()
            transaction.replace(R.id.flFragment,secondFragment)
            transaction.commit()
        }
        return binding.root
    }


    private fun setup0bservers() {
        ViewModel.getIsLoading().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }


        ViewModel.getIsUniqueEmail().observe(viewLifecycleOwner) {
            if (validateEmail(shoudUpdateView = false)) {
                if (it) {
                    binding.Mail.apply {
                        if (isErrorEnabled) isErrorEnabled = false
                        setStartIconDrawable(R.drawable.check_circle_24)
                        setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                    }
                } else {
                    binding.Mail.apply {
                        if (startIconDrawable != null) startIconDrawable = null
                        isErrorEnabled = true
                        error = "Email is already taken"
                    }
                }
            }
        }


        ViewModel.getErrorMessage().observe(viewLifecycleOwner) {

            val formErrorkeys = arrayOf("fullName", "email",  "password", "Mobile")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorkeys.contains(entry.key)) {
                    when (entry.key) {
                        "fullName" -> {
                            binding.FullName.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                        "email" -> {
                            binding.Mail.apply {

                                isErrorEnabled = true
                                error = entry.value
                            }
                        }


                        "password" -> {
                            binding.password.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                        "Mobile" -> {
                            binding.mobile.apply {

                                isErrorEnabled = true
                                error = entry.value
                            }
                        }
                    }

                } else {
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()) {
                    AlertDialog.Builder(requireContext())
                        .setIcon(R.drawable.info_24).setTitle("INFORMATION")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }
                        .show()
                }
            }
        }
        ViewModel.getUser().observe(viewLifecycleOwner) {

            if (it != null) {

            }

        }
    }


    private fun validateFullName(): Boolean {
        var errorMessage: String? = null
        val value: String = binding.edtSignUpFullName.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Full name is required"
        }
        if (errorMessage != null) {
            binding.FullName.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateEmail(shoudUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = binding.edtSignUpEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Email address is invalid"
        }
        if (errorMessage != null && shoudUpdateView) {
            binding.Mail.apply {
                isErrorEnabled = true
                error = errorMessage
            }


        }
        return errorMessage == null
    }


    private fun validatePhonenumber(): Boolean {

        var errorMessage: String? = null
        val value = binding.edtSignUpMobile.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Phone number is required"
        } else if (value.length < 8) {
            errorMessage = "Phone number is not valide"
        }
        if (errorMessage != null) {
            binding.mobile.apply {
                isErrorEnabled = true
                error = errorMessage
            }

        }
        return errorMessage == null
    }


    private fun validatePassword(): Boolean {

        var errorMessage: String? = null
        val value = binding.edtSignUpPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be 6 characters long"
        }
        if (errorMessage != null) {
            binding.password.apply {
                isErrorEnabled = true
                error = errorMessage
            }

        }
        return errorMessage == null
    }


    private fun validateConfirmPassword(): Boolean {
        var errorMessage: String? = null
        val value = binding.edtSignUpConfirmPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm password is required"
        } else if (value.length < 6) {
            errorMessage = "Confirm password must be 6 characters long"
        }
        if (errorMessage != null) {
            binding.cpassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePasswordAndConfirmPassword(): Boolean {
        var errorMessage: String? = null
        val password = binding.edtSignUpPassword.text.toString()
        val confirmPassword = binding.edtSignUpConfirmPassword.text.toString()
        if (password != confirmPassword) {
            errorMessage = "Confirm password doesn't match with password"
        }
        if (errorMessage != null) {
            binding.cpassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {

        if (view != null && view.id == R.id.btnSignUp)
            onSubmit()

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (binding.root != null && view != null) {
            when (view.id) {
                binding.edtSignUpFullName.id -> {
                    if (hasFocus) {
                        if (binding.FullName.isErrorEnabled) {
                            binding.FullName.isErrorEnabled = false
                        }
                    } else {
                        validateFullName()
                    }
                }

                binding.edtSignUpEmail.id -> {
                    if (hasFocus) {
                        if (binding.Mail.isErrorEnabled) {
                            binding.Mail.isErrorEnabled = false
                        }
                    } else {
                        (validateEmail())

                    }
                }

                binding.edtSignUpMobile.id -> {
                    if (hasFocus) {
                        if (binding.mobile.isErrorEnabled) {
                            binding.mobile.isErrorEnabled = false
                        }
                    } else {
                        validatePhonenumber()
                    }
                }

                binding.edtSignUpPassword.id -> {
                    if (hasFocus) {
                        if (binding.password.isErrorEnabled) {
                            binding.password.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && binding.edtSignUpConfirmPassword.text!!.isNotEmpty() && validateConfirmPassword() &&
                            validatePasswordAndConfirmPassword()
                        ) {
                            if (binding.cpassword.isErrorEnabled) {
                                binding.cpassword.isErrorEnabled = false
                            }
                            binding.cpassword.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }


                binding.edtSignUpConfirmPassword.id -> {
                    if (hasFocus) {
                        if (binding.cpassword.isErrorEnabled) {
                            binding.cpassword.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (binding.password.isErrorEnabled) {
                                binding.password.isErrorEnabled = false
                            }
                            binding.cpassword.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
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

    private fun onSubmit() {
        if (validate()) {
            val photoDrawable = resources.getDrawable(R.drawable.user)
            val photoUrl = photoDrawable.toString()
            ViewModel.registerUser(
                RegisterBody(

                    binding.edtSignUpFullName.text!!.toString(),
                    binding.edtSignUpEmail.text!!.toString(),
                    binding.edtSignUpPassword.text!!.toString(),
                    binding.edtSignUpMobile.text!!.toString(),
                    0.0f,
                    photoUrl

                )
            )

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModel.navigateToNextFragment.observe(viewLifecycleOwner, Observer { isLoggedIn ->
            Log.d("navigateToNextFragment", isLoggedIn.toString())
            if (isLoggedIn) {
                val client = ViewModel.getUser().value
                Log.d("client", client.toString())
                client?.let {
                    navigateToProfileFragment(it)
                }

            }
        })
    }

    private fun navigateToProfileFragment(client: Client) {
        val secondFragment = ProfileFragment()
        val bundle = Bundle()
        bundle.putParcelable("client", client)
        secondFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, secondFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun validate(): Boolean {
        var isValid = true
        if (!validateFullName()) isValid = false
        if (!validateEmail()) isValid = false
        if (!validatePassword()) isValid = false
        if (!validatePhonenumber()) isValid = false
        if (!validateConfirmPassword()) isValid = false
        if (isValid && !validatePasswordAndConfirmPassword()) isValid = false
        return isValid
    }

}