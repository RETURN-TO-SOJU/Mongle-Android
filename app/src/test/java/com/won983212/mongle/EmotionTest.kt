package com.won983212.mongle

import android.content.res.Resources
import com.won983212.mongle.domain.model.Emotion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

// TODO 갖가지 Test코드(Mockito이용)를 작성하여 유지보수 편의 개선필요
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