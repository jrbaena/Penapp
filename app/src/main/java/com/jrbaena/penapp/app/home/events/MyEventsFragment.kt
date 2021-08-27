package com.jrbaena.penapp.app.home.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.app.home.orders.OrderFormFragment
import com.jrbaena.penapp.data.persistance.dao.EventDao
import com.jrbaena.penapp.data.persistance.dao.OrderDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch

class MyEventsFragment : Fragment() {

  companion object {
    fun newInstance() = MyEventsFragment()
  }

  private lateinit var userDao : UserDao
  private lateinit var eventDao : EventDao
  private lateinit var database : ClubDataBase

  private lateinit var txtMyEventsTitle: TextView
  private lateinit var txtUserMyEvents: TextView
  private lateinit var txtMyEvents: TextView
  private lateinit var btnBackMyEvents: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    database = Room.databaseBuilder(requireActivity().applicationContext, ClubDataBase::class.java, "club.db").build()
    userDao = database.UserDao()
    eventDao = database.EventDao()

    return inflater.inflate(R.layout.fragment_my_events, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val prefs = requireContext().getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )

    txtMyEventsTitle = view.findViewById(R.id.txtMyEventsTitle)
    txtUserMyEvents = view.findViewById(R.id.txtUserMyEvents)
    txtMyEvents = view.findViewById(R.id.txtMyEvents)
    btnBackMyEvents = view.findViewById(R.id.btnBackMyEvents)

    txtMyEventsTitle.text = "Mis eventos"
    txtUserMyEvents.text = "Usuario ${prefs.getString("user-name", "")}"

    showEvents()

    btnBackMyEvents.setOnClickListener {
      showOrderFrom()
    }
  }

  private fun showEvents() {
    lifecycleScope.launch {
      val events = eventDao.getAll()
      events.forEach {
        var txtBefore = txtMyEvents.text.toString()
        txtMyEvents.text = (txtBefore + System.getProperty ("line.separator") + " " + it.description + " " + it.location + " " +  it.date.toString())
      }
    }
  }

  private fun showOrderFrom() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = EventsFormFragment.newInstance()
    fragmentTransaction.replace(R.id.events_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }

}