package br.com.joseneves.beerMais.ios.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Beer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Int = 0,
    val brand: String,
    val type: Int = 0,
    val value: Float = 0.0f)
