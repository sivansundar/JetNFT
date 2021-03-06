package com.sivan.jetnft.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sivan.jetnft.database.entity.BidCacheEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

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
    fun getAllBids(): LiveData<BidCacheEntity>

    @Transaction
    @Query("SELECT * FROM bids WHERE nft_id = :nft_id ORDER BY created_at DESC LIMIT 1")
    fun getLatestBid(nft_id: Long): Flow<BidCacheEntity>

    @Transaction
    @Query("SELECT * FROM bids WHERE nft_id = :nft_id")
    fun getBidsByNFT(nft_id: Long): Flow<List<BidCacheEntity>>
}