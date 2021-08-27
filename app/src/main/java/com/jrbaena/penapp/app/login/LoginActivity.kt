package com.jrbaena.penapp.app.login

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jrbaena.penapp.BuildConfig
import com.jrbaena.penapp.R

class LoginActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    showLoginFormFragment()



//    buttonRegistration = findViewById(R.id.btnRegistration)
//    buttonLogin = findViewById(R.id.btnLogin)
//
//    buttonRegistration.setOnClickListener{
//      val fragmentTransaction = supportFragmentManager.beginTransaction()
//      val fragment = RegistrationFormFragment.newInstance()
//      fragmentTransaction.replace(R.id.registration_fragment_container, fragment)
//      fragmentTransaction.commit()
//    }
//
//    buttonLogin.setOnClickListener {
//
//    }
  }

  fun showLoginFormFragment(){
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    val fragment = LoginFormFragment.newInstance()
    fragmentTransaction.replace(R.id.registration_fragment_container, fragment)
    fragmentTransaction.commit()
  }

  companion object {

    fun startActivity(
      context: Context,
    ) {
      context.startActivity(Intent(context, LoginActivity::class.java))
//
//      val launchIntent =
//        if (intent == null) Intent(context, LoginActivity::class.java)
//        else Intent(intent).apply {
//          component =
//            ComponentName(BuildConfig.APPLICATION_ID, LoginActivity::class.toString())
//          addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        }
//      context.startActivity(launchIntent)
    }
  }
}
