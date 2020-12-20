package mx.unam.ciencias.heuristicas.gap
import mx.unam.ciencias.heuristicas.DAO
import mx.unam.ciencias.heuristicas.modelo.Trabajador
import mx.unam.ciencias.heuristicas.modelo.Tarea
import java.util.*
import kotlin.collections.ArrayList

/**
 * Declaramos nuestra clase Grafica que representará los distintos métodos de nuestra grafica del problema
 * @property trabajadores Los trabajadores que realizarán las tareas
 * @property tareas Las tareas que serán asignadas
 * * @constructor Devuelve una gráfica
 */
class Grafica(val trabajadores: ArrayList<Trabajador>, val tareas: ArrayList<Tarea>){
    /** Cantidad de trabajadores del problema*/
    private val numTrabajadores = trabajadores.size
    /** Cantidad de tareas del problema*/
    val numTareas = tareas.size
    /** Cantidad de asignaciones que se realizarán*/
    private val asignacionesTotales = numTrabajadores * numTareas
    /** Matriz que contendrá la capacidad necesaria de un trabajador i para realizar una tarea j*/
    private val tablaCapacidades =  creaTablaCapacidades()
    /** Matriz que contendrá el costo de un trabajador i para realizar una tarea j*/
    private val tablaCostos = creaTablaCostos()
    /** Tabla con las capacidades máximas de cada trabajador*/
    private val tablaTrabajadoresCap = creaTablaTrabajadoresCap()

    /**
     * Función que crea la matriz de capacidades, donde se muestra la capacidad necesaria
     * de un trabajador i para realizar una tarea j
     * Hay que comentar que debido a que las bases de datos los ids inician en 1 y no 0, para ahorrar espacio
     * se consideran los ids con -1
     * @return La matriz de capacidades
     */
    private fun creaTablaCapacidades():Array<DoubleArray>{
        val tablaCapacidades = Array(numTrabajadores) { DoubleArray(numTareas) }
        val capacidades = DAO().getCapacidades()
        for (i in 0 until asignacionesTotales){
            val idTarea = capacidades[i].first.first -1
            val idTrabajador = capacidades[i].first.second -1
            tablaCapacidades[idTrabajador][idTarea] = capacidades[i].second
        }
        return tablaCapacidades
    }

    /**
     * Función que crea un arreglo con las capacidades máximas de cada trabajador
     * Los ids se están tomando -1, es decir la capacidad del trabajador 3, está en la posición 2
     * @return La tabla de capacidades
     */
    private fun creaTablaTrabajadoresCap():Array<Double>{
        val tablaTrabajadoresCap = Array(numTrabajadores){0.0}
        for (i in 0 until numTrabajadores){
            tablaTrabajadoresCap[i] = trabajadores[i].capacidad
        }
        return tablaTrabajadoresCap
    }

    /**
     * Función que crea la matriz de costos, donde se muestra el costo
     * de un trabajador i para realizar una tarea j
     * Hay que comentar que debido a que las bases de datos los ids inician en 1 y no 0, para ahorrar espacio
     * se consideran los ids con -1
     * @return La matriz de costos
     */
    private fun creaTablaCostos():Array<DoubleArray>{
        val tablaCostos = Array(numTrabajadores) { DoubleArray(numTareas) }
        val costos = DAO().getCostos()
        for (i in 0 until asignacionesTotales){
            val idTarea = costos[i].first.first -1
            val idTrabajador = costos[i].first.second -1
            tablaCostos[idTrabajador][idTarea] = costos[i].second
        }
        return tablaCostos
    }

    /**
     * Función que calcula el costo total de una asignación de tareas
     * @return El costo de la asignación
     */
    fun calculaCosto(asignaciones: Array<Int>):Double{
        var costoTotal = 0.0
        for (idTarea in asignaciones.indices){
            val idTrabajador = asignaciones[idTarea]
            val costoAux= tablaCostos[idTrabajador][idTarea]
            costoTotal += costoAux
        }
        return costoTotal
    }

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun revisaCapacidad(id_trabajador: Int, asignaciones: Array<Int>): Boolean {
        var capacidad = 0.0
        for(i in asignaciones.indices) {
            if(asignaciones[i] == id_trabajador) {
                capacidad += tablaCapacidades[id_trabajador][i]
            }
        }
        val capacidadTrabajador = tablaTrabajadoresCap[id_trabajador]
        return capacidad <= capacidadTrabajador
    }

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun esFactible(asignaciones: Array<Int>): Boolean{
        for(element in asignaciones){
            val idTrabajador = element
            //Revisamos que el trabajador no supere su capacidad
            if (!revisaCapacidad(idTrabajador, asignaciones)){
                return false
            }
        }
        return true
    }

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun calculaMejorTrabajador(id_tarea:Int): Array<Int>{
        val mejoresTrabajadores = ArrayList<Pair<Int, Double>>()
        val mejoresTrabajadoresIds = Array<Int>(numTrabajadores){0}
        for(i in 0 until numTrabajadores){
            val valorCosto = tablaCostos[i][id_tarea]
            val par = Pair(i, valorCosto)
            mejoresTrabajadores.add(par)
        }
        mejoresTrabajadores.sortBy { it.second }
        for(i in 0 until mejoresTrabajadores.size){
            mejoresTrabajadoresIds[i] = mejoresTrabajadores[i].first
        }
        return mejoresTrabajadoresIds
    }

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun generaAsignacionInicial(): Array<Int>{
        val asignacionInicial = Array<Int>(numTareas){0}
        var esAdmisible = false
        val tareasIds = Array<Int>(numTareas){it}
        for(i in 0 until 1000){
            esAdmisible = true
            val caps = Array(numTrabajadores){0.0}
            //Escogemos tareas aleatoriamente
            Collections.shuffle(tareasIds.asList())
            for(a in tareasIds.indices){
                val tarea = tareasIds[a]
                var minTrabajador = -1
                val mejoresCostos = calculaMejorTrabajador(tarea)
                for(b in mejoresCostos){
                    if (caps[b] + tablaCapacidades[b][tarea] <= tablaTrabajadoresCap[b]){
                        minTrabajador = b
                        break
                    }
                }
                if (minTrabajador == -1){
                    esAdmisible = false
                    break
                }
                else{
                    asignacionInicial[tarea] = minTrabajador
                    caps[minTrabajador] = caps[minTrabajador] + tablaCapacidades[minTrabajador][tarea]
                }
            }
            if (esAdmisible)
                break
        }
        if(!esAdmisible){
            for(i in 0 until numTareas){
                asignacionInicial[i] = (0 until numTrabajadores).random()
            }
        }
        return asignacionInicial
    }
}