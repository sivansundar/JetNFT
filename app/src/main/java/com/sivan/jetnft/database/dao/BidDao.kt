package com.sivan.jetnft.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity

@Dao
interface BidDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bidCacheEntity: BidCacheEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(bidCacheEntity: BidCacheEntity)

    @Update
    suspend fun update(bidCacheEntity: BidCacheEntity) : Int

    @Transaction
    @Query("SELECT * FROM bids")
    fun getAllBids(): LiveData<List<BidCacheEntity>>
}