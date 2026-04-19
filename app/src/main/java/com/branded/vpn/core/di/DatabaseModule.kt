package com.branded.vpn.core.di

import android.content.Context
import androidx.room.Room
import com.branded.vpn.data.local.AppDatabase
import com.branded.vpn.data.local.dao.VpnDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "branded_vpn.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideVpnDao(db: AppDatabase): VpnDao {
        return db.vpnDao()
    }
}
