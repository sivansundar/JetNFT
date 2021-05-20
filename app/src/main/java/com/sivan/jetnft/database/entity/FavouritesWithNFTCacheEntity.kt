package com.sivan.jetnft.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.sivan.jetnft.database.model.FavouritesWithNFTModel

data class FavouritesWithNFTCacheEntity(
    @Embedded val favouriteItem : FavouritesCacheEntity,

    @Relation(
        parentColumn = "nft_id",
        entityColumn = "id"
    )
    val nft : NFTCacheEntity,


) {
}

fun FavouritesWithNFTCacheEntity.toModel() = FavouritesWithNFTModel(
    favouriteItem = this.favouriteItem.toFavouritesModel(),
    nftItem = this.nft.toNFTModel()
)

fun List<FavouritesWithNFTCacheEntity>.toModel() =
    this.map { it.toModel() }
