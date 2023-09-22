package com.srilakshmikanthanp.clipbirdroid.types.except

/**
 * Exception Type for Not This Packet error
 */
class NotThisPacket(override val message: String) : Exception(message) {
  override fun toString(): String {
    return "NotThisPacket: $message"
  }
}
