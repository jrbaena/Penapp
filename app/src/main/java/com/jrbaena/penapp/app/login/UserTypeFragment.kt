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
import com.jrbaena.penapp.app.ClubApp
import com.jrbaena.penapp.data.persistance.dao.ClubDao
import com.jrbaena.penapp.data.persistance.dao.UserDao
import com.jrbaena.penapp.data.persistance.db.ClubDataBase
import kotlinx.coroutines.launch

class UserTypeFragment : Fragment() {

  companion object {
    fun newInstance() = UserTypeFragment()
  }

  lateinit var clubDao : ClubDao
  lateinit var database : ClubDataBase

  private lateinit var btnCreateClub : Button
  private lateinit var btnCreateUserWithCode : Button
  private lateinit var etxtClubCode : EditText
  private var code : String? = null

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    database = Room.databaseBuilder(requireActivity().applicationContext, ClubDataBase::class.java, "club.db").build()
    clubDao = database.ClubDao()
    return inflater.inflate(R.layout.fragment_user_type, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    btnCreateClub = requireActivity().findViewById(R.id.btnCreateClub)
    btnCreateUserWithCode = requireActivity().findViewById(R.id.btnRegisterNewUserWithCode)
    etxtClubCode = requireActivity().findViewById(R.id.etxtClubCode)

    etxtClubCode.addTextChangedListener(object : TextWatcher{
      override fun afterTextChanged(s: Editable?) {
        code = s.toString()
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

      }

    })

    btnCreateUserWithCode.setOnClickListener {
      checkUserCode(code)
    }

    btnCreateClub.setOnClickListener {
      showRegistrationClubFormFragment()
    }
  }

  private fun checkUserCode(code: String?) {
    if (code.isNullOrEmpty()){
      Toast.makeText(context,"El campo código no puede estar vacío", Toast.LENGTH_SHORT).show()
    }else{
      lifecycleScope.launch {
        val club = clubDao.findOneByCode(code)
        if (club != null){
          val prefs = requireContext().getSharedPreferences("preferences-key-name",
            AppCompatActivity.MODE_PRIVATE
          )
          val editor = prefs.edit()
          editor.putInt("club-id", club.id)
          editor.putString("club-name", club.name)
          editor.apply()
          showRegistrationFromUserCodeForm()
        } //pasar la id o el club al fragment para mostrar su nombre
        else Toast.makeText(context,"Código inválido", Toast.LENGTH_SHORT).show()
      }
    }
  }


  private fun showRegistrationClubFormFragment(){
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = RegistrationClubFormFragment.newInstance()
    fragmentTransaction.replace(R.id.registration_fragment_container, fragment)
    fragmentTransaction.commit()
  }

  private fun showRegistrationFromUserCodeForm(){
    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    val fragment = RegistrationFromUserCodeFormFragment.newInstance()
    fragmentTransaction.replace(R.id.registration_fragment_container, fragment)
    fragmentTransaction.commit()
  }
}