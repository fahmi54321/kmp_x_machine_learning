package com.kmpxmachinelearning.shared.core.error

sealed class Failure(val message: String)

class UnauthorizedFailure(message: String) : Failure(message)
class NotFoundFailure(message: String) : Failure(message)
class ServerFailure(message: String) : Failure(message)
class NetworkFailure : Failure("Koneksi bermasalah")
class TimeoutFailure : Failure("Koneksi bermasalah")
class UnknownFailure(message: String) : Failure(message)