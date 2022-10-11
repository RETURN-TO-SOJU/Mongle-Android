package com.rtsoju.mongle.exception

sealed class NetworkException(message: String, exception: Throwable) :
    RuntimeException(message, exception)

class ApiCallException(message: String, exception: Throwable) : NetworkException(message, exception)