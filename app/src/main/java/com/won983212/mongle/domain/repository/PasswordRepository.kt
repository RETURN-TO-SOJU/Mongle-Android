package com.won983212.mongle.domain.repository

import java.io.InputStream

interface PasswordRepository {

    /** 화면잠금 비밀번호가 일치하는지 확인합니다. */
    fun checkScreenPassword(password: String): Boolean

    /** 화면잠금 비밀번호가 설정되어있는지 확인합니다. */
    fun hasScreenPassword(): Boolean

    /** 화면잠금 비밀번호를 설정합니다. 만약 null을 전달하면 비밀번호를 삭제합니다. */
    fun setScreenPassword(password: String?)

    /** 암호화되어있는 데이터를 키 비밀번호로 복호화합니다. */
    fun decryptByKeyPassword(data: String): String

    /** 데이터를 암호화하기 위한 비밀번호가 설정되어있는지 확인합니다. */
    fun hasDataKeyPassword(): Boolean

    /** 서버에 전송할, 비밀번호가 포함된 카카오톡 데이터 패킷을 만듭니다. */
    fun makePwdKakaotalkDataPacket(dataStream: InputStream): ByteArray

    /** 데이터를 암호화하기 위한 비밀번호를 설정합니다. 만약 null을 전달하면 비밀번호를 삭제합니다. */
    fun setDataKeyPassword(password: String?)
}