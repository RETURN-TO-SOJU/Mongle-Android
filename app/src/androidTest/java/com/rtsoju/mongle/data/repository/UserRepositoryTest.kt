package com.rtsoju.mongle.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.rtsoju.mongle.data.repository.UserRepositoryImpl
import com.rtsoju.mongle.data.source.local.LocalTokenSource
import com.rtsoju.mongle.data.source.local.LocalUserDataSource
import com.rtsoju.mongle.data.source.remote.RemoteUserDataSource
import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.User
import com.rtsoju.mongle.domain.repository.UserRepository
import com.rtsoju.mongle.exception.NoResultException
import com.rtsoju.mongle.mock.MockUserApi
import com.rtsoju.mongle.util.generateUser1
import com.rtsoju.mongle.util.generateUser2
import com.rtsoju.mongle.util.testFailedResult
import com.rtsoju.mongle.util.testSuccessResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// TODO 일반 Unit테스트로 변경
@RunWith(AndroidJUnit4::class)
internal class UserRepositoryTest {
    private lateinit var localDataSource: LocalUserDataSource
    private lateinit var repository: UserRepository
    private lateinit var mockApi: MockUserApi

    private val expectedUser1 = generateUser1()
    private val expectedUser2 = generateUser2()

    @Before
    fun before() {
        mockApi = MockUserApi()

        val context = ApplicationProvider.getApplicationContext<Context>()
        val localTokenSource = LocalTokenSource(context)
        val remoteDataSource = RemoteUserDataSource(mockApi)

        localDataSource = LocalUserDataSource(context)
        repository = UserRepositoryImpl(localTokenSource, localDataSource, remoteDataSource)
        localDataSource.saveUser(null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun get_user_test(
        cachePolicy: CachePolicy,
        expectedValue1: User,
        expectedValue2: User
    ) = runTest {
        mockApi.userResponseGenerator = ::generateUser1
        repository.getUserInfo(cachePolicy)
        testSuccessResult(localDataSource.getSavedUser()) {
            assertThat(it).isEqualTo(expectedValue1)
        }

        mockApi.userResponseGenerator = ::generateUser2
        repository.getUserInfo(cachePolicy)
        testSuccessResult(localDataSource.getSavedUser()) {
            assertThat(it).isEqualTo(expectedValue2)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_user_once() = runTest {
        mockApi.userResponseGenerator = ::generateUser2
        repository.getUserInfo(CachePolicy.GET_OR_FETCH)
        get_user_test(CachePolicy.ONCE, expectedUser1, expectedUser1)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_user_get_or_fetch() = runTest {
        get_user_test(CachePolicy.GET_OR_FETCH, expectedUser1, expectedUser1)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_user_never() = runTest {
        mockApi.userResponseGenerator = ::generateUser1
        repository.getUserInfo(CachePolicy.NEVER)
        testFailedResult(localDataSource.getSavedUser()) {
            assertThat(it).isInstanceOf(NoResultException::class.java)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_user_refresh() = runTest {
        get_user_test(CachePolicy.REFRESH, expectedUser1, expectedUser2)
    }
}