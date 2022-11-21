package com.example.bibliotecaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bibliotecaapp.BibliotecaApplication
import com.example.bibliotecaapp.R
import com.example.bibliotecaapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.startActivityForResult

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    val reqCode: Int = 123
    private lateinit var listenerFirebase: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        title = "Iniciar Sesión"

        binding.btnSignUp.setOnClickListener{
            startActivity(Intent(this, RegistrarUsuarioActivity::class.java))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)

        verifyUser()

        binding.btnSave.setOnClickListener {
            login()
        }
        // AUTENTICACION POR GOOGLE
        binding.signinGoogle.setOnClickListener {
            val signingIntent: Intent = googleSignInClient.signInIntent
            startActivityForResult(signingIntent,reqCode)
        }

        configGlide()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == reqCode){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if(account != null){
                updateUI(account)
            }
        } catch (exception: ApiException){
            Toast.makeText(this,exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        // Verificar la ultima sesion iniciada
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        auth.addAuthStateListener (listenerFirebase)
    }

    override fun onPause() {
        super.onPause()
        auth.removeAuthStateListener(listenerFirebase)
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun verifyUser(){
        auth = FirebaseAuth.getInstance()
        listenerFirebase = FirebaseAuth.AuthStateListener { it ->
            if(it.currentUser != null){
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    // AUTENTICACION POR CORREO Y CONTRASEÑA
    private fun login(){
        if(binding.edtUserName.text.toString().isNotEmpty() && binding.edtPassword.text.toString().isNotEmpty()){
            auth.signInWithEmailAndPassword(binding.edtUserName.text.toString(), binding.edtPassword.text.toString())
                .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Verifique sus credenciales", Toast.LENGTH_SHORT).show()
                }
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