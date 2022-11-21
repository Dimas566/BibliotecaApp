package com.example.bibliotecaapp

import android.app.Application
import androidx.room.Room
import com.example.bibliotecaapp.database.BibliotecaDatabase
import com.google.firebase.auth.FirebaseUser

class BibliotecaApplication : Application() {

    companion object {
        lateinit var database: BibliotecaDatabase

        lateinit var currentUser: FirebaseUser
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            BibliotecaDatabase::class.java,
            "BibliotecaDB")
            .build()
    }
}