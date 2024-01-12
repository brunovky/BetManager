package com.brunooliveira.betmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brunooliveira.betmanager.util.format
import java.util.Date

@Entity("bet")
data class Bet(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var status: String,
    @ColumnInfo(name =  "bet_house")
    var betHouse: String,
    var description: String,
    var stake: Double,
    var odds: Double,
    var sport: String,
    var date: String
) {

    companion object {
        val NEW = Bet(0, "", "Em progresso", "Bet365", "", 1.0, 1.0, "Futebol", Date().format())
    }

}
