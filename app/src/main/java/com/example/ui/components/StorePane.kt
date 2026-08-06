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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun StorePane(
  isProUser: Boolean,
  subjects: List<Subject>,
  onSelectChapter: (Subject, com.example.model.Chapter) -> Unit,
  onOpenUpgradeModal: () -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(16.dp)
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Store Header & Active Plan Banner
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("store_banner_card"),
      shape = RoundedCornerShape(20.dp),
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
          .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = if (isProUser) Icons.Default.Verified else Icons.Default.WorkspacePremium,
              contentDescription = "Store",
              tint = if (isProUser) StatusGreen else PurpleAccent,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (isProUser) "Pro Pass Active" else "Course Store & Monetization",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = if (isProUser)
              "You have full unlocked access to all subjects, chapters, unlimited SRS flashcards, and canvas tools."
            else
              "Upgrade to Pro Pass to unlock all premium Civil Services courses & unlimited flashcards.",
            fontSize = 12.sp,
            color = TextSecondary,
            lineHeight = 16.sp
          )
        }

        if (!isProUser) {
          Spacer(modifier = Modifier.width(12.dp))
          Button(
            onClick = onOpenUpgradeModal,
            colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("store_upgrade_button")
          ) {
            Text(
              text = "Get Pro",
              color = DarkPurpleText,
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }
      }
    }

    // 2. Course Grid & Chapter Badges
    Text(
      text = "Course Modules & Chapters",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = PurpleAccent
    )

    subjects.forEach { subject ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("course_card_${subject.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkContainer),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
        )
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .background(DarkPurpleText),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Book,
                  contentDescription = subject.name,
                  tint = PurpleAccent,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "${subject.name} Masterclass",
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
                Text(
                  text = "${subject.chapters.size} Syllabus Modules",
                  fontSize = 11.sp,
                  color = TextMuted
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Chapter Items with FREE PREVIEW vs PREMIUM locks
          subject.chapters.forEach { chapter ->
            val isLocked = chapter.isPremium && !isProUser

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .border(
                  1.dp,
                  if (isLocked) DarkBorder else PurpleAccent.copy(alpha = 0.3f),
                  RoundedCornerShape(10.dp)
                )
                .clickable {
                  if (isLocked) {
                    onOpenUpgradeModal()
                  } else {
                    onSelectChapter(subject, chapter)
                  }
                }
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .testTag("store_chapter_${chapter.id}"),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = chapter.title,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = if (isLocked) TextSecondary else TextPrimary
                )
                Text(
                  text = chapter.subtitle,
                  fontSize = 11.sp,
                  color = TextMuted,
                  maxLines = 1
                )
              }

              Spacer(modifier = Modifier.width(8.dp))

              // Badge
              if (isLocked) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF3E2723))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Color(0xFFFF8A65),
                    modifier = Modifier.size(12.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "PREMIUM",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF8A65)
                  )
                }
              } else {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF1B5E20))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = "FREE PREVIEW",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = StatusGreen
                  )
                }
              }
            }
          }
        }
      }
    }

    // 3. Subscription Pricing Tiers
    Text(
      text = "Subscription Pricing Plans",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = PurpleAccent,
      modifier = Modifier.padding(top = 8.dp)
    )

    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Free Tier Card
        Card(
          modifier = Modifier
            .weight(1f)
            .testTag("free_tier_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkContainer),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
          )
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.Start
          ) {
            Text(text = "Free Tier", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = "₹0 / forever", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextMuted)

            Spacer(modifier = Modifier.height(10.dp))

            TierCheck("First 2 chapters unlocked")
            TierCheck("20 SRS Cards / Day")
            TierCheck("Basic Study Workspace")

            Spacer(modifier = Modifier.height(14.dp))

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = if (isProUser) "Basic Plan" else "Current Plan",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
              )
            }
          }
        }

        // Single Subject Access Card
        Card(
          modifier = Modifier
            .weight(1f)
            .testTag("subject_tier_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkContainer),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(DarkBorder)
          )
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.Start
          ) {
            Text(text = "Single Subject", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = "₹399 / lifetime", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF81D4FA))

            Spacer(modifier = Modifier.height(10.dp))

            TierCheck("Unlock 1 Complete Book")
            TierCheck("All chapters in subject")
            TierCheck("Subject Flashcard Deck")

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = onOpenUpgradeModal,
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("subject_tier_button")
            ) {
              Text(
                text = "Buy Subject",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Pro Pass Monthly Tier Card
        Card(
          modifier = Modifier
            .weight(1f)
            .testTag("pro_monthly_tier_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkContainer),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(PurpleAccent)
          )
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.Start
          ) {
            Text(text = "Pro Monthly", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PurpleAccent)
            Text(text = "₹299 / mo", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)

            Spacer(modifier = Modifier.height(10.dp))

            TierCheck("All Subjects & Chapters", highlight = true)
            TierCheck("Unlimited SRS Flashcards", highlight = true)
            TierCheck("Full Canvas & Note Tools", highlight = true)

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = onOpenUpgradeModal,
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isProUser) StatusGreen else PurpleAccent
              ),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("monthly_tier_button")
            ) {
              Text(
                text = if (isProUser) "Pro Active ✓" else "Get Monthly",
                color = if (isProUser) Color.White else DarkPurpleText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        // Pro Pass Annual Tier Card (Best Value)
        Card(
          modifier = Modifier
            .weight(1f)
            .testTag("pro_annual_tier_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkContainer),
          border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(StatusGreen)
          )
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.Start
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = "Pro Annual", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = StatusGreen)
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(StatusGreen)
                  .padding(horizontal = 4.dp, vertical = 2.dp)
              ) {
                Text(text = "BEST VALUE", fontSize = 7.sp, fontWeight = FontWeight.Bold, color = Color.Black)
              }
            }

            Text(text = "₹1,999 / yr", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)

            Spacer(modifier = Modifier.height(10.dp))

            TierCheck("Full Syllabus + Question Bank", highlight = true)
            TierCheck("Premium Mains Analytics", highlight = true)
            TierCheck("Save ~45% vs Monthly", highlight = true)

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = onOpenUpgradeModal,
              colors = ButtonDefaults.buttonColors(containerColor = StatusGreen),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.fillMaxWidth().testTag("annual_tier_button")
            ) {
              Text(
                text = if (isProUser) "Annual Active ✓" else "Get Annual",
                color = Color.Black,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
private fun TierCheck(text: String, highlight: Boolean = false) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(vertical = 2.dp)
  ) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = "Included",
      tint = if (highlight) PurpleAccent else TextMuted,
      modifier = Modifier.size(12.dp)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = text,
      fontSize = 11.sp,
      color = if (highlight) TextPrimary else TextSecondary
    )
  }
}
