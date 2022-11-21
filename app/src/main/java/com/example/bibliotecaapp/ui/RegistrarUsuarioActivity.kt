package com.example.bibliotecaapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bibliotecaapp.databinding.ActivityRegistrarUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarUsuarioBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        title = "Registrar Usuario"

        binding.btnSave.setOnClickListener {
            if(binding.edtUserName.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()){
              //  configSharedPreference()
                auth.createUserWithEmailAndPassword(binding.edtUserName.text.toString(),binding.edtPassword.text.toString())
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Creado con exito", Toast.LENGTH_SHORT).show()
                            sendEmailVerification()
                            finish()
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                binding.edtUserName.error = "Campo requerido"
                binding.edtPassword.error = "Campo requerido"
            }
        }
    }

    private fun sendEmailVerification(){
        auth.currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Correo enviado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun configSharedPreference(){
        val preferences = getSharedPreferences("Users", MODE_PRIVATE)

        with(preferences.edit()){
            putString("username", binding.edtUserName.text.toString())
            putString("password", binding.edtPassword.text.toString())
                .apply()
        }
    }
}