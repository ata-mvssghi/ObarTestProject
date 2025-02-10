package com.example.obartestproject.ui.screen

import com.example.obartestproject.MapActivity
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.obartestproject.R
import com.example.obartestproject.RegistrationViewModel
import com.example.obartestproject.ui.theme.ObarTestProjectTheme

@Composable
fun UserRegisterFormScreen(
    viewModel: RegistrationViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            navController.navigate("SecondScreen")
        }
    }
    if (isLoading) {
        LoadingPopup(stringResource(R.string.sending_info))
    }

    errorMessage?.let { message ->
        ErrorDialog(message = message, onDismiss = { viewModel.errorMessage.value = null })
    }
    val startActivityForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val latitude = result.data?.getDoubleExtra("latitude", 0.0)
                val longitude = result.data?.getDoubleExtra("longitude", 0.0)
                if (latitude != null && longitude != null)
                    viewModel.updateLocation(latitude, longitude)
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            TopBar()
            Spacer(Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.name.value ?: "",
                    onValueChange = { newVal -> viewModel.updateName(newVal) },
                    label = {
                        Text(
                            text = stringResource(R.string.name),
                            color = Color(0xFF439CA4),
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    trailingIcon = {
                        if (uiState.name.isValid) Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Green
                        )
                        else Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Gray
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.lastName.value ?: "",
                    onValueChange = { newVal -> viewModel.updateLastName(newVal) },
                    label = {
                        Text(
                            text = stringResource(R.string.lastName),
                            color = Color(0xFF439CA4),
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    trailingIcon = {
                        if (uiState.lastName.isValid) Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Green
                        )
                        else Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Gray
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.phoneNumber.value ?: "",
                    onValueChange = { newVal -> viewModel.updatePhoneNumber(newVal) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = stringResource(R.string.phone_number),
                            color = Color(0xFF439CA4),
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    trailingIcon = {
                        if (uiState.phoneNumber.isValid) Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Green
                        )
                        else Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Gray
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.coordinateNumber.value ?: "",
                    onValueChange = { newVal -> viewModel.updateCoordinateNumber(newVal) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = stringResource(R.string.coordinate_mobile),
                            color = Color(0xFF439CA4),
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    trailingIcon = {
                        if (uiState.coordinateNumber.isValid) Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Green
                        )
                        else Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Gray
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.address.value ?: "",
                    onValueChange = { newVal -> viewModel.updateAddress(newVal) },
                    label = {
                        Text(
                            text = stringResource(R.string.address),
                            color = Color(0xFF439CA4),
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    trailingIcon = {
                        if (uiState.address.isValid) Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Green
                        )
                        else Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "valid input",
                            tint = Color.Gray
                        )
                    }
                )
            }
            MapSelectorCard {
                val intent = Intent(context, MapActivity::class.java)
                startActivityForResult.launch(intent)
            }

            Spacer(modifier = Modifier.height(16.dp))

            GenderSelection(onGenderSelected = {
                viewModel.updateGender(it)
            })
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val formValidationResult = viewModel.formvalidation()
                if(formValidationResult!=null)
                    viewModel.errorMessage.value = formValidationResult
                else
                viewModel.submitRegistration()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF17C47F),
                disabledContainerColor = Color(0xFFA0DAB0)
            )
        ) {
            Text(
                text = stringResource(R.string.next_Step),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun TopBar() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ثبت نام",
                fontSize = 18.sp,
                color = Color(0xFF2A1212)
            )

            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back icon")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC9C6C6))
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.fill_info),
                fontSize = 14.sp,
                color = Color(0xFF424242)
            )
        }
    }
}

@Composable
fun GenderSelection(onGenderSelected: (String) -> Unit) {
    var selectedGender by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.gender),
            fontSize = 16.sp,
            color = Color(0xFF439CA4),
            modifier = Modifier.padding(end = 8.dp),
            textAlign = TextAlign.Right
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF3F7FBF))
                .width(120.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToggleButton(
                text = stringResource(R.string.female),
                isSelected = selectedGender == stringResource(R.string.female),
                onClick = {
                    onGenderSelected("خانم")
                    selectedGender = "خانم"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            ToggleButton(
                text = stringResource(R.string.male),
                isSelected = selectedGender == stringResource(R.string.male),
                onClick = {
                    onGenderSelected("اقا")
                    selectedGender = "اقا"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ToggleButton(text: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier=Modifier) {
    Box(
        modifier = modifier
            .background(if (isSelected) Color(0xFF3F7FBF) else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = if (isSelected) Color.White else Color(0xFF3F7FBF),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoadingPopup(message: String = "Loading...") {
    Dialog(onDismissRequest = {}) { // Prevent dismissing when tapping outside
        Box(
            modifier = Modifier
                .size(200.dp) // Popup Size
                .background(Color.White, shape = RoundedCornerShape(12.dp)), // White Popup
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        onDismissRequest = onDismiss,
        title = { Text("خطا", color = Color.Red) },
        text = { Text(message) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("باشه")
            }
        }
    )
}

@Composable
fun MapSelectorCard(onMapClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onMapClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Map Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "انتخاب از روی نقشه",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPrev() {
    ObarTestProjectTheme {
        TopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    ObarTestProjectTheme {
        UserRegisterFormScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun loadingPrev() {
    LoadingPopup("fetching")
}