package com.plcoding.echojournal.core.database.echo

import androidx.room.TypeConverter
import com.plcoding.echojournal.echos.ui.models.MoodUi

class MoodUiTypeConverter {

    @TypeConverter
    fun fromMood(mood: MoodUi): String {
        return mood.name
    }

    @TypeConverter
    fun toMood(moodName: String): MoodUi {
        return MoodUi.valueOf(moodName)
    }
}