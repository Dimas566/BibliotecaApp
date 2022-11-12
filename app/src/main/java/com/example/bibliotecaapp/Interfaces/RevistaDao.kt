package com.example.bibliotecaapp.Interfaces

import androidx.room.*
import com.example.bibliotecaapp.Models.RevistaEntity

@Dao
interface RevistaDao {

    @Query("SELECT * FROM RevistaEntity")
    fun getAll(): MutableList<RevistaEntity>

    @Insert
    fun addRevista(revistaEntity: RevistaEntity)

    @Update
    fun updateRevista(revistaEntity: RevistaEntity)

    @Delete
    fun deleteRevista(revistaEntity: RevistaEntity)
}