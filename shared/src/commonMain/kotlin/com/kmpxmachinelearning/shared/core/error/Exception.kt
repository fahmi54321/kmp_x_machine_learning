package com.kmpxmachinelearning.shared.core.error

abstract class AppException(message: String) : Exception(message)

class UnauthorizedException(message: String) : AppException(message)
class NotFoundException(message: String) : AppException(message)
class ServerException(message: String) : AppException(message)
class NetworkException : AppException("Koneksi bermasalah")
class TimeoutException : AppException("Koneksi bermasalah")
class UnknownException(message: String) : AppException(message)