package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.BrainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// ======================== MULTI-LANGUAGE DICTIONARY ========================

object Loc {
    private val Dictionary = mapOf(
        "EN" to mapOf(
            "dashboard" to "Dashboard Overview",
            "finance" to "Finance Tracker",
            "note" to "My Brain Notes",
            "todo" to "Tasks & Reminders",
            "diary" to "Daily Diary Reflection",
            "reports" to "Analytics & Trends",
            "master_data" to "Master Data pokoks",
            "customization" to "Dashboard Customizer",
            "widget" to "Interactive Widgets",
            "settings" to "App Settings & Sync",
            "profile" to "User Profile",
            "help" to "Help Center & Contact",
            "theme" to "App Theme",
            "language" to "App Language",
            "notif_enabled" to "Real-time Reminders & Notifications",
            "sync_enabled" to "Secure Offline-to-Cloud Syncing",
            "currency" to "Base Currency Preference",
            "total_balance" to "Total Net Balance",
            "income" to "Total Income",
            "expense" to "Total Expenses",
            "recent_notes" to "Recent Brain Notes",
            "pinned" to "Pinned",
            "unpinned" to "Unpinned",
            "add_failed" to "Please fill correct values",
            "save" to "Save & Secure",
            "delete" to "Delete Record",
            "add_btn" to "Add Item",
            "category" to "Category Class",
            "asset" to "Source / Asset Account",
            "amount" to "Financial Amount",
            "note_placeholder" to "Write clear extra notes here...",
            "todo_header" to "Daily Task Manager",
            "priority" to "Task Priority",
            "due" to "Tenggat Waktu Date & Time",
            "mood" to "How do you feel today?",
            "reflection" to "What has happened today? Let's take down our honest thoughts...",
            "sync_state" to "Sync Status",
            "synced" to "Synced Securely to Cloud",
            "not_synced" to "Pending Cloud backup sync",
            "reports_title" to "Monthly Financial Expense Trend",
            "productivity_title" to "Productivity Completion Rate",
            "no_data" to "No data input yet. Add some items to visualize reports!",
            "ai_btn" to "🧠 Ask AI Assistant",
            "ai_greeting" to "Hello! I am your AI Second Brain Assistant. Ask me anything about your notes, finances, or tasks. Or click suggestions below:",
            "ai_prompt_suggestion_1" to "Review my expense behavior and give dynamic micro-budgets",
            "ai_prompt_suggestion_2" to "Provide summary breakdown of unchecked HIGH tasks on my list",
            "ai_prompt_suggestion_3" to "Suggest a healthy routine based on my latest mood diaries",
            "widget_desc" to "Mock live desktop launcher layout. Place these widgets on your Android desktop for high-speed tracking:",
            "configure_widget" to "Simulate Placing on Home Screen",
            "widget_placed" to "Simulated Launcher Home Widgets updated!",
            "help_manual" to "Application Interactive Manual",
            "submit_ticket" to "Report a Bug / File Request Ticket",
            "ticket_success" to "Ticket submitted! Our tech support will review your issue.",
            "edit_profile_desc" to "Configure Second Brain accounts securely. Use sandbox passwords or modify titles.",
            "dashboard_drag" to "Arrange Dashboard Layout Components Ordering"
        ),
        "ID" to mapOf(
            "dashboard" to "Ringkasan Dasbor",
            "finance" to "Catatan Keuangan",
            "note" to "Catatan Otakku",
            "todo" to "Tugas & Pengingat",
            "diary" to "Jurnal Harian Refleksi",
            "reports" to "Analitik & Laporan Tren",
            "master_data" to "Data Pokok Utama",
            "customization" to "Kustomisasi Dasbor",
            "widget" to "Daftar Simulasi Widget",
            "settings" to "Pengaturan & Sinkronisasi",
            "profile" to "Profil Penggunaku",
            "help" to "Pusat Bantuan & Tiket",
            "theme" to "Tema Tampilan",
            "language" to "Bahasa Aplikasi",
            "notif_enabled" to "Notifikasi Pengingat Tugas Otomatis",
            "sync_enabled" to "Sinkronisasi Cloud Aman",
            "currency" to "Mata Uang Acuan",
            "total_balance" to "Saldo Bersih Keseluruhan",
            "income" to "Total Pemasukan",
            "expense" to "Total Pengeluaran",
            "recent_notes" to "Catatan Terbaru Anda",
            "pinned" to "Sematkan",
            "unpinned" to "Biasa",
            "add_failed" to "Isikan data dengan benar",
            "save" to "Simpan Data",
            "delete" to "Hapus Data",
            "add_btn" to "Tambah Baru",
            "category" to "Pilihan Kategori",
            "asset" to "Aset / Akun Sumber",
            "amount" to "Nominal Transaksi",
            "note_placeholder" to "Tulis rincian catatan tambahan di sini...",
            "todo_header" to "Manajer Kegiatan Harian",
            "priority" to "Tingkat Prioritas",
            "due" to "Tanggal & Waktu Tenggat",
            "mood" to "Bagaimana perasaanmu hari ini?",
            "reflection" to "Tuliskan seluruh refleksi emosional hari ini secara bebas...",
            "sync_state" to "Aktivitas Sinkronisasi",
            "synced" to "Tersinkronisasi Aman ke Cloud",
            "not_synced" to "Menunggu Sinkronisasi Cadangan Cloud",
            "reports_title" to "Tren Analisis Pengeluaran Bulanan",
            "productivity_title" to "Rasio Kelengkapan Produktivitas",
            "no_data" to "Belum ada rekaman data. Silakan isi data keuangan dan tugas Anda!",
            "ai_btn" to "🧠 Tanya Asisten AI",
            "ai_greeting" to "Halo! Saya adalah Asisten AI Otak Kedua Anda. Anda bisa menanyakan ringkasan catatan keuangan, daftar tugas, atau analisis diary Anda. Coba saran di bawah:",
            "ai_prompt_suggestion_1" to "Analisis pengeluaran saya dan berikan saran anggaran mikro",
            "ai_prompt_suggestion_2" to "Tampilkan daftar tugas penting HIGH yang belum saya selesaikan",
            "ai_prompt_suggestion_3" to "Berikan kesimpulan pola pikir berdasarkan mood diary saya",
            "widget_desc" to "Simulasi tampilan widget di layar utama (home screen) perangkat genggam Anda untuk akses super cepat:",
            "configure_widget" to "Simulasikan Pasang Widget Ke Home Screen",
            "widget_placed" to "Widget berhasil diletakkan pada Launcher Ponsel!",
            "help_manual" to "Panduan Penggunaan Aplikasi Komprehensif",
            "submit_ticket" to "Laporkan Kendala Teknis / Masalah Sistem",
            "ticket_success" to "Laporan dikirim! Tim IT kami akan segera meneliti kendala Anda.",
            "edit_profile_desc" to "Ubah detail informasi pribadi, ubah foto/avatar, dan ubah sandi akun sandbox Anda.",
            "dashboard_drag" to "Atur Urutan Tata Letak Kartu Ringkasan Dasbor"
        )
    )

    fun tr(key: String, lang: String): String {
        val uppercaseLang = lang.uppercase()
        return Dictionary[uppercaseLang]?.get(key) ?: Dictionary["EN"]?.get(key) ?: key
    }
}

// ======================== CORE APP ROUTER / SHELL ========================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApplicationShell(
    viewModel: BrainViewModel,
    onLogoutRequested: () -> Unit
) {
    val activeUser by viewModel.activeUser.collectAsState()
    val settings by viewModel.settingsState.collectAsState()
    val scope = rememberCoroutineScope()

    // Screen State Tracker: Use Simple tabs to ensure flawless UX in Android Emulator without deep nesting.
    var currentTab by remember { mutableStateOf("DASHBOARD") }
    var isAiDrawerOpen by remember { mutableStateOf(false) }

    // Dynamic UI styling (Primary theme colors generated dynamically)
    val isDark = isSystemInDarkTheme() || settings.themeMode == "DARK"
    val bentoBg = if (isDark) Color(0xFF131610) else Color(0xFFF7FBF1)
    val bentoText = if (isDark) Color(0xFFE2E3DD) else Color(0xFF191D17)
    val bentoTextSecondary = if (isDark) Color(0xFF9EA398) else Color(0xFF55624C)
    val bentoBorder = if (isDark) Color(0xFF2C3228) else Color(0xFFDDE4D6)

    val colorStyle = if (settings.dashboardOrder.contains("|")) {
        settings.dashboardOrder.split("|").getOrNull(0) ?: "PURPLE"
    } else {
        "PURPLE"
    }

    MyApplicationTheme(
        themeMode = settings.themeMode,
        colorStyle = colorStyle
    ) {
        // Scaffolds
        Scaffold(
            containerColor = bentoBg,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = "Brain App Logo",
                                tint = if (isDark) Color(0xFFD7E8CD) else Color(0xFF386A20)
                            )
                            Text(
                                text = "Second Brain",
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp,
                                color = bentoText,
                                fontFamily = FontFamily.SansSerif
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { isAiDrawerOpen = true }) {
                            Icon(
                                imageVector = Icons.Default.SmartButton,
                                contentDescription = "Toggle cognitive artificial intelligence AI drawer",
                                tint = if (isDark) Color(0xFFD7E8CD) else Color(0xFF386A20)
                            )
                        }
                    },
                    actions = {
                        // Avatar Badge Profile
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isDark) Color(0xFF24361C) else Color(0xFFD7E8CD))
                                .clickable {
                                    currentTab = "PROFILE"
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!activeUser?.profilePhotoUri.isNullOrBlank()) {
                                Text(
                                    text = activeUser?.profilePhotoUri ?: "🤠",
                                    fontSize = 18.sp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Face,
                                    contentDescription = "User Avatar Placeholder",
                                    tint = if (isDark) Color(0xFFD7E8CD) else Color(0xFF191D17),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = bentoBg
                    )
                )
            },
            bottomBar = {
                // Multi-Tab navigation representing 5 primary quick tabs, plus others available inside dashboard drawers
                Column {
                    Divider(color = bentoBorder, thickness = 1.dp)
                    NavigationBar(
                        modifier = Modifier.navigationBarsPadding(),
                        containerColor = bentoBg,
                        tonalElevation = 0.dp
                    ) {
                        val tabs = listOf(
                            Triple("DASHBOARD", Icons.Default.GridView, "dashboard"),
                            Triple("FINANCE", Icons.Default.AccountBalanceWallet, "finance"),
                            Triple("TODO", Icons.Default.FactCheck, "todo"),
                            Triple("NOTE", Icons.Default.StickyNote2, "note"),
                            Triple("DIARY", Icons.Default.MenuBook, "diary")
                        )
                        tabs.forEach { tab ->
                            NavigationBarItem(
                                selected = currentTab == tab.first,
                                onClick = { currentTab = tab.first },
                                icon = { Icon(tab.second, contentDescription = tab.first) },
                                label = {
                                    Text(
                                        text = Loc.tr(tab.third, settings.language),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 10.sp,
                                        fontWeight = if (currentTab == tab.first) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                alwaysShowLabel = true,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = bentoText,
                                    selectedTextColor = bentoText,
                                    indicatorColor = if (isDark) Color(0xFF24361C) else Color(0xFFD7E8CD),
                                    unselectedIconColor = bentoTextSecondary,
                                    unselectedTextColor = bentoTextSecondary
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            // Slide-down content box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    "DASHBOARD" -> DashboardScreen(
                        viewModel = viewModel,
                        settings = settings,
                        onTabChanged = { currentTab = it }
                    )
                    "FINANCE" -> FinanceScreen(viewModel = viewModel, settings = settings)
                    "TODO" -> TodoListScreen(viewModel = viewModel, settings = settings)
                    "NOTE" -> NotesScreen(viewModel = viewModel, settings = settings)
                    "DIARY" -> DiaryScreen(viewModel = viewModel, settings = settings)
                    "REPORTS" -> ReportsScreen(viewModel = viewModel, settings = settings)
                    "MASTER_DATA" -> MasterDataScreen(viewModel = viewModel, settings = settings)
                    "CUSTOMIZATION" -> CustomizationScreen(viewModel = viewModel, settings = settings)
                    "WIDGET" -> WidgetSpecScreen(viewModel = viewModel, settings = settings)
                    "SETTINGS" -> SettingsScreen(viewModel = viewModel, settings = settings, onLogout = onLogoutRequested)
                    "PROFILE" -> ProfileScreen(viewModel = viewModel, settings = settings)
                    "HELP" -> HelpScreen(viewModel = viewModel, settings = settings)
                    else -> DashboardScreen(
                        viewModel = viewModel,
                        settings = settings,
                        onTabChanged = { currentTab = it }
                    )
                }

                // AI ASSISTANT BOTTOM SHEET DRAWER OVERLAY
                if (isAiDrawerOpen) {
                    AiAssistantDrawer(
                        viewModel = viewModel,
                        settings = settings,
                        onClose = { isAiDrawerOpen = false }
                    )
                }
            }
        }
    }
}

// ======================== PORTABLE DASHBOARD DISPATCHER ========================

@Composable
fun DashboardScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity,
    onTabChanged: (String) -> Unit
) {
    val activeUser by viewModel.activeUser.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val todos by viewModel.todos.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val diaryEntries by viewModel.diaryEntries.collectAsState()

    // Calculate dynamic values
    val currentCurrency = settings.currency
    val incomeSum = transactions.filter { it.type == "INCOME" }.sumOf { it.amount }
    val expenseSum = transactions.filter { it.type == "EXPENSE" }.sumOf { it.amount }
    val netBalance = incomeSum - expenseSum

    val totalCompletedJobs = todos.count { it.isCompleted }
    val totalUncompletedJobs = todos.count { !it.isCompleted }

    // Parse ordering layout
    val orderList = remember(settings.dashboardOrder) {
        val rawOrder = if (settings.dashboardOrder.contains("|")) {
            settings.dashboardOrder.split("|").getOrNull(1) ?: ""
        } else {
            settings.dashboardOrder
        }
        val configured = rawOrder.split(",")
        val defaultLayout = listOf("FINANCE", "TODOS", "NOTES", "DIARY", "WIDGET", "REPORTS")
        val filtered = configured.filter { it in defaultLayout }
        if (filtered.isEmpty()) defaultLayout else filtered
    }

    val isDark = isSystemInDarkTheme() || settings.themeMode == "DARK"
    val bentoBg = if (isDark) Color(0xFF131610) else Color(0xFFF7FBF1)
    val bentoText = if (isDark) Color(0xFFE2E3DD) else Color(0xFF191D17)
    val bentoTextSecondary = if (isDark) Color(0xFF9EA398) else Color(0xFF55624C)
    val bentoBorder = if (isDark) Color(0xFF2C3228) else Color(0xFFDDE4D6)

    val financeCardColor = if (isDark) Color(0xFF24361C) else Color(0xFFD7E8CD)
    val financeText = if (isDark) Color(0xFFD7E8CD) else Color(0xFF386A20)

    val tasksCardColor = if (isDark) Color(0xFF452B14) else Color(0xFFFCE8D5)
    val tasksText = if (isDark) Color(0xFFFCE8D5) else Color(0xFF855400)

    val diaryCardColor = if (isDark) Color(0xFF322A40) else Color(0xFFE8DEF8)
    val diaryText = if (isDark) Color(0xFFE8DEF8) else Color(0xFF6750A4)

    val notesCardColor = if (isDark) Color(0xFF1E241B) else Color(0xFFFFFFFF)

    val reportsCardColor = if (isDark) Color(0xFF2D3139) else Color(0xFFE1E2EC)
    val reportsText = if (isDark) Color(0xFFE1E2EC) else Color(0xFF43474E)

    val widgetCardColor = if (isDark) Color(0xFF1E241B) else Color(0xFFFFFFFF)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Welcoming Hero Header Card (Integrated Bento Style)
        item {
            val initials = (activeUser?.name ?: "User").split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercase() }.joinToString("")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF24361C) else Color(0xFFD7E8CD))
                            .border(2.dp, if (isDark) Color(0xFF2E3D28) else Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (!activeUser?.profilePhotoUri.isNullOrBlank()) activeUser?.profilePhotoUri ?: "🤠" else initials.take(2),
                            fontSize = if (!activeUser?.profilePhotoUri.isNullOrBlank()) 22.sp else 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) Color(0xFFD7E8CD) else Color(0xFF191D17)
                        )
                    }
                    Column {
                        val greeting = if (settings.language == "ID") "Halo, ${activeUser?.name ?: "Kawan"}" else "Hello, ${activeUser?.name ?: "Friend"}"
                        Text(
                            text = greeting,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = bentoTextSecondary
                        )
                        Text(
                            text = "Second Brain",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = bentoText
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isDark) Color(0xFF1E241B) else Color.White)
                            .border(1.dp, bentoBorder, CircleShape)
                            .clickable { onTabChanged("SETTINGS") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Settings, "Setelan", tint = bentoText, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        // Quick shortcuts row for Bento vibe
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E241B) else Color.White),
                border = BorderStroke(1.dp, bentoBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (settings.language == "ID") "Akses Cepat" else "Quick Shortcuts",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = bentoTextSecondary,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val shortcuts = listOf(
                            Quadruple("REPORTS", Icons.Default.AreaChart, "reports", "REPORTS"),
                            Quadruple("MASTER", Icons.Default.Source, "master_data", "MASTER_DATA"),
                            Quadruple("CUSTOMIZE", Icons.Default.Palette, "customization", "CUSTOMIZATION"),
                            Quadruple("HELP", Icons.Default.HelpCenter, "help", "HELP")
                        )

                        shortcuts.forEach { s ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onTabChanged(s.fourth) }
                                    .padding(vertical = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(if (isDark) Color(0xFF2C3228) else Color(0xFFF0F4E9)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = s.second,
                                        contentDescription = s.third,
                                        tint = if (isDark) Color(0xFFD7E8CD) else Color(0xFF386A20),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Text(
                                    text = Loc.tr(s.third, settings.language),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = bentoText,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // BENTO ROW 1: Finance (Weight 1) + Tasks (Weight 1)
        item {
            val hasFinance = "FINANCE" in orderList
            val hasTodos = "TODOS" in orderList

            if (hasFinance || hasTodos) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (hasFinance) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clickable { onTabChanged("FINANCE") },
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = financeCardColor)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountBalanceWallet,
                                        contentDescription = "Wallet Icon",
                                        tint = financeText,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = if (settings.language == "ID") "KEUANGAN" else "FINANCE",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        color = financeText
                                    )
                                }

                                Column {
                                    Text(
                                        text = if (settings.language == "ID") "Saldo Anda" else "Your Balance",
                                        fontSize = 10.sp,
                                        color = if (isDark) Color(0xFFB5BEB1) else Color(0xFF55624C),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "$currentCurrency ${netBalance.toInt()}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = bentoText,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "▲ $currentCurrency ${incomeSum.toInt()}",
                                        fontSize = 10.sp,
                                        color = if (isDark) Color(0xFFA3E635) else Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (hasTodos) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clickable { onTabChanged("TODO") },
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = tasksCardColor)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FactCheck,
                                        contentDescription = "Todo",
                                        tint = tasksText,
                                        modifier = Modifier.size(20.dp)
                                    )

                                    val pct = if (todos.isNotEmpty()) (totalCompletedJobs * 100) / todos.size else 0
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .border(1.5.dp, tasksText.copy(alpha = 0.2f), CircleShape)
                                            .border(1.5.dp, tasksText, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("$pct%", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = tasksText)
                                    }
                                }

                                Column {
                                    Text(
                                        text = if (settings.language == "ID") "Tugas Hari Ini" else "Tasks Today",
                                        fontSize = 10.sp,
                                        color = tasksText.copy(alpha = 0.8f),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = if (settings.language == "ID") "$totalUncompletedJobs Tersisa" else "$totalUncompletedJobs Left",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Black,
                                        color = bentoText
                                    )
                                    Text(
                                        text = if (settings.language == "ID") "Selesai: $totalCompletedJobs" else "Completed: $totalCompletedJobs",
                                        fontSize = 10.sp,
                                        color = bentoText.copy(alpha = 0.6f),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // BENTO ROW 2: Diary Reflection Card (Wide)
        item {
            val hasDiary = "DIARY" in orderList
            if (hasDiary) {
                val lastEntry = diaryEntries.firstOrNull()
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTabChanged("DIARY") },
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = diaryCardColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(bottom = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star Icon",
                                    tint = diaryText,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (settings.language == "ID") "REFLEKSI HARIAN" else "DAILY REFLECTION",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = diaryText
                                )
                            }

                            if (lastEntry == null) {
                                Text(
                                    text = if (settings.language == "ID") "Belum ada diary harian hari ini..." else "No diary entry today yet...",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = bentoText
                                )
                                Text(
                                    text = if (settings.language == "ID") "Mulailah menulis pikiran Anda sekarang!" else "Tap to write down your thoughts!",
                                    fontSize = 11.sp,
                                    color = bentoText.copy(alpha = 0.6f),
                                    style = androidx.compose.ui.text.TextStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                                )
                            } else {
                                Text(
                                    text = lastEntry.reflection,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = bentoText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "\"${lastEntry.date} • Mood: ${lastEntry.mood}\"",
                                    fontSize = 11.sp,
                                    color = bentoText.copy(alpha = 0.6f),
                                    style = androidx.compose.ui.text.TextStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isDark) Color(0xFF151812) else Color.White.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = "Edit Diary",
                                tint = diaryText,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }

        // BENTO ROW 3: Notes (Square White) + Reports (Square Slate Blue)
        item {
            val hasNotes = "NOTES" in orderList
            val hasReports = "REPORTS" in orderList

            if (hasNotes || hasReports) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (hasNotes) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clickable { onTabChanged("NOTE") },
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = notesCardColor),
                            border = BorderStroke(1.dp, bentoBorder)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.StickyNote2,
                                        contentDescription = "Notes",
                                        tint = if (isDark) Color(0xFFCEEAD6) else Color(0xFF386A20),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = if (settings.language == "ID") "CATATAN" else "NOTES",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        color = bentoTextSecondary
                                    )
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (notes.isEmpty()) {
                                        Text(
                                            text = if (settings.language == "ID") "Kosong" else "Empty",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = bentoText
                                        )
                                        Text(
                                            text = if (settings.language == "ID") "Tambah ide baru" else "Add your ideas",
                                            fontSize = 10.sp,
                                            color = bentoTextSecondary
                                        )
                                    } else {
                                        val firstNote = notes.first()
                                        Text(
                                            text = firstNote.title,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = bentoText,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = if (settings.language == "ID") "Updated baru saja" else "Just updated",
                                            fontSize = 9.sp,
                                            color = bentoTextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (hasReports) {
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp)
                                .clickable { onTabChanged("REPORTS") },
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = reportsCardColor)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AreaChart,
                                        contentDescription = "Trends",
                                        tint = reportsText,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = if (settings.language == "ID") "TREN" else "TRENDS",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp,
                                        color = reportsText
                                    )
                                }

                                // Interactive mini bar graph visualization from the Bento template
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(36.dp)
                                        .padding(horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    val heights = listOf(0.4f, 0.6f, 0.9f, 0.7f, 1.0f)
                                    heights.forEachIndexed { idx, h ->
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight(h)
                                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                                .background(if (idx == heights.lastIndex) (if (isDark) Color(0xFFA3E635) else Color(0xFF386A20)) else bentoText.copy(alpha = 0.3f))
                                        )
                                    }
                                }

                                Text(
                                    text = if (settings.language == "ID") "Analitik Produktivitas" else "Productivity Stats",
                                    fontSize = 10.sp,
                                    color = bentoText.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // BENTO ROW 4: Widget Card / Categories Panel (Wide White)
        item {
            val hasWidget = "WIDGET" in orderList
            if (hasWidget) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTabChanged("WIDGET") },
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = widgetCardColor),
                    border = BorderStroke(1.dp, bentoBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = if (settings.language == "ID") "Kategori Utama" else "Main Categories",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = bentoText
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("Work", "Lifestyle", "Finance").forEach { tag ->
                                    Box(
                                        modifier = Modifier
                                            .background(if (isDark) Color(0xFF2C3228) else Color(0xFFF0F4E9), RoundedCornerShape(8.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(tag, fontSize = 9.sp, fontWeight = FontWeight.Medium, color = bentoText)
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (isDark) Color(0xFFD7E8CD) else Color(0xFF386A20))
                                .clickable { onTabChanged("WIDGET") },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Widget",
                                tint = if (isDark) Color(0xFF191D17) else Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ======================== FINANCE SCREEN MODULE ========================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val transactions by viewModel.transactions.collectAsState()
    val financeCategories by viewModel.financeCategories.collectAsState()
    val assets by viewModel.assets.collectAsState()

    val type = remember { mutableStateOf("EXPENSE") }
    val amount = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val source = remember { mutableStateOf("") }
    val extraNote = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val isDark = isSystemInDarkTheme() || settings.themeMode == "DARK"
    val bentoTextSecondary = if (isDark) Color(0xFF9EA398) else Color(0xFF55624C)

    // Initialize dates values using simple tools
    LaunchedEffect(Unit) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date.value = sdf.format(Date())
        if (financeCategories.isNotEmpty()) category.value = financeCategories.first().name
        if (assets.isNotEmpty()) source.value = assets.first().name
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { p ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
         ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = Loc.tr("finance", settings.language),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // BENTO STYLE FINANCIAL PORTFOLIO SUMMARY WIDGETS
                item {
                    val incomeSum = transactions.filter { it.type == "INCOME" }.sumOf { it.amount }
                    val expenseSum = transactions.filter { it.type == "EXPENSE" }.sumOf { it.amount }
                    val netBalance = incomeSum - expenseSum

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E241B) else Color.White),
                            border = BorderStroke(1.dp, if (isDark) Color(0xFF2C3228) else Color(0xFFDDE4D6))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (settings.language == "ID") "TOTAL SALDO BERSIH" else "TOTAL NET BALANCE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = bentoTextSecondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${settings.currency} ${netBalance.toInt()}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (netBalance >= 0) Color(0xFF10B981) else Color(0xFFEF4444)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF24361C) else Color(0xFFD7E8CD))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = if (settings.language == "ID") "Pemasukan" else "Income",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDark) Color(0xFFD7E8CD) else Color(0xFF386A20)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "${settings.currency} ${incomeSum.toInt()}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isDark) Color(0xFF10B981) else Color(0xFF386A20)
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF452B14) else Color(0xFFFCE8D5))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = if (settings.language == "ID") "Pengeluaran" else "Expense",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isDark) Color(0xFFFCE8D5) else Color(0xFF855400)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "${settings.currency} ${expenseSum.toInt()}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isDark) Color(0xFFEF4444) else Color(0xFF855400)
                                    )
                                }
                            }
                        }
                    }
                }

                // TRANSACTION RECORDER REGISTRY CARD
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Recorder",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            // Switch Selector type Tab
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(if (type.value == "INCOME") MaterialTheme.colorScheme.primary else Color.Transparent)
                                        .clickable { type.value = "INCOME" }
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Pemasukan",
                                        fontWeight = FontWeight.Bold,
                                        color = if (type.value == "INCOME") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(if (type.value == "EXPENSE") MaterialTheme.colorScheme.primary else Color.Transparent)
                                        .clickable { type.value = "EXPENSE" }
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Pengeluaran",
                                        fontWeight = FontWeight.Bold,
                                        color = if (type.value == "EXPENSE") MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            // Amount Text field
                            OutlinedTextField(
                                value = amount.value,
                                onValueChange = { amount.value = it },
                                label = { Text(Loc.tr("amount", settings.language)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                prefix = { Text("${settings.currency} ") }
                            )

                            // Category Selector Placeholder Dropdown
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = category.value,
                                    onValueChange = { category.value = it },
                                    label = { Text(Loc.tr("category", settings.language)) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = source.value,
                                    onValueChange = { source.value = it },
                                    label = { Text(Loc.tr("asset", settings.language)) },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }

                            OutlinedTextField(
                                value = extraNote.value,
                                onValueChange = { extraNote.value = it },
                                label = { Text("Keterangan Tambahan") },
                                placeholder = { Text(Loc.tr("note_placeholder", settings.language)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            Button(
                                onClick = {
                                    val amt = amount.value.toDoubleOrNull()
                                    if (amt == null || amt <= 0 || category.value.isBlank() || source.value.isBlank()) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(Loc.tr("add_failed", settings.language))
                                        }
                                    } else {
                                        viewModel.addTransaction(
                                            type = type.value,
                                            amount = amt,
                                            category = category.value,
                                            source = source.value,
                                            note = extraNote.value,
                                            date = date.value
                                        )
                                        amount.value = ""
                                        extraNote.value = ""
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Transaction registered successfully!")
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(Loc.tr("add_btn", settings.language), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // HISTORIC LIST OF TRANSACTIONS
                if (transactions.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Belum ada transaksi terekam harian.", color = Color.Gray, fontSize = 13.sp)
                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            text = if (settings.language == "ID") "Riwayat Transaksi (Bento Grid)" else "Transaction Records (Bento Grid)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = bentoTextSecondary,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )
                    }
                    val chunks = transactions.chunked(2)
                    items(chunks) { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowItems.forEach { tr ->
                                val cardBg = if (tr.type == "INCOME") {
                                    if (isDark) Color(0xFF1B2E1E) else Color(0xFFE8F5E9)
                                } else {
                                    if (isDark) Color(0xFF351C1C) else Color(0xFFFFEBEE)
                                }
                                val accentColor = if (tr.type == "INCOME") Color(0xFF10B981) else Color(0xFFEF4444)
                                
                                Card(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = cardBg),
                                    border = BorderStroke(1.dp, if (isDark) Color(0xFF2C3228) else Color(0xFFF3F3F3))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = tr.category,
                                                fontWeight = FontWeight.Black,
                                                fontSize = 13.sp,
                                                color = if (isDark) Color.White else Color.Black,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f)
                                            )
                                            IconButton(
                                                onClick = { viewModel.deleteTransaction(tr) },
                                                modifier = Modifier.size(20.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Hapus",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = tr.sourceOrAsset,
                                            fontSize = 11.sp,
                                            color = bentoTextSecondary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = tr.date,
                                            fontSize = 9.sp,
                                            color = Color.Gray
                                        )
                                        if (tr.note.isNotBlank()) {
                                            Text(
                                                text = tr.note,
                                                fontSize = 10.sp,
                                                color = bentoTextSecondary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        val sign = if (tr.type == "INCOME") "+" else "-"
                                        Text(
                                            text = "$sign${settings.currency} ${tr.amount.toInt()}",
                                            color = accentColor,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                            if (rowItems.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

// ======================== NOTES SCREEN MODULE ========================

@Composable
fun NotesScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val notes by viewModel.notes.collectAsState()

    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("General") }
    var expandedNoteId by remember { mutableStateOf<Int?>(null) }
    val isDark = isSystemInDarkTheme() || settings.themeMode == "DARK"
    val bentoTextSecondary = if (isDark) Color(0xFF9EA398) else Color(0xFF55624C)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = Loc.tr("note", settings.language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        // CREATE NEW NOTE COMPOSER
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Catat Ide Pikiran Baru", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Judul Catatan") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = content.value,
                        onValueChange = { content.value = it },
                        label = { Text("Isi Catatan") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        minLines = 2
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = category.value,
                            onValueChange = { category.value = it },
                            label = { Text("Kategori") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Button(
                            onClick = {
                                if (title.value.isNotBlank() && content.value.isNotBlank()) {
                                    viewModel.saveNote(
                                        title = title.value,
                                        content = content.value,
                                        category = category.value,
                                        isPinned = false
                                    )
                                    title.value = ""
                                    content.value = ""
                                }
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Add, "Save")
                            Text("Simpan Note")
                        }
                    }
                }
            }
        }

        // RENDER LIST OF SAVED NOTES
        if (notes.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Belum ada catatan terekam dalam asisten Anda.", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        } else {
            item {
                Text(
                    text = if (settings.language == "ID") "Semua Catatan (Bento Grid)" else "All Notes (Bento Grid)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = bentoTextSecondary,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            val chunks = notes.chunked(2)
            items(chunks) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { note ->
                        val cardBg = if (note.isPinned) {
                            if (isDark) Color(0xFF24361C) else Color(0xFFD7E8CD)
                        } else {
                            if (isDark) Color(0xFF1E241B) else Color(0xFFFFFFFF)
                        }
                        val borderCol = if (note.isPinned) {
                            if (isDark) Color(0xFF386A20) else Color(0xFFB5D1A2)
                        } else {
                            if (isDark) Color(0xFF2C3228) else Color(0xFFDDE4D6)
                        }
                        
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    expandedNoteId = if (expandedNoteId == note.id) null else note.id
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            border = BorderStroke(1.dp, borderCol)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    IconButton(
                                        onClick = {
                                            viewModel.saveNote(
                                                id = note.id,
                                                title = note.title,
                                                content = note.content,
                                                category = note.category,
                                                isPinned = !note.isPinned
                                            )
                                        },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (note.isPinned) Icons.Default.PushPin else Icons.Outlined.PushPin,
                                            contentDescription = "Pin toggles",
                                            tint = if (note.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = { viewModel.deleteNote(note) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, "Hapus Note", tint = Color.Gray, modifier = Modifier.size(14.dp))
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(4.dp))
                                
                                Text(
                                    text = note.title,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    color = if (isDark) Color.White else Color.Black,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                
                                Spacer(modifier = Modifier.height(2.dp))
                                
                                Text(
                                    text = note.category,
                                    fontSize = 9.sp,
                                    color = if (note.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                    modifier = Modifier
                                        .background(if (note.isPinned) Color(0x1F000000) else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val isExpanded = expandedNoteId == note.id || note.content.length < 40
                                Text(
                                    text = if (isExpanded) note.content else note.content.take(35) + "...",
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = if (isDark) Color(0xFFD1D5DB) else Color(0xFF374151)
                                )
                            }
                        }
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// ======================== TODO LIST / MANAGE DUTY ========================

@Composable
fun TodoListScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val todos by viewModel.todos.collectAsState()

    val isDark = isSystemInDarkTheme() || settings.themeMode == "DARK"
    val bentoTextSecondary = if (isDark) Color(0xFF9EA398) else Color(0xFF55624C)

    val title = remember { mutableStateOf("") }
    val priority = remember { mutableStateOf("MEDIUM") }
    val category = remember { mutableStateOf("Personal") }
    val wantsNotification = remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = Loc.tr("todo", settings.language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        // ADD NEW TASK DISPATCHER CARD
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(Loc.tr("todo_header", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Kegiatan / Tugas Harian") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Priority Choices Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val priorities = listOf("HIGH", "MEDIUM", "LOW")
                        priorities.forEach { p ->
                            val isSel = priority.value == p
                            val color = when (p) {
                                "HIGH" -> Color(0xFFEF4444)
                                "MEDIUM" -> Color(0xFFF59E0B)
                                else -> Color(0xFF10B981)
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) color else MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { priority.value = p }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = p,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) Color.White else Color.Black
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Checkbox(
                                checked = wantsNotification.value,
                                onCheckedChange = { wantsNotification.value = it }
                            )
                            Text("Simulasikan pengingat alarm harian", fontSize = 11.sp)
                        }

                        Button(
                            onClick = {
                                if (title.value.isNotBlank()) {
                                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    val currDate = sdf.format(Date())
                                    viewModel.saveTodo(
                                        title = title.value,
                                        isCompleted = false,
                                        dueDate = currDate,
                                        dueTime = "09:00",
                                        priority = priority.value,
                                        category = category.value,
                                        wantsNotification = wantsNotification.value
                                    )
                                    title.value = ""
                                }
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Daftarkan")
                        }
                    }
                }
            }
        }

        // RENDER TASKS BOARD
        if (todos.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Yey! Tidak ada tugas tersisa hari ini.", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        } else {
            item {
                Text(
                    text = if (settings.language == "ID") "Daftar Tugas (Bento Grid)" else "Task Items (Bento Grid)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = bentoTextSecondary,
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }
            val chunks = todos.chunked(2)
            items(chunks) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { todo ->
                        val cardBg = if (todo.isCompleted) {
                            if (isDark) Color(0xFF131610) else Color(0xFFF7FBF1)
                        } else {
                            val badgeColor = when (todo.priority) {
                                "HIGH" -> if (isDark) Color(0xFF5B1B1B) else Color(0xFFFFEBEE)
                                "MEDIUM" -> if (isDark) Color(0xFF5B3913) else Color(0xFFFFF3E0)
                                else -> if (isDark) Color(0xFF1B3D1F) else Color(0xFFE8F5E9)
                            }
                            badgeColor
                        }
                        val borderCol = if (todo.isCompleted) {
                            if (isDark) Color(0xFF2C3228) else Color(0xFFE2E3DD)
                        } else {
                            when (todo.priority) {
                                "HIGH" -> if (isDark) Color(0xFFEF4444) else Color(0xFFFFCDD2)
                                "MEDIUM" -> if (isDark) Color(0xFFF59E0B) else Color(0xFFFFE0B2)
                                else -> if (isDark) Color(0xFF10B981) else Color(0xFFC8E6C9)
                            }
                        }
                        
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            border = BorderStroke(1.dp, borderCol)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = todo.isCompleted,
                                        onCheckedChange = { viewModel.toggleTodoCompleted(todo) }
                                    )
                                    IconButton(
                                        onClick = { viewModel.deleteTodo(todo) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, "Delete", tint = Color.Gray, modifier = Modifier.size(14.dp))
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(4.dp))
                                
                                Text(
                                    text = todo.title,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp,
                                    color = if (todo.isCompleted) Color.Gray else (if (isDark) Color.White else Color.Black),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                
                                Spacer(modifier = Modifier.height(6.dp))
                                
                                val prioBadgeColor = when (todo.priority) {
                                    "HIGH" -> Color(0xFFEF4444)
                                    "MEDIUM" -> Color(0xFFF59E0B)
                                    else -> Color(0xFF10B981)
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = todo.priority,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier
                                            .background(prioBadgeColor, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                    if (todo.wantsNotification) {
                                        Text(
                                            text = "🔔 Alarm",
                                            fontSize = 8.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// ======================== REFLECTION JOURNAL MOCK DIARY ========================

@Composable
fun DiaryScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val diaryEntries by viewModel.diaryEntries.collectAsState()

    val isDark = isSystemInDarkTheme() || settings.themeMode == "DARK"
    val bentoTextSecondary = if (isDark) Color(0xFF9EA398) else Color(0xFF55624C)

    val reflection = remember { mutableStateOf("") }
    val mood = remember { mutableStateOf("😃 Happy") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { p ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = Loc.tr("diary", settings.language),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                // JOURNAL REFLECT EDITOR CARD
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(Loc.tr("mood", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                            // Mood Grid list
                            val moods = listOf("😃 Happy", "😴 Tired", "😇 Relaxed", "💡 Inspired", "😔 Sad")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                moods.forEach { m ->
                                    val isSel = mood.value == m
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                            .clickable { mood.value = m }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = m,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSel) Color.White else Color.Black
                                        )
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = reflection.value,
                                onValueChange = { reflection.value = it },
                                label = { Text("Tuliskan refleksi di sini") },
                                placeholder = { Text(Loc.tr("reflection", settings.language)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                minLines = 3
                            )

                            Button(
                                onClick = {
                                    if (reflection.value.isNotBlank()) {
                                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        val dateStr = sdf.format(Date())
                                        viewModel.saveDiary(
                                            reflection = reflection.value,
                                            mood = mood.value,
                                            date = dateStr,
                                            isSynced = false
                                        )
                                        reflection.value = ""
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Jurnal Anda tersimpan dalam memori lokal!")
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(Loc.tr("save", settings.language))
                            }
                        }
                    }
                }

                // DIARY SAVED REFLECTIONS LIST
                if (diaryEntries.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                        ) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Kosong. Catat refleksi emosional Anda saat ini.", color = Color.Gray, fontSize = 13.sp)
                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            text = if (settings.language == "ID") "Refleksi Diary (Bento Grid)" else "Diary Reflections (Bento Grid)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = bentoTextSecondary,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )
                    }
                    val chunks = diaryEntries.chunked(2)
                    items(chunks) { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowItems.forEach { d ->
                                Card(
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = if (isDark) Color(0xFF1E241B) else Color.White),
                                    border = BorderStroke(1.dp, if (isDark) Color(0xFF2C3228) else Color(0xFFDDE4D6))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(d.mood, fontSize = 20.sp)
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        if (!d.isSynced) {
                                                            viewModel.syncDiary(d)
                                                            scope.launch {
                                                                snackbarHostState.showSnackbar("Sinkronisasi cloud aman berhasil!")
                                                            }
                                                        }
                                                    },
                                                    modifier = Modifier.size(20.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = if (d.isSynced) Icons.Default.CloudDone else Icons.Default.CloudUpload,
                                                        contentDescription = "Sync controller",
                                                        tint = if (d.isSynced) Color(0xFF10B981) else Color.Gray,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                }
                                                IconButton(
                                                    onClick = { viewModel.deleteDiary(d) },
                                                    modifier = Modifier.size(20.dp)
                                                ) {
                                                    Icon(Icons.Default.Delete, "Hapus", tint = Color.Gray, modifier = Modifier.size(14.dp))
                                                }
                                            }
                                        }
                                        
                                        Spacer(modifier = Modifier.height(6.dp))
                                        
                                        Text(
                                            text = d.date,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = bentoTextSecondary
                                        )
                                        
                                        Spacer(modifier = Modifier.height(4.dp))
                                        
                                        Text(
                                            text = d.reflection,
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp,
                                            color = if (isDark) Color(0xFFD1D5DB) else Color(0xFF374151),
                                            maxLines = 4,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            if (rowItems.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

// ======================== CUSTOM VISUAL REPORTS COMPONENT ========================

@Composable
fun ReportsScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val transactions by viewModel.transactions.collectAsState()
    val todos by viewModel.todos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = Loc.tr("reports", settings.language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        // CUSTOM FINANCIAL EXPENDITURES CHART (Compose Canvas)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Loc.tr("reports_title", settings.language),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (transactions.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(Loc.tr("no_data", settings.language), color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
                        }
                    } else {
                        // Drawing custom dynamic expenses path graph
                        val expenseTrList = transactions.filter { it.type == "EXPENSE" }.take(5).reversed()
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        ) {
                            val maxAmt = (expenseTrList.maxOfOrNull { it.amount } ?: 1000.0).toFloat().coerceAtLeast(100f)
                            val sizeX = size.width
                            val sizeY = size.height
                            val spacing = sizeX / (expenseTrList.size.coerceAtLeast(2) - 1).coerceAtLeast(1)

                            // Grid drawings background
                            for (i in 0..3) {
                                val depthY = sizeY * (i / 3f)
                                drawLine(
                                    color = Color.LightGray.copy(alpha = 0.5f),
                                    start = Offset(0f, depthY),
                                    end = Offset(sizeX, depthY),
                                    strokeWidth = 1f
                                )
                            }

                            // Curved drawing points
                            if (expenseTrList.isNotEmpty()) {
                                var lastPoint: Offset? = null
                                expenseTrList.forEachIndexed { sIdx, tr ->
                                    val drawX = sIdx * spacing
                                    val drawY = sizeY - (sizeY * (tr.amount.toFloat() / maxAmt))

                                    // Point circle draws
                                    drawCircle(
                                        color = Color(0xFFEF4444),
                                        radius = 6f,
                                        center = Offset(drawX, drawY)
                                    )

                                    if (lastPoint != null) {
                                        drawLine(
                                            color = Color(0xFFEF4444),
                                            start = lastPoint!!,
                                            end = Offset(drawX, drawY),
                                            strokeWidth = 4f,
                                            cap = StrokeCap.Round
                                        )
                                    }
                                    lastPoint = Offset(drawX, drawY)
                                }
                            }
                        }

                        // Labels
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            expenseTrList.forEach { tr ->
                                Text(tr.category.take(8), fontSize = 9.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        // PRODUCTIVITY TASK COMPLETION PIE ANALYSER CHART
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Loc.tr("productivity_title", settings.language),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (todos.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(Loc.tr("no_data", settings.language), color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
                        }
                    } else {
                        val completed = todos.count { it.isCompleted }
                        val active = todos.count { !it.isCompleted }
                        val pct = (completed * 360f) / todos.size

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Ring Pie draws
                            Canvas(modifier = Modifier.size(100.dp)) {
                                val size = size
                                drawArc(
                                    color = Color.LightGray.copy(alpha = 0.3f),
                                    startAngle = 0f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = 16f)
                                )
                                drawArc(
                                    color = Color(0xFF10B981),
                                    startAngle = -90f,
                                    sweepAngle = pct,
                                    useCenter = false,
                                    style = Stroke(width = 16f, cap = StrokeCap.Round)
                                )
                            }

                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(modifier = Modifier.size(10.dp).background(Color(0xFF10B981), CircleShape))
                                    Text("Selesai ($completed)")
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(modifier = Modifier.size(10.dp).background(Color.LightGray, CircleShape))
                                    Text("Aktif ($active)")
                                }
                                Text("Total: ${todos.size} tugas", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ======================== MASTER DATA SEEDER SCREEN ========================

@Composable
fun MasterDataScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val financeCategories by viewModel.financeCategories.collectAsState()
    val assets by viewModel.assets.collectAsState()
    val incomeSources by viewModel.incomeSources.collectAsState()

    var activeMasterTab by remember { mutableStateOf("CATEGORY_FINANCE") }
    val newItemName = remember { mutableStateOf("") }

    val listing = when (activeMasterTab) {
        "CATEGORY_FINANCE" -> financeCategories
        "ASSET" -> assets
        else -> incomeSources
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = Loc.tr("master_data", settings.language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        // Horizontal toggle header Tabs
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(4.dp)
            ) {
                listOf(
                    "CATEGORY_FINANCE" to "Keuangan",
                    "ASSET" to "Dompet/Aset",
                    "SOURCE_INCOME" to "Penghasilan"
                ).forEach { item ->
                    val isSel = activeMasterTab == item.first
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSel) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { activeMasterTab = item.first }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.second,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // ADD NEW MASTER DATA CONTROL
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newItemName.value,
                        onValueChange = { newItemName.value = it },
                        placeholder = { Text("Nama Isian Baru...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            if (newItemName.value.isNotBlank()) {
                                viewModel.addMasterDataItem(activeMasterTab, newItemName.value)
                                newItemName.value = ""
                            }
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Simpan")
                    }
                }
            }
        }

        items(listing) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.name, fontWeight = FontWeight.SemiBold)
                    IconButton(
                        onClick = { viewModel.deleteMasterDataItem(item) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.Delete, "Delete", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

// ======================== CUSTOMIZATION PALETTE LAYOUTS ========================

@Composable
fun CustomizationScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val items = listOf("EMERALD", "OCEAN", "SUNSET", "SLATE", "PURPLE")
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = Loc.tr("customization", settings.language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
        }

        // PRIMARY SYSTEM THEME COLOR PALETTE SELECTION
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Pilih Vibe Skema Warna Aplikasi", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))

                    items.forEach { colorName ->
                        val isCurrent = settings.dashboardOrder.startsWith(colorName)
                        val colorRes = when (colorName) {
                            "EMERALD" -> Color(0xFF047857)
                            "OCEAN" -> Color(0xFF1D4ED8)
                            "SUNSET" -> Color(0xFFDC2626)
                            "SLATE" -> Color(0xFF334155)
                            else -> Color(0xFF6650A4)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isCurrent) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
                                .clickable {
                                    // Update layout sorting settings mimicking theme shifts
                                    val splitted = settings.dashboardOrder.split("|", limit = 2)
                                    val newOrder = colorName + "|" + (splitted.getOrNull(1) ?: "FINANCE,TODOS,NOTES,DIARY,WIDGET,REPORTS")
                                    viewModel.updateDashboardOrder(newOrder)
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(modifier = Modifier.size(24.dp).background(colorRes, CircleShape))
                            Text(colorName, fontWeight = FontWeight.Bold)
                            if (isCurrent) {
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(Icons.Default.Check, "Selected", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }

        // TACTILE DRAG REORDING DEMO
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(Loc.tr("dashboard_drag", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text("Ketuk item untuk memindahkan kartu ke prioritas paling atas dasbor:", fontSize = 12.sp, color = Color.Gray)

                    val itemsOrder = settings.dashboardOrder.split("|").getOrNull(1)?.split(",") ?: listOf("FINANCE", "TODOS", "NOTES", "DIARY", "WIDGET", "REPORTS")
                    itemsOrder.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    val mutable = itemsOrder.toMutableList()
                                    mutable.remove(item)
                                    mutable.add(0, item)
                                    val stringed = mutable.joinToString(",")
                                    val activeColor = settings.dashboardOrder.split("|").getOrNull(0) ?: "PURPLE"
                                    viewModel.updateDashboardOrder("$activeColor|$stringed")
                                }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.DragHandle, "Move")
                                Text(item, fontWeight = FontWeight.SemiBold)
                            }
                            Icon(Icons.Default.KeyboardArrowUp, "Move Up", tint = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

// ======================== DETACTIBLE HOME LAUNCHER WIDGET PREVIEW ========================

@Composable
fun WidgetSpecScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val scope = rememberCoroutineScope()
    val snackHost = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHost) }
    ) { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = Loc.tr("widget", settings.language),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )

            Text(Loc.tr("widget_desc", settings.language), fontSize = 13.sp, color = Color.Gray)

            // SIMULATOR DESKTOP LAUNCHER PREVIEW
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)) // Space Obsidian black colors
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Sky starts drawing canvas background
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(color = Color(0x1F38BDF8), center = Offset(size.width * 0.8f, size.height * 0.2f), radius = 120f)
                    }

                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Mock Launcher Home Interactive Screen", color = Color.LightGray, fontSize = 11.sp)

                        // Visual Widget Option 1: Balance tracker
                        Card(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            colors = CardDefaults.cardColors(containerColor = Color(0x3DFFFFFF)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Psychology, "Logo", tint = Color(0xFF38BDF8), modifier = Modifier.size(16.dp))
                                    Text("Brain Wallet Widget", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Active Balance Tracker", color = Color.LightGray, fontSize = 9.sp)
                                Text("${settings.currency} 4,820,000", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black)
                            }
                        }

                        // Visual Widget Option 2: Active Task Board
                        Card(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            colors = CardDefaults.cardColors(containerColor = Color(0x3DFFFFFF)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.FactCheck, "Logo", tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                                    Text("Brain Todo Quick Tracker", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Uncompleted Jobs:", color = Color.LightGray, fontSize = 10.sp)
                                    Text("3 Tasks", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                scope.launch {
                                    snackHost.showSnackbar(Loc.tr("widget_placed", settings.language))
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(Loc.tr("configure_widget", settings.language))
                        }
                    }
                }
            }
        }
    }
}

// ======================== APP SETTINGS & CONFIGS ========================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity,
    onLogout: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { p ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = Loc.tr("settings", settings.language),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
            }

            // APP PREFERENCE SETTINGS CARDS
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(Loc.tr("language", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("ID" to "Bahasa Indonesia", "EN" to "English").forEach { item ->
                                val isSel = settings.language == item.first
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                        .clickable { viewModel.updateLanguage(item.first) }
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(item.second, fontWeight = FontWeight.Bold, color = if (isSel) Color.White else Color.Black, fontSize = 11.sp)
                                }
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // Modes Light and Dark switches
                        Text(Loc.tr("theme", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("LIGHT" to "Terang", "DARK" to "Gelap", "SYSTEM" to "Sistem").forEach { item ->
                                val isSel = settings.themeMode == item.first
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                        .clickable { viewModel.updateThemeMode(item.first) }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(item.second, fontWeight = FontWeight.Bold, color = if (isSel) Color.White else Color.Black, fontSize = 11.sp)
                                }
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // Currencies Selector preferences
                        Text(Loc.tr("currency", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("IDR" to "Rp (IDR)", "USD" to "$ (USD)", "EUR" to "€ (EUR)").forEach { item ->
                                val isSel = settings.currency == item.first
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                        .clickable { viewModel.updateCurrency(item.first) }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(item.second, fontWeight = FontWeight.Bold, color = if (isSel) Color.White else Color.Black, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // MOCK INTEGRATIONS & NOTIFICATION SWITCHES
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text("Integrations config", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(Loc.tr("notif_enabled", settings.language), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Switch(
                                checked = settings.notificationEnabled,
                                onCheckedChange = { viewModel.updateNotificationToggle(it) }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(Loc.tr("sync_enabled", settings.language), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Switch(
                                checked = settings.syncEnabled,
                                onCheckedChange = { viewModel.updateSyncToggle(it) }
                            )
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    snackbar.showSnackbar("Synchronization session triggered! All local data is verified.")
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Trigger Cloud Backup Synchronization")
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        viewModel.logout()
                        onLogout()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Logout, "Log out")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout from Device")
                }
            }
        }
    }
}

// ======================== PROFILE CONFIGURATION ========================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val activeUser by viewModel.activeUser.collectAsState()

    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val avatarEmoji = remember { mutableStateOf("🙋‍♂️") }

    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(activeUser) {
        activeUser?.let {
            name.value = it.name
            avatarEmoji.value = it.profilePhotoUri ?: "🙋‍♂️"
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { p ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = Loc.tr("profile", settings.language),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
            }

            // AVATAR PICKER ROW
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(avatarEmoji.value, fontSize = 42.sp)
                        }

                        Text("Select custom Emoji avatar:", fontSize = 12.sp, color = Color.Gray)

                        val emojis = listOf("🙋‍♂️", "🤠", "👽", "🚀", "🐼", "🦊", "👑", "💡")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            emojis.forEach { e ->
                                Text(
                                    text = e,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .clickable { avatarEmoji.value = e }
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // USER METRICS CARD SETUP
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(Loc.tr("profile", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(Loc.tr("edit_profile_desc", settings.language), fontSize = 12.sp, color = Color.Gray)

                        OutlinedTextField(
                            value = name.value,
                            onValueChange = { name.value = it },
                            label = { Text("Display Name") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = { Text("New password (Leave blank to keep current)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Button(
                            onClick = {
                                if (name.value.isNotBlank()) {
                                    viewModel.updateProfile(name.value, avatarEmoji.value, password.value)
                                    scope.launch {
                                        snackbar.showSnackbar("Profile details saved securely!")
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(Loc.tr("save", settings.language))
                        }
                    }
                }
            }
        }
    }
}

// ======================== PRODUCT INTERACTIVE MANUAL HELP CENTER ========================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(
    viewModel: BrainViewModel,
    settings: SettingsEntity
) {
    val reportContent = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbar = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { p ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(p)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = Loc.tr("help", settings.language),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black
                )
            }

            // PANDUAN MANUAL
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(Loc.tr("help_manual", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        listOf(
                            "📚 Keuangan" to "Gunakan tab Keuangan untuk mencatat pengeluaran Anda. SSaldo diringkas di Dasbor secara langsung.",
                            "✏️ Note & Diary" to "Tab note menyimpan gagasan ide kasar, tab diary reflektif merekam emosi harian Anda.",
                            "🚀 Kustomisasi" to "Menu Customization memungkinkan Anda melacak letak layout menu prioritas di dasbor."
                        ).forEach { guild ->
                            Column {
                                Text(guild.first, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(guild.second, fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }

            // TICKET SYSTEM BUG REPORTING
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(Loc.tr("submit_ticket", settings.language), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        OutlinedTextField(
                            value = reportContent.value,
                            onValueChange = { reportContent.value = it },
                            placeholder = { Text("Tulis rincian kendala teknis / fitur di sini...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            minLines = 3
                        )

                        Button(
                            onClick = {
                                if (reportContent.value.isNotBlank()) {
                                    reportContent.value = ""
                                    scope.launch {
                                        snackbar.showSnackbar(Loc.tr("ticket_success", settings.language))
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Kirim Laporan Kendala")
                        }
                    }
                }
            }
        }
    }
}

// ======================== AI SMART DRAWER PANEL OVERLAY ========================

@Composable
fun AiAssistantDrawer(
    viewModel: BrainViewModel,
    settings: SettingsEntity,
    onClose: () -> Unit
) {
    val aiLogs by viewModel.aiChatHistory.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val userQuery = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onClose() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .clickable(enabled = false) {}, // Prevent taps escaping
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header drawer bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color(0xFF38BDF8), CircleShape)
                        )
                        Text(
                            text = "Second Brain AI Assistant",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                    }

                    Row {
                        IconButton(onClick = { viewModel.clearAiLogs() }) {
                            Icon(Icons.Default.ClearAll, "Clear dialog", tint = Color.Gray)
                        }
                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, "Close assistant Dialog", tint = Color.Gray)
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 10.dp))

                // Converse bubble streams lines
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = Loc.tr("ai_greeting", settings.language),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    // Prompt Suggestions Clickers triggers context
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("ai_prompt_suggestion_1", "ai_prompt_suggestion_2", "ai_prompt_suggestion_3").forEach { propKey ->
                                val suggText = Loc.tr(propKey, settings.language)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                                        .clickable { viewModel.askSecondBrainAI(suggText) }
                                        .padding(8.dp)
                                ) {
                                    Text(suggText, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }

                    items(aiLogs) { msg ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            // User Message card bubble
                            Box(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(10.dp)
                            ) {
                                Text(msg.first, color = Color.White, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // AI response card bubble
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(10.dp)
                            ) {
                                Text(msg.second, fontSize = 12.sp)
                            }
                        }
                    }

                    if (isAiLoading) {
                        item {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }

                // Interaction controls inputs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    OutlinedTextField(
                        value = userQuery.value,
                        onValueChange = { userQuery.value = it },
                        placeholder = { Text("Tanyakan asisten tentang produktivitas...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = {
                            if (userQuery.value.isNotBlank()) {
                                viewModel.askSecondBrainAI(userQuery.value)
                                userQuery.value = ""
                            }
                        })
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (userQuery.value.isNotBlank()) {
                                viewModel.askSecondBrainAI(userQuery.value)
                                userQuery.value = ""
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .size(52.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Default.Send, "Send", tint = Color.White)
                    }
                }
            }
        }
    }
}

// ======================== TUPLES HELPERS ========================

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
