package com.example.bibliotecaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bibliotecaapp.databinding.ActivityRegistrarUsuarioBinding

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Registrar Usuario"

        binding.btnSave.setOnClickListener {
            if(binding.edtUserName.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()){
                configSharedPreference()
                finish()
            } else {
                binding.edtUserName.error = "Campo requerido"
                binding.edtPassword.error = "Campo requerido"
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