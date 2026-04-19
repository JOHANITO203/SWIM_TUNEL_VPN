package com.branded.vpn.vpn.engine

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

interface XrayTunnelEngine {
    fun prepare(host: String)
    fun start(tunFd: Int)
    fun stop()
}

@Singleton
class MockXrayTunnelEngine @Inject constructor() : XrayTunnelEngine {
    
    override fun prepare(host: String) {
        Log.d("XrayEngine", "Preparing tunnel config for host: $host")
    }

    override fun start(tunFd: Int) {
        Log.d("XrayEngine", "Starting Xray-core with TUN FD: $tunFd")
    }

    override fun stop() {
        Log.d("XrayEngine", "Stopping Xray-core")
    }
}
