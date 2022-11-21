package com.example.bibliotecaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bibliotecaapp.R
import com.example.bibliotecaapp.Repository.PublicacionRepository
import com.example.bibliotecaapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Variable para manejar el viewBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    // Configuracion de companion object para agregar registros a la lista desde otras pantallas
    companion object {
        val publicacionRepository: PublicacionRepository = PublicacionRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuración de ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        // Configurar evento click de los botones
        binding.layoutPrincipal.btnAgregarPub.setOnClickListener(this)
        binding.layoutPrincipal.btnMostrarLista.setOnClickListener(this)
        binding.layoutPrincipal.btnMostrarDatos.setOnClickListener(this)
        binding.layoutPrincipal.btnCerrarSesion.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            binding.layoutPrincipal.btnAgregarPub.id -> {
                // Abrir pantalla seleccionar publicacion
                startActivity(Intent(this, SeleccionarPublicacionActivity::class.java))
            }
            binding.layoutPrincipal.btnMostrarLista.id -> {
                // Mostrar lista de publicaciones
                startActivity(Intent(this, TipoListaActivity::class.java))
            }
            binding.layoutPrincipal.btnMostrarDatos.id -> {
                // Mostrar datos del desarrollador
            }
            binding.layoutPrincipal.btnCerrarSesion.id -> {
                if(GoogleSignIn.getLastSignedInAccount(this) != null) {
                    googleSignInClient.signOut()
                        .addOnCompleteListener {
                            auth.signOut()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                } else {
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}