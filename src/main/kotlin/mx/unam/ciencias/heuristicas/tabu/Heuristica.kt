package mx.unam.ciencias.heuristicas.tabu

import mx.unam.ciencias.heuristicas.gap.Grafica
import mx.unam.ciencias.heuristicas.gap.Solucion
import kotlin.math.abs

/**
 * Declaramos nuestra clase Heuristica que realizará el recocido simulado con aceptación por umbrales
 *
 * @property g La grafica en la que estaremos trabajando
 * @property solucionInicial La primera posible solucion
 */
class Heuristica(g: Grafica, solucionInicial: Solucion) {
    /** Gráfica que estaremos utilizando*/
    private val grafica = g
    /** Variable que representará la solución que se tenga en el momento*/
    private var solucionActual = solucionInicial
    /** Variable que irá guardando la mejor solución del sistema */
    private var mejorSolucionActual: Solucion = solucionInicial



    fun tabu(){

    }


    /**
     * Función que regresa el string de la ruta de la mejor solución del sistema
     * @return La lista de ids de la mejor solución del sistema
     */
    fun asignacionString(): String = mejorSolucionActual.toString()

    /**
     * Función que regresa el costo de la mejor solución del sistema
     * @return El costo de la mejor solución del sistema
     */
    fun evaluacion(): Double = mejorSolucionActual.costo

    /**
     * Función que regresa si la mejor solución del sistema es factible o no
     * @return Un booleano que nos dice si es factible o no la solución
     */
    fun esFactible(): Boolean = mejorSolucionActual.factible

}