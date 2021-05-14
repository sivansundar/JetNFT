package com.sivan.jetnft.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class UserModel(

    var id: Long = 0L,

    val name: String,

    var bio: String,

    var balance : Long,

    var publicKey : String,

    var created_at : ZonedDateTime,

    var updated_at : ZonedDateTime
) : Parcelable{

}