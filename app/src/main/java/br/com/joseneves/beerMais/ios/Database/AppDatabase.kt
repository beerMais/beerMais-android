package br.com.joseneves.beerMais.ios.Database

import br.com.joseneves.beerMais.ios.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.ios.Model.Beer
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Beer::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDAO(): BeerDAO
}

object Database {

    private lateinit var database: AppDatabase

    fun instance(context: Context): AppDatabase {
        if (::database.isInitialized) return database
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "beermais-database")
            .build()
        return database
    }

}