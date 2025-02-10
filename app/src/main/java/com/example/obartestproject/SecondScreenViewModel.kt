package com.example.obartestproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SecondScreenViewModel : ViewModel() {
    private val retrofit by lazy { RetrofitClient.apiService }

    private val _uiState = MutableStateFlow(SecondScreenUiState())
    val uiState: StateFlow<SecondScreenUiState> = _uiState

    fun fetchInfo() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null, isSuccess = false)
            }

            try {
                val authHeader = AuthUtil.getBasicAuthHeader(username = "09822222222", password = "Sana12345678")
                val response = retrofit.fetchUserInfo(authHeader)

                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (!responseBody.isNullOrEmpty()) {
                        val userInfo = responseBody[0]
                        _uiState.update {
                            it.copy(
                                first_name = userInfo.first_name ,
                                last_name = userInfo.last_name ,
                                coordinate_mobile = userInfo.coordinate_mobile ,
                                address = userInfo.address,
                                coordinate_phone_number = userInfo.coordinate_phone_number,
                                errorMessage = null,
                                isSuccess = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(errorMessage = "No user data available", isSuccess = false)
                        }
                    }
                } else {
                    val errorMsg = "Error ${response.code()}: ${response.errorBody()?.string() ?: response.message()}"
                    Log.e("API", errorMsg)
                    _uiState.update { it.copy(errorMessage = errorMsg, isSuccess = false) }
                }
            } catch (e: IOException) {
                val errorMsg = "Network issue. Please check your internet connection."
                Log.e("API", "Network Error: ${e.message}", e)
                _uiState.update { it.copy(errorMessage = errorMsg, isSuccess = false) }

            } catch (e: HttpException) {
                val errorMsg = "Server error (${e.code()}). Please try again later."
                Log.e("API", "HTTP Error: ${e.message}", e)
                _uiState.update { it.copy(errorMessage = errorMsg, isSuccess = false) }

            } catch (e: Exception) {
                val errorMsg = "Unexpected error occurred. Please try again."
                Log.e("API", "Unexpected Error: ${e.message}", e)
                _uiState.update { it.copy(errorMessage = errorMsg, isSuccess = false) }

            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    fun updateIsLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun updateErrorMessage(errorMessage: String?) {
        _uiState.update { it.copy(errorMessage = errorMessage) }
    }



}

data class SecondScreenUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val address: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val coordinate_mobile: String? = null,
    val coordinate_phone_number: String? = null,
)
