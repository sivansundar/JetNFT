package com.sivan.jetnft

import android.util.Log
import androidx.lifecycle.*
import com.sivan.jetnft.database.entity.NFTCacheEntity
import com.sivan.jetnft.database.entity.NFTWithUserCacheEntity
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