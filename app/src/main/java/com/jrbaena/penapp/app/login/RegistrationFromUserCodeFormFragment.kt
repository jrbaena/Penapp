package com.jrbaena.penapp.app.login

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.data.entities.UserEntity
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch

class RegistrationFromUserCodeFormFragment : Fragment() {

  companion object {
    fun newInstance() = RegistrationFromUserCodeFormFragment()
  }

  lateinit var userDao: UserDao
  lateinit var database: ClubDataBase

  private lateinit var txtClubNameRegister: TextView
  private lateinit var etxtName: EditText
  private lateinit var etxtEmail: EditText
  private lateinit var etxtPsw: EditText
  private lateinit var etxtRPsw: EditText
  private lateinit var btnRegisterUser: Button

  private var name: String? = null
  private var email: String? = null
  private var password: String? = null
  private var repeatPassword: String? = null
  private var clubId: Int = 0

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
    return inflater.inflate(R.layout.fragment_registration_from_user_code_form, container, false)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    txtClubNameRegister = view.findViewById(R.id.txtClubNameRegister)
    etxtName = view.findViewById(R.id.etxtName)
    etxtEmail = view.findViewById(R.id.etxtEmail)
    etxtPsw = view.findViewById(R.id.etxtPsw)
    etxtRPsw = view.findViewById(R.id.etxtRPsw)
    btnRegisterUser = view.findViewById(R.id.btnRegisterUser)

    val prefs = requireContext().getSharedPreferences(
      "preferences-key-name",
      AppCompatActivity.MODE_PRIVATE
    )
    txtClubNameRegister.text = prefs.getString("club-name", "")
    clubId = prefs.getInt("club-id", 0)

    etxtName.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        name = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    etxtEmail.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        email = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    etxtPsw.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        password = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    etxtRPsw.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        repeatPassword = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })
    btnRegisterUser.setOnClickListener {
      registerUser()
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun registerUser() {
    lifecycleScope.launch {
      if (userIsValid()) {
        val user = UserEntity(
          clubId,
          name!!,
          email!!,
          password!!,
          false
        )
        user.id = userDao.insert(user).toInt()

        val prefs = requireContext().getSharedPreferences(
          "preferences-key-name",
          AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("user-id", user.id)
        editor.putString("user-name", user.name)
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
    etxtName.text.clear()
    etxtEmail.text.clear()
    etxtRPsw.text.clear()
    etxtRPsw.text.clear()
  }

  private suspend fun userIsValid(): Boolean {

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