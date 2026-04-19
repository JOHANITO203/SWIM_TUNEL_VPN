package com.branded.vpn.core.di

import com.branded.vpn.vpn.engine.MockXrayTunnelEngine
import com.branded.vpn.vpn.engine.XrayTunnelEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EngineModule {

    @Binds
    @Singleton
    abstract fun bindTunnelEngine(
        engine: MockXrayTunnelEngine
    ): XrayTunnelEngine
}
