package com.jrbaena.penapp.app.home.orders

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.data.entities.OrderEntity
import com.jrbaena.penapp.data.persistance.dao.OrderDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch


class NewOrderFromFragment : Fragment() {

  companion object {
    fun newInstance() = NewOrderFromFragment()
  }

  private lateinit var userDao : UserDao
  private lateinit var orderDao : OrderDao
  private lateinit var database : ClubDataBase

  private lateinit var txtClubNameOrderForm: TextView
  private lateinit var txtUserNameOrderForm: TextView
  private lateinit var spnCategory: Spinner
  private lateinit var etxtDescription: EditText
  private lateinit var etxtAmount: EditText
  private lateinit var btnOrderForm: Button
  private var category: String? = null
  private var description: String? = null
  private var amount: String? = null

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
    return inflater.inflate(R.layout.fragment_order_form, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val prefs = requireContext().getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )

    txtClubNameOrderForm = view.findViewById(R.id.txtClubNameOrderForm)
    txtUserNameOrderForm = view.findViewById(R.id.txtUserNameOrderForm)
    spnCategory = view.findViewById(R.id.spnCategory)
    etxtDescription = view.findViewById(R.id.etxtDescription)
    etxtAmount = view.findViewById(R.id.etxtAmount)
    btnOrderForm = view.findViewById(R.id.btnOrderForm)

    txtClubNameOrderForm.text = prefs.getString("club-name", "")
    txtUserNameOrderForm.text = prefs.getString("user-name", "")

    ArrayAdapter.createFromResource(
      requireContext(),
      R.array.categories,
      android.R.layout.simple_spinner_item
    ).also { adapter ->
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      spnCategory.adapter = adapter
    }

    spnCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

      override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        category = spnCategory.selectedItem.toString()
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
        category = spnCategory[0].toString()
      }
    }

      etxtDescription.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        description = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    etxtAmount.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        amount = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    btnOrderForm.setOnClickListener {
      registerOrder()
    }

  }

  private fun registerOrder() {
    lifecycleScope.launch {
    if(userIsValid()){
      val prefs = requireContext().getSharedPreferences(
        "preferences-key-name",
        AppCompatActivity.MODE_PRIVATE
      )
      val order = OrderEntity(
        prefs.getInt("user-id",0),
        category!!,
        description!!,
        amount!!.toInt()
      )
      order.id = orderDao.insert(order).toInt()
      val editor = prefs.edit()
      editor.putInt("order-id", order.id)
      editor.apply()
      requireActivity().finish()
    }
    }
  }

  private fun userIsValid(): Boolean {

    if (description.isNullOrEmpty()) {
      Toast.makeText(context, "El campo descripción de la peña no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    if (amount.isNullOrEmpty()) {
      Toast.makeText(context, "El campo cantidad no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    } else if (!isANumber(amount!!)) {
      Toast.makeText(context, "Tienes que poner un número", Toast.LENGTH_SHORT).show()
      return false
    }
    return true
  }

  private fun isANumber(amount: String): Boolean {
    try {
      val a = amount.toInt()
    }catch (e: Exception){
      return false
    }
    return true
  }


}