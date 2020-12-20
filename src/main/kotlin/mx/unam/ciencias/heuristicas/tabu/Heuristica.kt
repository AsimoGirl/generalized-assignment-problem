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

    private val numtareas = g.numTareas
    /** Variable que representará la solución que se tenga en el momento*/
    private var solucionActual = solucionInicial
    /** Variable que irá guardando la mejor solución del sistema */
    private var mejorSolucionActual: Solucion = solucionInicial

    private var maximoIteraciones = 1000

    private var maximoListaTabu = 100

    fun buscaVecino(solucionesVecinas: ArrayList<Solucion>, solucionesTabu: ArrayList<Solucion>): Solucion{
        //Eliminamos todos los resultados tabú de la lista
        solucionesVecinas.removeAll {
            it in solucionesTabu
        }
        //Ordena los vecinos
        solucionesVecinas.sortedWith(compareBy({ it.costo }, { it.costo }))
        //Devuelve al vecino con el menor valor
        return solucionesVecinas[0]
    }

    fun generaVecinos(solucion: Solucion): ArrayList<Solucion>{
        val vecindad =  ArrayList<Solucion>()
        for(i in 0 until numtareas) {
            var vecino1 = solucion.generaVecinoSwap()
            var vecino2 = solucion.generaVecinoShift()
            vecindad.add(vecino1)
            vecindad.add(vecino2)
        }
        return vecindad
    }

    fun tabu(){
        var iteracion = 0
        var listaTabu = ArrayList<Solucion>()
        listaTabu.add(solucionActual)
        while(iteracion != maximoIteraciones){
            var vecindad = generaVecinos(solucionActual)
            var mejorVecino = buscaVecino(vecindad, listaTabu)
            if(mejorVecino.costo < solucionActual.costo){
                mejorSolucionActual = mejorVecino
            }
            listaTabu.add(solucionActual)
            while(listaTabu.size > maximoListaTabu){
                listaTabu.removeAt(0)
            }
            iteracion ++
        }
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