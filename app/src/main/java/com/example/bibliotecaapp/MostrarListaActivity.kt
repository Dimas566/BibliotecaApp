package com.example.bibliotecaapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecaapp.Adapters.PublicacionAdapter
import com.example.bibliotecaapp.MainActivity.Companion.publicacionRepository
import com.example.bibliotecaapp.databinding.ActivityMostrarListaBinding

class MostrarListaActivity : AppCompatActivity() {

    // Variable para configurar viewBinding
    private lateinit var binding: ActivityMostrarListaBinding
    // Variables necesarias para configurar el recyclerview
    private lateinit var recyclerView: RecyclerView
    private lateinit var publicacionAdapter: PublicacionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuracion de viewBinding
        binding = ActivityMostrarListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Habilitar action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Validar si la lista esta vacia
        if(publicacionRepository.get().size == 0){
            AlertDialog.Builder(this)
                .setTitle(this.resources.getString(R.string.titulo_lista_vacia))
                .setMessage(this.resources.getString(R.string.msg_lista_vacia))
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener
                { dialogInterface, i ->
                    finish()
                }).show()
        } else {
            // Configurar RecyclerView
            configRecyclerView()
        }
        //configRecyclerView()
    }

    // Método que configura el recyclerview
    private fun configRecyclerView(){
        recyclerView = binding.rcPublicaciones
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        publicacionAdapter = PublicacionAdapter(publicacionRepository.get(), this)
        recyclerView.adapter = publicacionAdapter
    }

    // Método que configura el action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                // Finaliza la actividad
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}