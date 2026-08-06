package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Chapter
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class ReaderTheme(val label: String, val bg: Color, val textColor: Color) {
  DARK("Dark", Color(0xFF1C1B1F), Color(0xFFE6E1E5)),
  SEPIA("Sepia", Color(0xFF2D2A26), Color(0xFFE8DCC4)),
  LIGHT("Light", Color(0xFFF5F2EB), Color(0xFF1C1B1F))
}

@Composable
fun ReaderPane(
  chapter: Chapter,
  isCanvasOpen: Boolean = true,
  onToggleCanvas: (() -> Unit)? = null,
  isZenMode: Boolean = false,
  onToggleZenMode: (() -> Unit)? = null,
  isProUser: Boolean = false,
  onOpenUpgradeModal: (() -> Unit)? = null,
  userEmail: String = "sabhishek607@gmail.com",
  modifier: Modifier = Modifier
) {
  var readerTheme by remember { mutableStateOf(ReaderTheme.DARK) }
  var fontSizeSp by remember { mutableStateOf(14) }
  val scrollState = rememberScrollState()

  val isLocked = chapter.isPremium && !isProUser

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(readerTheme.bg)
      .border(1.dp, DarkBorder)
  ) {
    // Reader Header Controls
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(if (readerTheme == ReaderTheme.LIGHT) Color(0xFFE8E4DA) else DarkContainer)
        .padding(horizontal = 12.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.MenuBook,
          contentDescription = "Reader",
          tint = if (readerTheme == ReaderTheme.LIGHT) Color(0xFF381E72) else PurpleAccent,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "CHAPTER READER",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
          color = if (readerTheme == ReaderTheme.LIGHT) Color(0xFF381E72) else PurpleAccent
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // Zen Mode Toggle
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isZenMode) PurpleAccent else Color.Transparent)
            .border(1.dp, if (isZenMode) PurpleAccent else DarkBorder, RoundedCornerShape(8.dp))
            .clickable { onToggleZenMode?.invoke() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag("zen_mode_toggle"),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.SelfImprovement,
              contentDescription = "Zen Mode",
              tint = if (isZenMode) DarkPurpleText else PurpleAccent,
              modifier = Modifier.size(14.dp)
            )
            Text(
              text = if (isZenMode) "Zen Active" else "Zen Mode",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (isZenMode) DarkPurpleText else PurpleAccent
            )
          }
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Reader Theme Toggle Options
        ReaderTheme.entries.forEach { theme ->
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape)
              .background(theme.bg)
              .border(
                1.dp,
                if (theme == readerTheme) PurpleAccent else DarkBorder,
                CircleShape
              )
              .clickable { readerTheme = theme }
              .testTag("reader_theme_${theme.name.lowercase()}"),
            contentAlignment = Alignment.Center
          ) {
            if (theme == readerTheme) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(PurpleAccent)
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Font size adjuster
        IconButton(
          onClick = {
            fontSizeSp = if (fontSizeSp >= 18) 12 else fontSizeSp + 2
          },
          modifier = Modifier
            .size(28.dp)
            .testTag("font_size_toggle")
        ) {
          Icon(
            imageVector = Icons.Default.FormatSize,
            contentDescription = "Font Size",
            tint = if (readerTheme == ReaderTheme.LIGHT) Color.DarkGray else TextSecondary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }

    // Reading Scrollable Area with Watermark Overlay
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(12.dp)
    ) {
      // Dynamic semi-transparent watermark overlay
      WatermarkOverlay(userEmail = userEmail)

      // Reading Text Content
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(scrollState)
      ) {
        Text(
          text = chapter.title,
          fontSize = (fontSizeSp + 4).sp,
          fontWeight = FontWeight.Bold,
          color = if (readerTheme == ReaderTheme.LIGHT) Color(0xFF2C1A4A) else PurpleAccent,
          modifier = Modifier.padding(bottom = 2.dp)
        )

        Text(
          text = chapter.subtitle,
          fontSize = (fontSizeSp - 2).sp,
          fontWeight = FontWeight.Medium,
          color = if (readerTheme == ReaderTheme.LIGHT) Color.Gray else TextMuted,
          modifier = Modifier.padding(bottom = 8.dp)
        )

        // Dynamic PYQ & UPSC Masterbank Badges
        val chapterHash = remember(chapter.id) { kotlin.math.abs(chapter.id.hashCode()) }
        val pYear = listOf("2019", "2021", "2023", "2024")[chapterHash % 4]

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          PyqBadge(tag = "PYQ $pYear", color = PurpleAccent)
          PyqBadge(tag = "UPSC Quiz", color = Color(0xFF38BDF8))
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(if (readerTheme == ReaderTheme.LIGHT) Color.LightGray else DarkBorder)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (isLocked) {
          // Paywall Overlay
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 16.dp)
              .testTag("paywall_overlay_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkContainer),
            border = CardDefaults.outlinedCardBorder().copy(
              brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFF8A65))
            )
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .size(52.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF3E2723)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Lock,
                  contentDescription = "Locked",
                  tint = Color(0xFFFF8A65),
                  modifier = Modifier.size(28.dp)
                )
              }

              Spacer(modifier = Modifier.height(16.dp))

              Text(
                text = "🔒 Pro Pass Required",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF8A65),
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = "Chapter 3 onwards across all books (Laxmikanth, Spectrum, NCERTs, Nitin Singhania) are locked under the Free Tier. Upgrade to PrepCivil Pro Pass to unlock full access.",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
              )

              Spacer(modifier = Modifier.height(20.dp))

              Button(
                onClick = { onOpenUpgradeModal?.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(46.dp)
                  .testTag("paywall_unlock_button")
              ) {
                Text(
                  text = "Unlock Pro Pass — ₹299/mo",
                  color = DarkPurpleText,
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                )
              }
            }
          }

          // Render only first paragraph as preview
          if (chapter.textContent.isNotEmpty()) {
            Text(
              text = chapter.textContent.first() + " ...",
              fontSize = fontSizeSp.sp,
              lineHeight = (fontSizeSp * 1.5).sp,
              color = readerTheme.textColor.copy(alpha = 0.5f),
              modifier = Modifier.padding(bottom = 10.dp)
            )
          }
        } else {
          chapter.textContent.forEach { paragraph ->
            Text(
              text = paragraph,
              fontSize = fontSizeSp.sp,
              lineHeight = (fontSizeSp * 1.5).sp,
              color = readerTheme.textColor,
              fontWeight = if (paragraph.startsWith("Key Elements:") || paragraph.startsWith("Categorization") || paragraph.startsWith("Core Aggregate Metrics:")) FontWeight.Bold else FontWeight.Normal,
              modifier = Modifier.padding(bottom = 10.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(60.dp))
      }

      // Floating 'Notes / Canvas' toggle button
      if (onToggleCanvas != null) {
        Row(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(PurpleAccent)
            .clickable { onToggleCanvas() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .testTag("canvas_toggle_button"),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Brush,
            contentDescription = "Toggle Canvas",
            tint = DarkPurpleText,
            modifier = Modifier.size(16.dp)
          )
          Text(
            text = if (isCanvasOpen) "Hide Canvas" else "Notes / Canvas",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = DarkPurpleText
          )
        }
      }
    }
  }
}

@Composable
fun PyqBadge(
  tag: String,
  color: Color = PurpleAccent
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    modifier = Modifier
      .clip(RoundedCornerShape(6.dp))
      .background(color.copy(alpha = 0.15f))
      .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
      .padding(horizontal = 8.dp, vertical = 3.dp)
      .testTag("pyq_badge")
  ) {
    Icon(
      imageVector = Icons.Default.LocalOffer,
      contentDescription = "PYQ Tag",
      tint = color,
      modifier = Modifier.size(10.dp)
    )
    Text(
      text = tag,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      color = color
    )
  }
}

