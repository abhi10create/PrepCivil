package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkContainer
import com.example.ui.theme.DarkPurpleText
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.PurpleAccent
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AdminAuthModal(
  onDismiss: () -> Unit,
  onAuthenticateSuccess: () -> Unit,
  onAccessDenied: () -> Unit
) {
  var pinInput by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = DarkContainer,
      border = androidx.compose.foundation.BorderStroke(1.dp, PurpleAccent),
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
        .testTag("admin_auth_modal")
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
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
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = "Admin Auth",
                tint = DarkPurpleText,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Admin Access Control",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = PurpleAccent
            )
          }

          IconButton(
            onClick = {
              onAccessDenied()
              onDismiss()
            },
            modifier = Modifier
              .size(28.dp)
              .testTag("close_admin_auth_button")
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
          text = "Enter Administrator Passcode",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "The Admin Panel and AI Content Generator are restricted to authorized personnel. Default passcode: 1234",
          fontSize = 12.sp,
          color = TextSecondary,
          textAlign = TextAlign.Center,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
          value = pinInput,
          onValueChange = {
            pinInput = it
            errorMessage = null
          },
          label = { Text("Security Passcode (1234)") },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Key,
              contentDescription = "Passcode",
              tint = PurpleAccent
            )
          },
          visualTransformation = PasswordVisualTransformation(),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_pin_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PurpleAccent,
            unfocusedBorderColor = DarkBorder,
            focusedLabelColor = PurpleAccent,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
          )
        )

        if (errorMessage != null) {
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = errorMessage!!,
            color = ErrorRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = {
            if (pinInput.trim() == "1234" || pinInput.trim() == "admin" || pinInput.trim() == "admin123") {
              onAuthenticateSuccess()
              onDismiss()
            } else {
              errorMessage = "Access Denied: Incorrect Security Passcode"
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("verify_admin_passcode_button"),
          colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = "Authenticate & Unlock",
            color = DarkPurpleText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
