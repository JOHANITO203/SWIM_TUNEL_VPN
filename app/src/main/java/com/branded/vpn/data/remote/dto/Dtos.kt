package com.branded.vpn.data.remote.dto

data class LoginResponse(
    val token: String,
    val userId: String,
    val email: String
)

data class NodeResponse(
    val id: String,
    val name: String,
    val cc: String,
    val ip: String,
    val port: Int,
    val type: String
)

data class SubscriptionResponse(
    val planId: String,
    val planName: String,
    val expiresAt: Long,
    val active: Boolean,
    val subUrl: String
)
