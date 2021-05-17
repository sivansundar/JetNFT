package com.sivan.jetnft.database.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.sivan.jetnft.database.entity.FavouritesCacheEntity
import java.time.ZonedDateTime

data class FavouritesModel(
    var id: Long = 0L,

    val nft_id: Long,

    var created_at: ZonedDateTime,

    var updated_at: ZonedDateTime
) {
}

fun FavouritesModel.toFavouritesCacheEntity() = FavouritesCacheEntity(
    id = this.id,
    nft_id = this.nft_id,
    created_at = this.created_at,
    updated_at = this.updated_at
)

