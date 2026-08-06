package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CardRating
import com.example.model.Flashcard
import com.example.model.SrsStage
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun FlashcardsPane(
  flashcards: List<Flashcard>,
  subjectName: String,
  onRatingUpdated: (CardRating) -> Unit
) {
  var currentIndex by remember(flashcards) { mutableIntStateOf(0) }
  var isFlipped by remember(currentIndex) { mutableStateOf(false) }
  var reviewCountToday by remember { mutableIntStateOf(0) }

  val scrollState = rememberScrollState()

  val rotation by animateFloatAsState(
    targetValue = if (isFlipped) 180f else 0f,
    animationSpec = tween(durationMillis = 350),
    label = "cardFlipAnimation"
  )

  if (flashcards.isEmpty()) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "No flashcards available for $subjectName.",
        color = TextSecondary,
        fontSize = 14.sp
      )
    }
    return
  }

  // Calculate live SRS Metrics across current deck
  val dueCount = flashcards.count { it.stage == SrsStage.DUE || it.stage == SrsStage.NEW }
  val learningCount = flashcards.count { it.stage == SrsStage.LEARNING }
  val masteredCount = flashcards.count { it.stage == SrsStage.MASTERED }

  val isCompleted = currentIndex >= flashcards.size

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    WatermarkOverlay(userEmail = "sabhishek607@gmail.com")

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(scrollState),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
    // Header Info
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "$subjectName SRS Deck",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = PurpleAccent
        )
        Text(
          text = if (!isCompleted) "Card ${currentIndex + 1} of ${flashcards.size}" else "Deck Completed!",
          fontSize = 12.sp,
          color = TextMuted
        )
      }

      IconButton(
        onClick = {
          currentIndex = 0
          isFlipped = false
        },
        modifier = Modifier
          .size(32.dp)
          .testTag("reset_deck_button")
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = "Restart Deck",
          tint = PurpleAccent
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 1. SRS DASHBOARD & METRICS COUNTERS
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("srs_metrics_row"),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      MetricCard(
        title = "Due Today",
        count = dueCount,
        color = Color(0xFFFFB74D),
        icon = Icons.Default.Schedule,
        modifier = Modifier.weight(1f).testTag("cards_due_today_counter")
      )
      MetricCard(
        title = "Learning",
        count = learningCount,
        color = Color(0xFFBA68C8),
        icon = Icons.Default.Psychology,
        modifier = Modifier.weight(1f).testTag("learning_counter")
      )
      MetricCard(
        title = "Mastered",
        count = masteredCount,
        color = StatusGreen,
        icon = Icons.Default.Verified,
        modifier = Modifier.weight(1f).testTag("mastered_counter")
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (!isCompleted) {
      val currentCard = flashcards[currentIndex]

      // 2. FLASHCARD FLIP CONTAINER
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .height(290.dp)
          .graphicsLayer {
            rotationY = rotation
            cameraDistance = 12 * density
          }
          .clickable { isFlipped = !isFlipped }
          .testTag("flashcard_flip_area"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkContainer),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(
            when (currentCard.stage) {
              SrsStage.MASTERED -> StatusGreen
              SrsStage.LEARNING -> Color(0xFFBA68C8)
              else -> DarkBorder
            }
          )
        )
      ) {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
          contentAlignment = Alignment.Center
        ) {
          if (rotation <= 90f) {
            // FRONT SIDE: Question & SRS Stage Badge
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxSize()
            ) {
              // Top Badges
              val isPyqCard = currentIndex % 2 == 0
              val flashcardSourceTag = remember(currentIndex) {
                if (isPyqCard) {
                  when (currentIndex % 6) {
                    0 -> "PYQ 2019"
                    2 -> "PYQ 2021"
                    4 -> "PYQ 2023"
                    else -> "PYQ 2024"
                  }
                } else {
                  "UPSC Quiz"
                }
              }

              val cardTagBg = if (isPyqCard) Color(0xFF263238) else Color(0x260284C7)
              val cardTagBorder = if (isPyqCard) Color(0xFF80CBC4) else Color(0x660284C7)
              val cardTagTextColor = if (isPyqCard) Color(0xFF80CBC4) else Color(0xFF38BDF8)

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.weight(1f)
                ) {
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(DarkPurpleText)
                      .padding(horizontal = 8.dp, vertical = 3.dp)
                  ) {
                    Text(
                      text = currentCard.category,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = PurpleAccent,
                      maxLines = 1
                    )
                  }

                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(6.dp))
                      .background(cardTagBg)
                      .border(1.dp, cardTagBorder, RoundedCornerShape(6.dp))
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Text(
                      text = flashcardSourceTag,
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold,
                      color = cardTagTextColor
                    )
                  }
                }

                // SRS Stage Badge
                val (badgeLabel, badgeBg, badgeColor) = when (currentCard.stage) {
                  SrsStage.MASTERED -> Triple("Mastered • Next ${currentCard.nextReviewDays}d", Color(0xFF1B5E20), StatusGreen)
                  SrsStage.LEARNING -> Triple("Learning • Next ${currentCard.nextReviewDays}d", Color(0xFF4A148C), Color(0xFFE1BEE7))
                  SrsStage.DUE -> Triple("Due Today", Color(0xFFE65100), Color(0xFFFFCC80))
                  SrsStage.NEW -> Triple("New Card", Color(0xFF0D47A1), Color(0xFF90CAF9))
                }

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(badgeBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
                    .testTag("card_srs_badge")
                ) {
                  Text(
                    text = badgeLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeColor
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              // Question Text
              Text(
                text = currentCard.question,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
              )

              Spacer(modifier = Modifier.height(10.dp))

              // Tap hint
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Flip,
                  contentDescription = "Flip",
                  tint = TextMuted,
                  modifier = Modifier.size(14.dp)
                )
                Text(
                  text = "Tap card to reveal answer",
                  fontSize = 11.sp,
                  color = TextMuted
                )
              }
            }
          } else {
            // BACK SIDE: Answer & Explanation
            Column(
              modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { rotationY = 180f },
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "ANSWER & RECALL",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = StatusGreen
              )

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = currentCard.answer,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
              )

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = "Select difficulty rating below to schedule next review",
                fontSize = 11.sp,
                color = TextMuted
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 3. ACTION BUTTONS WITH INTERVAL SCHEDULING LOGIC
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = if (isFlipped) "Schedule Next SRS Review:" else "Tap Card or Rate Recall Difficulty:",
          fontSize = 12.sp,
          color = TextMuted,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // HARD BUTTON (+1 Day)
          Button(
            onClick = {
              currentCard.userRating = CardRating.HARD
              currentCard.nextReviewDays = 1
              currentCard.stage = SrsStage.LEARNING
              onRatingUpdated(CardRating.HARD)
              reviewCountToday++
              isFlipped = false
              currentIndex++
            },
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("reaction_hard"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E2723)),
            shape = RoundedCornerShape(12.dp)
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.SentimentDissatisfied,
                  contentDescription = "Hard",
                  tint = ErrorRed,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Hard", color = ErrorRed, fontSize = 13.sp, fontWeight = FontWeight.Bold)
              }
              Text(text = "Review 1d", color = ErrorRed.copy(alpha = 0.8f), fontSize = 10.sp)
            }
          }

          // GOOD BUTTON (+3 Days)
          Button(
            onClick = {
              currentCard.userRating = CardRating.GOOD
              currentCard.nextReviewDays = 3
              currentCard.stage = SrsStage.LEARNING
              onRatingUpdated(CardRating.GOOD)
              reviewCountToday++
              isFlipped = false
              currentIndex++
            },
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("reaction_good"),
            colors = ButtonDefaults.buttonColors(containerColor = DarkContainer),
            shape = RoundedCornerShape(12.dp)
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.SentimentNeutral,
                  contentDescription = "Good",
                  tint = PurpleAccent,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Good", color = PurpleAccent, fontSize = 13.sp, fontWeight = FontWeight.Bold)
              }
              Text(text = "Review 3d", color = PurpleAccent.copy(alpha = 0.8f), fontSize = 10.sp)
            }
          }

          // EASY BUTTON (+7 Days)
          Button(
            onClick = {
              currentCard.userRating = CardRating.EASY
              currentCard.nextReviewDays = 7
              currentCard.stage = SrsStage.MASTERED
              onRatingUpdated(CardRating.EASY)
              reviewCountToday++
              isFlipped = false
              currentIndex++
            },
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("reaction_easy"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
            shape = RoundedCornerShape(12.dp)
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.SentimentSatisfiedAlt,
                  contentDescription = "Easy",
                  tint = StatusGreen,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Easy", color = StatusGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
              }
              Text(text = "Review 7d", color = StatusGreen.copy(alpha = 0.8f), fontSize = 10.sp)
            }
          }
        }
      }
    } else {
      // 4. SRS DECK SUCCESS / COMPLETION SCREEN
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp)
          .testTag("srs_completion_card"),
        colors = CardDefaults.cardColors(containerColor = DarkContainer),
        shape = RoundedCornerShape(20.dp),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(PurpleAccent)
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
              .size(56.dp)
              .clip(CircleShape)
              .background(DarkPurpleText),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = "Completion",
              tint = PurpleAccent,
              modifier = Modifier.size(32.dp)
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "🎉 All Due Cards Reviewed!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleAccent
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Great job! You reviewed $reviewCountToday card${if (reviewCountToday > 1) "s" else ""} today in $subjectName.",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(20.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "$learningCount", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFBA68C8))
              Text(text = "Learning", fontSize = 11.sp, color = TextMuted)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "$masteredCount", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = StatusGreen)
              Text(text = "Mastered", fontSize = 11.sp, color = TextMuted)
            }
          }

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = {
              currentIndex = 0
              isFlipped = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("restart_srs_deck_button")
          ) {
            Text(
              text = "Review Deck Again",
              color = DarkPurpleText,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
}

@Composable
fun MetricCard(
  title: String,
  count: Int,
  color: Color,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = DarkContainer),
    border = CardDefaults.outlinedCardBorder().copy(
      brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
    )
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      horizontalAlignment = Alignment.Start
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = color,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = "$count",
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          color = color
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = TextMuted
      )
    }
  }
}
