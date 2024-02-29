package br.com.fiap.tempolafora.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.tempolafora.R
import br.com.fiap.tempolafora.models.Info
import br.com.fiap.tempolafora.service.RetrofitFactory
import br.com.fiap.tempolafora.ui.theme.Typography
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: HomeViewModel, navController: NavController){
    val cityName by viewModel.cityName.observeAsState(initial = "")
    val openAlertDialog by viewModel.openAlertDialog.observeAsState(initial = false)
    val location by viewModel.location.observeAsState()
    val openErrorDialog by viewModel.openErrorDialog.observeAsState(initial = false)
    val isTextFieldMissing by viewModel.isTextFieldMissing.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(96.dp))
            Icon(painter = painterResource(id = R.drawable.sun), contentDescription = "Thermal sensation", Modifier.size(36.dp), colorResource(
                id = R.color.blue
            ))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Bem-vindo ao TempoLaFora", style = Typography.titleLarge, color = colorResource(R.color.blue))
            Spacer(modifier = Modifier.height(4.dp))
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Vamos descobrir como está o tempo lá fora?",
                style = Typography.bodyLarge,
                color = colorResource(R.color.black))
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = cityName,
                onValueChange = {viewModel.setCityName(it)},
                colors = TextFieldDefaults.textFieldColors(textColor = colorResource(R.color.gray),
                containerColor = colorResource(R.color.white)),
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(text = "Digite o nome da sua cidade",
                        style = Typography.labelSmall,
                        color = colorResource(R.color.black))
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(2.dp))
            if(isTextFieldMissing){
                Text(text = "Campo obrigatório",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = Typography.labelSmall,
                    color = colorResource(R.color.red))
            }
            Spacer(modifier = Modifier.height(96.dp))
            Button(onClick = {
                if(cityName == ""){
                    viewModel.setIsTextFieldMissing(true)
                } else {
                    viewModel.setIsTextFieldMissing(false)
                    val call = RetrofitFactory().getWeatherService().getWeather(cityName)
                    call.enqueue(object : Callback<Info>{
                        override fun onResponse(call: Call<Info>, response: Response<Info>) {
                            Log.i("Response", response.body().toString())
                            if(response.body()?.location != null && response.body()?.weather != null){
                                viewModel.setLocation(response.body()?.location)
                                viewModel.setOpenAlertDialog(true)
                            } else {
                                viewModel.setErrorMessage("Cidade não encontrada")
                                viewModel.setOpenErrorDialog(true)
                            }
                        }

                        override fun onFailure(call: Call<Info>, t: Throwable) {
                            Log.i("Failure", t.message!!)
                            viewModel.setOpenErrorDialog(true)
                            if(t.message != null){
                                viewModel.setErrorMessage(t.message!!)
                            }
                        }

                    })
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Descobrir",
                    style = Typography.bodyLarge,
                    color = colorResource(R.color.white)
                    )

                }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("cities")
            },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Ver cidades selecionadas",
                    style = Typography.bodyLarge,
                    color = colorResource(R.color.white)
                )
                when {
                    openAlertDialog -> {
                        AlertDialog(
                            icon = {
                                   Icon(painter = painterResource(id = R.drawable.question_mark), contentDescription = "Question mark", tint = colorResource(
                                       id = R.color.blue
                                   ))
                            },
                            title = {
                                Text(text = "A cidade buscada é: " + location?.name + ", " + location?.country + "?",
                                    style = Typography.bodyLarge,
                                    color = colorResource(R.color.black),
                                    textAlign = TextAlign.Center
                                    )
                            },
                            onDismissRequest = {
                                viewModel.setOpenAlertDialog(false)
                            },
                            confirmButton = {
                                Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                                    onClick = {
                                        viewModel.setOpenAlertDialog(false)
                                        navController.navigate("info/${location?.name}")
                                    }) {
                                    Text(text = "Sim",
                                        style = Typography.bodyLarge,
                                        color = colorResource(R.color.white)
                                    )
                                }
                            },
                            dismissButton = {
                                Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red)),onClick = {viewModel.setOpenAlertDialog(false)}) {
                                    Text(text = "Não",
                                        style = Typography.bodyLarge,
                                        color = colorResource(R.color.white)
                                    )
                                }
                            },
                        )
                    }
                }
                when {
                    openErrorDialog -> {
                        AlertDialog(
                            icon = {
                                Icon(painter = painterResource(id = R.drawable.error), contentDescription = "Question mark", tint = colorResource(
                                    id = R.color.red
                                ))
                            },
                            title = {
                                Text(text = "Erro: $errorMessage",
                                    style = Typography.bodyLarge,
                                    color = colorResource(R.color.black),
                                    textAlign = TextAlign.Center
                                )
                            },
                            onDismissRequest = {
                                viewModel.setOpenErrorDialog(false)
                            },
                            confirmButton = {
                                Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red)),onClick = {viewModel.setOpenErrorDialog(false)}) {
                                    Text(text = "Tentar Novamente",
                                        style = Typography.bodyLarge,
                                        color = colorResource(R.color.white)
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}