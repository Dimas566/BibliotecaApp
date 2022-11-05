package com.example.bibliotecaapp.Interfaces

import com.example.bibliotecaapp.Models.Libro

interface IOnClickListener {
    fun onClickListener(libro: Libro, position: Int)
}