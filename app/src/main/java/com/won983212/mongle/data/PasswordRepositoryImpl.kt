package com.won983212.mongle.data

import com.won983212.mongle.repository.PasswordRepository
import javax.inject.Inject

// TODO Logic 구현
internal class PasswordRepositoryImpl
@Inject constructor() : PasswordRepository {
    override fun getPassword(): String {
        return "1234"
    }

    override fun setPassword(password: String) {

    }
}