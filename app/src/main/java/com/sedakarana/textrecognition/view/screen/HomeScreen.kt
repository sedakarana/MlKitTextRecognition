package com.sedakarana.textrecognition.view.screen

import android.Manifest
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.sedakarana.navigation.navigation.Screen
import com.sedakarana.textrecognition.R
import com.sedakarana.textrecognition.ui.theme.ColorBorder
import com.sedakarana.textrecognition.ui.theme.ColorCamera
import com.sedakarana.textrecognition.ui.theme.ColorClear
import com.sedakarana.textrecognition.ui.theme.ColorCopy
import com.sedakarana.textrecognition.ui.theme.ColorDetect
import com.sedakarana.textrecognition.ui.theme.ColorGallery
import com.sedakarana.textrecognition.ui.theme.ColorImage
import com.sedakarana.textrecognition.ui.theme.ColorShare
import com.sedakarana.textrecognition.ui.theme.ColorSpacer
import com.sedakarana.textrecognition.util.ComposeFileProvider
import com.sedakarana.textrecognition.viewModel.HomeViewModel
import kotlinx.coroutines.delay


@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) { // NavController parametresi gezinme işlemini yapar.
    val context = LocalContext.current
    var imageUri by viewModel.imageUri
    var hasImage by viewModel.hasImage
    var textResult by viewModel.textResult

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.hasImage.value = uri != null
            viewModel.imageUri.value = uri
        })

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            viewModel.hasImage.value = success
        })

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    LaunchedEffect(hasImage) {
        imageUri?.let {
            viewModel.textRecognizer(context = context)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(id = R.string.app_name),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ColorSpacer)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .border(1.dp, ColorBorder, shape = RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = textResult,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        if (textResult.isNotEmpty()) {
                            val clipboardManager =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                            val clip =
                                android.content.ClipData.newPlainText("Copied Text", "textToCopy")
                            clipboardManager.setPrimaryClip(clip)
                            Toast
                                .makeText(context, "Text Copied to Clipboard!", Toast.LENGTH_SHORT)
                                .show()
                        }


                    }
                    .padding(2.dp)
                    .background(ColorCopy, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.copy),
                    contentDescription = "Copy Icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_copy),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        if (hasImage && imageUri != null) {
                            navController.navigate(
                                Screen.ImageScreen.route
                            )
                        }

                    }
                    .padding(2.dp)
                    .background(ColorImage, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "Image Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_show_image),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        if (cameraPermissionState.hasPermission) {
                            val uri = ComposeFileProvider.getImageUri(context)
                            Log.e("ImageUri", uri.toString()) // URI'yi logla
                            viewModel.imageUri.value = uri
                            cameraLauncher.launch(uri)

                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }

                    }
                    .padding(2.dp)
                    .background(ColorCamera, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_camera),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        imagePicker.launch("image/*")
                    }
                    .padding(2.dp)
                    .background(ColorGallery, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = "Photo Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_gallery),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        viewModel.textRecognizer(context = context)
                    }
                    .padding(2.dp)
                    .background(ColorDetect, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Detect Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_detect),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        viewModel.hasImage.value = false
                        viewModel.imageUri.value = null
                        viewModel.textResult.value = ""
                    }
                    .padding(2.dp)
                    .background(ColorClear, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "Delete Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_clear),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        if(textResult.isNotEmpty()){
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, textResult)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share"))
                        }
                    }
                    .padding(2.dp)
                    .background(ColorShare, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "Share Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.s_share),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }

    }
}