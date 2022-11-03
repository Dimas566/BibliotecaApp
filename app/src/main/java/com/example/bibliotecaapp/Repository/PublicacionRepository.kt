package com.example.bibliotecaapp.Repository

import com.example.bibliotecaapp.Models.Publicacion

class PublicacionRepository(
    var lstPublicaciones: MutableList<Publicacion> = mutableListOf()) {


    fun add(publicacion: Publicacion){
        lstPublicaciones.add(publicacion)
    }

    fun get(): MutableList<Publicacion> = lstPublicaciones

}