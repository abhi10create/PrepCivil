package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
  onEnterWorkspace: () -> Unit
) {
  val alphaAnim = remember { Animatable(0f) }
  val scaleAnim = remember { Animatable(0.92f) }
  val progressAnim = remember { Animatable(0f) }

  LaunchedEffect(Unit) {
    alphaAnim.animateTo(
      targetValue = 1.0f,
      animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    scaleAnim.animateTo(
      targetValue = 1.0f,
      animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    progressAnim.animateTo(
      targetValue = 1.0f,
      animationSpec = tween(durationMillis = 1400)
    )
    delay(200)
    onEnterWorkspace()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            Color(0xFF0F172A),
            Color(0xFF1E1B4B),
            Color(0xFF0F172A)
          )
        )
      )
      .padding(32.dp)
      .testTag("splash_screen_container"),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxWidth()
        .alpha(alphaAnim.value)
        .scale(scaleAnim.value)
        .testTag("splash_content_column")
    ) {
      // Centered Glowing PC Monogram Logo
      Box(
        modifier = Modifier
          .size(80.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(Color(0x26818CF8))
          .border(1.5.dp, Color(0xFF818CF8), RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "PC",
          fontSize = 32.sp,
          fontWeight = FontWeight.Black,
          style = TextStyle(
            brush = Brush.horizontalGradient(
              listOf(Color(0xFFFFFFFF), Color(0xFF818CF8))
            )
          )
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // App Title in Matched Theme Gradient
      Text(
        text = buildAnnotatedString {
          withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Black)) {
            append("Prep")
          }
          withStyle(
            style = SpanStyle(
              brush = Brush.horizontalGradient(
                listOf(Color(0xFFFFFFFF), Color(0xFF818CF8))
              ),
              fontWeight = FontWeight.Black
            )
          ) {
            append("Civil")
          }
        },
        fontSize = 32.sp,
        letterSpacing = 0.5.sp,
        modifier = Modifier.testTag("splash_app_title")
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Subtext / Tagline
      Text(
        text = "Smart Civil Services Preparation • UPSC CSE",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF94A3B8),
        textAlign = TextAlign.Center,
        letterSpacing = 0.2.sp
      )

      Spacer(modifier = Modifier.height(40.dp))

      // Integrated Progress Bar Component
      Column(
        modifier = Modifier
          .fillMaxWidth(0.85f)
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0x331E293B))
          .border(1.dp, Color(0x33818CF8), RoundedCornerShape(12.dp))
          .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (progressAnim.value < 1f) "Loading Workspace..." else "Workspace Ready",
            fontSize = 12.sp,
            color = Color(0xFFCBD5E1),
            fontWeight = FontWeight.Medium
          )
          Text(
            text = "${(progressAnim.value * 100).toInt()}%",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF818CF8)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
          progress = { progressAnim.value },
          modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = Color(0xFF818CF8),
          trackColor = Color(0xFF1E293B)
        )
      }
    }
  }
}
