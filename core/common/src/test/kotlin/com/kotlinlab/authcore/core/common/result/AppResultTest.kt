package com.kotlinlab.authcore.core.common.result

import com.kotlinlab.authcore.core.common.error.AppError
import org.junit.Assert.assertEquals
import org.junit.Test

class AppResultTest {

    @Test
    fun `success contains the expected value`() {
        val result = AppResult.Success("authenticated user")

        assertEquals("authenticated user", result.value)
    }

    @Test
    fun `failure contains the expected error`() {
        val result = AppResult.Failure(AppError.Network)

        assertEquals(AppError.Network, result.error)
    }
}
