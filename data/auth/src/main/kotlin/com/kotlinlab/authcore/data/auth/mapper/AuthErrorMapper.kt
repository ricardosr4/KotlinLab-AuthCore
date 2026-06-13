package com.kotlinlab.authcore.data.auth.mapper

import com.kotlinlab.authcore.core.common.error.AppError

internal fun interface AuthErrorMapper {
    fun map(throwable: Throwable): AppError
}
