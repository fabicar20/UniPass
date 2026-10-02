
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navegacion() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onIniciarSesion = {
                    navController.navigate("inicio")
                }
            )
        }

        composable("inicio") {
            PantallaPrincipal(navController)
        }

        composable("detalle") {
            PantallaDetalle(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("buscar") {
            PantallaBusqueda(
                onOfertaClick = {
                    navController.navigate("detalle")
                }
            )
        }

        composable("postulaciones") {
            PantallaPostulaciones()
        }

        composable("guardados") {
            PantallaGuardados()
        }

        composable("perfil") {
            PantallaPerfil()
        }
    }
}

@Composable
fun PantallaPrincipal(navController: NavHostController) {

    Scaffold(
        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = {
                        navController.navigate("inicio")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio"
                        )
                    },
                    label = {
                        Text("Inicio")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("buscar")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    label = {
                        Text("Buscar")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("postulaciones")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Postulaciones"
                        )
                    },
                    label = {
                        Text("Postulaciones")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("guardados")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Guardados"
                        )
                    },
                    label = {
                        Text("Guardados")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("perfil")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil"
                        )
                    },
                    label = {
                        Text("Perfil")
                    }
                )
            }
        }
    ) { paddingValues ->

        PantallaInicio(
            modifier = Modifier.padding(paddingValues),
            onOfertaClick = {
                navController.navigate("detalle")
            }
        )
    }
}

@Composable
fun PantallaSimple(nombrePantalla: String) {

    Text(
        text = nombrePantalla
    )
}