package com.sivan.jetnft

import android.util.Log
import androidx.lifecycle.*
import com.sivan.jetnft.database.entity.BidCacheEntity
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity
import com.sivan.jetnft.database.model.NFTWithUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _name = MutableLiveData<List<NFTCacheEntity>>()
    var name: LiveData<List<NFTCacheEntity>> = _name

    var nftList: LiveData<List<NFTCacheEntity>> = MutableLiveData()

    var _nfts: LiveData<List<NFTCacheEntity>> = MutableLiveData()
    val nfts: LiveData<List<NFTCacheEntity>> get() = _nfts


    var getAllNFT: LiveData<List<NFTWithUserCacheEntity>>? = null

    var bidETHValue: MutableLiveData<Double> = MutableLiveData()
    var bidETH : LiveData<Double> = bidETHValue

    fun setBidETHValue(value : Double) {
        bidETHValue.value = value
    }


   suspend fun placeBid(nftModel: NFTWithUserModel, ethValue: Double?) {

        mainRepository.postBid(nftModel, ethValue)
    }

    var _latestBid : LiveData<BidCacheEntity>? = null

    suspend fun getLatestBid(nftId : Long) {
        _latestBid = mainRepository.getLatestBid(nft_id = nftId)
        Log.d("Repo", "Get latest bid : ${_latestBid?.value}")
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.postUser()
            mainRepository.postNFTs()

            getAllNFT = mainRepository.getNFTs()

        }

    }
//
    suspend fun getNftLists(): LiveData<List<NFTWithUserCacheEntity>> {
    val list = mainRepository.getNFTs()
    Log.d("Repo", "List : ${list.value}")
    return list
    }

}