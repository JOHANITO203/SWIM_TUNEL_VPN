package com.branded.vpn.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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
