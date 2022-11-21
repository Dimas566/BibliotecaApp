package com.example.bibliotecaapp.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity(tableName = "RevistaEntity")
class RevistaEntity(
    @PrimaryKey(autoGenerate = true)
    @get:Exclude override var id: Long = 0,
    override var codigo: Int,
    override var titulo: String,
    override var anioPublicacion: Int,
    @ColumnInfo(name = "numeroRevista")
    var numeroRev: Int) : Publicacion(codigo = codigo, titulo = titulo, anioPublicacion = anioPublicacion) {

    override fun getTipoPublicacion(): Int = 2
}