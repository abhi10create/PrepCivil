package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProUpgradeModal(
  onDismiss: () -> Unit,
  onUpgradeSuccess: () -> Unit
) {
  var isYearlyPlan by remember { mutableStateOf(true) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = DarkContainer,
      border = androidx.compose.foundation.BorderStroke(1.dp, PurpleAccent),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("pro_upgrade_modal")
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header with Close
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PurpleAccent),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.WorkspacePremium,
                contentDescription = "Pro Pass",
                tint = DarkPurpleText,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "PrepCivil Pro Pass",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = PurpleAccent
            )
          }

          IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(28.dp).testTag("close_pro_modal_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = TextMuted
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Unlock Full Civil Services Syllabus Access",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Get unlimited access to all chapters, advanced canvas tools, and full SRS flashcard decks.",
          fontSize = 12.sp,
          color = TextSecondary,
          textAlign = TextAlign.Center,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Plan Selector (Monthly vs Yearly)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (!isYearlyPlan) PurpleAccent else Color.Transparent)
              .clickable { isYearlyPlan = false }
              .padding(vertical = 8.dp)
              .testTag("monthly_plan_tab"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "Monthly • ₹299",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = if (!isYearlyPlan) DarkPurpleText else TextSecondary
            )
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(10.dp))
              .background(if (isYearlyPlan) PurpleAccent else Color.Transparent)
              .clickable { isYearlyPlan = true }
              .padding(vertical = 8.dp)
              .testTag("yearly_plan_tab"),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Annual • ₹1,999",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isYearlyPlan) DarkPurpleText else TextSecondary
              )
              Spacer(modifier = Modifier.width(4.dp))
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(if (isYearlyPlan) DarkPurpleText else StatusGreen)
                  .padding(horizontal = 4.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "Save 45%",
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isYearlyPlan) PurpleAccent else Color.White
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Feature checklist
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          FeatureRow("All Chapters Unlocked (Laxmikanth, Spectrum, NCERTs, Nitin Singhania)")
          FeatureRow("Unlimited Daily SRS Flashcard Reviews")
          FeatureRow("Advanced Dual Register Canvas & Drawing Export")
          FeatureRow("Detailed Quiz Analytics & Multi-Statement UPSC Solutions")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CTA Button
        Button(
          onClick = {
            onUpgradeSuccess()
            onDismiss()
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("get_pro_pass_button"),
          colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
          shape = RoundedCornerShape(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Pro Pass",
              tint = DarkPurpleText,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = if (isYearlyPlan) "Get Pro Pass — ₹1,999/yr" else "Get Pro Pass — ₹299/mo",
              color = DarkPurpleText,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Cancel anytime • 7-day money-back guarantee",
          fontSize = 10.sp,
          color = TextMuted
        )
      }
    }
  }
}

@Composable
private fun FeatureRow(text: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Icon(
      imageVector = Icons.Default.CheckCircle,
      contentDescription = "Included",
      tint = StatusGreen,
      modifier = Modifier.size(16.dp)
    )
    Text(
      text = text,
      fontSize = 12.sp,
      color = TextPrimary,
      fontWeight = FontWeight.Medium
    )
  }
}
