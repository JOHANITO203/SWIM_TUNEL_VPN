package com.branded.vpn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.branded.vpn.data.local.dao.VpnDao
import com.branded.vpn.data.local.entity.NodeEntity
import com.branded.vpn.data.local.entity.UserEntity

@Database(entities = [NodeEntity::class, UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vpnDao(): VpnDao
}
