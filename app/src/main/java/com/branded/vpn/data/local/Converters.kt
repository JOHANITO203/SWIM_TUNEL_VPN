package com.branded.vpn.data.local

import androidx.room.TypeConverter
import com.branded.vpn.core.domain.model.Protocol

class Converters {
    @TypeConverter
    fun fromProtocol(protocol: Protocol): String {
        return protocol.name
    }

    @TypeConverter
    fun toProtocol(value: String): Protocol {
        return Protocol.valueOf(value)
    }
}
