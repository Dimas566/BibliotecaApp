package com.example.bibliotecaapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecaapp.Interfaces.IOnClickListener
import com.example.bibliotecaapp.Models.Libro
import com.example.bibliotecaapp.Models.Publicacion
import com.example.bibliotecaapp.Models.Revista
import com.example.bibliotecaapp.R
import com.example.bibliotecaapp.databinding.ItemPublicacionListBinding

class PublicacionAdapter(private val lstPublicaciones: MutableList<Publicacion>, private val listener: IOnClickListener)
    : RecyclerView.Adapter<PublicacionAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(layout.inflate(R.layout.item_publicacion_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstPublicaciones[position]
        // Uso de funcion de alcance para agregar acciones al objeto en un mismo bloque
        with(holder){
            if(item.getTipoPublicacion() == 1){
                // Libro
                // A continuación se realiza un parceo o casteo
                val libro: Libro = item as Libro
                setListener(libro, position)
                // Configurando contenido del cardview en base al objeto casteado
                binding.txvcodigo.text = libro.getCode().toString()
                binding.txvTitulo.text = libro.getTitle()
                binding.txvAnio.text = libro.getYearPub().toString()
                binding.txvTipoPublicacion.text = context.resources.getString(R.string.libro_tag)
                // Se evalua el estado del libro
                if(libro.Prestado()){
                    binding.txvEstado.text = context.resources.getString(R.string.estado_tag)
                } else {
                    binding.txvEstado.text = context.resources.getString(R.string.estado_disp_tag)
                }
            } else {
                // Revista
                val revista: Revista = item as Revista
                // Configurando contenido del cardview en base al objeto casteado
                binding.txvcodigo.text = revista.getCode().toString()
                binding.txvTitulo.text = revista.getTitle()
                binding.txvAnio.text = revista.getYearPub().toString()
                binding.txvTipoPublicacion.text = context.resources.getString(R.string.revista_tag)
                // Se carga el numero de revista
                binding.txvEstado.text = revista.getNumRev().toString()
            }
        }
    }

    override fun getItemCount(): Int = lstPublicaciones.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPublicacionListBinding.bind(view)

        fun setListener(libro: Libro, position: Int){
            binding.root.setOnClickListener{
                listener.onClickListener(libro, position)
            }
        }
    }
}