package com.branded.vpn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.branded.vpn.data.local.dao.VpnDao
import com.branded.vpn.data.local.entity.NodeEntity

@Database(entities = [NodeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vpnDao(): VpnDao
}
