package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Chapter
import com.example.model.Subject
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class AppTab(val label: String) {
  DASHBOARD("Dashboard"),
  WORKSPACE("Workspace"),
  FLASHCARDS("Flashcards"),
  QUIZ("Quiz"),
  MAINS_AI("Mains AI"),
  STORE("Store"),
  ADMIN("Admin")
}

@Composable
fun TopNavBar(
  activeTab: AppTab,
  onTabSelected: (AppTab) -> Unit,
  isDarkMode: Boolean,
  onToggleDarkMode: () -> Unit,
  isSubscribed: Boolean = false
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.background)
      .border(1.dp, DarkBorder)
      .padding(horizontal = 12.dp, vertical = 8.dp)
  ) {
    // Top Row: App Title + Actions
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Monogram Icon Glow Box
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0x26818CF8))
            .border(1.dp, Color(0x66818CF8), RoundedCornerShape(8.dp)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "PC",
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            style = TextStyle(
              brush = Brush.horizontalGradient(
                listOf(Color(0xFFFFFFFF), Color(0xFF818CF8))
              )
            )
          )
        }

        // Monogram Icon + Title Row
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
            fontSize = 19.sp,
            modifier = Modifier.testTag("app_title")
          )

          if (isSubscribed) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(
                  Brush.horizontalGradient(
                    listOf(Color(0xFFFBBF24), Color(0xFFEAB308))
                  )
                )
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "PRO",
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A)
              )
            }
          }
        }
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Dark / Light toggle
        IconButton(
          onClick = onToggleDarkMode,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(DarkContainer)
            .testTag("dark_mode_toggle")
        ) {
          Icon(
            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = "Toggle Theme",
            tint = PurpleAccent,
            modifier = Modifier.size(20.dp)
          )
        }

        // Profile Avatar
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(PurpleAccent),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "JD",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = DarkPurpleText
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Tab Switcher Responsive Layout
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(DarkContainer)
        .padding(3.dp)
    ) {
      val isWideScreen = maxWidth >= 600.dp

      if (isWideScreen) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          AppTab.entries.forEach { tab ->
            val selected = tab == activeTab
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selected) Color(0xFF2E2A62) else Color.Transparent)
                .border(
                  width = if (selected) 1.dp else 0.dp,
                  color = if (selected) Color(0xFF818CF8) else Color.Transparent,
                  shape = RoundedCornerShape(10.dp)
                )
                .clickable { onTabSelected(tab) }
                .padding(vertical = 7.dp)
                .testTag("tab_${tab.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = tab.label,
                fontSize = 12.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
                color = if (selected) Color(0xFFE0E7FF) else TextSecondary,
                maxLines = 1
              )
            }
          }
        }
      } else {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          AppTab.entries.forEach { tab ->
            val selected = tab == activeTab
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (selected) Color(0xFF2E2A62) else Color.Transparent)
                .border(
                  width = if (selected) 1.dp else 0.dp,
                  color = if (selected) Color(0xFF818CF8) else Color.Transparent,
                  shape = RoundedCornerShape(10.dp)
                )
                .clickable { onTabSelected(tab) }
                .padding(horizontal = 14.dp, vertical = 7.dp)
                .testTag("tab_${tab.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = tab.label,
                fontSize = 12.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
                color = if (selected) Color(0xFFE0E7FF) else TextSecondary,
                maxLines = 1
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun SubjectChapterSelectorBar(
  selectedSubject: Subject,
  subjects: List<Subject>,
  onSubjectSelected: (Subject) -> Unit,
  selectedChapter: Chapter,
  chapters: List<Chapter>,
  onChapterSelected: (Chapter) -> Unit,
  isProUser: Boolean = false,
  onOpenUpgradeModal: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  var subjectMenuExpanded by remember { mutableStateOf(false) }
  var chapterMenuExpanded by remember { mutableStateOf(false) }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(DarkContainer)
      .padding(horizontal = 12.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    // Subject Dropdown Box
    Box(modifier = Modifier.weight(1f)) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.background)
          .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
          .clickable { subjectMenuExpanded = true }
          .padding(horizontal = 12.dp, vertical = 8.dp)
          .testTag("subject_dropdown"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "Subject", fontSize = 10.sp, color = TextMuted)
          Text(
            text = selectedSubject.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
          )
        }
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Select Subject",
          tint = TextSecondary
        )
      }

      DropdownMenu(
        expanded = subjectMenuExpanded,
        onDismissRequest = { subjectMenuExpanded = false },
        modifier = Modifier.background(DarkContainer)
      ) {
        subjects.forEach { subject ->
          DropdownMenuItem(
            text = {
              Text(
                text = subject.name,
                color = if (subject.id == selectedSubject.id) PurpleAccent else TextPrimary,
                fontWeight = if (subject.id == selectedSubject.id) FontWeight.Bold else FontWeight.Normal
              )
            },
            onClick = {
              onSubjectSelected(subject)
              subjectMenuExpanded = false
            }
          )
        }
      }
    }

    // Chapter Dropdown Box
    Box(modifier = Modifier.weight(1.2f)) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.background)
          .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
          .clickable { chapterMenuExpanded = true }
          .padding(horizontal = 12.dp, vertical = 8.dp)
          .testTag("chapter_dropdown"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "Chapter", fontSize = 10.sp, color = TextMuted)
          Text(
            text = selectedChapter.title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            maxLines = 1
          )
        }
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Select Chapter",
          tint = TextSecondary
        )
      }

      DropdownMenu(
        expanded = chapterMenuExpanded,
        onDismissRequest = { chapterMenuExpanded = false },
        modifier = Modifier.background(DarkContainer)
      ) {
        chapters.forEach { chapter ->
          val isLocked = chapter.isPremium && !isProUser
          DropdownMenuItem(
            text = {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = chapter.title,
                  color = if (chapter.id == selectedChapter.id) PurpleAccent else TextPrimary,
                  fontWeight = if (chapter.id == selectedChapter.id) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 13.sp,
                  modifier = Modifier.weight(1f)
                )
                if (isLocked) {
                  Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Premium Locked",
                    tint = Color(0xFFFF8A65),
                    modifier = Modifier.size(14.dp)
                  )
                } else {
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .background(Color(0xFF1B5E20))
                      .padding(horizontal = 4.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = "FREE",
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold,
                      color = StatusGreen
                    )
                  }
                }
              }
            },
            onClick = {
              chapterMenuExpanded = false
              if (isLocked) {
                onOpenUpgradeModal?.invoke()
              } else {
                onChapterSelected(chapter)
              }
            }
          )
        }
      }
    }
  }
}
