package br.com.joseneves.beerMais.android.Database

import br.com.joseneves.beerMais.android.Database.DAO.BeerDAO
import br.com.joseneves.beerMais.android.Model.Beer
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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