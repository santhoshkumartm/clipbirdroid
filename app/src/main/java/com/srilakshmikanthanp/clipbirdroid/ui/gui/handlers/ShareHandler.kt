package com.srilakshmikanthanp.clipbirdroid.ui.gui.handlers

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.srilakshmikanthanp.clipbirdroid.ui.gui.service.ClipbirdService
import com.srilakshmikanthanp.clipbirdroid.utility.functions.toPNG
import java.io.FileNotFoundException

class ShareHandler : AbstractHandler() {
  // Called when the connection is ready to service
  override fun onConnectionReady(binder: ClipbirdService.ServiceBinder) {
    // if the Type in image/*
    if (intent.type?.startsWith("image/") != true) return

    // Get the controller
    val controller = binder.getService().getController()

    // Get the URI
    val uri = intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri ?: return

    // get the content
    val result = try {
      this.contentResolver.openInputStream(uri)
    } catch (e: FileNotFoundException) {
      Log.e("ShareHandler", "File not found Exception", e)
      return
    } catch (e: SecurityException) {
      Log.e("ShareHandler", "Security Exception", e)
      return
    }.use {
      val content = it?.let { toPNG(it.readBytes()) } ?: return@use null
      val mimeType = "image/png"
      return@use Pair(mimeType, content)
    } ?: return

    // Sync the Clipboard
    controller.syncClipboard(listOf(Pair(result.first, result.second)))

    // Show toast
    val toast = Toast(this)
    toast.duration = Toast.LENGTH_SHORT
    toast.setText("Image Synced")
    toast.show()

    // Close the activity
    this.finish()
  }
}
