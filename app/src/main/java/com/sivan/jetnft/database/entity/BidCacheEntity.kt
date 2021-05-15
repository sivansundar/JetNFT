package com.sivan.jetnft.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "bids",
    foreignKeys = [androidx.room.ForeignKey(
    entity = UserCacheEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("user_id"),
    onDelete = androidx.room.ForeignKey.CASCADE
)])

data class BidCacheEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "user_id")
    val user_id: Long,

    @ColumnInfo(name = "nft_id")
    var nft_id: Long,

    @ColumnInfo(name = "bid_amount")
    var bidAmount: Double?,

    @ColumnInfo(name = "created_at")
    var created_at: ZonedDateTime,

    @ColumnInfo(name = "updated_at")
    var updated_at: ZonedDateTime

) {
}