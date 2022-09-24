package com.won983212.mongle.data.db;

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.model.Favorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Java6Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoriteDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun delete_favorite() = runTest {
        val favorite1 = Favorite(1, Emotion.ANGRY, LocalDate.now(), "test title")
        val favorite2 = Favorite(2, Emotion.ANGRY, LocalDate.now(), "test title")

        dao.insert(favorite1)
        dao.insert(favorite2)
        dao.deleteById(2)

        val favorites = dao.getAll()
        assertThat(favorites).contains(favorite1)
        assertThat(favorites).doesNotContain(favorite2)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun insert_favorite() = runTest {
        val favorite1 = Favorite(1, Emotion.ANGRY, LocalDate.now(), "test title")
        val favorite2 = Favorite(2, Emotion.ANGRY, LocalDate.now(), "test title")

        dao.insert(favorite1)
        dao.insert(favorite2)

        val favorites = dao.getAll()
        assertThat(favorites).contains(favorite1)
        assertThat(favorites).contains(favorite2)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_range_favorite() = runTest {
        val targets = mutableListOf<Favorite>()
        val startDay = LocalDate.of(2021, 12, 25)

        val from = LocalDate.of(2022, 1, 3).toEpochDay()
        val to = LocalDate.of(2022, 2, 3).toEpochDay()
        for (i in 1..50) {
            val date = startDay.plusDays(i.toLong())
            val dateEpoch = date.toEpochDay()
            val data = Favorite(i, Emotion.ANGRY, date, "test")
            if (dateEpoch in from..to) {
                targets.add(data)
            }
            dao.insert(data)
        }

        val favorites = dao.getRange(from, to)
        assertThat(favorites).containsAll(targets)
    }
}
