package com.jrbaena.penapp.app.home.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.data.persistance.dao.OrderDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch

class MyOrdersFragment : Fragment() {

  companion object {
    fun newInstance() = MyOrdersFragment()
  }

  private lateinit var userDao : UserDao
  private lateinit var orderDao : OrderDao
  private lateinit var database : ClubDataBase

  private lateinit var txtOrdersFromUser: TextView
  private lateinit var txtRecordOrders: TextView
  private lateinit var btnBack: Button

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
    return inflater.inflate(R.layout.fragment_my_orders, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val prefs = requireContext().getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )

    txtOrdersFromUser = view.findViewById(R.id.txtOrdersFromUser)
    txtRecordOrders = view.findViewById(R.id.txtRecordOrders)
    btnBack = view.findViewById(R.id.btnBack)

    txtOrdersFromUser.text = "Pedidos del usuario ${prefs.getString("user-name", "")}"

    showOrders()




    btnBack.setOnClickListener {
      showOrderFrom()
    }


  }

  private fun showOrders() {
    lifecycleScope.launch {
    val orders = orderDao.getAll()

      orders.forEach {
        var txtBefore = txtRecordOrders.text.toString()
        txtRecordOrders.text = (txtBefore + System.getProperty ("line.separator") + " " + it.name + " " + it.description + " " +  it.amount.toString())
      }
  }}

  private fun showOrderFrom() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = OrderFormFragment.newInstance()
    fragmentTransaction.replace(R.id.order_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }


}