package es.abd.project.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import es.abd.project.Fragments.LoginFragment
import es.abd.project.Fragments.RegisterFragment
import es.abd.project.Fragments.ResetPassFragment
import es.abd.project.R
import es.abd.project.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity(),
    LoginFragment.LoginFragmentListener,
    RegisterFragment.RegisterFragmentListener,
    ResetPassFragment.ResetPassFragmentListener {

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onLogged() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegisterBtnClick() {
        replaceFragment(RegisterFragment())
    }

    override fun onForgotPassClick() {
        replaceFragment(ResetPassFragment())
    }

    override fun onRegistered() {
        onLogged()
    }

    override fun onRecoveryPassMailSent() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }




}