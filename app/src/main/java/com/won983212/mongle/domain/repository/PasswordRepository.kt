package com.won983212.mongle.domain.repository

interface PasswordRepository {

    /**
     * Password를 리턴합니다. 비밀번호가 설정되어있지 않다면 null을 리턴합니다.
     */
    fun getPassword(): String?

    /**
     * Password를 설정합니다. 만약 null을 전달하면 비밀번호를 삭제합니다.
     */
    fun setPassword(password: String?)
}