package com.sivan.jetnft.di

import android.content.Context
import androidx.room.Room
import com.sivan.jetnft.database.NFTDatabase
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
object RoomModule {

    @Singleton
    @Provides
    fun provideNFTDb(@ApplicationContext context: Context) : NFTDatabase {
        return Room.databaseBuilder(
            context,
            NFTDatabase::class.java,
            NFTDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNFTDao(nftDatabase: NFTDatabase) : NFTDao {
        return nftDatabase.nftDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(nftDatabase: NFTDatabase) : UserDao {
        return nftDatabase.userDao()
    }


}