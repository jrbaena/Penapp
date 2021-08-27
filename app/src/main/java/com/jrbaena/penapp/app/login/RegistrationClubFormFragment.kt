package com.jrbaena.penapp.app.login

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.data.entities.ClubEntity
import com.jrbaena.penapp.data.entities.UserEntity
import com.jrbaena.penapp.data.persistance.dao.ClubDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch

class RegistrationClubFormFragment : Fragment() {

  companion object {
    fun newInstance() = RegistrationClubFormFragment()
  }

  lateinit var userDao: UserDao
  lateinit var clubDao: ClubDao
  lateinit var database: ClubDataBase

  private lateinit var btnRegistration: Button
  private lateinit var eTxtName: EditText
  private lateinit var eTxtEmail: EditText
  private lateinit var eTxtPassword: EditText
  private lateinit var eTxtRepeatPassword: EditText
  private lateinit var eTxtClubName: EditText

  private var clubName: String? = null
  private var name: String? = null
  private var email: String? = null
  private var password: String? = null
  private var repeatPassword: String? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    database = Room.databaseBuilder(
      requireActivity().applicationContext,
      ClubDataBase::class.java,
      "club.db"
    ).build()
    userDao = database.UserDao()
    clubDao = database.ClubDao()
    return inflater.inflate(R.layout.registration_club_form_fragment, container, false)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btnRegistration = view.findViewById(R.id.btnRegisterClub)
    eTxtName = view.findViewById(R.id.etxtName)
    eTxtEmail = view.findViewById(R.id.etxtEmail)
    eTxtPassword = view.findViewById(R.id.etxtPsw)
    eTxtRepeatPassword = view.findViewById(R.id.etxtRPsw)
    eTxtClubName = view.findViewById(R.id.etxtClubName)

    eTxtClubName.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        clubName = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    eTxtName.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        name = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    eTxtEmail.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        email = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    eTxtPassword.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        password = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    eTxtRepeatPassword.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        repeatPassword = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    btnRegistration.setOnClickListener {
      registerUser()
    }
  }


  @RequiresApi(Build.VERSION_CODES.O)
  private fun registerUser() {
    lifecycleScope.launch {
      if (userIsValid()) {
        val chars = ('a'..'z').toList().toTypedArray()
        val code = (1..2).map { chars.random() }.joinToString("")
        Toast.makeText(context, code, Toast.LENGTH_LONG)
          .show()


        val club = ClubEntity(
          clubName!!,
          code
        )
        club.id = clubDao.insert(club).toInt()
        val user = UserEntity(
          club.id,
          name!!,
          email!!,
          password!!,
          true
        )
        user.id = userDao.insert(user).toInt()

        val prefs = requireContext().getSharedPreferences(
          "preferences-key-name",
          AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("user-id", user.id)
        editor.putString("user-name", user.name)
        editor.putString("club-name", club.name)
        editor.apply()
        requireActivity().finish()
      }
    }
  }

  private suspend fun userExists(mail: String): Boolean {
    val userMail = userDao.findUserByMail(mail)
    if (userMail != null) return true
    return false
  }

  private fun cleanAll() {
    eTxtName.text.clear()
    eTxtEmail.text.clear()
    eTxtPassword.text.clear()
    eTxtRepeatPassword.text.clear()
    eTxtClubName.text.clear()
  }

  private suspend fun userIsValid(): Boolean {

    if (clubName.isNullOrEmpty()) {
      Toast.makeText(context, "El campo nombre de la peña no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    if (name.isNullOrEmpty()) {
      Toast.makeText(context, "El campo nombre no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    if (email.isNullOrEmpty()) {
      Toast.makeText(context, "El campo email no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    } else if (userExists(email!!)) {
      Toast.makeText(context, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
      return false
    }
    if (password.isNullOrEmpty()) {
      Toast.makeText(context, "El campo contraseña no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    if (repeatPassword.isNullOrEmpty()) {
      Toast.makeText(
        context,
        "El campo repetir contraseña no puede estar vacío",
        Toast.LENGTH_SHORT
      )
        .show()
      return false
    }
    if (repeatPassword != password) {
      Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    return true
  }

}