package com.jrbaena.penapp.app.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.jrbaena.penapp.R
import com.jrbaena.penapp.app.home.orders.OrderFormFragment
import com.jrbaena.penapp.app.login.LoginActivity
import com.jrbaena.penapp.app.login.LoginFormFragment

class OrdersActivity : AppCompatActivity() {




  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_orders)

    showOrderFormFragment()

  }

  fun showOrderFormFragment(){
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    val fragment = OrderFormFragment.newInstance()
    fragmentTransaction.add(R.id.order_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }


  companion object {
    fun startActivity(
      context: Context,
    ) {
      context.startActivity(Intent(context, OrdersActivity::class.java))
    }
  }

}