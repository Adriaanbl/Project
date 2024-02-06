package es.abd.project.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.abd.project.Resources.AuthManager
import es.abd.project.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: RegisterFragmentBinding
    private lateinit var mListener: RegisterFragmentListener

    private val authManager: AuthManager by lazy { AuthManager() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is RegisterFragmentListener){
            mListener = context
        }else{
            throw Exception("LoginFragmentListener exception")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){

        }
    }


    interface RegisterFragmentListener{

    }

}