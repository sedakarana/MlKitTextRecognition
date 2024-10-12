package com.sedakarana.textrecognition.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sedakarana.textrecognition.R
import com.sedakarana.textrecognition.ui.theme.ColorBorder
import com.sedakarana.textrecognition.viewModel.HomeViewModel

@Composable
fun ImageScreen(navController: NavController,
                viewModel: HomeViewModel
) { // NavController parametresi gezinme işlemini yapar.
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
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center

            ) {
                Image(
                    painter = painterResource(id = R.drawable.back), // Aynı drawable kaynağını burada da kullanabilirsin
                    contentDescription = "Copy Icon",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.app_name),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Box(modifier = Modifier.size(50.dp)) {


            }

        }

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
                AsyncImage(
                    model = viewModel.imageUri.value,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )

            }
        }
    }
}