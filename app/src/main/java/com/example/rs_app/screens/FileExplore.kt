package com.example.rs_app.screens

import android.annotation.SuppressLint
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


fun readDirectoryContents(directoryContents: MutableList<String>) {
    val directory = Environment.getExternalStorageDirectory()
    val files = directory.listFiles()
    if (files != null) {
        for (file in files) {
            directoryContents.add(file.name)
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExploreScreen(n: NavHostController) {
    var hasPermission by remember { mutableStateOf(false) }

    val directoryContents = remember { mutableStateListOf<String>() }
    val selectedForSync = remember { mutableStateListOf<String>() }

    val localContext = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        if (isGranted) {
            readDirectoryContents(directoryContents)
        } else {
            Toast.makeText(localContext, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val directory = Environment.getExternalStorageDirectory()
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                directoryContents.add(file.name)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Button(onClick = {

                if (selectedForSync.count() == directoryContents.count()) {
//                    selectedForSync.removeAll(selectedForSync)
                    selectedForSync.clear()
                } else {
                    selectedForSync.addAll(directoryContents)
                }
            }) {
                Text(text = "Select All")
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
        ) {

            items(directoryContents) { item ->
                Row(
                    modifier = Modifier
                        .background(Color(0x80EC759D))
                        .fillParentMaxWidth()
                ) {
                    val findIndex = selectedForSync.indexOf(item)
                    Checkbox(checked = findIndex != -1, onCheckedChange = {
                        if (findIndex == -1) {
                            selectedForSync.add(item)
                        } else {
                            selectedForSync.removeAt(findIndex)
                        }
                    })
                    Text(item)
                }
            }
        }
    }


}