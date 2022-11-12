package com.example.bibliotecaapp.Interfaces

import com.example.bibliotecaapp.Models.LibroEntity
import com.example.bibliotecaapp.Models.RevistaEntity

interface IOnClickListener {
    fun onClickListener(libroEntity: LibroEntity, position: Int)
    fun onDeleteLibro(libroEntity: LibroEntity, position: Int)
    fun onDeleteRevista(revistaEntity: RevistaEntity, position: Int)
}