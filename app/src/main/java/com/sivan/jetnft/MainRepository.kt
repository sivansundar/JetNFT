package com.sivan.jetnft

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.sivan.jetnft.database.NFTDatabase
import com.sivan.jetnft.database.dao.NFTDao
import com.sivan.jetnft.database.dao.UserDao
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.UserCacheEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.ZonedDateTime
import android.R
import android.content.Context

import android.graphics.BitmapFactory

import android.graphics.Bitmap
import androidx.core.content.res.ResourcesCompat

import android.graphics.drawable.BitmapDrawable







class MainRepository(
    private val nftDatabase: NFTDatabase,
    private val nftDao: NFTDao,
    private val userDao: UserDao,
    private val context : Context

) {



   suspend fun getNFTs(): LiveData<List<NFTCacheEntity>> {

        return nftDao.getAllNFT()
    }

    suspend fun postUser(){



        val userList = listOf<UserCacheEntity>(
            UserCacheEntity(
                id = 1,
            name = "Helloyyaaa",
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
            ))

        userDao.insertList(userList)
    }

    suspend fun postNFTs(){

        val nftList = listOf<NFTCacheEntity>(
            NFTCacheEntity(
                id = 1,
                nftName = "Nyan Cat",
                creatorId = 1,
                current_bid = 1.250,
                nftDescription = "Nyan cat desctiption about an nft with a rainbow 8bit cat",
                nftImage = "https://drive.google.com/file/d/1etQuK5rJLpuv8-vPfnPCHQcT4dica4N_/view?usp=sharing",
                created_at = ZonedDateTime.now(),
                updated_at = ZonedDateTime.now()
            ),

            NFTCacheEntity(
                id = 2,
                nftName = "Moon Doge",
                creatorId = 2,
                current_bid = 1.250,
                nftDescription = "Nyan cat desctiption about an nft with a rainbow 8bit cat",
                nftImage = "https://drive.google.com/file/d/16A2WzHxYm616bQOpg0Fp0GztACXEoMPx/view?usp=sharing",
                created_at = ZonedDateTime.now(),
                updated_at = ZonedDateTime.now()
            )


        )
        nftDao.insertList(nftList)
    }
}