package com.example.rs_app.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import java.util.Date

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }
}


data class ApiResponse<T>(
    val data: List<T>,
    val message: String
)
data class AuthenticatorModel (
    val id: Int,
    val accountName: String,
    val secret: String,
    val createdAt: Date
)
data class AuthenticatorOtpPasscodeModel (
    val id: Int,
    val accountId: String,
    val otpPasscode: String,
    val createdAt: Date
)
