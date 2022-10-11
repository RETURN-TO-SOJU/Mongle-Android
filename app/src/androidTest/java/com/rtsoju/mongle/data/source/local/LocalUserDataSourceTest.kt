package com.rtsoju.mongle.data.source.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.*
import com.rtsoju.mongle.data.source.local.LocalUserDataSource
import com.rtsoju.mongle.domain.model.User
import com.rtsoju.mongle.exception.NoResultException
import com.rtsoju.mongle.util.testFailedResult
import com.rtsoju.mongle.util.testSuccessResult
import com.rtsoju.mongle.util.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class LocalUserDataSourceTest {
    private lateinit var localDataSource: LocalUserDataSource

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        localDataSource = LocalUserDataSource(context)
    }

    @Test
    fun clear_user() {
        localDataSource.saveUser(null)
        testFailedResult(localDataSource.getSavedUser()) {
            assertThat(it).isInstanceOf(NoResultException::class.java)
        }
    }

    @Test
    fun save_user() {
        val expected = User("Username", "카카오이름")
        localDataSource.saveUser(expected)
        testSuccessResult(localDataSource.getSavedUser()) {
            assertThat(it).isEqualTo(expected)
        }
    }
}