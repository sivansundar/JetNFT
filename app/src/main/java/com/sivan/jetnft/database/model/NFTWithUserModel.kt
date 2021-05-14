package com.sivan.jetnft.database.model

import android.os.Parcelable
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.UserCacheEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NFTWithUserModel(
    var nft : NFTModel,
    var user : UserModel
) : Parcelable{
}