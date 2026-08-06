package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.delay

@Composable
fun FooterStatusBar(
  masteryPercentage: Int,
  modifier: Modifier = Modifier
) {
  var currentTimeString by remember {
    mutableStateOf(SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()))
  }

  // Update time every 10 seconds
  LaunchedEffect(Unit) {
    while (true) {
      delay(10_000)
      currentTimeString = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    }
  }

  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val alphaPulse by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(40.dp)
      .background(DarkContainer)
      .border(1.dp, DarkBorder)
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Auto-Sync On Indicator
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.testTag("auto_sync_indicator")
    ) {
      Box(
        modifier = Modifier
          .size(8.dp)
          .clip(CircleShape)
          .background(StatusGreen)
          .alpha(alphaPulse)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "AUTO-SYNC ON",
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        color = TextSecondary
      )
    }

    // Mastery & Time
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Text(
        text = "$masteryPercentage% Mastery",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextSecondary,
        modifier = Modifier.testTag("mastery_indicator")
      )

      Text(
        text = currentTimeString,
        fontSize = 11.sp,
        color = TextMuted,
        modifier = Modifier.testTag("time_indicator")
      )
    }
  }
}
