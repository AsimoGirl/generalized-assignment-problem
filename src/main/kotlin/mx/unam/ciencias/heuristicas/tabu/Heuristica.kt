package mx.unam.ciencias.heuristicas.gap

import mx.unam.ciencias.heuristicas.tsp.Grafica
import mx.unam.ciencias.heuristicas.tsp.Solucion
import kotlin.math.abs

/**
 * Declaramos nuestra clase Heuristica que realizará el recocido simulado con aceptación por umbrales
 *
 * @property g La grafica en la que estaremos trabajando
 * @property solucionInicial La primera posible solucion
 */
class Heuristica(g: Grafica, solucionInicial: Solucion) {
    /** Gráfica que estaremos utilizando*/
    private val g = g
    /** Variable que representará la solución que se tenga en el momento*/
    private var solucionActual = solucionInicial
    /** Variable que irá guardando la mejor solución del sistema */
    private var mejorSolucionActual: Solucion = solucionInicial


}