package com.sivan.jetnft.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sivan.jetnft.database.model.FavouritesModel
import com.sivan.jetnft.database.model.NFTModel
import java.time.ZonedDateTime

@Entity(tableName = "favourites",
    foreignKeys = [ForeignKey(entity = NFTCacheEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("nft_id"),
        onDelete = ForeignKey.CASCADE
    )])
data class FavouritesCacheEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "nft_id")
    val nft_id: Long,

    @ColumnInfo(name = "created_at")
    var created_at: ZonedDateTime,

    @ColumnInfo(name = "updated_at")
    var updated_at: ZonedDateTime
) {
}

fun FavouritesCacheEntity.toFavouritesModel() = FavouritesModel(
    id = this.id,
    nft_id = this.nft_id,
    created_at = this.created_at,
    updated_at = this.updated_at
)

