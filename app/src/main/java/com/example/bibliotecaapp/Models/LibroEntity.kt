package com.example.bibliotecaapp.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bibliotecaapp.Interfaces.IPrestable

@Entity(tableName = "LibroEntity")
class LibroEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var codigo: Int,
    override var titulo: String,
    override var anioPublicacion: Int,
    @ColumnInfo(name = "estadoPrestamo")
    var estadoPrestamo: Boolean = false) :
    Publicacion(codigo = codigo, titulo = titulo, anioPublicacion = anioPublicacion), IPrestable {

    override fun getTipoPublicacion(): Int = 1;

    override fun prestar() {
        this.estadoPrestamo = true
    }

    override fun devolver() {
        this.estadoPrestamo = false
    }

    override fun Prestado(): Boolean = this.estadoPrestamo
}