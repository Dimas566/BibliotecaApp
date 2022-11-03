package com.example.bibliotecaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bibliotecaapp.MainActivity.Companion.publicacionRepository
import com.example.bibliotecaapp.Models.Libro
import com.example.bibliotecaapp.Models.Publicacion
import com.example.bibliotecaapp.Models.Revista
import com.example.bibliotecaapp.databinding.ActivityRegistrarPublicacionBinding
import com.example.bibliotecaapp.databinding.ProgressDialogBinding

class RegistrarPublicacionActivity : AppCompatActivity(), View.OnClickListener {

    // Variable para gestionar el viewBinding
    private lateinit var binding: ActivityRegistrarPublicacionBinding
    private var tipoPublicacion: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // Configuracion de viewBinding
        binding = ActivityRegistrarPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Configuracion del action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tipoPublicacion = intent.extras!!.getInt("tipoPublicacion")

        // Configuracion de evento click para el botón de registro
        binding.layoutRegistrarPublicacion.btnGuardarRegistro.setOnClickListener(this)

        if(tipoPublicacion == 1) {
            // En este caso el tipo publicacion es libro
            // El formulario de registro debe ocultar el campo numero revista
            binding.layoutRegistrarPublicacion.tilNumeroRevista.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            binding.layoutRegistrarPublicacion.btnGuardarRegistro.id -> {
                // Guardar la publicacion
                // Hay que evaluar si el tipo publicacion es Libro o Revista
                if(tipoPublicacion == 1){
                    publicacionRepository.add(Libro(binding.layoutRegistrarPublicacion.edtCodigo.text.toString().toInt(),
                    binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
                    binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString().toInt()))
                    // Llamado al dialog
                    configProgressDialog()

                } else if(tipoPublicacion == 2){
                    publicacionRepository.add(Revista(binding.layoutRegistrarPublicacion.edtCodigo.text.toString().toInt(),
                        binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
                        binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString().toInt(),
                        binding.layoutRegistrarPublicacion.edtNumeroRevista.text.toString().toInt()))
                    // Llamado al dialog
                    configProgressDialog()
                }
            }
        }
    }

    // Configuracion del action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                // Deberá permitir volver a la actividad anterior
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Configurador del progress dialog
    fun configProgressDialog(){
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        alertBuilder.setView(dialogView)
        alertBuilder.setCancelable(false)
        val dialog = alertBuilder.create()
        dialog.show()
        // Configurando hilo, para asignar tiempo
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            finish()
        }, 3000)
    }
}