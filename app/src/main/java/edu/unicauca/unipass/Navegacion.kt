
package edu.unicauca.unipass

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private data class DestinoBarra(
    val ruta: String,
    val etiqueta: String,
    val icono: ImageVector
)

private val destinosBarra = listOf(
    DestinoBarra("inicio", "Inicio", Icons.Default.Home),
    DestinoBarra("buscar", "Buscar", Icons.Default.Search),
    DestinoBarra("postulaciones", "Postulaciones", Icons.Default.Description),
    DestinoBarra("guardados", "Guardados", Icons.Default.Bookmark),
    DestinoBarra("perfil", "Perfil", Icons.Default.Person)
)

@Composable
fun Navegacion() {

    val navController = rememberNavController()

    // Un solo ViewModel compartido por todas las pantallas
    val viewModel: OfertasViewModel = viewModel()

    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    // La barra inferior se ve en las 5 pestañas (no en login ni en detalle)
    val mostrarBarra = destinosBarra.any { it.ruta == rutaActual }

    Scaffold(
        bottomBar = {
            if (mostrarBarra) {
                NavigationBar {
                    destinosBarra.forEach { destino ->
                        NavigationBarItem(
                            selected = rutaActual == destino.ruta,
                            onClick = {
                                navController.navigate(destino.ruta) {
                                    popUpTo("inicio") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = destino.icono,
                                    contentDescription = destino.etiqueta
                                )
                            },
                            label = { Text(destino.etiqueta) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("login") {
                LoginScreen(
                    onIniciarSesion = {
                        navController.navigate("inicio") {
                            // Quita el login del historial: "atrás" ya no vuelve a él
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            composable("inicio") {
                PantallaInicio(
                    viewModel = viewModel,
                    onOfertaClick = { id -> navController.navigate("detalle/$id") }
                )
            }

            composable("buscar") {
                PantallaBusqueda(
                    viewModel = viewModel,
                    onOfertaClick = { id -> navController.navigate("detalle/$id") }
                )
            }

            composable("postulaciones") {
                PantallaPostulaciones(
                    viewModel = viewModel,
                    onOfertaClick = { id -> navController.navigate("detalle/$id") }
                )
            }

            composable("guardados") {
                PantallaGuardados(
                    viewModel = viewModel,
                    onOfertaClick = { id -> navController.navigate("detalle/$id") }
                )
            }

            composable("perfil") {
                PantallaPerfil()
            }

            composable(
                route = "detalle/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entrada ->
                val id = entrada.arguments?.getInt("id") ?: 0
                PantallaDetalle(
                    id = id,
                    viewModel = viewModel,
                    onVolver = { navController.popBackStack() }
                )
            }
        }
    }
}