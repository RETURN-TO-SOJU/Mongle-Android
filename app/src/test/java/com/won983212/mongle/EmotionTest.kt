package com.won983212.mongle

import android.content.res.Resources
import com.won983212.mongle.base.Emotion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EmotionTest {
    @Test
    fun emotion_of() {
        val emotions = Emotion.values()
        emotions.forEach {
            assertEquals(it, Emotion.of(it.id))
        }
    }

    @Test
    fun emotion_unknown_emotion_throw_exception() {
        assertThrows(Resources.NotFoundException::class.java) {
            Emotion.of("Unknown Emotion. Try it.")
        }
    }
}