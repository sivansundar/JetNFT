package com.sivan.jetnft.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class NFTModel(
    var id: Long = 0L,

    val nftName: String,

    var nftDescription: String,

    var nftImage: String,

    var creatorId: Long,

    var current_bid: Double,

    var created_at: ZonedDateTime,

    var updated_at: ZonedDateTime
) : Parcelable {
}