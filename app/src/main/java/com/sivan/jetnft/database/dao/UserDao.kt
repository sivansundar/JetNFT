package com.sivan.jetnft.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.UserCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userCacheEntity: UserCacheEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(userCacheEntity: List<UserCacheEntity>)
}