package com.branded.vpn.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.branded.vpn.core.domain.model.Protocol

@Entity(tableName = "vpn_nodes")
data class NodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val countryCode: String,
    val host: String,
    val port: Int,
    val protocol: Protocol,
    val load: Int,
    val ping: Int
)

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val token: String,
    val planName: String,
    val expiryDate: Long,
    val isActive: Boolean,
    val subscriptionUrl: String
)
