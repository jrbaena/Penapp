package com.jrbaena.penapp.app.home.orders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.app.login.UserTypeFragment
import com.jrbaena.penapp.data.persistance.dao.OrderDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase

class OrderFormFragment : Fragment() {

  companion object {
    fun newInstance() = OrderFormFragment()
  }

  private lateinit var txtClubOrders: TextView
  private lateinit var txtUserOrders: TextView
  private lateinit var btnOrder: Button
  private lateinit var btnMyOrders: Button
  private lateinit var btnExit: Button

  private lateinit var userDao : UserDao
  private lateinit var orderDao : OrderDao
  private lateinit var database : ClubDataBase



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    database = Room.databaseBuilder(requireActivity().applicationContext, ClubDataBase::class.java, "club.db").build()
    userDao = database.UserDao()
    orderDao = database.OrderDao()

    return inflater.inflate(R.layout.order_menu, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    txtClubOrders = view.findViewById(R.id.txtClubOrders)
    txtUserOrders = view.findViewById(R.id.txtUserOrders)
    btnOrder = view.findViewById(R.id.btnOrder)
    btnMyOrders = view.findViewById(R.id.btnMyOrders)
    btnExit = view.findViewById(R.id.btnExit)

    val prefs = requireContext().getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )

    txtClubOrders.text = prefs.getString("club-name","")
    txtUserOrders.text = prefs.getString("user-name","")

    btnOrder.setOnClickListener {
      showNewOrderFromFragment()
    }

    btnMyOrders.setOnClickListener {
      showMyOrdersFragment()
    }

    btnExit.setOnClickListener {
      requireActivity().finish()
    }


  }

  private fun showMyOrdersFragment() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = MyOrdersFragment.newInstance()
    fragmentTransaction.replace(R.id.order_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }

  private fun showNewOrderFromFragment() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = NewOrderFromFragment.newInstance()
    fragmentTransaction.replace(R.id.order_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }
}