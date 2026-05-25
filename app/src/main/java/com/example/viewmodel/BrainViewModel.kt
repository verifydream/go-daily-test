package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BrainViewModel(private val repository: AppRepository) : ViewModel() {

    // Active User State
    val activeUser: StateFlow<UserEntity?> = repository.activeUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // App Settings State (default to a mock preset if null)
    val settingsState: StateFlow<SettingsEntity> = repository.settings
        .map { it ?: SettingsEntity() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsEntity())

    // Finance List & Computed Trends
    val transactions: StateFlow<List<FinanceEntity>> = repository.transactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Notes List
    val notes: StateFlow<List<NoteEntity>> = repository.notes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Todos List
    val todos: StateFlow<List<TodoEntity>> = repository.todos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Diary Entries List
    val diaryEntries: StateFlow<List<DiaryEntity>> = repository.diaryEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Master Data Lists (Dynamic Filtered flows)
    val financeCategories: StateFlow<List<MasterDataEntity>> = repository.getMasterDataByType("CATEGORY_FINANCE")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val assets: StateFlow<List<MasterDataEntity>> = repository.getMasterDataByType("ASSET")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val incomeSources: StateFlow<List<MasterDataEntity>> = repository.getMasterDataByType("SOURCE_INCOME")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // AI Chat History logs
    private val _aiChatHistory = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val aiChatHistory: StateFlow<List<Pair<String, String>>> = _aiChatHistory.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    // Authentication States
    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    // ---------------- AUTH OPERATIONS ----------------

    fun register(name: String, email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _authError.value = null
            if (name.isBlank() || email.isBlank() || pass.isBlank()) {
                _authError.value = "All fields are required"
                return@launch
            }
            val existing = repository.getUserByEmail(email)
            if (existing != null) {
                _authError.value = "Email is already registered"
                return@launch
            }
            val newUser = UserEntity(email = email, name = name, passwordHash = pass, isSessionActive = true)
            repository.registerUser(newUser)
            onSuccess()
        }
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _authError.value = null
            if (email.isBlank() || pass.isBlank()) {
                _authError.value = "Email and Password cannot be blank"
                return@launch
            }
            val user = repository.getUserByEmail(email)
            if (user == null) {
                // Prepopulate a sandbox user if they input 'demo' for easy offline accessibility
                if (email == "demo@example.com" || email == "demo") {
                    val sandbox = UserEntity(email = "demo@example.com", name = "Second Brain Sandbox", passwordHash = "demo", isSessionActive = true)
                    repository.registerUser(sandbox)
                    repository.loginUser("demo@example.com")
                    onSuccess()
                    return@launch
                }
                _authError.value = "User not found. Register a new account first!"
                return@launch
            }
            if (user.passwordHash != pass) {
                _authError.value = "Incorrect password"
                return@launch
            }
            repository.loginUser(email)
            onSuccess()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun updateProfile(name: String, photoUri: String?, pass: String) {
        viewModelScope.launch {
            val current = activeUser.value ?: return@launch
            val updatedUser = current.copy(
                name = name,
                profilePhotoUri = photoUri ?: current.profilePhotoUri,
                passwordHash = if (pass.isNotBlank()) pass else current.passwordHash
            )
            repository.saveUserProfile(updatedUser)
        }
    }

    // ---------------- FINANCE OPERATIONS ----------------

    fun addTransaction(type: String, amount: Double, category: String, source: String, note: String, date: String) {
        viewModelScope.launch {
            val entity = FinanceEntity(
                type = type,
                amount = amount,
                category = category,
                sourceOrAsset = source,
                note = note,
                date = date
            )
            repository.addTransaction(entity)
        }
    }

    fun deleteTransaction(transaction: FinanceEntity) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    // ---------------- NOTE OPERATIONS ----------------

    fun saveNote(id: Int = 0, title: String, content: String, category: String, isPinned: Boolean) {
        viewModelScope.launch {
            val entity = NoteEntity(
                id = if (id != 0) id else 0,
                title = title,
                content = content,
                category = category,
                isPinned = isPinned,
                updatedAt = System.currentTimeMillis()
            )
            repository.saveNote(entity)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    // ---------------- TODO OPERATIONS ----------------

    fun saveTodo(id: Int = 0, title: String, isCompleted: Boolean, dueDate: String, dueTime: String, priority: String, category: String, wantsNotification: Boolean) {
        viewModelScope.launch {
            val entity = TodoEntity(
                id = if (id != 0) id else 0,
                title = title,
                isCompleted = isCompleted,
                dueDate = dueDate,
                dueTime = dueTime,
                priority = priority,
                category = category,
                wantsNotification = wantsNotification
            )
            repository.saveTodo(entity)
        }
    }

    fun toggleTodoCompleted(todo: TodoEntity) {
        viewModelScope.launch {
            repository.saveTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    // ---------------- DIARY OPERATIONS ----------------

    fun saveDiary(id: Int = 0, reflection: String, mood: String, date: String, isSynced: Boolean = false) {
        viewModelScope.launch {
            val entity = DiaryEntity(
                id = if (id != 0) id else 0,
                reflection = reflection,
                mood = mood,
                date = date,
                isSynced = isSynced
            )
            repository.saveDiary(entity)
        }
    }

    fun deleteDiary(diary: DiaryEntity) {
        viewModelScope.launch {
            repository.deleteDiary(diary)
        }
    }

    fun syncDiary(diary: DiaryEntity) {
        viewModelScope.launch {
            // Simulate safe cloud backup sync
            repository.syncDiaryEntry(diary.id)
        }
    }

    // ---------------- MASTER DATA OPERATIONS ----------------

    fun addMasterDataItem(type: String, name: String) {
        viewModelScope.launch {
            if (name.isNotBlank()) {
                repository.addMasterData(MasterDataEntity(type = type, name = name))
            }
        }
    }

    fun deleteMasterDataItem(item: MasterDataEntity) {
        viewModelScope.launch {
            repository.deleteMasterData(item)
        }
    }

    // ---------------- SETTINGS & LAYOUT CUSTOMIZATION ----------------

    fun updateLanguage(lang: String) {
        viewModelScope.launch {
            val cur = settingsState.value
            repository.saveSettings(cur.copy(language = lang))
        }
    }

    fun updateThemeMode(mode: String) {
        viewModelScope.launch {
            val cur = settingsState.value
            repository.saveSettings(cur.copy(themeMode = mode))
        }
    }

    fun updateCurrency(curr: String) {
        viewModelScope.launch {
            val cur = settingsState.value
            repository.saveSettings(cur.copy(currency = curr))
        }
    }

    fun updateNotificationToggle(enabled: Boolean) {
        viewModelScope.launch {
            val cur = settingsState.value
            repository.saveSettings(cur.copy(notificationEnabled = enabled))
        }
    }

    fun updateSyncToggle(enabled: Boolean) {
        viewModelScope.launch {
            val cur = settingsState.value
            repository.saveSettings(cur.copy(syncEnabled = enabled))
        }
    }

    fun updateDashboardOrder(order: String) {
        viewModelScope.launch {
            val cur = settingsState.value
            repository.saveSettings(cur.copy(dashboardOrder = order))
        }
    }

    // ---------------- AI ASSISTANT COGNITIVE INTERACTION ----------------

    fun askSecondBrainAI(userPrompt: String) {
        if (userPrompt.isBlank()) return
        _isAiLoading.value = true
        _aiChatHistory.value = _aiChatHistory.value + (userPrompt to "Thinking...")

        viewModelScope.launch {
            // Build rich contextual information regarding the user database to pass into Gemini as background schema
            val activeTodos = todos.value.filter { !it.isCompleted }
            val financeTotalInc = transactions.value.filter { it.type == "INCOME" }.sumOf { it.amount }
            val financeTotalExp = transactions.value.filter { it.type == "EXPENSE" }.sumOf { it.amount }
            val currentCurrency = settingsState.value.currency
            val recentDiaryCount = diaryEntries.value.size
            val recentNotesCount = notes.value.size

            val contextData = """
                Finance status: Income Sum = $currentCurrency $financeTotalInc, Expense Sum = $currentCurrency $financeTotalExp. Net = $currentCurrency ${financeTotalInc - financeTotalExp}.
                Uncompleted Todos list: ${activeTodos.joinToString { "[Priority: ${it.priority}] ${it.title} due on ${it.dueDate}" }}
                User has written $recentNotesCount notes and $recentDiaryCount diary reflections.
                System Preferred Language: ${settingsState.value.language}
            """.trimIndent()

            val response = GeminiBrainHelper.getSecondBrainResponse(userPrompt, contextData)

            // Update state safely
            _aiChatHistory.value = _aiChatHistory.value.dropLast(1) + (userPrompt to response)
            _isAiLoading.value = false
        }
    }

    fun clearAiLogs() {
        _aiChatHistory.value = emptyList()
    }
}

// Custom factory class to inject repositories cleanly
class BrainViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BrainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class representation")
    }
}
