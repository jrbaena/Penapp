package com.jrbaena.penapp.app.home.events

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.data.entities.EventEntity
import com.jrbaena.penapp.data.persistance.dao.EventDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class NewEventFragment : Fragment() {

  companion object {
    fun newInstance() = NewEventFragment()
  }


  private lateinit var eventDao : EventDao
  private lateinit var userDao: UserDao
  private lateinit var database : ClubDataBase

  private lateinit var txtNewEventClub: TextView
  private lateinit var txtNewEventUser: TextView
  private lateinit var etxtNewEventDescription: EditText
  private lateinit var etxtNewEventLocation: EditText
  private lateinit var clnvNewEvent: CalendarView
  private lateinit var btnPost: Button
  private lateinit var btnExitNewEvent: Button


  private var description: String? = null
  private var location: String? = null
  private lateinit var date: Date

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    database = Room.databaseBuilder(requireActivity().applicationContext, ClubDataBase::class.java, "club.db").build()
    eventDao = database.EventDao()
    userDao = database.UserDao()

    return inflater.inflate(R.layout.fragment_new_event, container, false)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val prefs = requireContext().getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )

    txtNewEventClub = view.findViewById(R.id.txtNewEventClub)
    txtNewEventUser = view.findViewById(R.id.txtNewEventUser)
    etxtNewEventDescription = view.findViewById(R.id.etxtNewEventDescription)
    etxtNewEventLocation = view.findViewById(R.id.etxtNewEventLocation)
    clnvNewEvent = view.findViewById(R.id.clnvNewEvent)
    btnPost = view.findViewById(R.id.btnPost)
    btnExitNewEvent = view.findViewById(R.id.btnExitNewEvent)

    txtNewEventClub.text = prefs.getString("club-name","")
    txtNewEventUser.text = prefs.getString("user-name","")

    etxtNewEventDescription.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        description = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    etxtNewEventLocation.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        location = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    clnvNewEvent.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
      @RequiresApi(Build.VERSION_CODES.O)
      override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
        val localDate = LocalDate.of(p1,p2,p3)
        val zdt = ZonedDateTime.of(localDate.atTime(0, 0, 0), ZoneId.systemDefault())
        date = Date.from(zdt.toInstant())
      }
    })

    btnPost.setOnClickListener {
      registerEvent()
    }

    btnExitNewEvent.setOnClickListener {
      showEventsForm()
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun registerEvent() {
    lifecycleScope.launch {
      if(userIsValid()){
        val prefs = requireContext().getSharedPreferences(
          "preferences-key-name",
          AppCompatActivity.MODE_PRIVATE
        )
        val event = EventEntity(
          userDao.findClubIdByName(prefs.getString("user-name","")!!).toInt(),
          date,
          description!!,
          location!!,
          true
        )
        eventDao.insert(event)
        Toast.makeText(context, "Evento registrado correctamente", Toast.LENGTH_SHORT)
          .show()
      }
    }
  }

  private fun userIsValid(): Boolean {
    if (description.isNullOrEmpty()) {
      Toast.makeText(context, "El campo descripción no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    if (location.isNullOrEmpty()) {
      Toast.makeText(context, "El campo lugar no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }

    return true
  }

  fun showEventsForm() {
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = EventsFormFragment.newInstance()
    fragmentTransaction.replace(R.id.events_form_fragment_container, fragment)
    fragmentTransaction.commit()
  }
}

