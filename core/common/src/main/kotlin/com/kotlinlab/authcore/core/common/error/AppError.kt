package com.kotlinlab.authcore.core.common.error

interface AppError

sealed interface CommonError : AppError {
    data object Network : CommonError
    data object Unauthorized : CommonError
    data object Unknown : CommonError
}
