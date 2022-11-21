package com.example.bibliotecaapp.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bibliotecaapp.Interfaces.LibroDao
import com.example.bibliotecaapp.Interfaces.RevistaDao
import com.example.bibliotecaapp.Models.LibroEntity
import com.example.bibliotecaapp.Models.RevistaEntity

@Database(entities = [LibroEntity::class, RevistaEntity::class], version = 1 )
abstract class BibliotecaDatabase : RoomDatabase(){
    abstract fun libroDao() : LibroDao
    abstract fun revistaDao() : RevistaDao
}