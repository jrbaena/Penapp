package com.jrbaena.penapp.app.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jrbaena.penapp.R
import com.jrbaena.penapp.app.home.events.EventsFormFragment
import com.jrbaena.penapp.app.home.orders.OrderFormFragment

class EventsActivity : AppCompatActivity() {



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_events)

    showEventsFormFragment()

  }

  fun showEventsFormFragment(){
    val prefs = applicationContext.getSharedPreferences("preferences-key-name", MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putInt("club-id", 0)
    editor.apply()
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    val fragment = EventsFormFragment.newInstance()
    fragmentTransaction.add(R.id.events_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }

  companion object {
    fun startActivity(
      context: Context,
    ) {
      context.startActivity(Intent(context, EventsActivity::class.java))
    }
  }
}