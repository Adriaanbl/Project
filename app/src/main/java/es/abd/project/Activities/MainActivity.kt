package es.abd.project.Activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.Firebase
import es.abd.project.Fragments.ChatFragment
import es.abd.project.Fragments.MultimediaFragment
import es.abd.project.Fragments.RetrofitFragment
import es.abd.project.R
import es.abd.project.Resources.AuthManager
import es.abd.project.databinding.MainActivityBinding
import es.abd.project.databinding.SettingsActivityBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: MainActivityBinding
    private val auth = AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnItemSelectedListener(this)

        val prefs = getSharedPreferences("es.abd.project_preferences", Context.MODE_PRIVATE)
        val username = auth.getCurrentUser()!!.email
        with(prefs.edit()){
            putString("username", username)
            apply()
        }

        setSupportActionBar(binding.myToolbar)


    }

    override fun onNavigationItemSelected(item: MenuItem) = when(item.itemId){

        R.id.retrofitNavItem -> {
            replaceFragment(RetrofitFragment())
            true
        }
        R.id.chatNavItem -> {
            replaceFragment(ChatFragment())
            true
        }
        R.id.multimediaNavItem -> {
            replaceFragment(MultimediaFragment())
            true
        }
        else -> false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings ->{
                val intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun vibrateDevice() {
        var vibrator: Vibrator
        if (Build.VERSION.SDK_INT>=31) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator = vibratorManager.defaultVibrator
        }
        else {
            @Suppress("DEPRECATION")
            vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val mVibratePattern = longArrayOf(0, 400, 200, 400)
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createWaveform(mVibratePattern,-1))
        }
        else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(mVibratePattern,-1)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(R.id.mainFragmentContainer, fragment)
            addToBackStack(null)
            setReorderingAllowed(true)
        }
    }

}