//package com.example.rs_app.screens
//
//
//import android.net.Uri
//import android.os.Bundle
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.unit.dp
//import androidx.core.net.toUri
//import coil.compose.rememberImagePainter
//import com.yalantis.ucrop.UCrop
//import java.io.File
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AddPhotoAlternate
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            PhotoPickerScreen()
//        }
//    }
//}
//
//@Composable
//fun PhotoPickerScreen() {
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//    var croppedImageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val photoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            val destinationUri = File(applicationContext.cacheDir, "croppedImage.jpg").toUri()
//            val uCrop = UCrop.of(it, destinationUri)
//            cropLauncher.launch(uCrop.getIntent(applicationContext))
//        }
//    }
//
//    val cropLauncher = rememberLauncherForActivityResult(
//        contract = StartActivityForResult()
//    ) { result ->
//        val resultUri = UCrop.getOutput(result.data!!)
//        resultUri?.let { uri ->
//            croppedImageUri = uri
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(
//            onClick = { photoPickerLauncher.launch("image/*") },
//            content = {
//                Icon(
//                    imageVector = Icons.Default.AddPhotoAlternate,
//                    contentDescription = "Pick a Photo",
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Pick a Photo")
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        croppedImageUri?.let { uri ->
//            Image(
//                painter = rememberImagePainter(data = uri),
//                contentDescription = null,
//                modifier = Modifier.size(200.dp),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}
