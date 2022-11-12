package com.example.bibliotecaapp.Repository

import com.example.bibliotecaapp.Models.Publicacion

class PublicacionRepository(
    var lstPublicaciones: MutableList<Publicacion> = mutableListOf()) {

    fun add(publicacionEntity: Publicacion){
        lstPublicaciones.add(publicacionEntity)
    }

    fun get(): MutableList<Publicacion> = lstPublicaciones

}