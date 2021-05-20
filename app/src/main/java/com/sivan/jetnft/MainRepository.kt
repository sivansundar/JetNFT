package com.sivan.jetnft

import androidx.lifecycle.LiveData
import com.sivan.jetnft.database.NFTDatabase
import com.sivan.jetnft.database.dao.NFTDao
import com.sivan.jetnft.database.dao.UserDao
import java.time.ZonedDateTime
import android.content.Context
import android.util.Log
import com.sivan.jetnft.database.dao.BidDao
import com.sivan.jetnft.database.dao.FavouritesDao
import com.sivan.jetnft.database.entity.*
import com.sivan.jetnft.database.model.FavouritesModel

import com.sivan.jetnft.database.model.NFTWithUserModel
import com.sivan.jetnft.database.model.UserModel
import com.sivan.jetnft.database.model.toFavouritesCacheEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.Exception


class MainRepository(
    private val nftDatabase: NFTDatabase,
    private val nftDao: NFTDao,
    private val userDao: UserDao,
    private val bidDao: BidDao,
    private val favouritesDao: FavouritesDao,
    private val context : Context

) {

    suspend fun getCurrentUser(): LiveData<UserCacheEntity> {
        val user = userDao.getUserAsLiveData(3)

        return user
    }

   suspend fun getNFTs(): LiveData<List<NFTWithUserCacheEntity>> {

        return nftDao.getAllNFT()
    }

    suspend fun postUser(){

        val userList = listOf<UserCacheEntity>(

            UserCacheEntity(
            id = 1,
            name = "Meta Kovan",
            balance = 750,
            bio = "An early stage NFT lover | Digital Art | Doge to the Moon!",
            created_at = ZonedDateTime.now(),
            updated_at = ZonedDateTime.now(),
            publicKey = "0xasd50a498asfa2sa516a8s51da68sf41a3s21da6"
            ),

            UserCacheEntity(
                id = 2,
                name = "Elon Musk",
                balance = 1650,
                bio = "SpaceX | Memes control us | We are in a simulation!",
                created_at = ZonedDateTime.now(),
                updated_at = ZonedDateTime.now(),
                publicKey = "0xca0215as0a2s0sx5ax0560as5s056416"
            ),

        UserCacheEntity(
            id = 3,
            name = "Sivan",
            balance = 854,
            bio = "Decentralize the universe",
            created_at = ZonedDateTime.now(),
            updated_at = ZonedDateTime.now(),
            publicKey = "0x51a56sd4a0sa560a850asd1azx0asd56as4"
        ))

        userDao.insertList(userList)
    }

    suspend fun postNFTs(){

        val nftList = listOf<NFTCacheEntity>(
            NFTCacheEntity(
                id = 1,
                nftName = "Nyan Cat",
                creatorId = 1,
                current_bid = 6.0,
                nftDescription = "Nyan Cat has harnessed the essence of space and time. It blasts off with ultimate power.\n" +
                        "\n" +
                        "This extra special gift was originally released to the holders of Lucky Nyan Cat as a thank you for believing in the power of memes.\n" +
                        "\n" +
                        "Owning this NFT grants the following stats:\n" +
                        "\n" +
                        "Quantum+5 Power+20 Infinity+∞",
                nftImage = "https://raw.githubusercontent.com/sivansundar/JetNFT/master/app/src/main/res/drawable/nft_2.jpg",
                created_at = ZonedDateTime.now(),
                updated_at = ZonedDateTime.now()
            ),

            NFTCacheEntity(
                id = 2,
                nftName = "Moon Doge",
                creatorId = 2,
                current_bid = 4.0,
                nftDescription = "Nyan cat desctiption about an nft with a rainbow 8bit cat",
                nftImage = "https://raw.githubusercontent.com/sivansundar/JetNFT/master/app/src/main/res/drawable/nft_4.jpg",
                created_at = ZonedDateTime.now(),
                updated_at = ZonedDateTime.now()
            )


        )
        nftDao.insertList(nftList)
    }

   suspend fun postBid(nftModel: NFTWithUserModel, ethValue: Double?) {

       try {
           val user = userDao.getUser(3)

           val bidItem = BidCacheEntity(
               user_id = user.id,
               bidAmount = ethValue!!,
               nft_id = nftModel.nft.id,
               created_at = ZonedDateTime.now(),
               updated_at = ZonedDateTime.now()

           )

           val insertBid = bidDao.insert(bidItem)
           Log.d("Repo" , "Insert bid success! Status ${insertBid}")

           val updateNFTBidValue = nftDao.updateNFTCurrentValue(nftModel.nft.id, ethValue!!.toDouble())
           Log.d("Repo" , "NFT update success! Status ${updateNFTBidValue}")


       } catch (e : Exception) {
           Log.d("Repo" , "Failed bid insert : ${e.localizedMessage} : ${e.cause} : ${e.message}")
       }


    }

    suspend fun getAllBidsByNFT(nft_id: Long): Flow<List<BidCacheEntity>> {

        val result = bidDao.getBidsByNFT(nft_id)
        return result
    }

    suspend fun getLatestBid(nft_id : Long): Flow<BidCacheEntity> {
        val latestBid = bidDao.getLatestBid(nft_id)
        Log.d("Repo" , "Latest ${latestBid}")

        return latestBid
    }


    suspend fun getFavouritesList(): Flow<List<FavouritesWithNFTCacheEntity>> {
        val favouritesList = favouritesDao.getFavouritesList()
        Log.d("Repo" , "Latest ${favouritesList}")

        return favouritesList
    }

    suspend fun isNftFavourite(nft_id: Long): Boolean {
        val fav = favouritesDao.exists(nft_id)
        return fav

    }

    suspend fun addFavouriteItem(nftId: Long) {

        val favouriteItem = FavouritesModel(
            nft_id = nftId,
            created_at = ZonedDateTime.now(),
            updated_at = ZonedDateTime.now()
        )
        val insertFavouriteItem = favouritesDao.insert(favouriteItem.toFavouritesCacheEntity())
    }

    suspend fun removeFavouriteItem(nftId: Long) {
        val deleteFavouriteItem = favouritesDao.deleteFavourite(nftId)
    }
}