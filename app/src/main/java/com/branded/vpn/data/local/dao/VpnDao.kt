package com.branded.vpn.data.local.dao

import androidx.room.*
import com.branded.vpn.data.local.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VpnDao {
    @Query("SELECT * FROM vpn_nodes")
    fun getAllNodes(): Flow<List<NodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNodes(nodes: List<NodeEntity>)

    @Query("DELETE FROM vpn_nodes")
    suspend fun clearNodes()
}
