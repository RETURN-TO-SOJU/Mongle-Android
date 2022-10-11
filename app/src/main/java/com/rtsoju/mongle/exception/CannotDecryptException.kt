package com.rtsoju.mongle.exception

class CannotDecryptException(throwable: Throwable) :
    RuntimeException("암호를 풀 수 없습니다. 틀린 비밀번호를 사용하고 계신 것 같아요.", throwable)