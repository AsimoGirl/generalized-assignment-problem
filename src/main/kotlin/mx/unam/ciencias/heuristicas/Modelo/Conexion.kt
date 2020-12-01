package mx.unam.ciencias.heuristicas.modelo
/**
 * Declaramos nuestra clase ciudad que almacenará la información de las distancias entre
 * ciudades de nuestro problema.
 *
 * @property idCity1 El identificador de la primera ciudad.
 * @property idCity2 El identificador de la segunda ciudad.
 * @property distance El país donde se encuentra la ciudad.
 * @constructor Crea un objeto conexion
 */
data class Conexion(
    val idCity1: Int,
    val idCity2: Int,
    val distance: Double
)