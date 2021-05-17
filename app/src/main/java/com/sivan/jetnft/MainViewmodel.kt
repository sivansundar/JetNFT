package com.sivan.jetnft

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.entity.FavouritesCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity
import com.sivan.jetnft.database.model.NFTWithUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var getAllNFT: LiveData<List<NFTWithUserCacheEntity>>? = null

    var bidETHValue: MutableLiveData<Double> = MutableLiveData()
    var bidETH : LiveData<Double> = bidETHValue

    val nftsByBid : MutableState<List<BidCacheEntity>?> = mutableStateOf(listOf())
    fun getBidsByNFT(id : Long) {
        viewModelScope.launch {
            val result = mainRepository.getAllBidsByNFT(id)
            result.distinctUntilChanged().collect {
                nftsByBid.value = it

                Log.d("VM", "NFTS : ${it}")
            }
        }
    }

    val latestBidByNFT : MutableState<List<BidCacheEntity>?> = mutableStateOf(listOf())
    fun getLatestBidByNFT(id : Long) {
        viewModelScope.launch {
            val result = mainRepository.getLatestBid(id)
            result.distinctUntilChanged().collect {
                latestBidByNFT.value = listOf(it)
            }
        }
    }

    val isNFTFavourite : MutableState<Boolean> = mutableStateOf(false)
    fun checkNFTFavourite(id : Long) {
        viewModelScope.launch {
            val result = mainRepository.isNftFavourite(id)
            isNFTFavourite.value = result
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.postUser()
            mainRepository.postNFTs()

            getAllNFT = mainRepository.getNFTs()

        }

    }

    fun setBidETHValue(value : Double) {
        bidETHValue.value = value
    }

   suspend fun placeBid(nftModel: NFTWithUserModel, ethValue: Double?) {
       mainRepository.postBid(nftModel, ethValue)
    }

    fun addToFavourites(nftId : Long) {
        viewModelScope.launch {
            mainRepository.addFavouriteItem(nftId)
        }

   }

    fun removeFromFavourites(nft_id: Long) {
        viewModelScope.launch {
            mainRepository.removeFavouriteItem(nft_id)
        }
    }

}