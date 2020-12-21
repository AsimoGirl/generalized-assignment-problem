package mx.unam.ciencias.heuristicas.modelo
/**
 * Declaramos nuestra clase Tarea que almacenará la información de las tareas
 * de nuestro problema.
 *
 * @property id El identificador de la tarea.
 * @property nombre El nombre de la tarea.
 * @constructor Crea un objeto tarea
 */
data class Tarea(
    val id: Int,
    val nombre: String,
)