package br.com.joseneves.beerMais.ios.Database.DAO

import br.com.joseneves.beerMais.ios.Model.Beer
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface BeerDAO {
    @Query("SELECT * FROM beer ORDER BY id DESC")
    fun all(): LiveData<List<Beer>>

    @Insert
    fun add(vararg product: Beer)
}