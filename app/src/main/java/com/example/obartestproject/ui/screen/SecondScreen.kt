package com.example.obartestproject.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.obartestproject.R
import com.example.obartestproject.SecondScreenUiState
import com.example.obartestproject.SecondScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    viewModel: SecondScreenViewModel = viewModel(),
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingPopup(stringResource(R.string.fetching_info))
    }
    if (uiState.errorMessage != null) {
        ErrorDialog(uiState.errorMessage!!) {
            viewModel.updateErrorMessage(null)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.updateIsLoading(true)
        viewModel.fetchInfo()
    }
Column(modifier = modifier) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "arrow back icon",
        modifier = Modifier.clickable {
            navController.popBackStack()
        }
            .align(Alignment.End)
    )
    Column {
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            UserInfo(uiState)
        }
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {}
    }
}

}

@Composable
fun UserInfo(uiState: SecondScreenUiState) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                Text(
                    text = stringResource(R.string.user_address),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                )
                OutlinedCard(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = uiState.address ?: "",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.name_lastname),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                    )
                    OutlinedCard(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "${uiState.first_name ?: ""} ${uiState.last_name ?: ""}",
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.phone_number),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                    )
                    OutlinedCard(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = uiState.coordinate_phone_number ?: "",
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun customTextPrev() {
    UserInfo(SecondScreenUiState())
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPrev() {
    SecondScreen()
}



