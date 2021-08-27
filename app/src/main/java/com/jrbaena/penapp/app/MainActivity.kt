package com.jrbaena.penapp.app

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jrbaena.penapp.R
import com.jrbaena.penapp.app.home.EventsActivity
import com.jrbaena.penapp.app.home.OrdersActivity
import com.jrbaena.penapp.app.login.LoginActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var txtClubHome: TextView
    private lateinit var txtUserWelcome: TextView
    private lateinit var txtDate: TextView
    private lateinit var txtMoney: TextView
    private lateinit var txtEvents: TextView
    private lateinit var btnOrders: Button
    private lateinit var btnEvents: Button
    private lateinit var btnFinances: Button
    private lateinit var btnLogout: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


//        val prefs = applicationContext.getSharedPreferences("preferences-key-name", MODE_PRIVATE)
//        val userId = prefs.getInt("user-id", -1)
//
//        val isLogged = userId != -1
//
//        if(!isLogged){
//            LoginActivity.startActivity(this, intent)
//        }else{
//            setContentView(R.layout.activity_main)
//            setTitle("nuevo")
//        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val prefs = applicationContext.getSharedPreferences("preferences-key-name", MODE_PRIVATE)
        val userId = prefs.getInt("user-id", 0)

        val isLogged = userId != 0

        if(!isLogged){
            LoginActivity.startActivity(this)
        }else{
            setContentView(R.layout.activity_main)

            txtClubHome = findViewById(R.id.txtClubHome)
            txtUserWelcome = findViewById(R.id.txtUserWelcome)
            txtDate = findViewById(R.id.txtDate)
            txtMoney = findViewById(R.id.txtMoney)
            txtEvents = findViewById(R.id.txtEvents)
            btnOrders = findViewById(R.id.btnOrders)
            btnEvents = findViewById(R.id.btnEvents)
            btnFinances = findViewById(R.id.btnFinances)
            btnLogout = findViewById(R.id.btnLogout)

            txtClubHome.text = prefs.getString("club-name","")
            txtUserWelcome.text = "Bienvenido ${prefs.getString("user-name","")}"
            val dateFormat = SimpleDateFormat("dd/M/yyyy")
            val currentDate = dateFormat.format(Date())
            txtDate.text = currentDate

            btnOrders.setOnClickListener {
                OrdersActivity.startActivity(it.context)
            }

            btnEvents.setOnClickListener {
                EventsActivity.startActivity(it.context)
            }

            btnFinances.setOnClickListener {
                Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT)
                    .show()
            }

            btnLogout.setOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val prefs = applicationContext.getSharedPreferences("preferences-key-name", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove("user-id")
        editor.apply()
        Toast.makeText(applicationContext, "saliendo de aqui", Toast.LENGTH_SHORT)
            .show()
    }

    companion object{

    }
}