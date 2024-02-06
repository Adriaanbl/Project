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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var mListener: LoginFragmentListener

    private val authManager: AuthManager by lazy { AuthManager() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is LoginFragmentListener){
            mListener = context
        }else{
            throw Exception("LoginFragmentListener exception")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.logBtnLogFragment.setOnClickListener(this)
        binding.RegBtnLogFragment.setOnClickListener(this)

        return binding.root

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.logBtnLogFragment -> {

                val email = binding.mailInputLogin.text.toString()
                val pass = binding.passInputLogin.text.toString()
                if(!email.isNullOrBlank() && ! pass.isNullOrBlank()){
                    lifecycleScope.launch(Dispatchers.IO) {
                        val userLogged = authManager.login(email,pass)
                        withContext(Dispatchers.Main){
                            if(userLogged != null){
                                mListener.onLogged()
                            }else{//ERROR
                                Toast.makeText(requireContext(), "Bad credentials", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    Toast.makeText(requireContext(), "No user or pass", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.RegBtnLogFragment -> {
                mListener.onRegisterBtnClick()
            }

        }

    }

    interface LoginFragmentListener{
        fun onLogged()
        fun onRegisterBtnClick()

    }
}