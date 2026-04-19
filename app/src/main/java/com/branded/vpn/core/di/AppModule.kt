package com.branded.vpn.core.di

import android.content.Context
import com.branded.vpn.core.domain.repository.AuthRepository
import com.branded.vpn.core.domain.repository.VpnRepository
import com.branded.vpn.data.repository.MockAuthRepository
import com.branded.vpn.data.repository.MockVpnRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = MockAuthRepository()

    @Provides
    @Singleton
    fun provideVpnRepository(): VpnRepository = MockVpnRepository()
}
