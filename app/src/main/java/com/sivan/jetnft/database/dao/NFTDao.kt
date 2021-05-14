package com.sivan.jetnft.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NFTDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nftCacheEntity: NFTCacheEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(nftCacheEntity: List<NFTCacheEntity>)

    @Update
    suspend fun update(nftCacheEntity: NFTCacheEntity) : Int

    @Transaction
    @Query("SELECT * FROM nft")
    fun getAllNFT(): LiveData<List<NFTWithUserCacheEntity>>

}