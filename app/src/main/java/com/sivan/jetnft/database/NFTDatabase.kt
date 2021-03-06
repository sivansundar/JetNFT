package com.sivan.jetnft.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sivan.jetnft.database.dao.BidDao
import com.sivan.jetnft.database.dao.FavouritesDao
import com.sivan.jetnft.database.dao.NFTDao
import com.sivan.jetnft.database.dao.UserDao
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.entity.FavouritesCacheEntity
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.UserCacheEntity
import com.sivan.jetnft.util.BitmapConverter
import com.sivan.jetnft.util.DateTimeConverter

@Database(entities =
    [NFTCacheEntity::class,
    BidCacheEntity::class,
    UserCacheEntity::class,
    FavouritesCacheEntity::class], version = 1, exportSchema = false)

@TypeConverters(DateTimeConverter::class, BitmapConverter::class)
abstract class NFTDatabase : RoomDatabase() {
    abstract fun nftDao() : NFTDao
    abstract fun userDao() : UserDao
    abstract fun bidDao() : BidDao
    abstract fun favouritesDao() : FavouritesDao

    companion object {
        val DATABASE_NAME : String = "nft_db"
    }

}