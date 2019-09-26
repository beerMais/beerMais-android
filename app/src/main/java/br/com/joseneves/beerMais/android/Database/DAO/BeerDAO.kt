package br.com.joseneves.beerMais.android.Database.DAO

import br.com.joseneves.beerMais.android.Model.Beer
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BeerDAO {
    @Query("SELECT * FROM beer ORDER BY (value/amount) ASC")
    fun all(): LiveData<List<Beer>>

    @Insert
    fun add(vararg product: Beer)

    @Delete
    fun delete(beer: Beer)

    @Update
    fun update(beer: Beer)
}