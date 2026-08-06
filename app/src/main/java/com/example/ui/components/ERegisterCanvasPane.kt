package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

data class DrawPath(
  val path: Path,
  val color: Color,
  val strokeWidth: Float,
  val isEraser: Boolean = false
)

enum class CanvasTool {
  PEN, ERASER
}

@Composable
fun ERegisterCanvasPane(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var activeTool by remember { mutableStateOf(CanvasTool.PEN) }
  var selectedColor by remember { mutableStateOf(PurpleAccent) }
  var strokeWidth by remember { mutableStateOf(6f) }
  var savedNotification by remember { mutableStateOf<String?>(null) }

  val paths = remember { mutableStateListOf<DrawPath>() }
  var currentPathPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }

  val availableColors = listOf(
    PurpleAccent,
    Color(0xFF81D4FA), // Cyan
    Color(0xFFA5D6A7), // Green
    Color(0xFFFFF59D), // Yellow
    Color(0xFFFFAB91), // Orange/Red
    Color.White
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasBackground)
      .border(1.dp, DarkBorder)
  ) {
    // Toolbar Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(DarkContainer)
        .border(1.dp, DarkBorder)
        .padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Tool selector buttons
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        // Pen Tool Button
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (activeTool == CanvasTool.PEN) DarkPurpleText else Color.Transparent)
            .border(
              1.dp,
              if (activeTool == CanvasTool.PEN) PurpleAccent else Color.Transparent,
              RoundedCornerShape(8.dp)
            )
            .clickable { activeTool = CanvasTool.PEN }
            .testTag("pen_tool"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Create,
            contentDescription = "Pen Tool",
            tint = PurpleAccent,
            modifier = Modifier.size(18.dp)
          )
        }

        // Stroke Eraser Button
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (activeTool == CanvasTool.ERASER) DarkPurpleText else Color.Transparent)
            .border(
              1.dp,
              if (activeTool == CanvasTool.ERASER) PurpleAccent else Color.Transparent,
              RoundedCornerShape(8.dp)
            )
            .clickable { activeTool = CanvasTool.ERASER }
            .testTag("eraser_tool"),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Backspace,
            contentDescription = "Eraser Tool",
            tint = TextSecondary,
            modifier = Modifier.size(18.dp)
          )
        }

        // Clear Canvas Button
        IconButton(
          onClick = {
            paths.clear()
            savedNotification = "Canvas Cleared"
          },
          modifier = Modifier
            .size(34.dp)
            .testTag("clear_canvas_button")
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Clear Canvas",
            tint = TextSecondary,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      // Color Palette Picker
      if (activeTool == CanvasTool.PEN) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          availableColors.forEach { color ->
            Box(
              modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(color)
                .border(
                  1.dp,
                  if (selectedColor == color) Color.White else Color.Transparent,
                  CircleShape
                )
                .clickable { selectedColor = color }
            )
          }
        }
      }

      // Note Download/Save Button
      IconButton(
        onClick = {
          savedNotification = "Note Saved to E-Register (${paths.size} strokes)"
          Toast.makeText(context, "Note downloaded & saved successfully!", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
          .size(34.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(DarkPurpleText)
          .testTag("download_note_button")
      ) {
        Icon(
          imageVector = Icons.Default.Download,
          contentDescription = "Download Note",
          tint = PurpleAccent,
          modifier = Modifier.size(18.dp)
        )
      }
    }

    // Canvas Drawing Surface
    Box(
      modifier = Modifier
        .fillMaxSize()
        .weight(1f)
        .padding(8.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(CanvasBackground)
        .border(1.dp, DarkBorder, RoundedCornerShape(12.dp))
    ) {
      Canvas(
        modifier = Modifier
          .fillMaxSize()
          .pointerInput(activeTool, selectedColor, strokeWidth) {
            detectDragGestures(
              onDragStart = { offset ->
                currentPathPoints = listOf(offset)
              },
              onDrag = { change, _ ->
                currentPathPoints = currentPathPoints + change.position
              },
              onDragEnd = {
                if (currentPathPoints.isNotEmpty()) {
                  val newPath = Path().apply {
                    moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                    for (i in 1 until currentPathPoints.size) {
                      lineTo(currentPathPoints[i].x, currentPathPoints[i].y)
                    }
                  }
                  paths.add(
                    DrawPath(
                      path = newPath,
                      color = if (activeTool == CanvasTool.ERASER) CanvasBackground else selectedColor,
                      strokeWidth = if (activeTool == CanvasTool.ERASER) 36f else strokeWidth,
                      isEraser = activeTool == CanvasTool.ERASER
                    )
                  )
                  currentPathPoints = emptyList()
                }
              }
            )
          }
          .testTag("drawing_canvas")
      ) {
        // Draw grid lines in canvas background
        val gridStep = 40.dp.toPx()
        val numCols = (size.width / gridStep).toInt()
        val numRows = (size.height / gridStep).toInt()

        for (i in 0..numCols) {
          drawLine(
            color = DarkBorder.copy(alpha = 0.25f),
            start = Offset(i * gridStep, 0f),
            end = Offset(i * gridStep, size.height),
            strokeWidth = 1f
          )
        }
        for (j in 0..numRows) {
          drawLine(
            color = DarkBorder.copy(alpha = 0.25f),
            start = Offset(0f, j * gridStep),
            end = Offset(size.width, j * gridStep),
            strokeWidth = 1f
          )
        }

        // Draw saved paths
        paths.forEach { drawPath ->
          drawPath(
            path = drawPath.path,
            color = drawPath.color,
            style = Stroke(
              width = drawPath.strokeWidth,
              cap = StrokeCap.Round,
              join = StrokeJoin.Round
            )
          )
        }

        // Draw current dragging path in real-time
        if (currentPathPoints.size > 1) {
          val livePath = Path().apply {
            moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
            for (i in 1 until currentPathPoints.size) {
              lineTo(currentPathPoints[i].x, currentPathPoints[i].y)
            }
          }
          drawPath(
            path = livePath,
            color = if (activeTool == CanvasTool.ERASER) CanvasBackground else selectedColor,
            style = Stroke(
              width = if (activeTool == CanvasTool.ERASER) 36f else strokeWidth,
              cap = StrokeCap.Round,
              join = StrokeJoin.Round
            )
          )
        }
      }

      // E-Register Watermark Label
      Box(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(12.dp)
          .clip(RoundedCornerShape(6.dp))
          .background(DarkContainer.copy(alpha = 0.8f))
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = if (paths.isEmpty()) "E-Register Active" else "E-Register (${paths.size} strokes)",
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace,
          color = TextMuted,
          letterSpacing = 1.sp
        )
      }

      // Status Notification Banner if saved
      savedNotification?.let { notif ->
        Box(
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(DarkPurpleText)
            .border(1.dp, PurpleAccent, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = notif,
            fontSize = 11.sp,
            color = PurpleAccent,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
