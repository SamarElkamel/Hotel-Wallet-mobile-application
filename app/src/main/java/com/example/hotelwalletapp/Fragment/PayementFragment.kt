package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Repository.CommandeRepository
import com.example.hotelwalletapp.Token.AuthClient
import com.example.hotelwalletapp.databinding.PayementBinding
import com.example.hotelwalletapp.model.Client
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.viewmodel.*
import com.itextpdf.text.*
import java.text.SimpleDateFormat
import java.util.*

class PayementFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var commandeViewModel: CommandeViewModel
    private lateinit var clientViewModel: ClientViewModel
    private lateinit var pdfFilePath: String
    private lateinit var binding: PayementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PayementBinding.inflate(layoutInflater)
        val payHereRadioButton=binding.radioButton
        val payAtCheckoutRadioButton=binding.radioButton2
        commandeViewModel = ViewModelProvider(
            this,
            CommandeViewModelFactory(
                CommandeRepository(APIService.getService()),
                requireActivity().application
            )
        ).get(CommandeViewModel::class.java)
        clientViewModel= ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)



        binding.button1.setOnClickListener {
            if (payHereRadioButton.isChecked)
            {
                generatePdf()
                val id = UUID.randomUUID().toString()
                val num = arguments?.getString("num") ?: ""
                val type = arguments?.getString("type") ?: ""
                val totalPrice = sharedViewModel.getTotalPrice()
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val serviceList = sharedViewModel.myCommandeServiceList.value ?: emptyList()
                val commandeList = serviceList.map { it.command }.distinct().mapNotNull { it }
                val facture = Command(
                    id = id,
                    type = type,
                    num = num,
                    totalPrice = totalPrice.toFloat(),
                    Date = date,
                    clientId =null ,
                   // client=null ,
                    services = serviceList,
                )
                commandeViewModel.createCommand(facture)
                sharedViewModel.clearServices()
                val secondFragment = PaymentHereFragment.newInstance(pdfFilePath)
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.flFragment, secondFragment)
                transaction.addToBackStack(null)
                transaction.commit()

            }

            else if (payAtCheckoutRadioButton.isChecked)
            {
                var Credit= sharedViewModel.getTotalPrice()
                val client = AuthClient.getInstance().client
                val num = arguments?.getString("num") ?: ""
                val type = arguments?.getString("type") ?: ""
                val id =arguments?.getString("id") ?: ""
                openLoginFragment(id,num,type,Credit)

                if (client != null)
                {

                    val commandId = arguments?.getString("id") ?: ""
                    val commandNum = arguments?.getString("num") ?: ""
                    val commandType = arguments?.getString("type") ?: ""
                    val serviceList = sharedViewModel.myCommandeServiceList.value ?: emptyList()
                    val command = Command(
                        id = UUID.randomUUID().toString(),
                        type = commandType,
                        num = commandNum,
                        totalPrice = Credit.toFloat(),
                        Date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                        clientId =client?.id ?: "",
                        services = serviceList,
                    )
                    if( command.totalPrice >0) {
                        commandeViewModel.createCommand(command)
                        try {
                            client?.let { it ->
                                it.credit = (it.credit ?: 0f) + Credit.toFloat()
                                it.history.add(command)
                                clientViewModel.updateClient(it.id, it)


                            }
                            sharedViewModel.clearServices()


                        } catch (e: Exception) {
                            Log.e("Exception", "Error updating client credit", e)
                        }
                    }
                    navigateToProfileFragment(id,num,type,Credit,client )
                }
                else
                    openLoginFragment(id,num,type,Credit)

            }
            else
            {
                Toast.makeText(requireContext(),"Please select a payment option", Toast.LENGTH_SHORT).show()

            }
            }

         return binding.root
        }


    companion object {
        fun newInstance(id: String?, num: String?, type: String?): PayementFragment {
            val args = Bundle()
            args.putString("id", id)
            args.putString("num", num)
            args.putString("type", type)
            val fragment = PayementFragment()
            fragment.arguments = args
            return fragment
        }
    }


    private fun generatePdf() {
        val document = Document()
        pdfFilePath = requireContext().cacheDir.absolutePath + File.separator + "facture.pdf"
        val writer = PdfWriter.getInstance(document, FileOutputStream(pdfFilePath))
        val num = arguments?.getString("num") ?: ""
        val type = arguments?.getString("type") ?: ""
        val totalPrice = sharedViewModel.getTotalPrice().toString()
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val serviceList = sharedViewModel.myCommandeServiceList.value ?: emptyList()
        PdfWriter.getInstance(document, FileOutputStream(pdfFilePath))
        document.open()
        val fontBold = Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD)
        val fontBold2 = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val paragraph = Paragraph("Client Bill", fontBold)
        paragraph.alignment = Element.ALIGN_CENTER
        document.add(paragraph)
        paragraph.add(Chunk())
        document.add(Paragraph(date))
        document.add(Paragraph(num))
        val servicesParagraph = Paragraph("Services:")
        for (commandeService in serviceList) {
                val service = commandeService.services
                val serviceName = service.Libelle
                val servicePrice = "${service.Prix} DT"

                servicesParagraph.add(Chunk("\n $serviceName : $servicePrice\n"))
            }
        document.add(servicesParagraph)

        val totalPriceParagraph = Paragraph()
        totalPriceParagraph.add(Chunk("Total: ",fontBold2))
        totalPriceParagraph.add(Chunk(totalPrice,fontBold2))
        totalPriceParagraph.add(Chunk(" DT",fontBold2))
        paragraph.add(Chunk())
        document.add(totalPriceParagraph)
        document.close()
        this.pdfFilePath = pdfFilePath
    }


    private fun openLoginFragment(id: String? , num: String?, type: String?,credit:Double) {
        sharedViewModel.hasClickedButton = true
        val secondFragment = LoginFragment.newInstance(id, num, type,credit)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, secondFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun navigateToProfileFragment(id: String? , num: String?, type: String?,credit:Double ,client: Client) {
        val secondFragment = UserAccountFragment.newInstance(id, num, type,credit)
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



        }
