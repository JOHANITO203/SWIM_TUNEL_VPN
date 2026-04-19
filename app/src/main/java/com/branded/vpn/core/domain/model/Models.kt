package com.branded.vpn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val email: String,
    val subscription: Subscription?
) : Parcelable

@Parcelize
data class Subscription(
    val planId: String,
    val planName: String,
    val expiryDate: Long,
    val isActive: Boolean,
    val subscriptionUrl: String?
) : Parcelable

data class VpnNode(
    val id: String,
    val name: String,
    val countryCode: String,
    val host: String,
    val port: Int,
    val protocol: Protocol,
    val load: Int,
    val ping: Int
)

enum class Protocol {
    VLESS, VMESS, TROJAN, SHADOWSOCKS
}

sealed class VpnStatus {
    object Disconnected : VpnStatus()
    object Connecting : VpnStatus()
    object Connected : VpnStatus()
    object Disconnecting : VpnStatus()
    data class Error(val message: String) : VpnStatus()
}

data class TrafficStats(
    val downByte: Long,
    val upByte: Long,
    val downSpeed: String,
    val upSpeed: String
)
