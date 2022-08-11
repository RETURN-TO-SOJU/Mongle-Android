package com.won983212.mongle.password

class PasswordRepository(val maxDigits: Int) {
    private var length = 0
    private val digits = CharArray(maxDigits)
    private var fullListener: PasswordFullListener? = null

    /**
     * digit을 끝에 하나 추가합니다. 리턴되는 길이가 항상 1씩 더해지는 보장은 없습니다.
     * 패스워드가 꽉 찼을 경우 0이될 수 있습니다.
     * @return digit을 삽입한 뒤에, 패스워드 길이 리턴.
     */
    fun pushDigit(digit: Char): Int {
        digits[length++] = digit
        if (length >= maxDigits) {
            length = 0
            fullListener?.onFull(String(digits))
        }
        return length
    }

    /**
     * digit을 끝에서 하나 제거합니다.
     * @return digit을 제거한 뒤에, 패스워드 길이 리턴
     */
    fun popDigit(): Int {
        if (length == 0) {
            return 0;
        }
        return --length
    }

    fun setOnFullListener(listener: PasswordFullListener) {
        fullListener = listener
    }
}

fun interface PasswordFullListener {
    fun onFull(password: String)
}