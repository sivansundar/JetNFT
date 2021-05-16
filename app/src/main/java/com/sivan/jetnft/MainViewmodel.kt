package com.sivan.jetnft

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity
import com.sivan.jetnft.database.model.NFTWithUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

}