package com.example.rs_app.screens

import android.os.Environment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.io.File

data class FileFolder(
    val name: String = "",
    val size: Long = 0,
    val absolutePath: String = "",
    val isFile: Boolean = false,
    val lastModified: Long = 0,
)


fun readDirectoryContents(directoryContents: MutableList<String>) {
    val directory = Environment.getExternalStorageDirectory()
    val files = directory.listFiles()
    if (files != null) {
        for (file in files) {
            directoryContents.add(file.name)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExploreScreen(n: NavHostController) {
    var hasPermission by remember { mutableStateOf(false) }


    val directoryContents = remember { mutableStateListOf<FileFolder>() }
    val selectedForSync = remember { mutableStateListOf<String>() }
    val prevPath = remember { mutableStateOf("/storage/emulated/0/") }

    val aFile = "/storage/emulated/0/Android"

    val localContext = LocalContext.current

//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        hasPermission = isGranted
//        if (isGranted) {
//            readDirectoryContents(directoryContents)
//        } else {
//            Toast.makeText(localContext, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }

    fun mapToListed(files: Array<File>?) {
        if (files != null) {
            for (file in files) {
                directoryContents.add(
                    FileFolder(
                        name = file.name,
                        size = file.length(),
                        isFile = file.isFile,
                        absolutePath = file.absolutePath,
                        lastModified = file.lastModified()
                    )
                )
            }
        }
    }

    fun expandListOfFiles(absolutePath: String) {
        val file = File(absolutePath)
        directoryContents.clear()
        mapToListed(file.listFiles())
        prevPath.value = file.parent.toString()
    }

    LaunchedEffect(Unit) {
        val directory = Environment.getExternalStorageDirectory()
        val files = directory.listFiles()
        mapToListed(files)
    }

    fun handleDeleteFile() {
//        Os.remove("")
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E2444))

    ) {

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (selectedForSync.count() == directoryContents.count()) {
//                    selectedForSync.removeAll(selectedForSync)
                        selectedForSync.clear()
                    } else {
//                    selectedForSync.addAll(directoryContents)
                    }
                }) {
                    Text(text = "Select All")
                }

                Button(onClick = {
                    handleDeleteFile()
                }) {
                    Text(text = "Delete")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
                    .background(Color(0xBA5F6786))
                    .clickable(enabled = true, onClick = {
                        if (prevPath.value != "/storage/emulated") {
                            directoryContents.clear()
                            expandListOfFiles(prevPath.value)
                        }
                    }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "... back...",
                    modifier = Modifier.padding(33.dp, 0.dp),
                    fontSize = 14.sp,
                    color = Color(
                        0xFFE4E4E4
                    )
                )

            }
        }

        LazyColumn(
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.padding(0.dp, 90.dp, 0.dp, 0.dp)
        ) {
            items(directoryContents) { item ->
                Row(

                    modifier = Modifier
                        .fillParentMaxWidth()
                        .clickable(enabled = true, onClick = {
                            expandListOfFiles(item.absolutePath)
                        })
                ) {
                    val findIndex = selectedForSync.indexOf(item.name)
                    Checkbox(checked = findIndex != -1, onCheckedChange = {
                        if (findIndex == -1) {
                            selectedForSync.add(item.name)
                        } else {
                            selectedForSync.removeAt(findIndex)
                        }
                    })
                    Column(modifier = Modifier) {
                        Row {
                            Text(
                                item.name, modifier = Modifier, fontSize = 14.sp, color = Color(
                                    0xFFE4E4E4
                                )
                            )
                        }
                        Row {
                            Text(
                                (item.size / 1024).toString() + "Kb",
                                modifier = Modifier,
                                fontSize = 12.sp,
                                color = Color(0xFFA3A3A3)
                            )
                            Text(
                                "Last modified: " + item.lastModified.toString(),
                                modifier = Modifier.padding(10.dp, 0.dp),
                                fontSize = 12.sp,
                                color = Color(0xFFA3A3A3)
                            )
                        }
                    }
                }
            }
        }
    }


}
