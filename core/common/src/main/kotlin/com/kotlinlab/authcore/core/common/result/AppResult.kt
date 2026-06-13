package com.kotlinlab.authcore.core.common.result

import com.kotlinlab.authcore.core.common.error.AppError

sealed interface AppResult<out T> {
    data class Success<T>(val value: T) : AppResult<T>
    data class Failure(val error: AppError) : AppResult<Nothing>
}
