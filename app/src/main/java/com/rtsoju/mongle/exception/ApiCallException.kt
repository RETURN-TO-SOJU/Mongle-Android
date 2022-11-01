package com.rtsoju.mongle.exception

class ApiCallException(message: String, exception: Throwable) : RuntimeException(message, exception)