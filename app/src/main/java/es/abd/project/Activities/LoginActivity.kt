package es.abd.project.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import es.abd.project.Fragments.LoginFragment
import es.abd.project.Fragments.RegisterFragment
import es.abd.project.R
import es.abd.project.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentListener {

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

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }


}