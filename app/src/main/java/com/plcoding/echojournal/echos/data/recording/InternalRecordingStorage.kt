package com.plcoding.echojournal.echos.data.recording

import android.content.Context
import com.plcoding.echojournal.echos.domain.recording.RecordingStorage
import com.plcoding.echojournal.echos.domain.recording.RecordingStorage.Companion.PERSISTENT_FILE_PREFIX
import com.plcoding.echojournal.echos.domain.recording.RecordingStorage.Companion.RECORDING_FILE_EXTENSION
import com.plcoding.echojournal.echos.domain.recording.RecordingStorage.Companion.TEMP_FILE_PREFIX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit

class InternalRecordingStorage(
    private val context: Context
) : RecordingStorage {

    override suspend fun savePersistently(tempFilePath: String): String? {
        val tempFile = File(tempFilePath)
        if (!tempFile.exists()) {
            Timber.e("The temporary file does not exist.")
            return null
        }
        return withContext(Dispatchers.IO) {
            try {
                val savedFile = generateSavedFile()
                tempFile.copyTo(savedFile)
                savedFile.absolutePath
            } catch (e: Exception) {
                Timber.e(e)
                null
            } finally {
                withContext(NonCancellable) {
                    cleanUpTemporaryFiles()
                }
            }
        }
    }

    override suspend fun cleanUpTemporaryFiles() {
        withContext(Dispatchers.IO) {
            context
                .cacheDir
                .listFiles()
                ?.filter { it.name.startsWith(TEMP_FILE_PREFIX) }
                ?.forEach { it.delete() }
        }
    }

    private fun generateSavedFile(): File {
        val timestamp = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString()
        return File(
            context.filesDir,
            "${PERSISTENT_FILE_PREFIX}_$timestamp.$RECORDING_FILE_EXTENSION"
        )
    }
}