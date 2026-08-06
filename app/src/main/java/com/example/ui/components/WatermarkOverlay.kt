package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.testTag

@Composable
fun WatermarkOverlay(
  userEmail: String = "sabhishek607@gmail.com",
  userId: String = "UPSC-2026-981",
  modifier: Modifier = Modifier
) {
  val watermarkText = "CONFIDENTIAL • $userEmail • ID: $userId"

  Box(
    modifier = modifier
      .fillMaxSize()
      .testTag("watermark_overlay")
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val paint = android.graphics.Paint().apply {
        // Subtle light grey color with ~6% opacity (alpha 15 out of 255)
        color = android.graphics.Color.argb(15, 160, 160, 160)
        textSize = 22f
        isAntiAlias = true
        typeface = android.graphics.Typeface.DEFAULT
      }

      val stepX = 420f
      val stepY = 280f

      rotate(-25f) {
        var y = -size.height
        while (y < size.height * 2) {
          var x = -size.width
          while (x < size.width * 2) {
            drawContext.canvas.nativeCanvas.drawText(
              watermarkText,
              x,
              y,
              paint
            )
            x += stepX
          }
          y += stepY
        }
      }
    }
  }
}
