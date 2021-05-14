package com.sivan.jetnft.database.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.sivan.jetnft.database.model.NFTModel
import java.time.ZonedDateTime

@Entity(tableName = "nft",
    foreignKeys = [ForeignKey(entity = UserCacheEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("creator_id"),
    onDelete = CASCADE)])

data class NFTCacheEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "nft_name")
    val nftName: String,

    @ColumnInfo(name = "nft_description")
    var nftDescription: String,

    @ColumnInfo(name = "nft_image")
    var nftImage: String,

    @ColumnInfo(name = "creator_id", index = true)
    var creatorId: Long,

    @ColumnInfo(name = "current_bid")
    var current_bid: Double,

    @ColumnInfo(name = "created_at")
    var created_at: ZonedDateTime,

    @ColumnInfo(name = "updated_at")
    var updated_at: ZonedDateTime

) {
}

fun NFTCacheEntity.toNFTModel() = NFTModel(
    id = this.id,
    nftName = this.nftName,
    nftDescription = this.nftDescription,
    nftImage = this.nftImage,
    creatorId = this.creatorId,
    current_bid = this.current_bid,
    created_at = this.created_at,
    updated_at = this.updated_at
)

