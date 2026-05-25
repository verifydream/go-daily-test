package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// ======================== ENTITIES ========================

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val isSessionActive: Boolean = false,
    val profilePhotoUri: String? = null,
    val passwordHash: String
)

@Entity(tableName = "finance")
data class FinanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "INCOME" or "EXPENSE"
    val amount: Double,
    val category: String,
    val sourceOrAsset: String,
    val note: String,
    val date: String, // YYYY-MM-DD
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val category: String = "General",
    val isPinned: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val dueDate: String, // YYYY-MM-DD
    val dueTime: String, // HH:MM
    val priority: String, // "HIGH", "MEDIUM", "LOW"
    val category: String = "Personal",
    val wantsNotification: Boolean = true
)

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reflection: String,
    val mood: String, // "😃 Happy", "😴 Tired", "😇 Relaxed", "💡 Inspired", "😔 Sad"
    val date: String, // YYYY-MM-DD
    val isSynced: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "master_data")
data class MasterDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "CATEGORY_FINANCE", "ASSET", "SOURCE_INCOME"
    val name: String
)

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 1,
    val language: String = "ID", // "ID" (Indonesian), "EN" (English)
    val themeMode: String = "SYSTEM", // "LIGHT", "DARK", "SYSTEM"
    val currency: String = "IDR", // "IDR", "USD", "EUR"
    val notificationEnabled: Boolean = true,
    val syncEnabled: Boolean = true,
    val dashboardOrder: String = "FINANCE,TODOS,NOTES,DIARY,WIDGET,REPORTS" // Settings for customizable dashboard layout
)

// ======================== DAOS ========================

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE isSessionActive = 1 LIMIT 1")
    fun getActiveSessionUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET isSessionActive = 0")
    suspend fun clearActiveSessions()

    @Query("UPDATE users SET isSessionActive = 1 WHERE email = :email")
    suspend fun activateSession(email: String)
}

@Dao
interface FinanceDao {
    @Query("SELECT * FROM finance ORDER BY date DESC, id DESC")
    fun getAllTransactions(): Flow<List<FinanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: FinanceEntity)

    @Delete
    suspend fun deleteTransaction(transaction: FinanceEntity)

    @Query("DELETE FROM finance")
    suspend fun clearAll()
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY isPinned DESC, updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM notes")
    suspend fun clearAll()
}

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY isCompleted ASC, dueDate ASC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("DELETE FROM todos")
    suspend fun clearAll()
}

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date DESC, id DESC")
    fun getAllDiaryEntries(): Flow<List<DiaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: DiaryEntity)

    @Delete
    suspend fun deleteDiary(diary: DiaryEntity)

    @Query("UPDATE diary SET isSynced = 1 WHERE id = :id")
    suspend fun setSynced(id: Int)

    @Query("DELETE FROM diary")
    suspend fun clearAll()
}

@Dao
interface MasterDataDao {
    @Query("SELECT * FROM master_data")
    fun getAllMasterData(): Flow<List<MasterDataEntity>>

    @Query("SELECT * FROM master_data WHERE type = :type")
    fun getMasterDataByType(type: String): Flow<List<MasterDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMasterData(item: MasterDataEntity)

    @Delete
    suspend fun deleteMasterData(item: MasterDataEntity)
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1 LIMIT 1")
    fun getSettings(): Flow<SettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsEntity)
}

// ======================== DATABASE ========================

@Database(
    entities = [
        UserEntity::class,
        FinanceEntity::class,
        NoteEntity::class,
        TodoEntity::class,
        DiaryEntity::class,
        MasterDataEntity::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun financeDao(): FinanceDao
    abstract fun noteDao(): NoteDao
    abstract fun todoDao(): TodoDao
    abstract fun diaryDao(): DiaryDao
    abstract fun masterDataDao(): MasterDataDao
    abstract fun settingsDao(): SettingsDao
}

// ======================== REPOSITORY ========================

class AppRepository(private val db: AppDatabase) {
    // User Session
    val activeUser = db.userDao().getActiveSessionUser()
    suspend fun registerUser(user: UserEntity) = db.userDao().insertUser(user)
    suspend fun loginUser(email: String): Boolean {
        val user = db.userDao().getUserByEmail(email)
        return if (user != null) {
            db.userDao().clearActiveSessions()
            db.userDao().activateSession(email)
            true
        } else {
            false
        }
    }
    suspend fun getUserByEmail(email: String) = db.userDao().getUserByEmail(email)
    suspend fun logout() = db.userDao().clearActiveSessions()
    suspend fun saveUserProfile(user: UserEntity) = db.userDao().insertUser(user)

    // Finance
    val transactions = db.financeDao().getAllTransactions()
    suspend fun addTransaction(transaction: FinanceEntity) = db.financeDao().insertTransaction(transaction)
    suspend fun deleteTransaction(transaction: FinanceEntity) = db.financeDao().deleteTransaction(transaction)

    // Note
    val notes = db.noteDao().getAllNotes()
    suspend fun saveNote(note: NoteEntity) = db.noteDao().insertNote(note)
    suspend fun deleteNote(note: NoteEntity) = db.noteDao().deleteNote(note)

    // Todo
    val todos = db.todoDao().getAllTodos()
    suspend fun saveTodo(todo: TodoEntity) = db.todoDao().insertTodo(todo)
    suspend fun deleteTodo(todo: TodoEntity) = db.todoDao().deleteTodo(todo)

    // Diary
    val diaryEntries = db.diaryDao().getAllDiaryEntries()
    suspend fun saveDiary(diary: DiaryEntity) = db.diaryDao().insertDiary(diary)
    suspend fun deleteDiary(diary: DiaryEntity) = db.diaryDao().deleteDiary(diary)
    suspend fun syncDiaryEntry(id: Int) = db.diaryDao().setSynced(id)

    // Master Data
    val masterData = db.masterDataDao().getAllMasterData()
    fun getMasterDataByType(type: String) = db.masterDataDao().getMasterDataByType(type)
    suspend fun addMasterData(item: MasterDataEntity) = db.masterDataDao().insertMasterData(item)
    suspend fun deleteMasterData(item: MasterDataEntity) = db.masterDataDao().deleteMasterData(item)

    // Settings
    val settings = db.settingsDao().getSettings()
    suspend fun saveSettings(settings: SettingsEntity) = db.settingsDao().insertSettings(settings)

    // Setup initial system configuration and prepopulate master data if empty
    suspend fun prepopulateIfNeeded() {
        // Run checks or seed initial elements
        db.settingsDao().insertSettings(SettingsEntity())
    }
}
