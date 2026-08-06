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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Chapter
import com.example.model.Flashcard
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
fun AdminPane(
  onPublishChapter: (subjectName: String, chapter: Chapter) -> Unit,
  isAdminAuthenticated: Boolean = true,
  onLogoutAdmin: () -> Unit = {},
  onReturnToDashboard: () -> Unit = {}
) {
  if (!isAdminAuthenticated) {
    // Access Denied Screen for Unauthorized Non-Admin Users
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("access_denied_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkContainer),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(ErrorRed)
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(28.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(56.dp)
              .clip(CircleShape)
              .background(ErrorRed.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.AdminPanelSettings,
              contentDescription = "Access Denied",
              tint = ErrorRed,
              modifier = Modifier.size(32.dp)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = "Access Denied",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ErrorRed,
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Restricted to Authorized Administrators. Non-admin users cannot access AI content generation and publishing tools.",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(24.dp))

          Button(
            onClick = onReturnToDashboard,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("return_to_dashboard_button")
          ) {
            Text(
              text = "Return to Main Dashboard",
              color = DarkPurpleText,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    }
    return
  }

  var bookSource by remember { mutableStateOf("Laxmikanth Indian Polity (7th Ed)") }
  var subjectName by remember { mutableStateOf("Indian Polity & Governance") }
  var chapterTitle by remember { mutableStateOf("Chapter 3: Preamble of the Constitution") }
  var isPremium by remember { mutableStateOf(false) }

  var isGenerating by remember { mutableStateOf(false) }
  var generatedChapter by remember { mutableStateOf<Chapter?>(null) }
  var activePreviewTab by remember { mutableIntStateOf(0) }
  var publishSuccessMessage by remember { mutableStateOf<String?>(null) }

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Admin Header
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("admin_header_card"),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = DarkContainer),
      border = CardDefaults.outlinedCardBorder().copy(
        brush = androidx.compose.ui.graphics.SolidColor(PurpleAccent)
      )
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PurpleAccent),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = "Admin Panel",
                tint = DarkPurpleText,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "AI Course Content Generator",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleAccent
              )
              Text(
                text = "Role: Authorized Administrator",
                fontSize = 11.sp,
                color = StatusGreen,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Generate structured notes, UPSC statement MCQs, and SRS flashcards directly from standard reference books using AI models.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 16.sp
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(horizontalAlignment = Alignment.End) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFF1B5E20))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "AI ENGINE ACTIVE",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = StatusGreen
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Button(
            onClick = onLogoutAdmin,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E2723)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.testTag("lock_admin_session_button")
          ) {
            Text(
              text = "Lock Admin",
              color = Color(0xFFFF8A65),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // Success Notification Dialog/Banner
    publishSuccessMessage?.let { msg ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("publish_success_banner"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(StatusGreen)
        )
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = StatusGreen,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = msg,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }

    // 2. Input Fields Section
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("admin_input_card"),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = DarkContainer),
      border = CardDefaults.outlinedCardBorder().copy(
        brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
      )
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "Source Book & Chapter Configuration",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )

        // Book Source Name
        OutlinedTextField(
          value = bookSource,
          onValueChange = { bookSource = it },
          label = { Text("Standard Book / Source Name") },
          placeholder = { Text("e.g. Laxmikanth Polity, Ramesh Singh Economy") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_book_source"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurpleAccent,
            unfocusedBorderColor = DarkBorder,
            focusedLabelColor = PurpleAccent,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          ),
          singleLine = true
        )

        // Subject Name
        OutlinedTextField(
          value = subjectName,
          onValueChange = { subjectName = it },
          label = { Text("Subject Name") },
          placeholder = { Text("e.g. Indian Polity & Governance") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_subject_name"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurpleAccent,
            unfocusedBorderColor = DarkBorder,
            focusedLabelColor = PurpleAccent,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          ),
          singleLine = true
        )

        // Chapter Title
        OutlinedTextField(
          value = chapterTitle,
          onValueChange = { chapterTitle = it },
          label = { Text("Chapter Title / Topic") },
          placeholder = { Text("e.g. Chapter 3: Preamble of the Constitution") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_chapter_title"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurpleAccent,
            unfocusedBorderColor = DarkBorder,
            focusedLabelColor = PurpleAccent,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          ),
          singleLine = true
        )

        // Premium Chapter Checkbox
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { isPremium = !isPremium }
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          androidx.compose.material3.Checkbox(
            checked = isPremium,
            onCheckedChange = { isPremium = it },
            colors = androidx.compose.material3.CheckboxDefaults.colors(
              checkedColor = PurpleAccent,
              uncheckedColor = TextMuted
            ),
            modifier = Modifier.testTag("input_premium_checkbox")
          )
          Spacer(modifier = Modifier.width(6.dp))
          Column {
            Text(
              text = "Set as Premium Chapter (Pro Pass Required)",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = TextPrimary
            )
            Text(
              text = "Free users will see a lock icon & upgrade trigger.",
              fontSize = 10.sp,
              color = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Action Buttons Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = {
              if (bookSource.isNotBlank() && chapterTitle.isNotBlank()) {
                isGenerating = true
                publishSuccessMessage = null
                generatedChapter = generateAiContent(bookSource, subjectName, chapterTitle, isPremium)
                isGenerating = false
              }
            },
            modifier = Modifier
              .weight(1f)
              .height(46.dp)
              .testTag("generate_all_button"),
            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
            shape = RoundedCornerShape(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "Generate AI",
                tint = DarkPurpleText,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "⚡ Generate AI Content",
                color = DarkPurpleText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // Loading State
    if (isGenerating) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkContainer)
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          CircularProgressIndicator(color = PurpleAccent, modifier = Modifier.size(36.dp))
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "AI is parsing source materials and generating notes, MCQs, and flashcards...",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
          )
        }
      }
    }

    // 3. Content Preview Window & Publish
    generatedChapter?.let { ch ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("admin_preview_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkContainer),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(PurpleAccent)
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Generated Content Preview",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleAccent
              )
              Text(
                text = "${ch.textContent.size} Sections • ${ch.quizQuestions.size} MCQs • ${ch.flashcards.size} Flashcards",
                fontSize = 11.sp,
                color = TextMuted
              )
            }

            Button(
              onClick = {
                onPublishChapter(subjectName, ch)
                publishSuccessMessage = "🎉 Successfully published '${ch.title}' under '$subjectName'! Students can now access this chapter across all tools."
              },
              colors = ButtonDefaults.buttonColors(containerColor = StatusGreen),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("publish_to_app_button")
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Publish,
                  contentDescription = "Publish",
                  tint = Color.White,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Publish to App",
                  color = Color.White,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Preview Sub-Tabs
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(MaterialTheme.colorScheme.background)
              .padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            val tabs = listOf("Notes (${ch.textContent.size})", "MCQs (${ch.quizQuestions.size})", "Flashcards (${ch.flashcards.size})")
            tabs.forEachIndexed { index, title ->
              val isSelected = activePreviewTab == index
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) PurpleAccent else Color.Transparent)
                  .clickable { activePreviewTab = index }
                  .padding(vertical = 6.dp)
                  .testTag("preview_tab_$index"),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = title,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) DarkPurpleText else TextSecondary
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Content Tab Panels
          when (activePreviewTab) {
            0 -> {
              // Notes Preview
              Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.testTag("preview_notes_section")
              ) {
                ch.textContent.forEach { paragraph ->
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(8.dp))
                      .background(MaterialTheme.colorScheme.background)
                      .padding(10.dp)
                  ) {
                    Text(
                      text = paragraph,
                      fontSize = 12.sp,
                      color = TextPrimary,
                      lineHeight = 17.sp
                    )
                  }
                }
              }
            }
            1 -> {
              // MCQs Preview
              Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.testTag("preview_mcq_section")
              ) {
                ch.quizQuestions.forEachIndexed { qIdx, question ->
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(8.dp))
                      .background(MaterialTheme.colorScheme.background)
                      .padding(12.dp)
                  ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                      Text(
                        text = "Q${qIdx + 1}. ${question.questionText}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                      )
                      question.options.forEachIndexed { optIdx, option ->
                        val isCorrect = optIdx == question.correctAnswerIndex
                        Text(
                          text = option,
                          fontSize = 11.sp,
                          color = if (isCorrect) StatusGreen else TextSecondary,
                          fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Normal
                        )
                      }
                      Text(
                        text = "Explanation: ${question.explanation}",
                        fontSize = 10.sp,
                        color = TextMuted,
                        lineHeight = 14.sp
                      )
                    }
                  }
                }
              }
            }
            2 -> {
              // Flashcards Preview
              Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.testTag("preview_flashcards_section")
              ) {
                ch.flashcards.forEachIndexed { fIdx, flashcard ->
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clip(RoundedCornerShape(8.dp))
                      .background(MaterialTheme.colorScheme.background)
                      .padding(12.dp)
                  ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                      Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                      ) {
                        Text(
                          text = "Card #${fIdx + 1}",
                          fontSize = 10.sp,
                          fontWeight = FontWeight.Bold,
                          color = PurpleAccent
                        )
                        Text(
                          text = flashcard.category,
                          fontSize = 10.sp,
                          color = TextMuted
                        )
                      }
                      Text(
                        text = "Q: ${flashcard.question}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                      )
                      Text(
                        text = "A: ${flashcard.answer}",
                        fontSize = 11.sp,
                        color = StatusGreen
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

private fun generateAiContent(
  sourceBook: String,
  subject: String,
  title: String,
  isPremium: Boolean
): Chapter {
  val cleanId = title.lowercase().replace(Regex("[^a-z0-9]"), "_")

  val notes = listOf(
    "Synthesized from $sourceBook for Civil Services Examination.",
    "Core Objectives & Key Constitutional Directives:",
    "1. Declaratory Purpose: Sets out the preamble philosophy, declaring India as a 'Sovereign Socialist Secular Democratic Republic'.",
    "2. Source of Authority: Begins with 'We, The People of India', establishing popular sovereignty.",
    "3. Key Objectives: Justice (Social, Economic, Political), Liberty (Thought, Expression, Belief, Faith, Worship), Equality (Status and Opportunity), and Fraternity (Assuring dignity of the individual and unity/integrity of the Nation).",
    "4. Historical Landmark: Based on the 'Objective Resolution' drafted and moved by Pandit Jawaharlal Nehru on December 13, 1946, and adopted by the Constituent Assembly on January 22, 1947.",
    "5. Amendability (Article 368): Amended only once by the 42nd Constitutional Amendment Act, 1976, which added three new words: 'Socialist', 'Secular', and 'Integrity'."
  )

  val flashcards = listOf(
    Flashcard(
      id = "f_gen_${cleanId}_1",
      question = "When was the Preamble amended and what words were added?",
      answer = "42nd Amendment Act (1976). Added 'Socialist', 'Secular', and 'Integrity'.",
      category = "Constitutional History"
    ),
    Flashcard(
      id = "f_gen_${cleanId}_2",
      question = "Is the Preamble a part of the Constitution according to the Supreme Court?",
      answer = "Yes, held in Kesavananda Bharati case (1973), reversing the Berubari Union (1960) stance.",
      category = "Judicial Precedents"
    )
  )

  val mcqs = listOf(
    QuizQuestion(
      id = "q_gen_${cleanId}_1",
      questionText = "Consider the following statements regarding the Preamble to the Constitution of India:\n1. It is directly enforceable in a court of law.\n2. It was amended by the 42nd Constitutional Amendment Act 1976.\n3. It was based on the Objective Resolution moved by Jawaharlal Nehru.\nWhich of the statements given above are correct?",
      options = listOf(
        "A. 1 and 2 only",
        "B. 2 and 3 only",
        "C. 1 and 3 only",
        "D. 1, 2 and 3"
      ),
      correctAnswerIndex = 1,
      explanation = "Statement 1 is incorrect: The Preamble is non-justiciable and non-enforceable in courts of law. Statements 2 and 3 are correct."
    ),
    QuizQuestion(
      id = "q_gen_${cleanId}_2",
      questionText = "Which one of the following objectives is NOT embodied in the Preamble to the Constitution of India?",
      options = listOf(
        "A. Liberty of thought",
        "B. Economic liberty",
        "C. Liberty of expression",
        "D. Liberty of belief"
      ),
      correctAnswerIndex = 1,
      explanation = "The Preamble secures 'Economic Justice', whereas Liberty is granted for 'Thought, Expression, Belief, Faith and Worship'. 'Economic Liberty' is not explicitly stated."
    )
  )

  return Chapter(
    id = "ch_gen_$cleanId",
    title = title,
    subtitle = "Derived from $sourceBook",
    textContent = notes,
    flashcards = flashcards,
    quizQuestions = mcqs,
    isPremium = isPremium
  )
}
