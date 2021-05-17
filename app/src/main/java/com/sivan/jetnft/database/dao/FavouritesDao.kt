package com.sivan.jetnft.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sivan.jetnft.database.entity.FavouritesCacheEntity
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favouritesCacheEntity: FavouritesCacheEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(favouritesCacheEntityList: List<FavouritesCacheEntity>)

    @Update
    suspend fun update(favouritesCacheEntity: FavouritesCacheEntity) : Int

    @Transaction
    @Query("SELECT * FROM favourites")
    fun getFavouritesList(): Flow<List<FavouritesCacheEntity>>

//    @Transaction
//    @Query("SELECT * FROM favourites where nft_id = :nft_id")
//    suspend fun getFavouriteItem(nft_id : Long): Flow<FavouritesCacheEntity>

    @Transaction
    @Query("SELECT EXISTS (SELECT * FROM favourites where nft_id = :nft_id)")
    suspend fun exists(nft_id : Long): Boolean

    @Transaction
    @Query("DELETE from favourites WHERE nft_id = :id")
    suspend fun deleteFavourite(id : Long)

}