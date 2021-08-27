package com.jrbaena.penapp.app.home.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jrbaena.penapp.R
import com.jrbaena.penapp.app.home.orders.MyOrdersFragment

class EventsFormFragment : Fragment() {

  companion object {
    fun newInstance() = EventsFormFragment()
  }

  private lateinit var txtClubNameEvents: TextView
  private lateinit var txtUserNameEvents: TextView
  private lateinit var btnNewEvent: Button
  private lateinit var btnMyEvents: Button
  private lateinit var btnPastEvents: Button
  private lateinit var btnExitFromEvents: Button


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.event_menu, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    txtClubNameEvents = view.findViewById(R.id.txtClubNameEvents)
    txtUserNameEvents = view.findViewById(R.id.txtUserNameEvents)
    btnNewEvent = view.findViewById(R.id.btnNewEvent)
    btnMyEvents = view.findViewById(R.id.btnMyEvents)
    btnPastEvents = view.findViewById(R.id.btnPastEvents)
    btnExitFromEvents = view.findViewById(R.id.btnExitFromEvents)

    val prefs = requireContext().applicationContext.getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )

    txtClubNameEvents.text = prefs.getString("club-name","")
    txtUserNameEvents.text = prefs.getString("user-name","")

    btnNewEvent.setOnClickListener {
      showNewEventFragment()
    }

    btnMyEvents.setOnClickListener {
      showMyEventsFragment()
    }

    btnPastEvents.setOnClickListener {
      showPastEventsFragment()
    }

    btnExitFromEvents.setOnClickListener {
      requireActivity().finish()
    }
  }

  private fun showPastEventsFragment() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = PastEventsFragment.newInstance()
    fragmentTransaction.replace(R.id.events_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }

  private fun showMyEventsFragment() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = MyEventsFragment.newInstance()
    fragmentTransaction.replace(R.id.events_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }

  private fun showNewEventFragment() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = NewEventFragment.newInstance()
    fragmentTransaction.replace(R.id.events_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }


}