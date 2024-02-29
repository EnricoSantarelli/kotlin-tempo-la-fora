package br.com.fiap.tempolafora.cities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.tempolafora.R
import br.com.fiap.tempolafora.sqlite.repository.LocationRepository
import br.com.fiap.tempolafora.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesView(viewModel: CitiesViewModel, navController: NavController) {
    val cities = viewModel.cities.observeAsState()
    val context = LocalContext.current
    val repo = LocationRepository(context)

    viewModel.setCities(repo.getAllLocations())
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Minha lista de cidades", style = Typography.titleLarge, color = colorResource(
                    R.color.blue
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            if(cities.value?.size == 0) {
                Text(
                    text = "Não há cidades selecionadas", style = Typography.titleLarge, color = colorResource(
                        R.color.black
                    ))
            } else {
                cities.value?.forEach { location ->
                Card(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.gray),
                    contentColor = colorResource(id = R.color.black)
                ),
                    onClick = {
                        navController.navigate("info/${location.city}")
                    }) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp,vertical = 16.dp)) {
                        Text(
                            text = location.city, style = Typography.titleLarge, color = colorResource(
                                R.color.black
                            )
                        )
                        Text(
                            text = location.country,
                            style = Typography.bodyLarge,
                            color = colorResource(
                                R.color.black
                            )
                        )

                    }
                }
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
            Spacer(modifier = Modifier.height(96.dp))
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

    }
}