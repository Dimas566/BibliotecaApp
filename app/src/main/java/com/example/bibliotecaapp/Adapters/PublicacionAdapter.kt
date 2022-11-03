package com.example.bibliotecaapp.Adapters

import android.content.Context
import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliotecaapp.Models.Libro
import com.example.bibliotecaapp.Models.Publicacion
import com.example.bibliotecaapp.Models.Revista
import com.example.bibliotecaapp.R
import com.example.bibliotecaapp.databinding.ItemPublicacionListBinding

class PublicacionAdapter(var lstPublicaciones: MutableList<Publicacion> = ArrayList(), var context: Context)
    : RecyclerView.Adapter<PublicacionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(layout.inflate(R.layout.item_publicacion_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstPublicaciones[position]
        holder.bind(item, context, position)
    }

    override fun getItemCount(): Int = lstPublicaciones.size

    // Cambiar de estado al libro
    fun updateState(position: Int){
        notifyItemChanged(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPublicacionListBinding.bind(view)

        fun bind(publicacion: Publicacion, context: Context, position: Int){
            // Es necesario identificar el tipo de publicacion.
            if(publicacion.getTipoPublicacion() == 1){
                // Libro
                // A continuación se realiza un parceo o casteo
                 val libro: Libro = publicacion as Libro
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
                // Cambiar de estado al libro
                itemView.setOnClickListener{
                   // Validar si el libro ya fue prestado
                    if(libro.Prestado()){
                        // Si el libro esta prestado, ejecutar devolucion
                        libro.devolver()
                        updateState(position)
                    } else {
                        // El libro se encuentra disponible para ser prestado
                        libro.prestar()
                        updateState(position)
                    }
                }
            } else {
                // Revista
                val revista: Revista = publicacion as Revista
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
}