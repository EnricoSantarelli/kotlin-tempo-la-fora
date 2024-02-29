package br.com.fiap.tempolafora.info

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.tempolafora.R
import br.com.fiap.tempolafora.models.Info
import br.com.fiap.tempolafora.service.RetrofitFactory
import br.com.fiap.tempolafora.sqlite.model.LocationSQL
import br.com.fiap.tempolafora.sqlite.repository.LocationRepository
import br.com.fiap.tempolafora.ui.theme.Typography
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun InfoView(viewModel: InfoViewModel, cityName: String?, navController: NavController) {
    val context = LocalContext.current
    val repo = LocationRepository(context)
    val weather by viewModel.weather.observeAsState()
    val location by viewModel.location.observeAsState()
    val wasCityAdd by viewModel.wasCityAdd.observeAsState(initial = false)
    val cityAlredyAdded by viewModel.cityAlredyAdded.observeAsState(initial = false)

    val call = RetrofitFactory().getWeatherService().getWeather(cityName!!)
    call.enqueue(object : Callback<Info> {
        override fun onResponse(call: Call<Info>, response: Response<Info>) {
            Log.i("Response", response.body().toString())
            if (response.body()?.location != null && response.body()?.weather != null) {
                viewModel.setWeather(response.body()?.weather)
                viewModel.setLocation(response.body()?.location)
            }
        }

        override fun onFailure(call: Call<Info>, t: Throwable) {
            Log.i("Failure", t.message!!)
        }
    })



    Box {
        Column {
            when {
                weather != null -> {
                    Log.i("icon", "https:${weather!!.condition.icon}")
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(96.dp))
                        AsyncImage(model = "https:${weather!!.condition.icon}", contentDescription = "Weather condition icon", Modifier.size(112.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${weather!!.temperature}°", textAlign = TextAlign.Center, style = Typography.titleLarge, color = colorResource(
                            R.color.blue)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = cityName,
                            style = Typography.bodyLarge,
                            color = colorResource(R.color.black))
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(text = weather!!.condition.text,
                            style = Typography.bodyLarge,
                            color = colorResource(R.color.black))
                        Spacer(modifier = Modifier.height(112.dp))
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(painter = painterResource(id = R.drawable.thermometer), contentDescription = "Thermal sensation", Modifier.size(36.dp), colorResource(
                                    id = R.color.blue
                                ))
                                Text(text = "${weather!!.feelsTemperature}°",
                                    style = Typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                    color = colorResource(R.color.black))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(painter = painterResource(id = R.drawable.watch), contentDescription = "Thermal sensation", Modifier.size(36.dp), colorResource(
                                    id = R.color.blue
                                ))
                                Text(text = if(weather!!.isDay == 0){"Noite"} else { "Dia"},
                                    style = Typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                    color = colorResource(R.color.black))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(painter = painterResource(id = R.drawable.water), contentDescription = "Thermal sensation", Modifier.size(36.dp), colorResource(
                                    id = R.color.blue
                                ))
                                Text(text = "${weather!!.humidity}°",
                                    style = Typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                    color = colorResource(R.color.black))
                            }

                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(onClick = {
                            val cities : List<LocationSQL> = repo.getAllLocations()
                            Log.i("CIDADES", cities.toString())
                            Log.i("CIDADES", location!!.name)
                            if(!cities.any{it.city == location!!.name}){
                                repo.addLocation(LocationSQL(city = location!!.name, country = location!!.country))
                                viewModel.setWasCityAdd(true)
                            } else {
                                viewModel.setCityAlreadyAdded(true)
                            }
                    },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                            modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Adicionar cidade à lista",
                                style = Typography.bodyLarge,
                                color = colorResource(R.color.white)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            navController.navigate("home")
                        },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                            modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Voltar",
                                style = Typography.bodyLarge,
                                color = colorResource(R.color.white)
                            )
                        }
                    }
                    when {
                        wasCityAdd -> {
                            AlertDialog(
                                icon = {
                                    Icon(painter = painterResource(id = R.drawable.check), contentDescription = "Question mark", tint = colorResource(
                                        id = R.color.green
                                    ))
                                },
                                title = {
                                    Text(text = "Cidade adicionada à lista",
                                        style = Typography.bodyLarge,
                                        color = colorResource(R.color.black),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onDismissRequest = {
                                    viewModel.setWasCityAdd(false)
                                },
                                confirmButton = {
                                    Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),onClick = {viewModel.setWasCityAdd(false)}) {
                                        Text(text = "Ok",
                                            style = Typography.bodyLarge,
                                            color = colorResource(R.color.white)
                                        )
                                    }
                                },
                            )
                        }
                    }
                    when {
                        cityAlredyAdded -> {
                            AlertDialog(
                                icon = {
                                    Icon(painter = painterResource(id = R.drawable.error), contentDescription = "Question mark", tint = colorResource(
                                        id = R.color.red
                                    ))
                                },
                                title = {
                                    Text(text = "Cidade repetida",
                                        style = Typography.bodyLarge,
                                        color = colorResource(R.color.black),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onDismissRequest = {
                                    viewModel.setCityAlreadyAdded(false)
                                },
                                confirmButton = {
                                    Button(modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red)),onClick = {viewModel.setCityAlreadyAdded(false)}) {
                                        Text(text = "Ok",
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
}}
