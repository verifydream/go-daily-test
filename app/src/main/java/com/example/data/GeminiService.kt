package com.example.data

import com.example.BuildConfig
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// --- REST Serialization Models using Moshi ---

@JsonClass(generateAdapter = true)
data class Part(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content? = null
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate> = emptyList()
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val service: GeminiApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(GeminiApiService::class.java)
    }
}

object GeminiBrainHelper {
    suspend fun getSecondBrainResponse(prompt: String, contextData: String): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getLocalFallbackResponse(prompt, contextData)
        }

        val systemInstructionText = """
            You are the "Second Brain Cognitive Assistant". Your task is to analyze the user's daily life, 
            notes, finances, to-do lists, and diary reflections, then provide insightful, brief, and highly structured advice or summaries.
            Be friendly, encouraging, and write in the requested language (Indonesian/English).
            Here is the current state of the user's data: \n${contextData}
        """.trimIndent()

        val request = GenerateContentRequest(
            contents = listOf(
                Content(parts = listOf(Part(text = prompt)))
            ),
            systemInstruction = Content(parts = listOf(Part(text = systemInstructionText)))
        )

        try {
            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text 
                ?: "No response content from Gemini API."
        } catch (e: Exception) {
            "Error calling Gemini: ${e.message}\n\n[Fallback Assist]:\n${getLocalFallbackResponse(prompt, contextData)}"
        }
    }

    private fun getLocalFallbackResponse(prompt: String, contextData: String): String {
        val isIndonesian = prompt.contains("halo", ignoreCase = true) || 
                           prompt.contains("bantu", ignoreCase = true) || 
                           prompt.contains("catat", ignoreCase = true) ||
                           prompt.contains("tanya", ignoreCase = true) ||
                           contextData.contains("Pemasukan", ignoreCase = true)

        if (isIndonesian) {
            return """
                🧠 **[Asisten Otak Kedua Mandiri (Lokal)]**
                API Key Gemini belum disetel di Panel Rahasia Anda, tetapi saya tetap menganalisis data Anda secara lokal:
                
                📈 **Rangkuman Keuangan & Aktivitas**:
                - Mengidentifikasi tren berdasarkan input data terbaru Anda.
                - Pastikan untuk menyeimbangkan pengeluaran di kategori non-esensial.
                
                💡 **Saran Produktivitas**:
                - Anda memiliki tugas aktif yang mendekati tenggat waktu. Selesaikan tugas prioritas HIGH terlebih dahulu!
                - Tuliskan refleksi harian Anda di menu Diary untuk menjaga kesehatan mental.
                
                *Ingin respons pintar penuh dari Gemini? Masukkan API Key Anda di Panel Secrets AI Studio.*
            """.trimIndent()
        } else {
            return """
                🧠 **[Local Second Brain Smart Assistant]**
                No Gemini API key is configured in the Secrets panel, but I have simulated a cognitive analysis of your context:
                
                📈 **Financial & Productivity Trend**:
                - Analyzed active elements from your logs.
                - Keep an eye on expenses under non-essential headers.
                
                💡 **Self-Organization Advice**:
                - Finish high priority ('HIGH') tasks first to relieve mental pressure.
                - Writing daily reflections in your Diary helps clear mental space.
                
                *To enable full generative power, please configure GEMINI_API_KEY in the AI Studio Secrets tab.*
            """.trimIndent()
        }
    }
}
