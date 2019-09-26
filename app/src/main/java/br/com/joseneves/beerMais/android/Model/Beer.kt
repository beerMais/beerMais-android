package br.com.joseneves.beerMais.android.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Beer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var amount: Int = 0,
    var brand: String,
    var type: Int = 0,
    var value: Float = 0.0f)