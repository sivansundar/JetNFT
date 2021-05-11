package com.sivan.jetnft.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "users")
data class UserCacheEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "bio")
    var bio: String,

    @ColumnInfo(name = "balance")
    var balance : Long,

    @ColumnInfo(name = "public_key")
    var publicKey : String,

    @ColumnInfo(name = "created_at")
    var created_at : ZonedDateTime,

    @ColumnInfo(name = "updated_at")
    var updated_at : ZonedDateTime

) {
}