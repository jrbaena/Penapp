package com.jrbaena.penapp.app.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.jrbaena.penapp.R
import com.jrbaena.penapp.data.persistance.dao.ClubDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch

class LoginFormFragment : Fragment() {

  companion object {
    fun newInstance() = LoginFormFragment()
  }

  lateinit var userDao : UserDao
  lateinit var clubDao : ClubDao
  lateinit var database : ClubDataBase

  private lateinit var btnRegister: Button
  private lateinit var btnLogin: Button
  private lateinit var eTxtEmail: EditText
  private lateinit var eTxtPassword: EditText
  private var email: String? = null
  private var password: String? = null
  //private lateinit var club : Int

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    database = Room.databaseBuilder(requireActivity().applicationContext, ClubDataBase::class.java, "club.db").build()
    userDao = database.UserDao()
    clubDao = database.ClubDao()

    return inflater.inflate(R.layout.login_form_fragment, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btnLogin = view.findViewById(R.id.btnLoginForm)
    btnRegister = view.findViewById(R.id.btnRegisterFromLogin)
    eTxtEmail = view.findViewById(R.id.etxtLoginEmail)
    eTxtPassword = view.findViewById(R.id.etxtLoginPassword)

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
    btnRegister.setOnClickListener {
      showUserTypeFragment()
    }

    btnLogin.setOnClickListener {
      checkLogin()
    }
  }

  private fun checkLogin() {
    if (userIsValid())lifecycleScope.launch {
      //TODO Buscar el usuario por su correo y si lo encuentro, comprobar la contraseña
      val user = userDao.findUserByMail(email!!)
      if (user == null){
        //club = user.club_id
        //showHomeActivity()
        Toast.makeText(context, "Usuario no registrado", Toast.LENGTH_SHORT)
          .show()
      }else if(user.password != password){
        Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT)
          .show()
      }else{
        val prefs = requireContext().getSharedPreferences("preferences-key-name",
          AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("user-id", user.id)
        editor.putString("user-name", user.name)
        val club = clubDao.findById(user.club_id)
        editor.putString("club-name", club.name)
        editor.apply()
        requireActivity().finish()
      }
    }
  }

  private fun userIsValid(): Boolean {
    if (email.isNullOrEmpty()) {
      Toast.makeText(context, "El campo email no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    if (password.isNullOrEmpty()) {
      Toast.makeText(context, "El campo contraseña no puede estar vacío", Toast.LENGTH_SHORT)
        .show()
      return false
    }
    return true
  }


  fun showUserTypeFragment(){
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = UserTypeFragment.newInstance()
    fragmentTransaction.replace(R.id.registration_fragment_container, fragment)
    fragmentTransaction.commit()
  }
}