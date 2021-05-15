package com.sivan.jetnft.di

import android.content.Context
import com.sivan.jetnft.MainRepository
import com.sivan.jetnft.database.NFTDatabase
import com.sivan.jetnft.database.dao.BidDao
import com.sivan.jetnft.database.dao.NFTDao
import com.sivan.jetnft.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        nftDatabase: NFTDatabase,
        nftDao: NFTDao,
        userDao: UserDao,
        bidDao : BidDao,
        @ApplicationContext context: Context
    )  : MainRepository {
        return MainRepository(
            nftDatabase,
            nftDao,
            userDao,
            bidDao,
            context

        )
    }

}