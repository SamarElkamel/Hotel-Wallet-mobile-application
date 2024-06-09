package com.example.hotelwalletapp.Fragment


import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Repository.CommandeRepository
import com.example.hotelwalletapp.data.LoginBody
import com.example.hotelwalletapp.databinding.LoginBinding
import com.example.hotelwalletapp.model.Client
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.viewmodel.*
import java.text.SimpleDateFormat
import java.util.*


class LoginFragment : Fragment(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener {
    private lateinit var binding: LoginBinding
    private lateinit var ViewModel: LoginViewModel
    private lateinit var clientViewModel: ClientViewModel
    private lateinit var commandeViewModel: CommandeViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBinding.inflate(layoutInflater)
        binding.btnSignIn.setOnClickListener(this)
        binding.Mail.onFocusChangeListener = this
        binding.password.onFocusChangeListener = this
        ViewModel = ViewModelProvider(
            this,
            LoginActivityViewModelFactory(ClientRepository(APIService.getService()), requireActivity().application)
        ).get(LoginViewModel::class.java)
        clientViewModel= ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)
        commandeViewModel = ViewModelProvider(
            this,
            CommandeViewModelFactory(
                CommandeRepository(APIService.getService()),
                requireActivity().application
            )
        ).get(CommandeViewModel::class.java)

        setup0bservers()
        return binding.root

    }



    private fun onLoginSuccess(client: Client) {
        sharedViewModel.isLoggedIn = true


    }


    private fun setup0bservers() {

        ViewModel.getIsLoading().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }


        ViewModel.getErrorMessage().observe(viewLifecycleOwner) {

            val formErrorkeys = arrayOf("fullName", "email", "Mobile", "password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorkeys.contains(entry.key)) {
                    when (entry.key) {

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


        }
    }

    private fun validateEmail(shoudUpdateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = binding.edtSignInEmail.text.toString()
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





    private fun validatePassword(): Boolean {

        var errorMessage: String? = null
        val value = binding.edtSignInPassword.text.toString()
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

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btnSignIn -> {
                    submitForm()
                }
            }
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (binding.root != null && view != null) {
            when (view.id) {

                binding.edtSignInEmail.id -> {
                    if (hasFocus) {
                        if (binding.Mail.isErrorEnabled) {
                            binding.Mail.isErrorEnabled = false
                        }
                    } else {
                        (validateEmail())

                    }
                }


                binding.edtSignInPassword.id -> {
                    if (hasFocus) {
                        if (binding.password.isErrorEnabled) {
                            binding.password.isErrorEnabled = false
                        }
                    } else {
                        (validatePassword())
                    }


                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModel.navigateToNextFragment.observe(viewLifecycleOwner, Observer { isLoggedIn ->
            if (isLoggedIn) {
                val credit = arguments?.getDouble("credit") ?: 0.0
                val commandId = arguments?.getString("id") ?: ""
                val commandNum = arguments?.getString("num") ?: ""
                val commandType = arguments?.getString("type") ?: ""
                val serviceList = sharedViewModel.myCommandeServiceList.value ?: emptyList()
                val client = ViewModel.getUser().value
                val command = Command(
                    id = UUID.randomUUID().toString(),
                    type = commandType,
                    num = commandNum,
                    totalPrice = credit.toFloat(),
                    Date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                    clientId =client?.id ?: "",
                    services = serviceList,
                )
                if( command.totalPrice >0) {
                    commandeViewModel.createCommand(command)
                    try {
                        client?.let { it ->
                            it.credit = (it.credit ?: 0f) + credit.toFloat()
                            it.history.add(command)
                            clientViewModel.updateClient(it.id, it)


                        }
                        sharedViewModel.clearServices()


                    } catch (e: Exception) {
                        Log.e("Exception", "Error updating client credit", e)
                    }
                }
                client?.let { it ->
                    navigateToProfileFragment(it)
                }
            }
        })
    }

    private fun navigateToProfileFragment(client: Client) {
        val secondFragment = UserAccountFragment()
        val bundle = Bundle()
        bundle.putParcelable("client", client)
        secondFragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val flFragment = requireActivity().findViewById<FrameLayout>(R.id.flFragment)
        if (flFragment != null) {
            transaction.replace(R.id.flFragment, secondFragment)
        } else {
            transaction.replace(R.id.maincont, secondFragment)
        }

        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!validateEmail()) isValid = false
        if (!validatePassword()) isValid = false
        return isValid
    }

    private fun submitForm(){
        if(validate())
       // verify user credentials
            ViewModel.loginUser (LoginBody(binding.edtSignInEmail.text!!.toString(), binding.edtSignInPassword.text!!.toString()))
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        if(event == KeyEvent.KEYCODE_ENTER && keyEvent!!.action == KeyEvent.ACTION_UP) {
            submitForm()
        }
        return false
    }

    companion object {
        fun newInstance(id: String? , num: String?, type: String?,credit:Double): LoginFragment {
            val args = Bundle()
            args.putString("id", id)
            args.putString("num", num)
            args.putString("type", type)
            args.putDouble("credit", credit)
            val fragment = LoginFragment()
            fragment.arguments = args
            return fragment
        }
    }





}
