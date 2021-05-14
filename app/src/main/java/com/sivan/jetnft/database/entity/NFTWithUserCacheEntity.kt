package com.sivan.jetnft.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NFTWithUserCacheEntity(
    @Embedded val nft : NFTCacheEntity,

    @Relation(
        parentColumn = "creator_id",
        entityColumn = "id"
    )
    val user : UserCacheEntity
) {
}