package com.branded.vpn.core.di

import com.branded.vpn.core.domain.repository.AuthRepository
import com.branded.vpn.core.domain.repository.VpnRepository
import com.branded.vpn.data.repository.AuthRepositoryImpl
import com.branded.vpn.data.repository.VpnRepositoryImpl
import com.branded.vpn.vpn.manager.VpnManager
import com.branded.vpn.vpn.manager.VpnManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindVpnRepository(
        impl: VpnRepositoryImpl
    ): VpnRepository

    @Binds
    @Singleton
    abstract fun bindVpnManager(
        impl: VpnManagerImpl
    ): VpnManager
}
