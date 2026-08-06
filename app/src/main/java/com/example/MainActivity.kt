package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.model.CardRating
import com.example.model.Chapter
import com.example.model.StudyRepository
import com.example.model.Subject
import com.example.ui.components.AppTab
import com.example.ui.components.DashboardPane
import com.example.ui.components.ERegisterCanvasPane
import com.example.ui.components.FlashcardsPane
import com.example.ui.components.FooterStatusBar
import com.example.ui.components.QuizEnginePane
import com.example.ui.components.ReaderPane
import com.example.ui.components.TopNavBar
import com.example.ui.theme.EduStudyTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      EduStudyApp()
    }
  }
}

@Composable
fun EduStudyApp() {
  var showSplashScreen by remember { mutableStateOf(true) }
  var isDarkMode by remember { mutableStateOf(true) }
  var subjects by remember { mutableStateOf(StudyRepository.subjects) }
  var selectedSubject by remember { mutableStateOf(subjects.first()) }
  var selectedChapter by remember { mutableStateOf(selectedSubject.chapters.first()) }
  var activeTab by remember { mutableStateOf(AppTab.DASHBOARD) }
  var masteryPercentage by remember { mutableIntStateOf(64) }
  var isProUser by remember { mutableStateOf(false) }
  var showUpgradeModal by remember { mutableStateOf(false) }
  var isZenMode by remember { mutableStateOf(false) }
  var isAdminAuthenticated by remember { mutableStateOf(false) }
  var showAdminAuthModal by remember { mutableStateOf(false) }
  var accessDeniedNotification by remember { mutableStateOf<String?>(null) }

  EduStudyTheme(darkTheme = isDarkMode) {
    if (showSplashScreen) {
      com.example.ui.components.SplashScreen(
        onEnterWorkspace = {
          showSplashScreen = false
        }
      )
    } else {
      Scaffold(
        modifier = Modifier
          .fillMaxSize()
          .testTag("main_scaffold"),
        topBar = {
          if (!isZenMode || activeTab != AppTab.WORKSPACE) {
            TopNavBar(
              activeTab = activeTab,
              onTabSelected = { newTab ->
                if (newTab == AppTab.ADMIN) {
                  if (isAdminAuthenticated) {
                    activeTab = AppTab.ADMIN
                  } else {
                    showAdminAuthModal = true
                  }
                } else {
                  activeTab = newTab
                }
              },
              isDarkMode = isDarkMode,
              onToggleDarkMode = {
                isDarkMode = !isDarkMode
              },
              isSubscribed = isProUser
            )
          }
        },
        bottomBar = {
          if (!isZenMode || activeTab != AppTab.WORKSPACE) {
            FooterStatusBar(masteryPercentage = masteryPercentage)
          }
        }
      ) { innerPadding ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(if (!isZenMode || activeTab != AppTab.WORKSPACE) innerPadding else androidx.compose.foundation.layout.PaddingValues(0.dp))
            .background(MaterialTheme.colorScheme.background)
        ) {
          when (activeTab) {
            AppTab.DASHBOARD -> {
              DashboardPane(
                masteryPercentage = masteryPercentage,
                subjects = subjects,
                onSelectSubject = { subject ->
                  selectedSubject = subject
                  selectedChapter = subject.chapters.first()
                  activeTab = AppTab.WORKSPACE
                },
                onStartSrsReview = {
                  activeTab = AppTab.FLASHCARDS
                },
                isProUser = isProUser,
                onOpenUpgradeModal = {
                  showUpgradeModal = true
                }
              )
            }
            AppTab.WORKSPACE -> {
              WorkspaceSplitScreenView(
                selectedSubject = selectedSubject,
                subjects = subjects,
                onSubjectSelected = { newSubject ->
                  selectedSubject = newSubject
                  selectedChapter = newSubject.chapters.first()
                },
                selectedChapter = selectedChapter,
                chapters = selectedSubject.chapters,
                onChapterSelected = { newChapter ->
                  selectedChapter = newChapter
                },
                isProUser = isProUser,
                onOpenUpgradeModal = {
                  showUpgradeModal = true
                },
                isZenMode = isZenMode,
                onToggleZenMode = { isZenMode = !isZenMode }
              )
            }
          AppTab.FLASHCARDS -> {
            FlashcardsPane(
              flashcards = selectedSubject.chapters.flatMap { it.flashcards },
              subjectName = selectedSubject.name,
              onRatingUpdated = { rating ->
                if (rating == CardRating.EASY) {
                  masteryPercentage = (masteryPercentage + 4).coerceAtMost(100)
                } else if (rating == CardRating.GOOD) {
                  masteryPercentage = (masteryPercentage + 2).coerceAtMost(100)
                }
              }
            )
          }
          AppTab.QUIZ -> {
            QuizEnginePane(
              chapterTitle = selectedChapter.title,
              quizQuestions = selectedChapter.quizQuestions,
              onQuizCompleted = { score, total ->
                val addition = ((score.toFloat() / total.toFloat()) * 15).toInt()
                masteryPercentage = (masteryPercentage + addition).coerceAtMost(100)
              }
            )
          }
          AppTab.MAINS_AI -> {
            com.example.ui.components.MainsAiPane(
              isProUser = isProUser,
              onOpenUpgradeModal = {
                showUpgradeModal = true
              }
            )
          }
          AppTab.STORE -> {
            com.example.ui.components.StorePane(
              isProUser = isProUser,
              subjects = subjects,
              onSelectChapter = { sub, ch ->
                selectedSubject = sub
                selectedChapter = ch
                activeTab = AppTab.WORKSPACE
              },
              onOpenUpgradeModal = {
                showUpgradeModal = true
              }
            )
          }
          AppTab.ADMIN -> {
            com.example.ui.components.AdminPane(
              isAdminAuthenticated = isAdminAuthenticated,
              onLogoutAdmin = {
                isAdminAuthenticated = false
                activeTab = AppTab.DASHBOARD
              },
              onReturnToDashboard = {
                activeTab = AppTab.DASHBOARD
              },
              onPublishChapter = { targetSubjectName, newChapter ->
                val existingSubject = subjects.find { it.name.equals(targetSubjectName, ignoreCase = true) }
                if (existingSubject != null) {
                  val updatedSubject = existingSubject.copy(
                    chapters = existingSubject.chapters + newChapter
                  )
                  subjects = subjects.map { if (it.id == existingSubject.id) updatedSubject else it }
                  selectedSubject = updatedSubject
                  selectedChapter = newChapter
                } else {
                  val newSubId = "sub_${targetSubjectName.lowercase().replace(Regex("[^a-z0-9]"), "_")}"
                  val newSubject = Subject(
                    id = newSubId,
                    name = targetSubjectName,
                    chapters = listOf(newChapter)
                  )
                  subjects = subjects + newSubject
                  selectedSubject = newSubject
                  selectedChapter = newChapter
                }
              }
            )
          }
        }

        if (showAdminAuthModal) {
          com.example.ui.components.AdminAuthModal(
            onDismiss = { showAdminAuthModal = false },
            onAuthenticateSuccess = {
              isAdminAuthenticated = true
              activeTab = AppTab.ADMIN
              accessDeniedNotification = null
            },
            onAccessDenied = {
              accessDeniedNotification = "Access Denied: Restricted to Authorized Administrators"
              activeTab = AppTab.DASHBOARD
            }
          )
        }

        if (showUpgradeModal) {
          com.example.ui.components.ProUpgradeModal(
            onDismiss = { showUpgradeModal = false },
            onUpgradeSuccess = {
              isProUser = true
            }
          )
        }
      }
    }
  }
}
}

@Composable
fun WorkspaceSplitScreenView(
  selectedSubject: Subject,
  subjects: List<Subject>,
  onSubjectSelected: (Subject) -> Unit,
  selectedChapter: Chapter,
  chapters: List<Chapter>,
  onChapterSelected: (Chapter) -> Unit,
  isProUser: Boolean = false,
  onOpenUpgradeModal: (() -> Unit)? = null,
  isZenMode: Boolean = false,
  onToggleZenMode: (() -> Unit)? = null
) {
  var isCanvasOpen by remember { mutableStateOf(true) }

  Column(modifier = Modifier.fillMaxSize()) {
    if (!isZenMode) {
      // Subject & Chapter Dropdowns bar ONLY inside Workspace view when NOT in Zen mode
      com.example.ui.components.SubjectChapterSelectorBar(
        selectedSubject = selectedSubject,
        subjects = subjects,
        onSubjectSelected = onSubjectSelected,
        selectedChapter = selectedChapter,
        chapters = chapters,
        onChapterSelected = onChapterSelected,
        isProUser = isProUser,
        onOpenUpgradeModal = onOpenUpgradeModal
      )
    }

    BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxWidth()) {
      val isLandscape = maxWidth > maxHeight || maxWidth > 600.dp

      if (isZenMode) {
        // Pure Zen Mode: 100% full screen reader
        ReaderPane(
          chapter = selectedChapter,
          isCanvasOpen = false,
          onToggleCanvas = null,
          isZenMode = true,
          onToggleZenMode = onToggleZenMode,
          isProUser = isProUser,
          onOpenUpgradeModal = onOpenUpgradeModal,
          modifier = Modifier.fillMaxSize()
        )
      } else if (isLandscape) {
        // Landscape or Wide Screen
        Row(modifier = Modifier.fillMaxSize()) {
          ReaderPane(
            chapter = selectedChapter,
            isCanvasOpen = isCanvasOpen,
            onToggleCanvas = { isCanvasOpen = !isCanvasOpen },
            isZenMode = false,
            onToggleZenMode = onToggleZenMode,
            isProUser = isProUser,
            onOpenUpgradeModal = onOpenUpgradeModal,
            modifier = Modifier
              .weight(if (isCanvasOpen) 0.45f else 1.0f)
              .fillMaxHeight()
          )
          if (isCanvasOpen) {
            ERegisterCanvasPane(
              modifier = Modifier
                .weight(0.55f)
                .fillMaxHeight()
            )
          }
        }
      } else {
        // Portrait or Compact Screen
        Column(modifier = Modifier.fillMaxSize()) {
          ReaderPane(
            chapter = selectedChapter,
            isCanvasOpen = isCanvasOpen,
            onToggleCanvas = { isCanvasOpen = !isCanvasOpen },
            isZenMode = false,
            onToggleZenMode = onToggleZenMode,
            isProUser = isProUser,
            onOpenUpgradeModal = onOpenUpgradeModal,
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
          )
          if (isCanvasOpen) {
            ERegisterCanvasPane(
              modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
            )
          }
        }
      }
    }
  }
}
