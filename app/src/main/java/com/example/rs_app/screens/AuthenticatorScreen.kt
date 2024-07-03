package com.example.rs_app.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.rs_app.GlobalAuthState
import com.example.rs_app.apis.BASE_URL
import com.example.rs_app.http.ApiResponse
import com.example.rs_app.http.AuthenticatorModel
import com.example.rs_app.http.AuthenticatorOtpPasscodeModel
import com.example.rs_app.http.client
import com.example.rs_app.utils.formatDate
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random
import kotlin.time.Duration


@Composable
fun AuthenticatorScreen(navController: NavHostController, applicationContext: Context) {
    var authUser = GlobalAuthState.authUser

    var items by remember { mutableStateOf<List<AuthenticatorModel>>(emptyList()) }
    var selectAccount by remember { mutableStateOf<AuthenticatorModel?>(null) }


    val coroutineScope = rememberCoroutineScope()


    suspend fun fetchAccount(){
        try {
            val response: HttpResponse =
                client.get("$BASE_URL/authenticator")
            if (response.status.value == 200) {
                Toast.makeText(applicationContext, "fetch success", Toast.LENGTH_SHORT).show()
                val result: ApiResponse<AuthenticatorModel> = response.body()
                items = result.data
            } else {
                Toast.makeText(applicationContext, "Failed to fetch user. Status code:", Toast.LENGTH_SHORT).show()
                println("Failed to fetch user. Status code : ${response.status.value}")
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "fetch fail", Toast.LENGTH_SHORT).show()

        }
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            fetchAccount()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0))
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4C669F),
                            Color(0xFF3B5998),
                            Color(0xFF192F6A)
                        )
                    )
                )
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .fillMaxWidth()
//                .height(250.dp)
//                .clip(shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
        ) {


            Button(onClick = { navController.navigate("login") }) {
                Text("Login Page")
            }

            Box(modifier = Modifier.padding(100.dp))

            if(items.count() == 0){
                Button(onClick = { coroutineScope.launch { fetchAccount() } }) {
                    Text(text = "Re Fetch")
                }
            }

            LazyColumn {

                items(items) { item ->
                    Column(modifier = Modifier
                        .padding(0.dp, 10.dp)
                        .fillMaxWidth()
                        .clickable {
                            selectAccount = if (selectAccount == null) item else null

                        }) {
                        Text(text = item.id.toString())
                        Text(text = item.accountName)
                        Text(text = formatDate(item.createdAt))
                    }
                }
            }


            if (selectAccount != null) {
                OneTimePassword(selectAccount)

            }

        }
    }
}


@OptIn(InternalAPI::class)
@Composable
fun OneTimePassword(selectedItem: AuthenticatorModel?) {

    val context = LocalContext.current

    data class RequestBody(val account_name: String, val secret: String, val otp_passcode: String)
    data class RequestBody2(val account_name: String, val secret: String, val account_id: Int)

    val coroutineScope = rememberCoroutineScope()

    var otpCode by remember { mutableStateOf<String?>(null) }
    var prevOtps by remember { mutableStateOf<List<AuthenticatorOtpPasscodeModel>>(emptyList()) }
    var isOpenAllOtpViewModal by remember { mutableStateOf(false) }


    fun generateOtp(): String {
        val otpLength = 6 // Length of OTP
        val otp = StringBuilder(otpLength)
        val random = Random()
        repeat(otpLength) {
            val digit = random.nextInt(10) // Generate a random digit (0-9)
            otp.append(digit)
        }
        return otp.toString()
    }

    suspend fun saveOtpPassword(otp: String, authenticator: AuthenticatorModel) {
        try {

            val response: HttpResponse =
                client.post("$BASE_URL/authenticator/generate-otp") {
                    contentType(ContentType.Application.Json)
                    setBody(RequestBody(authenticator.accountName, authenticator.secret, otp))
                }

            if (response.status.value == 201) {
                Toast.makeText(context, "Saved.", Toast.LENGTH_SHORT).show()
            } else {
                println("Failed to fetch user. Status code : ${response.status.value}")
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Fail to save otp.", Toast.LENGTH_SHORT).show()
            println(e.message)
        }
    }

    suspend fun getPrevAllOtps(authenticator: AuthenticatorModel) {
        try {
            val response: HttpResponse =
                client.post("$BASE_URL/authenticator/prev-otps") {
                    contentType(ContentType.Application.Json)
                    setBody(
                        RequestBody2(
                            authenticator.accountName,
                            authenticator.secret,
                            authenticator.id
                        )
                    )
                }
            if (response.status.value == 200) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                val result: ApiResponse<AuthenticatorOtpPasscodeModel> = response.body()
                prevOtps = result.data
            } else {
                println("Failed to fetch user. Status code : ${response.status.value}")
            }

        } catch (e: Exception) {

            Toast.makeText(context, "fetch fail", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    LaunchedEffect(Unit) {
        selectedItem?.let { it ->
            while (true) {
                val otp = generateOtp()
                saveOtpPassword(otp, it)
                delay(5000)
            }
        }

    }

    Text(text = "Your ONE Time Password")
    otpCode?.let { otp ->
        Text(text = otp)
    }


    Button(onClick = {
        selectedItem?.let { it ->
            coroutineScope.launch {
                getPrevAllOtps(it)
                isOpenAllOtpViewModal = true
            }
        }

    }) {
        Text("All Prev Otps")
    }

    otpCode?.let { otp ->
        Text(text = otp)
    }


    if (isOpenAllOtpViewModal) {
        Dialog(onDismissRequest = { isOpenAllOtpViewModal = false }) {
            Box(
                modifier = Modifier
                    .width(800.dp)
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text(text = "All Otp:", modifier = Modifier.padding(1.dp))
                        Text(text = "Total :${prevOtps.count()}", modifier = Modifier.padding(1.dp))
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(prevOtps) { otpItem ->
                            Column(
                                modifier = Modifier
                                    .padding(0.dp, 10.dp)
                                    .background(Color(0xFFE7E7E7))
                                    .fillMaxWidth()
                            ) {
                                Text(text = otpItem.otpPasscode)
                                Text(text = formatDate(otpItem.createdAt))
                            }

                        }
                    }

                    Button(onClick = { isOpenAllOtpViewModal = false }) {
                        Text(text = "Close")
                    }
                }
            }
        }
    }
}
