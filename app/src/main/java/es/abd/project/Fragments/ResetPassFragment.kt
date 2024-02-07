package es.abd.project.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import es.abd.project.R
import es.abd.project.Resources.AuthManager
import es.abd.project.databinding.LoginFragmentBinding
import es.abd.project.databinding.ResetPassFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPassFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: ResetPassFragmentBinding
    private lateinit var mListener: ResetPassFragmentListener

    private val authManager: AuthManager by lazy { AuthManager() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is ResetPassFragmentListener){
            mListener = context
        }else{
            throw Exception("ResetPassFragmentListener exception")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ResetPassFragmentBinding.inflate(inflater, container, false)

        binding.btnSendMail.setOnClickListener(this)

        return binding.root
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSendMail -> {
                val email = binding.etMail.text.toString()

                if (!email.isNullOrBlank()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val mailSent = authManager.resetPassword(email)
                        withContext(Dispatchers.Main) {
                            if (mailSent) {
                                Toast.makeText(requireContext(), "Mail sent", Toast.LENGTH_SHORT).show()
                                mListener.onRecoveryPassMailSent()
                            } else {//ERROR
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "No email", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    interface ResetPassFragmentListener{
        fun onRecoveryPassMailSent()
    }

}