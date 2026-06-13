package com.kotlinlab.authcore.core.common.error

sealed interface AppError {
    data object Network : AppError
    data object Unauthorized : AppError
    data object Unknown : AppError
}
