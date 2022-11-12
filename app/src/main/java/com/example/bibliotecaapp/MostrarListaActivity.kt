package com.example.bibliotecaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecaapp.Adapters.PublicacionAdapter
import com.example.bibliotecaapp.Interfaces.IOnClickListener
import com.example.bibliotecaapp.MainActivity.Companion.publicacionRepository
import com.example.bibliotecaapp.Models.LibroEntity
import com.example.bibliotecaapp.Models.Publicacion
import com.example.bibliotecaapp.Models.RevistaEntity
import com.example.bibliotecaapp.databinding.ActivityMostrarListaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MostrarListaActivity : AppCompatActivity(), IOnClickListener {

    // Variable para configurar viewBinding
    private lateinit var binding: ActivityMostrarListaBinding
    // Variables necesarias para configurar el recyclerview
    private lateinit var recyclerView: RecyclerView
    private lateinit var publicacionAdapter: PublicacionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var tipoPublicacion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuracion de viewBinding
        binding = ActivityMostrarListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Habilitar action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tipoPublicacion = intent.extras!!.getInt("tipoPublicacion")

        // Validar si la lista esta vacia
        /*if(publicacionRepository.get().size == 0){
            AlertDialog.Builder(this)
                .setTitle(this.resources.getString(R.string.titulo_lista_vacia))
                .setMessage(this.resources.getString(R.string.msg_lista_vacia))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    finish()
                }.show()
        } else {
            // Configurar RecyclerView
            configRecyclerView()
        }*/
        configRecyclerView()
    }

    private fun getLibros(){
        doAsync {
            val libros = BibliotecaApplication.database.libroDao().getAll()
            uiThread {
                publicacionAdapter.setLibros(libros)
            }
        }
    }

    private fun getRevistas(){
        doAsync {
            val revistas = BibliotecaApplication.database.revistaDao().getAll()
            uiThread {
                publicacionAdapter.setRevistas(revistas)
            }
        }
    }

    // Método que configura el recyclerview
    private fun configRecyclerView(){
        recyclerView = binding.rcPublicaciones
        publicacionAdapter = PublicacionAdapter(mutableListOf(), this)
        linearLayoutManager = LinearLayoutManager(this)

       if(tipoPublicacion == 1){
           // Libros
           getLibros()
       } else {
           // Revistas
           getRevistas()
       }

        recyclerView.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = publicacionAdapter
        }
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

    override fun onClickListener(libroEntity: LibroEntity, position: Int) {
        if(libroEntity.Prestado()){
            // Si el libroEntity esta prestado, ejecutar devolucion
            doAsync {
                libroEntity.devolver()
                BibliotecaApplication.database.libroDao().updateLibro(libroEntity)
                uiThread {
                    publicacionAdapter.updateLibro(libroEntity)
                }
            }
        } else {
            // El libroEntity se encuentra disponible para ser prestado
            doAsync {
                libroEntity.prestar()
                BibliotecaApplication.database.libroDao().updateLibro(libroEntity)
                uiThread {
                    publicacionAdapter.updateLibro(libroEntity)
                }
            }
        }
    }

    override fun onDeleteLibro(libroEntity: LibroEntity, position: Int) {
        doAsync {
            BibliotecaApplication.database.libroDao().deleteLibro(libroEntity)
            uiThread {
                publicacionAdapter.deleteLibro(libroEntity)
            }
        }
    }

    override fun onDeleteRevista(revistaEntity: RevistaEntity, position: Int) {
        AlertDialog.Builder(this)
            .setTitle(this.resources.getString(R.string.titulo_eliminar))
            .setMessage(this.resources.getString(R.string.msg_eliminar))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                doAsync {
                    BibliotecaApplication.database.revistaDao().deleteRevista(revistaEntity)
                    uiThread {
                        publicacionAdapter.deleteRevista(revistaEntity)
                    }
                }
            }.show()
    }
}