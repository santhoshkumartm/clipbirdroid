package com.srilakshmikanthanp.clipbirdroid.ui.gui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.srilakshmikanthanp.clipbirdroid.R
import com.srilakshmikanthanp.clipbirdroid.constant.appMaxHistory
import com.srilakshmikanthanp.clipbirdroid.controller.AppController
import com.srilakshmikanthanp.clipbirdroid.intface.OnSyncRequestHandler
import com.srilakshmikanthanp.clipbirdroid.ui.gui.composables.ClipData
import com.srilakshmikanthanp.clipbirdroid.ui.gui.composables.ClipHistory
import com.srilakshmikanthanp.clipbirdroid.ui.gui.composables.ClipSend
import com.srilakshmikanthanp.clipbirdroid.utility.functions.generateX509Certificate

/**
 * History Screen Used To See the History of the Clipboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun History(controller: AppController, onMenuClick: () -> Unit = {}) {
  // State List that has last max copied items
  var history by remember { mutableStateOf(listOf<ClipData>()) }

  // On Sync Request Handler
  val syncRequestHandler = OnSyncRequestHandler {
    val copy: MutableList<ClipData> = mutableListOf(*history.toTypedArray(), it)
    if (copy.size > appMaxHistory()) copy.removeFirst()
    history = copy.toList()
  }

  // On Send Handler
  val sendHandler = {
    controller.syncClipboard(controller.getClipboard())
  }

  // OnCopy Handler
  val onCopy = { idx: Int ->
    controller.setClipboard(history[idx])
  }

  // OnDelete Handler
  val onDelete = { idx: Int ->
    val copy: MutableList<ClipData> = mutableListOf(*history.toTypedArray())
    copy.removeAt(idx)
    history = copy.toList()
  }

  // Set up the History Composable
  val setup = {
    controller.addSyncRequestHandler(syncRequestHandler)
  }

  // Dispose the History Composable
  val dispose = {
    controller.removeSyncRequestHandler(syncRequestHandler)
  }

  // Setup & Dispose
  DisposableEffect(history) {
    setup(); onDispose(dispose)
  }

  // Navigation Icon is the Menu Icon
  val menuIcon = @Composable {
    IconButton(onClick = onMenuClick) {
      Image(painter = painterResource(R.drawable.menu), contentDescription = "Menu",)
    }
  }

  // History Top Bar
  val historyTopBar = @Composable {
    TopAppBar(
      navigationIcon = { menuIcon() },
      title = { Text("Clipbird History", modifier = Modifier.padding(horizontal = 3.dp)) },
      modifier = Modifier.padding(3.dp),
    )
  }

  // history Content
  val historyContent = @Composable { padding : PaddingValues ->
    Box (modifier = Modifier.padding(padding), contentAlignment = Alignment.Center,) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(10.dp),
      ) {
        // White Space
        Spacer(modifier = Modifier.padding(5.dp))

        // Clip Send Box
        ClipSend(
          modifier = Modifier.fillMaxWidth(),
          onSend = sendHandler,
        )

        // White Space
        Spacer(modifier = Modifier.padding(10.dp))

        // Clip History
        ClipHistory(
          clipHistory = history,
          onCopy = onCopy,
          onDelete = onDelete
        )
      }
    }
  }

  // Scaffold
  Scaffold(
    topBar = historyTopBar,
    content = historyContent,
  )
}

/**
 * Preview History
 */
@Preview(showBackground = true)
@Composable
private fun PreviewHistory() {
  History(AppController(generateX509Certificate(LocalContext.current), LocalContext.current))
}
