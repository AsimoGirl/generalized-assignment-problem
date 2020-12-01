package mx.unam.ciencias.heuristicas.modelo
/**
 * Declaramos nuestra clase ciudad que almacenará la información de las ciudades de nuestro problema.
 *
 * @property id El identificador de la ciudad.
 * @property name El nombre de la ciudad.
 * @property country El país donde se encuentra la ciudad.
 * @property population La población de la ciudad.
 * @property latitude La latitud de la ciudad.
 * @property longitude La longitud de la ciudad.
 * @constructor Crea un objeto ciudad
 */
data class Ciudad(
    val id: Int,
    val name: String,
    val country: String,
    val population: Int,
    val latitude: Double,
    val longitude: Double
)