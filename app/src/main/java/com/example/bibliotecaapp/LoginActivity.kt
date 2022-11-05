package com.example.bibliotecaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bibliotecaapp.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Iniciar Sesión"

        binding.btnSignUp.setOnClickListener{
            startActivity(Intent(this, RegistrarUsuarioActivity::class.java))
        }

        binding.btnSave.setOnClickListener {
            login()
        }

        configGlide()
    }

    private fun login(){
        if(binding.edtUserName.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()){
            // Obtener valores del shared preferences
            val preferences =getSharedPreferences("Users",MODE_PRIVATE)
            val userName = preferences.getString("username","")
            val password = preferences.getString("password","")

            if(binding.edtUserName.text.toString() == userName && binding.edtPassword.text.toString() == password){
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Bienvenido $userName", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Credenciales incorrectas...")
                    .setMessage("Usuario/Contraseña no son correctos")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        }
    }

    private fun configGlide(){
        val url = "https://yt3.ggpht.com/ytc/AMLnZu9-hbX2bZLgbBKPQ9P1sSg9U0wL44dmcHLcSX5BvQ=s900-c-k-c0x00ffffff-no-rj"
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .into(binding.imgvIconApp)
    }

}