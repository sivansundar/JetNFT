package com.sivan.jetnft.database.model

import com.sivan.jetnft.database.entity.FavouritesWithNFTCacheEntity
import com.sivan.jetnft.database.entity.toFavouritesModel
import com.sivan.jetnft.database.entity.toNFTModel

data class FavouritesWithNFTModel(
    var favouriteItem : FavouritesModel,
    var nftItem : NFTModel
) {
}

