package com.won983212.mongle.data.repository

import com.won983212.mongle.data.local.source.PasswordDataSource
import com.won983212.mongle.repository.PasswordRepository
import javax.inject.Inject

internal class PasswordRepositoryImpl
@Inject constructor(
    private val passwordDataSource: PasswordDataSource
) : PasswordRepository {

    override fun getPassword(): String = passwordDataSource.getPassword()

    override fun setPassword(password: String) = passwordDataSource.setPassword(password)
}