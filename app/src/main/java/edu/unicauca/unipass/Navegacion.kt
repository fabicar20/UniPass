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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState



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

    // ViewModel compartido para las ofertas
    val viewModel: OfertasViewModel = viewModel()

    // ViewModel compartido para autenticación
    val authViewModel: AuthViewModel = viewModel()

    val perfilViewModel: PerfilViewModel = viewModel()

    // Cada vez que cambia la sesión, el ViewModel de ofertas cambia de usuario
    LaunchedEffect(authViewModel.sesion?.id) {
        viewModel.fijarUsuario(authViewModel.sesion?.id)
        perfilViewModel.fijarUsuario(authViewModel.sesion?.id)
    }

    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    // La barra inferior se ve en las 5 pestañas
    // y no aparece en login, registro, acerca ni detalle.
    val mostrarBarra = destinosBarra.any {
        it.ruta == rutaActual
    }

    Scaffold(
        bottomBar = {
            if (mostrarBarra) {
                NavigationBar {

                    destinosBarra.forEach { destino ->

                        NavigationBarItem(
                            selected = rutaActual == destino.ruta,

                            onClick = {
                                navController.navigate(destino.ruta) {
                                    popUpTo("inicio") {
                                        saveState = true
                                    }

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

                            label = {
                                Text(destino.etiqueta)
                            }
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

            // ---------- LOGIN ----------

            composable("login") {

                LoginScreen(
                    authViewModel = authViewModel,

                    onLoginExitoso = {
                        navController.navigate("inicio") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    },

                    onCrearCuenta = {
                        authViewModel.limpiarError()
                        navController.navigate("registro")
                    }
                )
            }

            // ---------- REGISTRO ----------

            composable("registro") {

                PantallaRegistro(
                    authViewModel = authViewModel,

                    onRegistroExitoso = {
                        navController.navigate("inicio") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    },

                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            // ---------- INICIO ----------

            composable("inicio") {

                PantallaInicio(
                    viewModel = viewModel,
                    nombre = authViewModel.sesion?.nombre ?: "",

                    onOfertaClick = { id ->
                        navController.navigate("detalle/$id")
                    }
                )
            }

            // ---------- BUSCAR ----------

            composable("buscar") {

                PantallaBusqueda(
                    viewModel = viewModel,

                    onOfertaClick = { id ->
                        navController.navigate("detalle/$id")
                    }
                )
            }

            // ---------- POSTULACIONES ----------

            composable("postulaciones") {

                PantallaPostulaciones(
                    viewModel = viewModel,

                    onOfertaClick = { id ->
                        navController.navigate("detalle/$id")
                    }
                )
            }

            // ---------- GUARDADOS ----------

            composable("guardados") {

                PantallaGuardados(
                    viewModel = viewModel,

                    onOfertaClick = { id ->
                        navController.navigate("detalle/$id")
                    }
                )
            }

            // ---------- PERFIL ----------

            composable("perfil") {
                val perfil by perfilViewModel.perfil.collectAsState()

                PantallaPerfil(
                    nombre = authViewModel.sesion?.nombre ?: "",
                    correo = authViewModel.sesion?.correo ?: "",
                    perfil = perfil,
                    onEditar = { navController.navigate("editar_perfil") },
                    onAcerca = { navController.navigate("acerca") },
                    onCerrarSesion = {
                        authViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable("editar_perfil") {
                val perfil by perfilViewModel.perfil.collectAsState()

                PantallaEditarPerfil(
                    perfil = perfil,
                    onGuardar = { universidad, carrera, semestre, habilidades, idiomas, sobreMi ->
                        perfilViewModel.guardar(
                            universidad, carrera, semestre, habilidades, idiomas, sobreMi
                        )
                        navController.popBackStack()
                    },
                    onCancelar = { navController.popBackStack() }
                )
            }

            // ---------- ACERCA DE UNIPASS ----------

            composable("acerca") {

                PantallaAcerca(
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }

            // ---------- DETALLE ----------

            composable(
                route = "detalle/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { entrada ->

                val id = entrada.arguments?.getInt("id") ?: 0

                PantallaDetalle(
                    id = id,
                    viewModel = viewModel,

                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}