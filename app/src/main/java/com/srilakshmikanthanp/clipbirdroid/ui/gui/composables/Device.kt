package com.srilakshmikanthanp.clipbirdroid.ui.gui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.srilakshmikanthanp.clipbirdroid.types.device.Device
import java.net.InetAddress
import java.util.Locale

/**
 * Device Action Enum To Represent Connect or Disconnect
 */
enum class DeviceAction {
  // Allowed Types connect or disconnect
  CONNECT, DISCONNECT;

  // Enum to String in Capitalize
  override fun toString(): String {
    return super.toString().lowercase(Locale.getDefault()).replaceFirstChar {
      it.titlecase(Locale.getDefault())
    }
  }
}

/**
 * DeviceActionable Type Alias
 */
typealias DeviceActionable = Pair<Device, DeviceAction>

/**
 * Device Composable to Represent the Device
 */
@Composable
fun Device(
  deviceActionable: DeviceActionable,
  modifier: Modifier,
  onAction: (DeviceAction) -> Unit
) {
  // Click Handler for the Device Action Clicked either Connect or Disconnect
  val onClickHandler = { onAction(deviceActionable.second) }

  // Hostname And Action placed in row name left and action right
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    // Align Test the Center both horizontally and vertically
    Text(
      text = deviceActionable.first.name,
      modifier = Modifier.padding(5.dp)
    )

    // Connect or Disconnect Button
    TextButton(
      onClick = onClickHandler
    ) {
      Text(text = deviceActionable.second.toString())
    }
  }
}

/**
 * Device Composable to Represent the Device Preview
 */
@Preview(showBackground = true)
@Composable
private fun DevicePreview() {
  // A Constant Device Value to Preview
  val device = Device(InetAddress.getLocalHost(), 8080, "LAPTOP-HP")

  // Preview the Device
  Column() {
    Device(
      deviceActionable = DeviceActionable(device, DeviceAction.DISCONNECT),
      modifier = Modifier.fillMaxWidth(),
      onAction = {}
    )

    Device(
      deviceActionable = DeviceActionable(device, DeviceAction.CONNECT),
      modifier = Modifier.fillMaxWidth(),
      onAction = {}
    )

    Device(
      deviceActionable = DeviceActionable(device, DeviceAction.DISCONNECT),
      modifier = Modifier.fillMaxWidth(),
      onAction = {}
    )
  }
}
