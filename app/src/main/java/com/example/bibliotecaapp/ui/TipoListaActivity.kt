package com.example.bibliotecaapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.bibliotecaapp.R
import com.example.bibliotecaapp.databinding.ActivityTipoListaBinding
import com.google.android.material.snackbar.Snackbar

class TipoListaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTipoListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuracion de viewBinding
        binding = ActivityTipoListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Habilitar action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configuracion del evento click en los botones
        binding.btnSiguiente.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            binding.btnSiguiente.id -> {
                // Validar si se ha seleccionado uno de los radiobuttons
                if(!binding.rbtLibro.isChecked && !binding.rbtRevista.isChecked){
                    Snackbar.make(binding.root, R.string.seleccionar_opcion, Snackbar.LENGTH_SHORT).show()
                } else if(binding.rbtLibro.isChecked) {
                    // Caso LibroEntity
                    val intent: Intent = Intent(this, MostrarListaActivity::class.java)
                    intent.putExtra("tipoPublicacion",1)
                    startActivity(intent)
                } else {
                    // Caso RevistaEntity
                    val intent: Intent = Intent(this, MostrarListaActivity::class.java)
                    intent.putExtra("tipoPublicacion",2)
                    startActivity(intent)
                }
            }
        }
    }

    // Configurar action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                // Debe permitir regresar a la actividad anterior
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}