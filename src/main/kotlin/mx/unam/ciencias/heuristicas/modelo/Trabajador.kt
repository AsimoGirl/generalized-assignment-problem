package mx.unam.ciencias.heuristicas.modelo
/**
 * Declaramos nuestra clase Trabajador que almacenará la información de los trabajadores de nuestro problema.
 *
 * @property id El identificador del trabajador.
 * @property nombre El nombre del trabajador.
 * @property capacidad La capacidad que tiene el trabajador.
 * @constructor Crea un objeto trabajador
 */
data class Trabajador(
    val id: Int,
    val nombre: String,
    val capacidad: Double,
)