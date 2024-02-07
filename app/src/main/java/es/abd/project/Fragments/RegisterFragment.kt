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
import es.abd.project.databinding.RegisterFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: RegisterFragmentBinding
    private lateinit var mListener: RegisterFragmentListener

    private val authManager: AuthManager by lazy { AuthManager() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is RegisterFragmentListener){
            mListener = context
        }else{
            throw Exception("RegisterFragmentListener exception")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)

        binding.RegBtnRegFragment.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.RegBtnRegFragment ->{
                val email = binding.etUsername.text.toString()
                val pass = binding.etPassword.text.toString()
                val pass2 = binding.etPassword2.text.toString()

            if(pass != pass2){
                Toast.makeText(requireContext(), "No name or pass", Toast.LENGTH_SHORT).show()
            }else {
                if (!email.isNullOrBlank() && !pass.isNullOrBlank()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val userRegistered = authManager.createUser(email, pass)
                        withContext(Dispatchers.Main) {
                            if (userRegistered != null) {
                                mListener.onRegistered()
                            } else {//ERROR
                                Toast.makeText(requireContext(), "bad credentials", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else { Toast.makeText(requireContext(), "No user or pass", Toast.LENGTH_SHORT).show()
                }
            }
        }
        }
    }


    interface RegisterFragmentListener{
        fun onRegistered()
    }

}