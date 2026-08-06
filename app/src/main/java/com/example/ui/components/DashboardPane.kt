package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Subject
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DashboardPane(
  masteryPercentage: Int,
  subjects: List<Subject>,
  onSelectSubject: (Subject) -> Unit,
  onStartSrsReview: () -> Unit,
  isProUser: Boolean = false,
  onOpenUpgradeModal: (() -> Unit)? = null
) {
  val scrollState = rememberScrollState()
  val srsDueCount = subjects.flatMap { it.chapters }.flatMap { it.flashcards }
    .count { it.stage == com.example.model.SrsStage.DUE || it.stage == com.example.model.SrsStage.NEW }

  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val isTablet = maxWidth >= 650.dp

    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(if (isTablet) 24.dp else 16.dp)
        .verticalScroll(scrollState),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 0. Compact Free Plan / Pro Banner
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("dashboard_plan_banner"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isProUser) Color(0xFF1B5E20) else DarkContainer
        ),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(if (isProUser) StatusGreen else PurpleAccent)
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = if (isProUser) Icons.Default.CheckCircle else Icons.Default.AutoAwesome,
              contentDescription = "Plan Banner",
              tint = if (isProUser) StatusGreen else PurpleAccent,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = if (isProUser) "Pro Pass Active" else "Free Plan",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = if (isProUser) "Full access to all modules & SRS" else "Upgrade to Pro for full syllabus & unlimited flashcards",
                fontSize = 10.sp,
                color = TextSecondary,
                maxLines = 1
              )
            }
          }

          if (!isProUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Button(
              onClick = { onOpenUpgradeModal?.invoke() },
              colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
              shape = RoundedCornerShape(8.dp),
              contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp),
              modifier = Modifier.height(32.dp).testTag("dashboard_upgrade_button")
            ) {
              Text(
                text = "Upgrade",
                color = DarkPurpleText,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            }
          }
        }
      }

      // Middle Section: Hero Mastery + SRS Review Card (Side-by-side on Tablet, stacked on Mobile)
      if (isTablet) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Box(modifier = Modifier.weight(1.2f)) {
            HeroMasteryCard(masteryPercentage = masteryPercentage, srsDueCount = srsDueCount)
          }
          Box(modifier = Modifier.weight(0.8f)) {
            SrsReviewCard(srsDueCount = srsDueCount, onStartSrsReview = onStartSrsReview)
          }
        }
      } else {
        HeroMasteryCard(masteryPercentage = masteryPercentage, srsDueCount = srsDueCount)
        SrsReviewCard(srsDueCount = srsDueCount, onStartSrsReview = onStartSrsReview)
      }

      // 7-Day Study Consistency Heatmap Widget
      RevisionHeatmapWidget()

      // 3. Subject Completion Cards Header
      Text(
        text = "Syllabus Progress & Subjects",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = PurpleAccent,
        modifier = Modifier.padding(top = 4.dp)
      )

      // Subject Cards Grid (2-column on Tablet, 1-column on Mobile)
      if (isTablet) {
        val rows = subjects.chunked(2)
        rows.forEach { rowSubjects ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            rowSubjects.forEach { subject ->
              Box(modifier = Modifier.weight(1f)) {
                SubjectProgressCard(subject = subject, onSelectSubject = onSelectSubject)
              }
            }
            if (rowSubjects.size == 1) {
              Spacer(modifier = Modifier.weight(1f))
            }
          }
        }
      } else {
        subjects.forEach { subject ->
          SubjectProgressCard(subject = subject, onSelectSubject = onSelectSubject)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun HeroMasteryCard(masteryPercentage: Int, srsDueCount: Int) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("overall_progress_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = DarkContainer),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
    )
  ) {
    Column(
      modifier = Modifier.padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(DarkPurpleText),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = "Overall Progress",
              tint = PurpleAccent,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Overall Syllabus Mastery",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "Target: UPSC Civil Services",
              fontSize = 11.sp,
              color = TextMuted
            )
          }
        }

        // Streak indicator
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF3E2723))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = "Streak",
            tint = Color(0xFFFFAB91),
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "7 Days",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFAB91)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Mastery Level",
          fontSize = 12.sp,
          color = TextSecondary
        )
        Text(
          text = "$masteryPercentage%",
          fontSize = 16.sp,
          fontWeight = FontWeight.Black,
          color = PurpleAccent
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      LinearProgressIndicator(
        progress = { masteryPercentage / 100f },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = PurpleAccent,
        trackColor = MaterialTheme.colorScheme.background
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Quick Stats Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        StatChip(title = "Study Time", value = "18.5 hrs", icon = Icons.Default.Timer)
        StatChip(title = "Flashcards", value = "$srsDueCount Due", icon = Icons.Default.Psychology)
        StatChip(title = "Quiz Acc.", value = "88.4%", icon = Icons.Default.CheckCircle)
      }
    }
  }
}

@Composable
fun SrsReviewCard(srsDueCount: Int, onStartSrsReview: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("srs_flashcards_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = DarkContainer),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = "SRS",
          tint = PurpleAccent,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "SRS Review Queue",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "$srsDueCount Cards ready for spaced-repetition memory review today",
        fontSize = 12.sp,
        color = TextMuted,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = onStartSrsReview,
        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("start_srs_button")
      ) {
        Text(
          text = "Start SRS Review ($srsDueCount)",
          color = DarkPurpleText,
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        )
      }
    }
  }
}

@Composable
fun SubjectProgressCard(subject: Subject, onSelectSubject: (Subject) -> Unit) {
  val (progress, colorAccent) = when (subject.id) {
    "polity" -> Pair(0.72f, PurpleAccent)
    "history" -> Pair(0.55f, Color(0xFF81D4FA))
    "geography" -> Pair(0.65f, Color(0xFF81C784))
    "economy" -> Pair(0.60f, Color(0xFFFFB74D))
    else -> Pair(0.50f, Color(0xFFBA68C8))
  }
  val chapterCount = subject.chapters.size

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelectSubject(subject) }
      .testTag("subject_card_${subject.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkContainer),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorAccent.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Book,
            contentDescription = subject.name,
            tint = colorAccent,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = subject.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "$chapterCount Chapter${if (chapterCount > 1) "s" else ""}",
              fontSize = 11.sp,
              color = TextMuted
            )
            val isPyq = subject.id.hashCode() % 2 == 0
            val badgeTag = if (isPyq) "PYQs" else "UPSC Quiz"
            val badgeBg = if (isPyq) colorAccent.copy(alpha = 0.15f) else Color(0x260284C7)
            val badgeBorder = if (isPyq) colorAccent.copy(alpha = 0.4f) else Color(0x660284C7)
            val badgeTextColor = if (isPyq) colorAccent else Color(0xFF38BDF8)

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(badgeBg)
                .border(1.dp, badgeBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = badgeTag,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = badgeTextColor
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth(0.9f)
              .height(5.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = colorAccent,
            trackColor = MaterialTheme.colorScheme.background
          )
        }
      }

      Column(
        horizontalAlignment = Alignment.End
      ) {
        Text(
          text = "${(progress * 100).toInt()}%",
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = colorAccent
        )
        Spacer(modifier = Modifier.height(2.dp))
        Icon(
          imageVector = Icons.Default.ChevronRight,
          contentDescription = "Open Subject",
          tint = TextMuted,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@Composable
fun StatChip(
  title: String,
  value: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(MaterialTheme.colorScheme.background)
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = title,
      tint = TextMuted,
      modifier = Modifier.size(12.dp)
    )
    Spacer(modifier = Modifier.width(4.dp))
    Column {
      Text(text = title, fontSize = 8.sp, color = TextMuted)
      Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
  }
}

@Composable
fun RevisionHeatmapWidget() {
  val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
  val activityHours = listOf(2.5f, 1.8f, 3.2f, 2.0f, 4.5f, 1.2f, 3.8f)

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("study_heatmap_widget"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = DarkContainer),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
    )
  ) {
    Column(
      modifier = Modifier.padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Heatmap",
            tint = PurpleAccent,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "7-Day Revision Heatmap",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "Daily Revision Consistency Grid",
              fontSize = 11.sp,
              color = TextMuted
            )
          }
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(StatusGreen.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = "Streak",
            tint = StatusGreen,
            modifier = Modifier.size(12.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "100% Active",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = StatusGreen
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 7-Day Grid Heatmap Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        days.forEachIndexed { index, dayLabel ->
          val hours = activityHours[index]
          val boxAlpha = (hours / 4.5f).coerceIn(0.25f, 1.0f)

          Column(
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = dayLabel,
              fontSize = 10.sp,
              fontWeight = FontWeight.Medium,
              color = TextMuted
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PurpleAccent.copy(alpha = boxAlpha))
                .border(
                  1.dp,
                  PurpleAccent.copy(alpha = (boxAlpha + 0.2f).coerceAtMost(1f)),
                  RoundedCornerShape(8.dp)
                )
                .testTag("heatmap_day_$index"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${hours}h",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (boxAlpha > 0.6f) DarkPurpleText else TextPrimary
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.background)
          .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Total This Week: 19.0 hrs • 142 Flashcards Reviewed",
          fontSize = 11.sp,
          color = TextSecondary,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}


