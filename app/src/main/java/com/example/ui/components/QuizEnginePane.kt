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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.model.QuizQuestion
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
fun QuizEnginePane(
  chapterTitle: String,
  quizQuestions: List<QuizQuestion>,
  onQuizCompleted: (Int, Int) -> Unit
) {
  val selectedAnswers = remember(quizQuestions) { mutableStateMapOf<Int, Int>() }
  var isSubmitted by remember(quizQuestions) { mutableStateOf(false) }
  var score by remember(quizQuestions) { mutableStateOf(0) }
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .verticalScroll(scrollState)
  ) {
    // Quiz Title Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Quiz,
          contentDescription = "Quiz",
          tint = PurpleAccent,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = "PrepCivil Quiz Engine",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleAccent
          )
          Text(
            text = chapterTitle,
            fontSize = 12.sp,
            color = TextMuted
          )
        }
      }

      if (isSubmitted) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(DarkPurpleText)
            .border(1.dp, PurpleAccent, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Score: $score / ${quizQuestions.size}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleAccent
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // 3 Questions List
    quizQuestions.forEachIndexed { qIndex, question ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkContainer),
        shape = RoundedCornerShape(14.dp),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(
            if (isSubmitted) {
              val isCorrect = selectedAnswers[qIndex] == question.correctAnswerIndex
              if (isCorrect) StatusGreen else ErrorRed
            } else DarkBorder
          )
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Question Source Badge (PYQs vs UPSC Quiz)
          val isPyqQuestion = qIndex % 2 == 0
          val badgeText = if (isPyqQuestion) {
            when (qIndex % 8) {
              0 -> "PYQ 2019"
              2 -> "PYQ 2020"
              4 -> "PYQ 2022"
              6 -> "PYQ 2023"
              else -> "PYQ 2024"
            }
          } else {
            "UPSC Quiz"
          }

          val badgeBgColor = if (isPyqQuestion) PurpleAccent.copy(alpha = 0.12f) else Color(0x260284C7)
          val badgeBorderColor = if (isPyqQuestion) PurpleAccent.copy(alpha = 0.4f) else Color(0x660284C7)
          val badgeTextColor = if (isPyqQuestion) PurpleAccent else Color(0xFF38BDF8)

          Row(
            modifier = Modifier
              .padding(bottom = 8.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(badgeBgColor)
              .border(1.dp, badgeBorderColor, RoundedCornerShape(6.dp))
              .padding(horizontal = 8.dp, vertical = 3.dp)
              .testTag("quiz_pyq_badge_$qIndex"),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = badgeText,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = badgeTextColor
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            Text(
              text = "Q${qIndex + 1}. ${question.questionText}",
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary,
              modifier = Modifier.weight(1f)
            )

            if (isSubmitted) {
              val isCorrect = selectedAnswers[qIndex] == question.correctAnswerIndex
              Icon(
                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                contentDescription = if (isCorrect) "Correct" else "Incorrect",
                tint = if (isCorrect) StatusGreen else ErrorRed,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Options
          question.options.forEachIndexed { optIndex, optionText ->
            val isSelected = selectedAnswers[qIndex] == optIndex
            val isCorrectOption = optionText.startsWith(
              when(question.correctAnswerIndex) {
                0 -> "A"
                1 -> "B"
                2 -> "C"
                else -> "D"
              }
            ) || optIndex == question.correctAnswerIndex

            val optionBg = when {
              isSubmitted && isCorrectOption -> StatusGreen.copy(alpha = 0.2f)
              isSubmitted && isSelected && !isCorrectOption -> ErrorRed.copy(alpha = 0.2f)
              isSelected -> DarkPurpleText
              else -> Color.Transparent
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(optionBg)
                .border(
                  1.dp,
                  if (isSelected) PurpleAccent else DarkBorder,
                  RoundedCornerShape(8.dp)
                )
                .clickable(enabled = !isSubmitted) {
                  selectedAnswers[qIndex] = optIndex
                }
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .testTag("q_${qIndex}_opt_$optIndex")
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                  selected = isSelected,
                  onClick = if (!isSubmitted) { { selectedAnswers[qIndex] = optIndex } } else null,
                  colors = RadioButtonDefaults.colors(
                    selectedColor = PurpleAccent,
                    unselectedColor = TextMuted
                  )
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = optionText,
                  fontSize = 13.sp,
                  color = if (isSelected || (isSubmitted && isCorrectOption)) TextPrimary else TextSecondary,
                  fontWeight = if (isSelected || (isSubmitted && isCorrectOption)) FontWeight.SemiBold else FontWeight.Normal
                )
              }
            }
          }

          // Explanation when submitted
          if (isSubmitted) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
            ) {
              Text(
                text = "Explanation: ${question.explanation}",
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp
              )
            }
          }
        }
      }
    }

    // Submit / Retry Action
    if (!isSubmitted) {
      Button(
        onClick = {
          var calculatedScore = 0
          quizQuestions.forEachIndexed { idx, q ->
            if (selectedAnswers[idx] == q.correctAnswerIndex) {
              calculatedScore++
            }
          }
          score = calculatedScore
          isSubmitted = true
          onQuizCompleted(calculatedScore, quizQuestions.size)
        },
        enabled = selectedAnswers.size == quizQuestions.size,
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("submit_quiz_button"),
        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text(
          text = if (selectedAnswers.size == quizQuestions.size) "Submit Quiz" else "Answer All 3 Questions (${selectedAnswers.size}/3)",
          color = DarkPurpleText,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }
    } else {
      Button(
        onClick = {
          selectedAnswers.clear()
          isSubmitted = false
          score = 0
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("retry_quiz_button"),
        colors = ButtonDefaults.buttonColors(containerColor = DarkContainer),
        shape = RoundedCornerShape(12.dp)
      ) {
        Text(
          text = "Try Quiz Again",
          color = PurpleAccent,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
