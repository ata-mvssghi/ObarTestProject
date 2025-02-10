package com.example.obartestproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obartestproject.model.body.RegistrationBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegistrationViewModel : ViewModel() {
    private val retrofit by lazy { RetrofitClient.apiService }

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState

    var isLoading = MutableStateFlow(false)
    var errorMessage = MutableStateFlow<String?>(null)
    var isSuccess = MutableStateFlow(false)

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(
                name = FormItemData(value = newName, isValid = newName.isNotEmpty())
            )
        }
    }

    fun updateLastName(newLastName: String) {
        _uiState.update {
            it.copy(
                lastName = FormItemData(value = newLastName, isValid = newLastName.isNotEmpty())
            )
        }
    }

    fun updatePhoneNumber(newPhone: String) {
        _uiState.update {
            it.copy(
                phoneNumber = FormItemData(value = newPhone, isValid = isValidPhoneNumber(newPhone))
            )
        }
    }

    fun updateAddress(newAddress: String) {
        _uiState.update {
            it.copy(
                address = FormItemData(value = newAddress, isValid = newAddress.isNotEmpty())
            )
        }
    }

    fun updateGender(newGender: String) {
        _uiState.update {
            it.copy(gender = newGender)
        }
    }

    fun updateLocation(latitude: Double, longitude: Double) {
        _uiState.update { it.copy(latitude = latitude, longitude = longitude) }
    }

    fun updateCoordinateNumber(coordinateNumber: String) {
        _uiState.update {
            it.copy(
                coordinateNumber = FormItemData(
                    value = coordinateNumber,
                    isValid = isValidPhoneNumber(coordinateNumber)
                )
            )
        }
    }


    fun submitRegistration() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val authHeader =
                    AuthUtil.getBasicAuthHeader(username = "09822222222", password = "Sana12345678")

                val uiStateValue = uiState.value
                val requestBody = RegistrationBody(
                    address = uiStateValue.address.value!!,
                    lat = uiStateValue.latitude!!,
                    lng = uiStateValue.longitude!!,
                    first_name = uiStateValue.name.value!!,
                    last_name = uiStateValue.lastName.value!!,
                    gender = if (uiStateValue.gender.equals("خانم")) "Female" else "Male",
                    coordinate_mobile = uiStateValue.coordinateNumber.value!!,
                    coordinate_phone_number = uiStateValue.phoneNumber.value!!
                )

                val response = retrofit.sendUserInfo(authHeader, requestBody)

                if (response.isSuccessful) {
                    isSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string() ?: response.message()
                    Log.e("API", "Registration Failed: $errorBody")
                    errorMessage.value = "Error ${response.code()}: $errorBody"
                }
            } catch (e: IOException) {
                Log.e("API", "Network Error: ${e.message}", e)
                errorMessage.value = "Network issue. Please check your internet connection."

            } catch (e: HttpException) {
                Log.e("API", "HTTP Error: ${e.code()} ${e.message}", e)
                errorMessage.value = "Server error (${e.code()}). Please try again later."

            } catch (e: Exception) {
                Log.e("API", "Unexpected Error: ${e.message}", e)
                errorMessage.value = "Unexpected error occurred. Please try again."

            } finally {
                isLoading.value = false
            }
        }
    }


    fun formvalidation(): String? {
        val state = _uiState.value
        val areInputsValid =
                    state.name.isValid &&
                    state.lastName.isValid &&
                    state.phoneNumber.isValid &&
                    state.coordinateNumber.isValid &&
                    state.address.isValid
        if(!areInputsValid)
            return "لطفا مقادیر را به درستی وارد کنید"
        else if (!(state.latitude != null &&
            state.longitude != null))
            return "لطفا مکان خود را از روی نقشه انتخاب کنید"
        else if(state.gender==null)
            return "لطفا جنسیت را مشخص کنید"
        else
            return null

    }

}

fun isValidPhoneNumber(phone: String): Boolean {
    val cleanedPhone = phone.replace("\\s".toRegex(), "").replace("-", "")
    val isNumeric = cleanedPhone.matches(Regex("^[+]?[0-9]{10,15}$"))
    return isNumeric && cleanedPhone.length == 11
}


data class RegistrationUiState(
    val name: FormItemData = FormItemData(),
    val lastName: FormItemData = FormItemData(),
    val phoneNumber: FormItemData = FormItemData(),
    val coordinateNumber: FormItemData = FormItemData(),
    val address: FormItemData = FormItemData(),
    val gender: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)

data class FormItemData(
    val value: String? = null,
    val isValid: Boolean = false,
)